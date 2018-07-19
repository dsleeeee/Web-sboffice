package kr.co.solbipos.application.com.bkmk.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.com.bkmk.service.BkmkService;
import kr.co.solbipos.application.com.bkmk.service.BkmkVO;
import kr.co.solbipos.application.com.fixing.service.FixingService;
import kr.co.solbipos.application.com.fixing.service.FixingVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

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
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Controller
@RequestMapping(value = "/application/com")
public class BkmkController {

    @Autowired
    BkmkService bkmkService;
    @Autowired
    FixingService fixingService;
    @Autowired
    SessionService sessionService;
    @Autowired
    CmmMenuService cmmMenuService;

    /**
     * 즐겨찾기 관리 조회
     *
     * @param menuType
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bkmk/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bkmkList(HttpServletRequest request, HttpServletResponse response) {
        List<String> menuData = new ArrayList<String>();
        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 전체메뉴, 즐겨찾기, 고정메뉴 순서로 로드하여 List 에 Add
        menuData.add(sessionInfoVO.getMenuData());
        menuData.add(sessionInfoVO.getBkmkData());
        menuData.add(sessionInfoVO.getFixData());

        return returnJson(Status.OK, menuData);
    }

    /**
     * 즐겨찾기 관리 저장
     *
     * @param params
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "//bkmk/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result bkmkSave(@RequestParam HashMap<String, Object> params, HttpServletRequest request,
            HttpServletResponse response) {
        List<String> result = new ArrayList<String>();

        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 즐겨찾기 데이터
        BkmkVO bkmkVO = new BkmkVO();
        bkmkVO.setResrceCds(params.get("favorMenuCodes").toString().split(","));
        // 즐겨찾기 저장
        bkmkService.saveBkmk(bkmkVO, sessionInfoVO.getUserId());
        // 고정메뉴 데이터
        FixingVO fixingVO = new FixingVO();
        fixingVO.setResrceCds(params.get("fixMenuCodes").toString().split(","));
        // 고정메뉴 저장
        fixingService.saveFixing(fixingVO, sessionInfoVO.getUserId());

        // 즐겨찾기&고정메뉴 저장 후 해당 내용 세션에 갱신
        // 고정 메뉴 리스트 조회후 set
        sessionInfoVO.setFixMenu(cmmMenuService.selectFixingMenu(sessionInfoVO));
        // 즐겨찾기 메뉴 리스트 조회후 set
        sessionInfoVO.setBkmkMenu(cmmMenuService.selectBkmkMenu(sessionInfoVO));
        // 즐겨찾기메뉴 json 형태로 set
        sessionInfoVO.setBkmkData(convertToJson(cmmMenuService.makeMenu(sessionInfoVO, "F")));
        // 고정 메뉴 json 형태로 set
        sessionInfoVO.setFixData(convertToJson(sessionInfoVO.getFixMenu()));
        // redis에 세션 세팅
        sessionService.setSessionInfo(sessionInfoVO);
        // 메뉴Data Return
        result.add(sessionInfoVO.getBkmkData());
        result.add(sessionInfoVO.getFixData());

        return returnJson(Status.OK, result);
    }

}
