package kr.co.solbipos.application.com.bkmk.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.bkmk.service.BkmkService;
import kr.co.solbipos.application.com.bkmk.service.BkmkVO;
import kr.co.solbipos.application.com.fixing.service.FixingService;
import kr.co.solbipos.application.com.fixing.service.FixingVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
* @Class Name : BizNoServiceImpl.java
* @Description : 어플리케이션 > 공통 > 즐겨찾기
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  노현수      최초생성
*
* @author 솔비포스 차세대개발실 노현수
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/application/com")
public class BkmkController {

    private final BkmkService bkmkService;
    private final FixingService fixingService;
    private final SessionService sessionService;
    private final CmmMenuService cmmMenuService;

    /** Constructor Injection */
    @Autowired public BkmkController(BkmkService bkmkService, FixingService fixingService,
        SessionService sessionService, CmmMenuService cmmMenuService) {
        this.bkmkService = bkmkService;
        this.fixingService = fixingService;
        this.sessionService = sessionService;
        this.cmmMenuService = cmmMenuService;
    }

    /**
     * 즐겨찾기 관리 조회
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bkmk/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bkmkList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> menuData = new HashMap<String, Object>();
        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 전체메뉴, 즐겨찾기, 고정메뉴 순서로 로드하여 List 에 Add
        menuData.put("menuData", sessionInfoVO.getMenuData());
        menuData.put("bkmkData", sessionInfoVO.getBkmkMenuData());
        menuData.put("fixData", sessionInfoVO.getFixedMenuData());

        return returnJson(Status.OK, menuData);
    }

    /**
     * 즐겨찾기 관리 저장
     *
     * @param params HashMap<String, Object>
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return
     */
    @RequestMapping(value = "/bkmk/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveBkmk(@RequestBody HashMap<String, Object> params, HttpServletRequest request,
            HttpServletResponse response) {
        List<String> result = new ArrayList<String>();

        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 즐겨찾기 데이터
        BkmkVO bkmkVO = new BkmkVO();
        if ( params.get("favorMenuCodes") != null ) {
            bkmkVO.setResrceCds(params.get("favorMenuCodes").toString().split(","));
        }
        // 즐겨찾기 저장
        bkmkService.saveBkmk(bkmkVO, sessionInfoVO.getUserId());
        // 고정메뉴 데이터
        FixingVO fixingVO = new FixingVO();
        if ( params.get("fixMenuCodes") != null ) {
            fixingVO.setResrceCds(params.get("fixMenuCodes").toString().split(","));
        }
        // 고정메뉴 저장
        fixingService.saveFixing(fixingVO, sessionInfoVO.getUserId());

        // 즐겨찾기&고정메뉴 저장 후 해당 내용 세션에 갱신
        // 사용자의 메뉴 리스트 Set : 권한포함
        sessionInfoVO.setMenuData(cmmMenuService.getUserMenuList(sessionInfoVO));
        // 즐겨찾기 메뉴 리스트 Set
        sessionInfoVO.setBkmkMenuData(cmmMenuService.getBkmkMenuList(sessionInfoVO));
        // 고정 메뉴 리스트 Set
        sessionInfoVO.setFixedMenuData(cmmMenuService.getFixedMenuList(sessionInfoVO));

        // redis에 세션 세팅
        sessionService.setSessionInfo(sessionInfoVO);
        // 메뉴Data Return
        result.add(StringUtil.convertToJson(sessionInfoVO.getBkmkMenuData()));
        result.add(StringUtil.convertToJson(sessionInfoVO.getFixedMenuData()));

        return returnJson(Status.OK, result);
    }

}
