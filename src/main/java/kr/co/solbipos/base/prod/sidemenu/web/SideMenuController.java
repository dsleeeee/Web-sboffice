package kr.co.solbipos.base.prod.sidemenu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrCdVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuAttrClassVO;
import kr.co.solbipos.base.prod.sidemenu.service.SideMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SideMenuController.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/sideMenu")
public class SideMenuController {

    private final SideMenuService sideMenuService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public SideMenuController(SideMenuService sideMenuService, SessionService sessionService) {
        this.sideMenuService = sideMenuService;
        this.sessionService = sessionService;
    }

    /**
     * 사이드메뉴 - 페이지 이동
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return String
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String templateView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/prod/sideMenu/sideMenu";
    }

    /**
     * 사이드메뉴-속성 - 속성분류 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrClassVO SideMenuAttrClassVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrClass/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAttrClassList(HttpServletRequest request, HttpServletResponse response,
        SideMenuAttrClassVO sideMenuAttrClassVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 속성분류 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getAttrClassList(sideMenuAttrClassVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuAttrClassVO);
    }

    /**
     * 사이드메뉴-속성 - 속성분류 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrClassVOs SideMenuAttrClassVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrClass/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAttrClassList(@RequestBody SideMenuAttrClassVO[] sideMenuAttrClassVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveAttrClassList(sideMenuAttrClassVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사이드메뉴-속성 - 속성 목록 조회
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrCdVO SideMenuAttrCdVO
     * @param model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrCd/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAttrCdList(HttpServletRequest request, HttpServletResponse response,
        SideMenuAttrCdVO sideMenuAttrCdVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 속성분류 목록 조회
        List<DefaultMap<String>> list = sideMenuService.getAttrCdList(sideMenuAttrCdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, sideMenuAttrCdVO);
    }

    /**
     * 사이드메뉴-속성 - 속성 목록 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sideMenuAttrCdVOs SideMenuAttrCdVO[]
     * @param model Model
     * @return Result
     * @author 노현수
     * @since 2018. 11. 14.
     */
    @RequestMapping(value = "/attrCd/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveAttrClassList(@RequestBody SideMenuAttrCdVO[] sideMenuAttrCdVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = sideMenuService.saveAttrCdList(sideMenuAttrCdVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
