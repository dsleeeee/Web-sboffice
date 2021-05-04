package kr.co.solbipos.store.manage.pwdmanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageService;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : PwdManageController.java
 * @Description : 가맹점관리 > 매장관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/store/manage/pwdManage")
public class PwdManageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PwdManageService pwdManageService;
    private final MessageService messageService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired public PwdManageController(PwdManageService pwdManageService,
        MessageService messageService, SessionService sessionService) {
        this.pwdManageService = pwdManageService;
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
    @RequestMapping(value = "/pwdManage/view.sb", method = RequestMethod.GET)
    public String pwdManageView(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "store/manage/pwdManage/pwdManage";
    }

    /**
     * 비밀번호 임의변경 - 조회
     * @param   request
     * @param   response
     * @param   pwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPwdManageList(HttpServletRequest request, HttpServletResponse response,
            PwdManageVO pwdManageVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = pwdManageService.getPwdManageList(pwdManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, pwdManageVO);

    }

    /**
     * 비밀번호 임의변경 - 변경
     * @param   request
     * @param   response
     * @param   pwdManageVO
     * @param   model
     * @return  Result
     * @author  노현수
     * @since   2018. 06. 15.
     */
    @RequestMapping(value = "/pwdManage/modify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(@RequestBody PwdManageVO pwdManageVO, HttpServletRequest request,
            HttpServletResponse response,  Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int authResultCheck = pwdManageService.checkModifyPwd(pwdManageVO, sessionInfoVO);
        if(authResultCheck <= 0)
        {
            return returnJson(Status.OK, PwChgResult.ID_NOT_MATCH);
        }

        pwdManageVO.setRegId(sessionService.getSessionInfo().getUserId());
        pwdManageVO.setModId(sessionService.getSessionInfo().getUserId());

        // 패스워드 변경
        PwChgResult result = pwdManageService.modifyPwd(pwdManageVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 웹 비밀번호 잠김해제
     * @param   request
     * @param   response
     * @param   pwdManageVO
     * @param   model
     * @return  Result
     * @author  이다솜
     * @since   2020. 03. 05.
     */
    @RequestMapping(value = "/pwdManage/updatePasswordUnLock.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePasswordUnLock(@RequestBody PwdManageVO pwdManageVO, HttpServletRequest request,
                                 HttpServletResponse response,  Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 패스워드 변경
        PwChgResult result = pwdManageService.updatePasswordUnLock(pwdManageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
