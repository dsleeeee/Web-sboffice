package kr.co.solbipos.application.controller.menu;

import static kr.co.common.utils.grid.ReturnUtil.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.domain.cmm.StoreVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;

/**
 * 화면 상단에 고정 메뉴 및 히스토리 메뉴 관리 관련 컨트롤러
 *
 * @author 정용길
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {
    @Autowired
    SessionService sessionService;

    @Autowired
    CmmMenuService cmmMenuService;

    /**
     * 히스토리 메뉴 삭제
     *
     * @param menuId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/delHistMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delHistMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cmmMenuService.deleteHistMenu(menuId, sessionInfoVO);
        return returnJson(Status.OK);
    }

    /**
     * 고정 메뉴 삭제
     *
     * @param menuId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/delFixMenu.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result delFixMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        cmmMenuService.deleteFixMenu(menuId, sessionInfoVO);
        return returnJson(Status.OK);
    }

    /**
      * 레이어 팝업 매장 조회
      *
      * @param storeVO
      * @param request
      * @param model
      * @return
      */
    @RequestMapping(value = "/selectStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectStore(StoreVO storeVO, HttpServletRequest request, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 로그인한 유저의 본사 코드를 세팅한다.
        storeVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
        List<StoreVO> list = cmmMenuService.selectStore(storeVO);
        return returnJson(Status.OK, list);
    }

}


