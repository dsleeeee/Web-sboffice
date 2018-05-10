package kr.co.solbipos.application.controller.com;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.domain.com.BkmkVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.service.com.BkmkService;

/**
 * 즐겨찾기 관리
 *
 * @author 노현수
 */
@Controller
@RequestMapping( value = "/com/bkmk" )
public class BkmkController {

    @Autowired
    SessionService sessionService;
    @Autowired
    BkmkService bkmkService;
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
    @RequestMapping( value = "list.sb", method = RequestMethod.POST )
    @ResponseBody
    public Result bkmkList( HttpServletRequest request, HttpServletResponse response ) {
        List<String> menuData = new ArrayList<String>();
        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo( request );
        // 전체메뉴, 즐겨찾기, 고정메뉴 순서로 로드하여 List 에 Add
        menuData.add( sessionInfoVO.getMenuData() );
        menuData.add( sessionInfoVO.getBkmkData() );
        menuData.add( sessionInfoVO.getFixData() );

        return returnJson(Status.OK, menuData);
    }

    /**
     * 즐겨찾기 관리 저장
     *
     * @param bkmkVO
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "regist.sb", method = RequestMethod.POST )
    @ResponseBody
    public Result bkmkSave( BkmkVO bkmkVO, HttpServletRequest request, HttpServletResponse response ) {
        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo( request );
        // 즐겨찾기 등록
        int result = bkmkService.saveBkmk( bkmkVO, sessionInfoVO.getUserId() );

        return returnJson(Status.OK, result);
    }

    /**
     * 즐겨찾기 관리 세션내용 갱신
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping( value = "refresh.sb", method = RequestMethod.POST )
    @ResponseBody
    public Result bkmkRefresh( HttpServletRequest request, HttpServletResponse response ) {
        List<String> menuData = new ArrayList<String>();
        // 세션 가져오기
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo( request );
        // 고정 메뉴 리스트 저장
        sessionInfoVO.setFixMenu( cmmMenuService.selectFixingMenu( sessionInfoVO ) );
        // 즐겨찾기 메뉴 리스트 저장
        sessionInfoVO.setBkmkMenu( cmmMenuService.selectBkmkMenu( sessionInfoVO ) );
        // 즐겨찾기메뉴 조회 (리스트)
        sessionInfoVO.setBkmkData( convertToJson( cmmMenuService.makeMenu( sessionInfoVO, "F" ) ) );
        // 고정 메뉴 조회 (리스트)
        sessionInfoVO.setFixData( convertToJson(sessionInfoVO.getFixMenu()) );
        // redis에 세션 세팅
        sessionService.setSessionInfo( sessionInfoVO );

        menuData.add( sessionInfoVO.getBkmkData() );
        menuData.add( sessionInfoVO.getFixData() );

        return returnJson(Status.OK, menuData);
    }
}
