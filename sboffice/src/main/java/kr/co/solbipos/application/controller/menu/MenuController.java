package kr.co.solbipos.application.controller.menu;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.cmm.CmmMenuService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.utils.grid.ReturnUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 화면 상단에 고정 메뉴 및 히스토리 메뉴 관리 관련 컨트롤러
 * 
 * @author 정용길
 */
@Slf4j
@Controller
@RequestMapping(value = "/menu")
public class MenuController {
    
    // all 2
    
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
    public JsonResult delHistMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        cmmMenuService.deleteHistMenu(menuId, sessionInfo);
        return ReturnUtil.returnJson(Status.OK);
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
    public JsonResult delFixMenu(String menuId, HttpServletRequest request, Model model) {
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        cmmMenuService.deleteFixMenu(menuId, sessionInfo);
        return ReturnUtil.returnJson(Status.OK);
    }
}


