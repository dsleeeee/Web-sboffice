package kr.co.solbipos.application.controller.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.HttpUtils.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import static org.springframework.util.StringUtils.*;
import java.util.Date;
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
import kr.co.solbipos.application.validate.user.AuthNumber;
import kr.co.solbipos.application.validate.user.IdFind;
import kr.co.solbipos.application.validate.user.PwChange;
import kr.co.solbipos.application.validate.user.PwFind;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.message.MessageService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.DateUtil;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.StringUtil;
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
    public JsonResult sendNum(@Validated(AuthNumber.class) User user, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // 유져 정보 조회
        User findUser = userService.selectUserByNmAndId(user);

        // 조회된 유져가 없으면 에러 메세지 전송
        if (ObjectUtil.isEmpty(findUser)) {
            log.warn("문자 발송 유져 조회 실패 : id : {}, nm : {}", user.getUserId(), user.getEmpNm());
            String msg = messageService.get("msg.pw.find.error1")
                    + messageService.get("msg.pw.find.error2");
            return returnJson(Status.FAIL, "msg", msg);
        } else {

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

        // id, 이름으로 유져 조회
        String findUserNm = userService.selectUserCheck(user);

        // 아이디가 없으면 에러 메시지 리턴
        if (StringUtil.isEmpty(findUserNm)) {
            // id 를 찾을 수 없습니다.
            String msg = messageService.get("label.login.userId")
                    + messageService.get("msg.cmm.not.find");
            model.addAttribute("msg", msg);
            return "user/login:idFind";
        }

        // 찾은 아이디를 마스킹해서 보여줌
        model.addAttribute("findUserNm", strMaskingHalf(findUserNm));

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
    public JsonResult passwordFindProcess(@Validated(PwFind.class) User user,
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
            return returnJson(Status.FAIL, "msg", messageService.get("msg.cmm.input.fail"));
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
            return returnJson(Status.VALID_FAIL);
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
        throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
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
            throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
        }

        // id, 이름 체크
        User findUser = userService.selectUserByNmAndId(user);

        if (ObjectUtil.isEmpty(findUser)) {
            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
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
            throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
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

        PwChgResult result = userService.processPwdChg(pwdChg);

        log.info("패스워드 변경 결과 : halfId : {}, result : {}, uuid : {}", pwdChg.getHalfId(), result,
                pwdChg.getUuid());

        if (result == PwChgResult.PASSWORD_NOT_MATCH) {
            // 새 비밀번호와 비밀번호 확인이 일치하지 않습니다.
            throw new AuthenticationException(messageService.get("msg.pw.find.not.match"), "");
        } else if (result == PwChgResult.UUID_NOT_MATCH || result == PwChgResult.EMPTY_USER
                || result == PwChgResult.ID_NOT_MATCH) {
            /**
             * uuid가 없는 경우 uuid로 조회한 user가 있는지 확인 halfId 와 uuid 로 조회된 id 매칭 여부 리턴 메세지 : 잘못된 접근입니다.
             */
            throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
        } else if (result == PwChgResult.LOCK_USER) {
            // 잠금 유져는 패스워드 변경 불가능 > 잠겨있는 유저 입니다. 고객센터로 연락 주세요.
            throw new AuthenticationException(messageService.get("msg.pw.find.lock.user"), "");
        } else if (result == PwChgResult.UUID_LIMIT_ERROR) {
            // 인증유효 시간이 지났습니다. 다시 인증 해주세요.
            throw new AuthenticationException(messageService.get("msg.pw.find.limit"),
                    "/user/pwdFind.sb");
        } else if (result == PwChgResult.CHECK_OK) {
            String userId = pwdChg.getUserId();

            // 패스워드 세팅 및 변경
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setUserId(userId);
            sessionInfo.setUserPwd(pwdChg.getNewPw());
            int r1 = userService.updateUserPwd(sessionInfo);

            // 패스워드 변경 내역 저장
            PwdChgHist pch = new PwdChgHist();
            pch.setUserId(userId);
            pch.setPriorPwd(pwdChg.getOrginPwd());
            pch.setRegDt(currentDateTimeString());
            pch.setRegIp(getClientIp(request));
            int r2 = userService.insertPwdChgHist(pch);

            return "user/login:pwdChgOk";
        } else {
            throw new AuthenticationException(messageService.get("msg.cmm.invalid.access"), "");
        }
    }
}


