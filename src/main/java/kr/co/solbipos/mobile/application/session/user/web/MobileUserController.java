package kr.co.solbipos.mobile.application.session.user.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.exception.JsonException;
import kr.co.common.service.code.CmmCodeService;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.common.validate.*;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.application.session.user.enums.PwFindResult;
import kr.co.solbipos.application.session.user.service.*;
import kr.co.solbipos.mobile.application.session.auth.service.MobileAuthService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnJsonBindingFieldError;
import static kr.co.common.utils.spring.StringUtil.strMaskingHalf;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @Class Name : MobileUserController.java
 * @Description : 어플리케이션 > 세션 > 사용자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.08  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.04.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/mobile/user")
public class MobileUserController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    MobileAuthService mobileAuthService;
    @Autowired
    MessageService messageService;
    @Autowired
    SessionService sessionService;
    @Autowired
    CmmCodeService cmmCodeService;

    /**
     * 인증번호 발송
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "sendNum.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result sendNum(@Validated(AuthNumber.class) UserVO userVO, BindingResult bindingResult,
                          HttpServletRequest request, HttpServletResponse response, Model model) {

        // 입력값 에러 처리
        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // 유져 정보 조회
        List<UserVO> findUsers = userService.selectUserList(userVO, false);

        // 조회된 유져가 없으면 에러 메세지 전송
        if (ObjectUtil.isEmpty(findUsers)) {
            LOGGER.warn("문자 발송 유져 조회 실패 : id : {}, nm : {}", userVO.getUserId(), userVO.getEmpNm());
            String msg = messageService.get("login.pw.find.error1") + " "
                    + messageService.get("login.pw.find.error2");
            return returnJson(Status.FAIL, "msg", msg);
        }
        // 조회된 사용자 정보가 2개 이상일 때 오류 처리
        if (findUsers.size() > 1) {
            LOGGER.warn("문자 발송 유져 여러명 조회됨 : id : {}, nm : {}", userVO.getUserId(), userVO.getEmpNm());
            String msg = messageService.get("login.pw.find.error1") + " "
                    + messageService.get("login.pw.find.error2");
            return returnJson(Status.FAIL, "msg", msg);
        }

        UserVO findUser = findUsers.get(0);

        OtpAuthVO otpAuthVO = new OtpAuthVO();
        otpAuthVO.setUserId(findUser.getUserId());
        otpAuthVO.setAuthFg("001");
        otpAuthVO.setRecvMpNo(findUser.getMpNo());
        otpAuthVO.setReqIp(getClientIp(request));
        otpAuthVO.setReqDate(currentDateString());
        otpAuthVO.setOtpLimit(BaseEnv.OTP_LIMIT_MINUTE);

        // limit 에 걸렸는지 확인
        if (checkOtpLimit(otpAuthVO)) {
            LOGGER.warn("인증문자 제한 시간 걸림, 제한 시간 : {}, id : {}, name : {}", BaseEnv.OTP_LIMIT_MINUTE,
                    findUser.getUserId(), findUser.getEmpNm());
            String msg = String.valueOf(otpAuthVO.getOtpLimit())
                    + messageService.get("login.pw.find.otp.limit");
            return returnJson(Status.FAIL, "authNumber", msg);
        }

        // 인증 번호 생성 후 전송
        // 신규 OTP 생성 리턴
        userService.insertOtpAuth(otpAuthVO);


        /**
         *
         * TODO : OTP 문자 발송 로직 들어가야됨
         *
         */
        /** 문자 발송 로직 */
        List<DefaultMap<String>> codeList = null;
        // 코드조회
        codeList = cmmCodeService.selectCmmCodeList("128");

        System.out.println("WEB_SMS >>> 비밀번호찾기 인증번호 SMS전송 >>> 128 코드값 : " + codeList.get(0).getStr("nmcodeItem1"));
        System.out.println("WEB_SMS >>> 비밀번호찾기 인증번호 SMS전송 >>> 아이디 : " + otpAuthVO.getUserId());
        System.out.println("WEB_SMS >>> 비밀번호찾기 인증번호 SMS전송 >>> 핸드폰번호 : " + otpAuthVO.getRecvMpNo());
        System.out.println("WEB_SMS >>> 비밀번호찾기 인증번호 SMS전송 >>> 인증번호 : " + otpAuthVO.getAuthNo());

        if(("Y").equals(codeList.get(0).getStr("nmcodeItem1"))) {
            // SMS전송
            userService.getSmsSendSave(otpAuthVO);
        }
        /** //문자 발송 로직 */


        return returnJson(Status.OK, "msg", messageService.get("login.pw.find.send.ok"));
    }


    /**
     * otp limit 해당되는지 확인
     *
     * @param otpAuthVO
     * @return 10:23(limit) >= 10:20(now) ? true : false 조회 못한 경우에도 false return
     */
    public boolean checkOtpLimit(OtpAuthVO otpAuthVO) {
        OtpAuthVO o = userService.selectOtpTopOne(otpAuthVO);

        if (ObjectUtil.isEmpty(o)) {
            return false;
        }

        // otp 생성 시간
        String otpDateTime = o.getReqDate() + o.getReqDt();
        Date otpDt = DateUtil.getDatetime(otpDateTime);
        // otp 리미티드 시간 더해줌
        otpDt = DateUtils.addMinutes(otpDt, BaseEnv.OTP_LIMIT_MINUTE);

        // 현재 시간
        Date current = new Date();

        if (otpDt.getTime() >= current.getTime()) {
            return true;
        }

        return false;
    }

    /**
     * 모바일 아이디 찾기 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "idFind.sb", method = RequestMethod.GET)
    public String idFind(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "user/mlogin:idFind";
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
    public String idFindProcess(@Validated(IdFind.class) UserVO userVO, BindingResult bindingResult,
                                HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/mlogin:idFind";
        }

        // id, 이름으로 유져 조회 - 아이디 마스킹
        List<UserVO> findUsers = userService.selectUserList(userVO, true);

        // 아이디가 없으면 에러 메시지 리턴
        if (findUsers.size() < 1) {
            // id 를 찾을 수 없습니다.
            String msg = messageService.get("login.userId")
                    + messageService.get("cmm.not.find");
            model.addAttribute("msg", msg);
            return "user/mlogin:idFind";
        }

        // 찾은 아이디를 마스킹해서 보여줌
        model.addAttribute("userId", findUsers.get(0).getUserId());

        return "user/mlogin:idFindOk";
    }

    /**
     * 모바일 패스워드 찾기 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.GET)
    public String passwordFind(HttpServletRequest request, HttpServletResponse response,
                               Model model) {
        model.addAttribute("otpLimit", BaseEnv.OTP_LIMIT_MINUTE);
        return "user/mlogin:pwdFind";
    }

    /**
     * 패스워드 찾기
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdFind.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result passwordFindProcess(@Validated(PwFind.class) UserVO userVO,
                                      BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                                      Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        // otp 체크
        PwFindResult pfr = userService.processPwFind(userVO);

        // otp 맞음
        if (pfr == PwFindResult.OTP_OK) {
            return returnJson(Status.OK, "uuid", userVO.getAuthNumber());
        }
        // 입력한 정보가 올바르지 않습니다.
        else if (pfr == PwFindResult.EMPTY_USER) {
            return returnJson(Status.FAIL, "msg", messageService.get("cmm.input.fail"));
        }
        // 인증번호가 틀렸습니다.
        else if (pfr == PwFindResult.OTP_ERROR) {
            return returnJson(Status.FAIL, "authNumber", messageService.get("login.pw.find.pw.fail"));
        }
        // otp 입력시간 지남
        else if (pfr == PwFindResult.OTP_LIMIT_ERROR) {
            return returnJson(Status.FAIL, "authNumber",
                    BaseEnv.OTP_LIMIT_MINUTE + messageService.get("login.pw.find.limit.otp.minute"));
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 모바일 패스워드 변경 페이지 이동(어디서 쓰는지...)
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
     * 모바일 패스워드 변경 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChg.sb", method = RequestMethod.POST)
    public String passwordChangeProcess(UserVO userVO, String uuid, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        // 필수 값 체크
        if (ObjectUtil.isEmpty(userVO) || isEmpty(userVO.getUserId()) || isEmpty(userVO.getEmpNm())
                || isEmpty(uuid)) {

            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }

        // id, 이름 체크
        List<UserVO> findUsers = userService.selectUserList(userVO, false);

        if (ObjectUtil.isEmpty(findUsers)) {
            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
        // 조회된 사용자 정보가 2개 이상일 때 오류 처리
        if (findUsers.size() > 1) {
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }

        OtpAuthVO otpAuthVO = new OtpAuthVO();
        otpAuthVO.setUserId(userVO.getUserId());
        otpAuthVO.setReqDate(currentDateString());
        otpAuthVO.setAuthFg("001");

        // seq, otp 체크
        OtpAuthVO findOtp = userService.selectOtpTopOne(otpAuthVO);

        if (!ObjectUtil.isEmpty(findOtp) && findOtp.getSeq().equals(uuid)
                && findOtp.getAuthNo().equals(userVO.getAuthNumber())
                && !StringUtil.isEmpty(userVO.getUserId())) {
            model.addAttribute("userId", strMaskingHalf(userVO.getUserId()));
            model.addAttribute("uuid", uuid);
            return "user/mlogin:pwdChg";
        } else {
            // 실패 처리 > 잘못된 접근입니다.
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
    }

    /**
     * 모바일 패스워드 변경
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdChgOk.sb", method = RequestMethod.POST)
    public String pwdChgOk(@Validated(PwChange.class) PwdChgVO pwdChgVO, BindingResult bindingResult,
                           HttpServletRequest request, HttpServletResponse response, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", pwdChgVO.getHalfId());
            model.addAttribute("uuid", pwdChgVO.getUuid());
            return "user/mlogin:pwdChg";
        }

        /**
         * 패스워드 정책 체크
         */
        PwChgResult pwdChk;
        try {
            pwdChk = CmmUtil.checkPasswd(pwdChgVO.getNewPw());
        } catch (Exception e) {
            LOGGER.info(messageService.get("login.pwchg.error"));
            throw new JsonException(Status.FAIL, messageService.get("login.pwchg.error"));
        }

        if(pwdChk != PwChgResult.CHECK_OK) {
            if(pwdChk == PwChgResult.PASSWORD_NOT_MATCH_LENGTH){
                // 비밀번호는 최소 6자 이상 20자 이하만 가능 합니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.not.match.length"));

            }else if(pwdChk == PwChgResult.PASSWORD_NOT_MATCH_CHAR){
                // 비밀번호는 숫자와 영문만 사용 가능합니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.not.match.char"));

            }else if(pwdChk == PwChgResult.PASSWORD_NOT_CONTAIN_NUMBER){
                // 비밀번호는 반드시 숫자가 포함되어야 합니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.not.contain.number"));

            }else if(pwdChk == PwChgResult.PASSWORD_NOT_CONTAIN_ENG_CHAR){
                // 비밀번호는 비밀번호는 반드시 영문자가 포함되어야 합니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.not.contain.char"));

            }else if(pwdChk == PwChgResult.PASSWORD_CONTINUED_CHAR){
                // 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.cannot.be.used.continued.char"));

            }else if(pwdChk == PwChgResult.PASSWORD_SAME_CHAR){
                // 동일한 문자 또는 숫자를 3자 이상 사용할 수 없습니다.
                throw new JsonException(Status.FAIL, messageService.get("login.pw.cannot.be.used.same.char"));

            }else {
                throw new JsonException(Status.FAIL, messageService.get("login.pwchg.error"));
            }
        }

        PwChgResult pcr = userService.processPwdChg(pwdChgVO);

        LOGGER.info("패스워드 변경 결과 : halfId : {}, result : {}, uuid : {}", pwdChgVO.getHalfId(), pcr,
                pwdChgVO.getUuid());

        if (pcr == PwChgResult.PASSWORD_NOT_MATCH) {
            // 새 비밀번호와 비밀번호 확인이 일치하지 않습니다.
            throw new AuthenticationException(messageService.get("login.pw.find.not.match"), "");
        } else if (pcr == PwChgResult.UUID_NOT_MATCH || pcr == PwChgResult.EMPTY_USER
                || pcr == PwChgResult.ID_NOT_MATCH) {
            /**uuid가 없는 경우 uuid로 조회한 user가 있는지 확인 halfId 와 uuid 로 조회된 id 매칭 여부 리턴 메세지 : 잘못된 접근입니다. */
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        } else if (pcr == PwChgResult.NOT_USE) {
            // 사용하지 않는 사용자 패스워드 변경 불가능 > 사용하지 않는 유저 입니다. 고객센터로 연락 주세요.
            throw new AuthenticationException(messageService.get("login.pw.find.not.use"), "");
        } else if (pcr == PwChgResult.UUID_LIMIT_ERROR) {
            // 인증유효 시간이 지났습니다. 다시 인증 해주세요.
            throw new AuthenticationException(messageService.get("login.pw.find.limit"),
                    "/mobile/user/pwdFind.sb");
        } else if (pcr == PwChgResult.CHECK_OK) {
            // 패스워드 변경 성공
            return "user/mlogin:pwdChgOk";
        } else {
            throw new AuthenticationException(messageService.get("cmm.invalid.access"), "");
        }
    }

    /**
     * 모바일 메인 화면에서 비밀번호 변경
     *
     * @param pwdChgVO
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "userPwdChg.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result userPwdChg(@Validated(UserPwChange.class) PwdChgVO pwdChgVO,
                             BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        SessionInfoVO param = new SessionInfoVO();

        // 메인 화면에서 패스워드를 변경 할때는 세션이 없음
        param.setUserId(
                ObjectUtil.isEmpty(sessionInfoVO) ? pwdChgVO.getUserId() : sessionInfoVO.getUserId());

        // 패스워드 결과 조회
        PwChgResult result = userService.processLayerPwdChg(param, pwdChgVO);

        if (result == PwChgResult.PASSWORD_NOT_MATCH) {
            /**
             * 기존 패스워드 비교
             */
            throw new JsonException(Status.FAIL,  messageService.get("login.layer.pwchg.pwfail"));
        } else if (result == PwChgResult.NEW_PASSWORD_NOT_MATCH) {

            /**
             * 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
             */
            throw new JsonException(Status.FAIL,  messageService.get("login.pw.find.not.match"));
        } else if (result == PwChgResult.PASSWORD_NEW_OLD_MATH) {
            /**
             * 변경 패스워드가 기존 비밀번호가 같은지 체크
             */
            throw new JsonException(Status.FAIL, messageService.get("login.layer.pwchg.current"));
        } else if (result == PwChgResult.PASSWORD_NOT_MATCH_LENGTH) {
            /**
             * 비밀번호는 최소 6자 이상 20자 이하만 가능
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.not.match.length"));
        } else if (result == PwChgResult.PASSWORD_NOT_MATCH_CHAR) {
            /**
             * 비밀번호는 숫자와 영문, 특수문자(!,@,$,~)만 사용 가능
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.not.match.char"));
        } else if (result == PwChgResult.PASSWORD_NOT_CONTAIN_NUMBER) {
            /**
             * 비밀번호는 반드시 숫자가 포함
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.not.contain.number"));
        } else if (result == PwChgResult.PASSWORD_NOT_CONTAIN_ENG_CHAR) {
            /**
             * 비밀번호는 영문자가 반드시 포함
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.not.contain.char"));
        } else if (result == PwChgResult.PASSWORD_CONTINUED_CHAR) {
            /**
             * 숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용할 수 없습니다.
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.cannot.be.used.continued.char"));
        } else if (result == PwChgResult.PASSWORD_SAME_CHAR) {
            /**
             * 동일한 문자 또는 숫자를 3자 이상 사용할 수 없습니다.
             */
            throw new JsonException(Status.FAIL, messageService.get("login.pw.cannot.be.used.same.char"));
        }

        HashMap<String, String> returnData = new HashMap<>();
        returnData.put("msg", messageService.get("login.pw.find.h2.1") + messageService.get("login.pw.find.h2.2"));
        returnData.put("url", "/mobile/auth/logout.sb");

        return returnJson(Status.OK, returnData);
    }

    /**
     * 모바일 비밀번호 연장
     *
     * @param pwdChgVO
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "pwdExtension.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result pwdExtension(@Validated(Login.class) PwdChgVO pwdChgVO,
                               BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return returnJsonBindingFieldError(bindingResult);
        }

        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        sessionInfoVO.setUserId(pwdChgVO.getUserId());

        SessionInfoVO sessionInfo = mobileAuthService.selectWebUser(sessionInfoVO);

        /**
         * 기존 패스워드 비교
         */
        if (!sessionInfo.getUserPwd().equals(pwdChgVO.getCurrentPw())) {
            return returnJson(Status.FAIL, "msg", messageService.get("login.layer.pwchg.pwfail"));
        }

        /**
         * 패스워드 변경 내역 저장 하면서 패스워드 유효기간을 연장한다.
         *
         */
        PwdChgHistVO pwdChgHistVO = new PwdChgHistVO();
        pwdChgHistVO.setUserId(sessionInfo.getUserId());
        pwdChgHistVO.setPriorPwd(pwdChgVO.getCurrentPw());
        pwdChgHistVO.setRegDt(currentDateTimeString());
        pwdChgHistVO.setRegIp(getClientIp(WebUtil.getRequest()));
        @SuppressWarnings("unused")
        int r2 = userService.insertPwdChgHist(pwdChgHistVO);

        HashMap<String, String> result = new HashMap<>();
        result.put("msg", messageService.get("login.pw.find.h2.1") +  messageService.get("login.pw.find.h2.2"));

        result.put("url", "/mobile/auth/logout.sb");

        return returnJson(Status.OK, result);
    }
}
