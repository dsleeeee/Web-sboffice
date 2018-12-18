package kr.co.solbipos.base.store.emp.system.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpPwdManageService;
import kr.co.solbipos.base.store.emp.system.service.SystemEmpPwdManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : SystemEmpPwdManageController.java
 * @Description : 시스템관리 > 사원관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자      수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.12  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.12
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/store/emp/")
public class SystemEmpPwdManageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SystemEmpPwdManageService systemEmpPwdManageService;
    private final MessageService messageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired public SystemEmpPwdManageController(SystemEmpPwdManageService systemEmpPwdManageService,
        MessageService messageService, SessionService sessionService) {
        this.systemEmpPwdManageService = systemEmpPwdManageService;
        this.messageService = messageService;
        this.sessionService = sessionService;
    }

    /**
     * 비밀번호 임의변경 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/systemPwdManage.sb", method = RequestMethod.GET)
    public String pwdManageView(HttpServletRequest request, HttpServletResponse response,
        Model model) {
        return "base/store/emp/systemEmpPwdManage";
    }

    /**
     * 비밀번호 임의변경 - 조회
     * @param   request
     * @param   response
     * @param   systemEmpPwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/systemPwdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPwdManageList(HttpServletRequest request, HttpServletResponse response,
        SystemEmpPwdManageVO systemEmpPwdManageVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = systemEmpPwdManageService.getPwdManageList(systemEmpPwdManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, systemEmpPwdManageVO);
    }

    /**
     * 비밀번호 임의변경 - 변경
     * @param   request
     * @param   response
     * @param   systemEmpPwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/systemPwdModify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(@RequestBody SystemEmpPwdManageVO systemEmpPwdManageVO, HttpServletRequest request,
        HttpServletResponse response,  Model model) {

        systemEmpPwdManageVO.setRegId(sessionService.getSessionInfo().getUserId());
        systemEmpPwdManageVO.setModId(sessionService.getSessionInfo().getUserId());

        // 패스워드 변경
        PwChgResult result = systemEmpPwdManageService.modifyPwd(systemEmpPwdManageVO);

        return returnJson(Status.OK, result);
    }

}
