package kr.co.solbipos.application.controller.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.HttpUtils.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import static org.springframework.util.StringUtils.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.PwdChg;
import kr.co.solbipos.application.domain.user.PwdChgHist;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.enums.user.PwChgResult;
import kr.co.solbipos.application.enums.user.PwFindResult;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.application.service.user.UserService;
import kr.co.solbipos.application.validate.login.Login;
import kr.co.solbipos.application.validate.user.AuthNumber;
import kr.co.solbipos.application.validate.user.IdFind;
import kr.co.solbipos.application.validate.user.PwChange;
import kr.co.solbipos.application.validate.user.PwFind;
import kr.co.solbipos.application.validate.user.UserPwChange;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.DateUtil;
import kr.co.solbipos.utils.security.EncUtil;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.StringUtil;
import kr.co.solbipos.utils.spring.WebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 정용길
 */

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    Prop prop;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    MessageService messageService;

    @Autowired
    SessionService sessionService;

    /**
     * 인증번호 발사!!
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sendNum.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result sendNum(@Validated(AuthNumber.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // 유져 정보 조회
        List<User> findUsers = userService.selectUserList(user, false);

        // 조회된 유져가 없으면 에러 메세지 전송
        if (ObjectUtil.isEmpty(findUsers)) {
            log.warn("문자 발송 유져 조회 실패 : id : {}, nm : {}", user.getUserId(), user.getEmpNm());
            String msg = messageService.get("msg.pw.find.error1")
                    + messageService.get("msg.pw.find.error2");
            return returnJson(Status.FAIL, "msg", msg);
        }
        // 조회된 사용자 정보가 2개 이상일 때 오류 처리
        if (findUsers.size() > 1) {
            log.warn("문자 발송 유져 여러명 조회됨 : id : {}, nm : {}", user.getUserId(), user.getEmpNm());
            String msg = messageService.get("msg.pw.find.error1")
                    + messageService.get("msg.pw.find.error2");
            return returnJson(Status.FAIL, "msg", msg);
        }
        
        User findUser = findUsers.get(0);
        
        OtpAuth otp = new OtpAuth();
        otp.setUserId(findUser.getUserId());
        otp.setAuthFg("001");
        otp.setRecvMpNo(findUser.getMpNo());
        otp.setReqIp(getClientIp(request));
        otp.setReqDate(currentDateString());
        otp.setOtpLimit(prop.otpLimit);

        // limit 에 걸렸는지 확인
        if (checkOtpLimit(otp)) {
            log.warn("인증문자 제한 시간 걸림, 제한 시간 : {}, id : {}, name : {}", prop.otpLimit,
                    findUser.getUserId(), findUser.getEmpNm());
            String msg = String.valueOf(otp.getOtpLimit())
                    + messageService.get("msg.pw.find.otp.limit");
            return returnJson(Status.FAIL, "authNumber", msg);
        }

        // 인증 번호 생성 후 전송
        // 신규 OTP 생성 리턴
        userService.insertOtpAuth(otp);

        /**
         * 
         * TODO : OTP 문자 발송 로직 들어가야됨
         * 
         */

        return returnJson(Status.OK, "msg", messageService.get("msg.pw.find.send.ok"));
    }


    /**
     * otp limit 해당되는지 확인
     * 
     * @param otp
     * @return 10:23(limit) >= 10:20(now) ? true : false 조회 못한 경우에도 false return
     */
    public boolean checkOtpLimit(OtpAuth otp) {
        OtpAuth o = userService.selectOtpTopOne(otp);

        if (ObjectUtil.isEmpty(o)) {
            return false;
        }

        // otp 생성 시간
        String otpDateTime = o.getReqDate() + o.getReqDt();
        Date otpDt = DateUtil.getDatetime(otpDateTime);
        // otp 리미티드 시간 더해줌
        otpDt = DateUtils.addMinutes(otpDt, prop.otpLimit);

        // 현재 시간
        Date current = new Date();

        if (otpDt.getTime() >= current.getTime()) {
            return true;
        }

        return false;
    }

    /**
     * 아이디 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.GET)
    public String idFind(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/login:idFind";
    }

    /**
     * 아이디 찾기
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.POST)
    public String idFindProcess(@Validated(IdFind.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/login:idFind";
        }

        // id, 이름으로 유져 조회 - 아이디 마스킹
        List<User> findUsers = userService.selectUserList(user, true);

        // 아이디가 없으면 에러 메시지 리턴
        if (findUsers.size() < 1) {
            // id 를 찾을 수 없습니다.
            String msg = messageService.get("login.userId")
                    + messageService.get("cmm.not.find");
            model.addAttribute("msg", msg);
            return "user/login:idFind";
        }

        // 찾은 아이디를 마스킹해서 보여줌
        model.addAttribute("list", findUsers);

        return "user/login:idFindOk";
    }

    /**
     * 패스워드 찾기 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.GET)
    public String passwordFind(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        model.addAttribute("otpLimit", prop.otpLimit);
        return "user/login:pwdFind";
    }

    /**
     * 패스워드 찾기 > 비밀번호 변경 페이지로 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result passwordFindProcess(@Validated(PwFind.class) User user,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // otp 체크
        PwFindResult pfr = userService.processPwFind(user);

        // otp 맞음
        if (pfr == PwFindResult.OTP_OK) {
            return returnJson(Status.OK, "uuid", user.getAuthNumber());
        }
        // 입력한 정보가 올바르지 않습니다.
        else if (pfr == PwFindResult.EMPTY_USER) {
            return returnJson(Status.FAIL, "msg", messageService.get("cmm.input.fail"));
        }
        // 인증번호가 틀렸습니다.
        else if (pfr == PwFindResult.OTP_ERROR) {
            return returnJson(Status.FAIL, "authNumber", messageService.get("msg.pw.find.pw.fail"));
        }
        // otp 입력시간 지남
        else if (pfr == PwFindResult.OTP_LIMIT_ERROR) {
            return returnJson(Status.FAIL, "authNumber",
                    prop.otpLimit + messageService.get("msg.pw.find.limit.otp.minute"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 패스워드 변경 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.GET)
    public String passwordChange(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        // 잘못된 접근입니다.
        throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
    }

    /**
     * 패스워드 변경
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.POST)
    public String passwordChangeProcess(User user, String uuid, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 필수 값 체크
        if (ObjectUtil.isEmpty(user) || isEmpty(user.getUserId()) || isEmpty(user.getEmpNm())
                || isEmpty(uuid)) {

            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }

        // id, 이름 체크
        List<User> findUsers = userService.selectUserList(user, false);

        if (ObjectUtil.isEmpty(findUsers)) {
            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
        // 조회된 사용자 정보가 2개 이상일 때 오류 처리
        if (findUsers.size() > 1) {
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
        
        OtpAuth otp = new OtpAuth();
        otp.setUserId(user.getUserId());
        otp.setReqDate(currentDateString());
        otp.setAuthFg("001");

        // seq, otp 체크
        OtpAuth findOtp = userService.selectOtpTopOne(otp);

        if (!ObjectUtil.isEmpty(findOtp) && findOtp.getSeq().equals(uuid)
                && findOtp.getAuthNo().equals(user.getAuthNumber())
                && !StringUtil.isEmpty(user.getUserId())) {
            model.addAttribute("userId", strMaskingHalf(user.getUserId()));
            model.addAttribute("uuid", uuid);
            return "user/login:pwdChg";
        } else {
            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
    }

    /**
     * 패스워드 변경 완료 페이지 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChgOk.sb", method = RequestMethod.POST)
    public String pwdChgOk(@Validated(PwChange.class) PwdChg pwdChg, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", pwdChg.getHalfId());
            model.addAttribute("uuid", pwdChg.getUuid());
            return "user/login:pwdChg";
        }

        /**
         * 패스워드 정책 체크
         */
        if (!EncUtil.passwordPolicyCheck(pwdChg.getNewPw())
                || !EncUtil.passwordPolicyCheck(pwdChg.getNewPwConf())) {
            throw new AuthenticationException(messageService.get("msg.pw.chg.regexp"), "");
        }

        PwChgResult pcr = userService.processPwdChg(pwdChg);

        log.info("패스워드 변경 결과 : halfId : {}, result : {}, uuid : {}", pwdChg.getHalfId(), pcr,
                pwdChg.getUuid());

        if (pcr == PwChgResult.PASSWORD_NOT_MATCH) {
            // 새 비밀번호와 비밀번호 확인이 일치하지 않습니다.
            throw new AuthenticationException(messageService.get("msg.pw.find.not.match"), "");
        } else if (pcr == PwChgResult.UUID_NOT_MATCH || pcr == PwChgResult.EMPTY_USER
                || pcr == PwChgResult.ID_NOT_MATCH) {
            /**
             * uuid가 없는 경우 uuid로 조회한 user가 있는지 확인 halfId 와 uuid 로 조회된 id 매칭 여부 리턴 메세지 : 잘못된 접근입니다.
             */
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        } else if (pcr == PwChgResult.LOCK_USER) {
            // 잠금 유져는 패스워드 변경 불가능 > 잠겨있는 유저 입니다. 고객센터로 연락 주세요.
            throw new AuthenticationException(messageService.get("msg.pw.find.lock.user"), "");
        } else if (pcr == PwChgResult.UUID_LIMIT_ERROR) {
            // 인증유효 시간이 지났습니다. 다시 인증 해주세요.
            throw new AuthenticationException(messageService.get("msg.pw.find.limit"),
                    "/user/pwdFind.sb");
        } else if (pcr == PwChgResult.CHECK_OK) {
            // 패스워드 변경 성공
            return "user/login:pwdChgOk";
        } else {
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
    }

    /**
     * 메인 화면에서 비밀번호 변경
     * 
     * @param pwdChg
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "userPwdChg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result userPwdChg(@Validated(UserPwChange.class) PwdChg pwdChg,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        SessionInfo si = new SessionInfo();

        // 메인 화면에서 패스워드를 변경 할때는 세션이 없음
        si.setUserId(
                ObjectUtil.isEmpty(sessionInfo) ? pwdChg.getUserId() : sessionInfo.getUserId());

        // 패스워드 결과 조회
        PwChgResult result = userService.processLayerPwdChg(si, pwdChg);

        if (result == PwChgResult.PASSWORD_NOT_MATCH) {
            /**
             * 기존 패스워드 비교
             */
            return returnJson(Status.FAIL, "msg", messageService.get("msg.layer.pwchg.pwfail"));
        } else if (result == PwChgResult.NEW_PASSWORD_NOT_MATCH) {
            /**
             * 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
             */
            return returnJson(Status.FAIL, "msg", messageService.get("msg.pw.find.not.match"));
        } else if (result == PwChgResult.PASSWORD_NEW_OLD_MATH) {
            /**
             * 변경 패스워드가 기존 비밀번호가 같은지 체크
             */
            return returnJson(Status.FAIL, "msg", messageService.get("msg.layer.pwchg.match"));
        } else if (result == PwChgResult.PASSWORD_REGEXP) {
            /**
             * 패스워드 정책 체크
             */
            return returnJson(Status.FAIL, "msg", messageService.get("msg.pw.chg.regexp"));
        }

        HashMap<String, String> returnData = new HashMap<>();
        returnData.put("msg", messageService.get("login.pw.find.h2.1")
                + messageService.get("login.pw.find.h2.2"));
        returnData.put("url", "/auth/logout.sb");

        return returnJson(Status.OK, returnData);
    }

    /**
     * 비밀번호 연장
     * 
     * @param pwdChg
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdExtension.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result pwdExtension(@Validated(Login.class) PwdChg pwdChg,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(pwdChg.getUserId());

        SessionInfo si = loginService.selectWebUser(sessionInfo);

        /**
         * 기존 패스워드 비교
         */
        if (!si.getUserPwd().equals(pwdChg.getCurrentPw())) {
            return returnJson(Status.FAIL, "msg", messageService.get("msg.layer.pwchg.pwfail"));
        }

        /**
         * 패스워드 변경 내역 저장 하면서 패스워드 유효기간을 연장한다.
         * 
         */
        PwdChgHist pch = new PwdChgHist();
        pch.setUserId(si.getUserId());
        pch.setPriorPwd(pwdChg.getCurrentPw());
        pch.setRegDt(currentDateTimeString());
        pch.setRegIp(getClientIp(WebUtil.getRequest()));
        int r2 = userService.insertPwdChgHist(pch);

        HashMap<String, String> result = new HashMap<>();
        result.put("msg", messageService.get("login.pw.find.h2.1")
                + messageService.get("login.pw.find.h2.2"));

        result.put("url", "/auth/logout.sb");

        return returnJson(Status.OK, result);
    }
}


