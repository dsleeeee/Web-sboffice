package kr.co.solbipos.adi.sms.msgManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.msgManage.service.MsgManageService;
import kr.co.solbipos.adi.sms.msgManage.service.MsgManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MsgManageController.java
 * @Description : 부가서비스 > SMS관리 > 메세지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/msgManage")
public class MsgManageController {

    private final SessionService sessionService;
    private final MsgManageService msgManageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MsgManageController(SessionService sessionService, MsgManageService msgManageService) {
        this.sessionService = sessionService;
        this.msgManageService = msgManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/msgManage/list.sb", method = RequestMethod.GET)
    public String msgManageView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/msgManage/msgManage";
    }

    /**
     * 메세지관리 - 그룹 조회
     *
     * @param msgManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 22.
     */
    @RequestMapping(value = "/msgManage/getMsgManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgManageList(MsgManageVO msgManageVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = msgManageService.getMsgManageList(msgManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, msgManageVO);
    }

    /**
     * 메세지관리 - 그룹 저장
     *
     * @param msgManageVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 22.
     */
    @RequestMapping(value = "/msgManage/getMsgManageSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgManageSave(@RequestBody MsgManageVO[] msgManageVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = msgManageService.getMsgManageSave(msgManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메세지관리 - 메세지서식 조회
     *
     * @param msgManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 22.
     */
    @RequestMapping(value = "/msgManage/getMsgManageDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgManageDtlList(MsgManageVO msgManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = msgManageService.getMsgManageDtlList(msgManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, msgManageVO);
    }

    /**
     * 메세지관리 - 메세지서식 저장
     *
     * @param msgManageVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 22.
     */
    @RequestMapping(value = "/msgManage/getMsgManageDtlSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgManageDtlSave(@RequestBody MsgManageVO msgManageVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = msgManageService.getMsgManageDtlSave(msgManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
