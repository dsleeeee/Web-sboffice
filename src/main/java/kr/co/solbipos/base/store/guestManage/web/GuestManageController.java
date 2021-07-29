package kr.co.solbipos.base.store.guestManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.guestManage.service.GuestManageService;
import kr.co.solbipos.base.store.guestManage.service.GuestManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : GuestManageController.java
 * @Description : 기초관리 > 매장관리 > 객층관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.07.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.07.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/guestManage")
public class GuestManageController {

    private final SessionService sessionService;
    private final GuestManageService guestManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public GuestManageController(SessionService sessionService, GuestManageService guestManageService) {
        this.sessionService = sessionService;
        this.guestManageService = guestManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/guestManage/list.sb", method = RequestMethod.GET)
    public String guestManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/guestManage/guestManage";
    }

    /**
     * 객층관리 - 조회
     *
     * @param guestManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 05.
     */
    @RequestMapping(value = "/guestManage/getGuestManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGuestManageList(GuestManageVO guestManageVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = guestManageService.getGuestManageList(guestManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, guestManageVO);
    }

    /**
     * 객층관리 - 저장
     *
     * @param guestManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 05.
     */
    @RequestMapping(value = "/guestManage/getGuestManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGuestManageSave(@RequestBody GuestManageVO[] guestManageVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = guestManageService.getGuestManageSave(guestManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 객층관리 매장적용 팝업 - 조회
     *
     * @param guestManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 13.
     */
    @RequestMapping(value = "/guestManage/getGuestManageStoreRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGuestManageStoreRegistList(GuestManageVO guestManageVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = guestManageService.getGuestManageStoreRegistList(guestManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, guestManageVO);
    }

    /**
     * 객층관리 매장적용 팝업 - 저장
     *
     * @param guestManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 07. 13.
     */
    @RequestMapping(value = "/guestManage/getGuestManageStoreRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getGuestManageStoreRegistSave(@RequestBody GuestManageVO[] guestManageVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = guestManageService.getGuestManageStoreRegistSave(guestManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}