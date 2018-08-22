package kr.co.solbipos.store.manage.pwdmanage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageService;
import kr.co.solbipos.store.manage.pwdmanage.service.PwdManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /** service */
    @Autowired
    PwdManageService pwdManageService;
    @Autowired
    MessageService messageService;
    @Autowired
    SessionService sessionService;

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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        System.out.println("####################################################");
        System.out.println(EncUtil.setEncSHA256("sysAdmin0000"));
        System.out.println(EncUtil.setEncSHA256(sessionInfoVO.getUserId() + "Qwer1234!"));
        System.out.println(EncUtil.setEncSHA256(sessionInfoVO.getUserId() + "0000"));
        System.out.println("####################################################");

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

        List<DefaultMap<String>> list = pwdManageService.getPwdManageList(pwdManageVO);

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
    public Result updatePassword(HttpServletRequest request,
            HttpServletResponse response, PwdManageVO pwdManageVO, Model model) {

        pwdManageVO.setRegId(sessionService.getSessionInfo().getUserId());
        pwdManageVO.setModId(sessionService.getSessionInfo().getUserId());

        // 패스워드 변경
        PwChgResult result = pwdManageService.modifyPwd(pwdManageVO);

        if (result == PwChgResult.NEW_PASSWORD_NOT_MATCH) {
            /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
            return returnJson(Status.FAIL, "msg", messageService.get("login.pw.find.not.match"));
        } else if (result == PwChgResult.PASSWORD_NEW_OLD_MATH) {
            /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
            return returnJson(Status.FAIL, "msg", messageService.get("login.layer.pwchg.current"));
        }else if (result == PwChgResult.PASSWORD_NOT_MATCH_LENGTH) {
            /**
             * 비밀번호는 최소 6자 이상 20자 이하만 가능
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.not.match.length"));
        } else if (result == PwChgResult.PASSWORD_NOT_MATCH_CHAR) {
            /**
             * 비밀번호는 숫자와 영문, 특수문자(!,@,$,~)만 사용 가능
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.not.match.char"));
        } else if (result == PwChgResult.PASSWORD_NOT_CONTAIN_NUMBER) {
            /**
             * 비밀번호는 반드시 숫자가 포함
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.not.contain.number"));
        } else if (result == PwChgResult.PASSWORD_NOT_CONTAIN_ENG_CHAR) {
            /**
             * 비밀번호는 영문자가 반드시 포함
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.not.contain.char"));
        } else if (result == PwChgResult.PASSWORD_CONTINUED_CHAR) {
            /**
             * 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다.
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.cannot.be.used.continued.char"));
        } else if (result == PwChgResult.PASSWORD_SAME_CHAR) {
            /**
             * 동일한 문자 또는 숫자를 3자 이상 사용할 수 없습니다.
             */
            return returnJson(Status.FAIL, messageService.get("login.pw.cannot.be.used.same.char"));
        }


        return returnJson(Status.OK, result);

    }

}
