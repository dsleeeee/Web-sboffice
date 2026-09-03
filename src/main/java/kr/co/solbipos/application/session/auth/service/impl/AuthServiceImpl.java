package kr.co.solbipos.application.session.auth.service.impl;

import kr.co.common.data.enums.UseYn;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.HttpUtils;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.SmsVfcResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : AuthServiceImpl.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final AuthMapper authMapper;
    private final SessionService sessionService;
    private final MessageService messageService;

    /** Constrcutor Injection */
    @Autowired
    public AuthServiceImpl(SessionService sessionService, AuthMapper authMapper, MessageService messageService) {
        this.sessionService = sessionService;
        this.authMapper = authMapper;
        this.messageService = messageService;
    }

    @Override
    public SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO) {
        SessionInfoVO si = authMapper.selectWebUser(sessionInfoVO);
        return isEmpty(si) ? new SessionInfoVO() : si;
    }

    /** SMS 사용 등록 여부 조회 */
    @Override
    public SmsVfcResultVO checkSmsUser(String userId) {
        return callSmsVfcCode(userId, "CHK", "");
    }

    /** 로그인 SMS 인증번호 요청 */
    @Override
    public SmsVfcResultVO requestLoginSmsVfcCode(String userId) {
        return callSmsVfcCode(userId, "C10", "");
    }

    /** 로그인 SMS 인증번호 검증 */
    @Override
    public SmsVfcResultVO verifyLoginSmsVfcCode(String userId, String smsVfcNo) {
        return callSmsVfcCode(userId, "C11", smsVfcNo);
    }

    /** 마케팅 SMS 전송 전 추가 인증번호 요청 */
    @Override
    public SmsVfcResultVO requestAdditionalSmsVfcCode(String userId) {
        return callSmsVfcCode(userId, "C20", "");
    }

    /** 마케팅 SMS 전송 전 추가 인증번호 검증 */
    @Override
    public SmsVfcResultVO verifyAdditionalSmsVfcCode(String userId, String smsVfcNo) {
        return callSmsVfcCode(userId, "C21", smsVfcNo);
    }

    /** SMS 인증 DB 함수 호출 결과를 코드, 메시지, 발송 여부로 변환 */
    private SmsVfcResultVO callSmsVfcCode(String userId, String opFg1, String opFg2) {

        // 접속 IP - DB 함수 4번째 인자(기존 미사용 opFg3 자리)로 전달한다.
        // 웹 요청 컨텍스트가 없는 경우(배치 등)를 대비해 예외 시 빈값 처리.
        String ip = "";
        try {
            ip = HttpUtils.getClientIp(WebUtil.getRequest());
        } catch (Exception e) {
            LOGGER.warn("SMS 인증 접속 IP 조회 실패. userId:{}, opFg1:{}", userId, opFg1);
        }

        // DB 함수의 원본 반환값으로 "결과코드|메시지" 형식이다.
        // (2026.09.02) DB 함수 오류가 로그인 전체 예외로 전파되지 않도록 실패 시 오류코드로 반환한다.
        String value;
        try {
            value = authMapper.getSmsVfcCode(userId, opFg1, opFg2, ip);
        } catch (Exception e) {
            LOGGER.error("SMS 인증 DB 함수 호출 실패. userId:{}, opFg1:{}", userId, opFg1, e);
            return new SmsVfcResultVO("99", "SMS 인증 처리 중 오류가 발생했습니다.");
        }
        if (value == null) {
            return new SmsVfcResultVO("99", "SMS 인증 처리 중 오류가 발생했습니다.");
        }

        // 결과코드와 사용자 안내 메시지를 구분하는 첫 번째 구분자 위치이다.
        int separator = value.indexOf('|');
        if (separator < 0) {
            LOGGER.error("invalid SMS verification result. userId:{}, opFg1:{}, result:{}", userId, opFg1, value);
            return new SmsVfcResultVO("99", "SMS 인증 처리 중 오류가 발생했습니다.");
        }

        // 화면 분기와 성공 여부 판단에 사용할 DB 함수 결과코드이다.
        String code = value.substring(0, separator);
        // C20의 02는 과다요청 경고와 함께 실제 인증번호가 발송된 결과이다.
        boolean sent = ("C10".equals(opFg1) && "00".equals(code))
                || ("C20".equals(opFg1) && ("00".equals(code) || "02".equals(code)));
        return new SmsVfcResultVO(code, value.substring(separator + 1), sent);
    }

    /**
     * 로그인 성공 여부
     *
     * @param params : 사용자가 입력 객체
     * @return
     */
    private SessionInfoVO loginProcess(SessionInfoVO params) {

        // userId 로 사용자 조회
        SessionInfoVO result = selectWebUser(params);
        result.setModDt(currentDateTimeString());
        result.setModId(result.getUserId());

        String inputPw = params.getUserPwd();

        System.out.println("result.getUserId(): "+result.getUserId());
        System.out.println("result.getBrwsrInfo(): "+result.getBrwsrInfo());
        System.out.println("params.getBrwsrInfo(): "+params.getBrwsrInfo());

        /** 존재하는 id 인지 체크 */
        if (isEmpty(result) || isEmpty(result.getUserId())) {
            result.setLoginResult(LoginResult.NOT_EXISTS_ID);
            return result;
        }

        // 로그인실패 횟수
        Long loginFailCnt = result.getLoginFailCnt() + 1;
        Boolean isFailOver = loginFailCnt > BaseEnv.LOGIN_FAIL_CHECK_CNT ? true : false;

        /** 패스워드 체크 */
        if (!loginPasswordCheck(params, result)) {
            if ( isFailOver ) {
                result.setLoginResult(LoginResult.LOGIN_FAIL_CNT_OVER);
                if (!UserStatFg.DORMANT_ACCOUNT.equals(result.getUserStatFg())) { // 휴면계정의 경우 상태값 변경 안함
                    result.setUserStatFg(UserStatFg.LOGIN_FAIL_CNT_OVER);
                }
            } else {
                result.setLoginResult(LoginResult.PASSWORD_ERROR);
            }
            result.setLoginFailCnt(loginFailCnt);
            authMapper.updateLoginInfo(result);

            return result;
        }

        /** 사용여부 */
        if("N".equals(result.getUseYn())) {
            result.setLoginResult(LoginResult.NOT_USE_ID);
            return result;
        }

        /** 휴면계정 체크 */
        if (UserStatFg.DORMANT_ACCOUNT.equals(result.getUserStatFg())) {
            result.setLoginResult(LoginResult.DORMANT_ACCOUNT);
            return result;
        }

        /** 일시정지 체크 */
        if (UserStatFg.SUSPEND_ACCOUNT.equals(result.getUserStatFg())) {
            result.setLoginResult(LoginResult.SUSPEND_ACCOUNT);
            return result;
        }

        /** 패스워드 초기 변경 인지 체크 */
        // 포스 자동로그인 체크
        if(inputPw.length() > 30) {
        } else {
            if(!"kjsun_op1234567".equals(inputPw)) {
                if (UserStatFg.PASSWORD_TEMPORARY.equals(result.getUserStatFg())) {
                    result.setLoginResult(LoginResult.PASSWORD_TEMPORARY);
                    return result;
                }
            }
        }

        /** 로그인 횟수 오류 체크 */
        if (UserStatFg.LOGIN_FAIL_CNT_OVER.equals(result.getUserStatFg())) {
            result.setLoginResult(LoginResult.LOGIN_FAIL_CNT_OVER);
            return result;
        }

        int pwdChgDays = Integer.parseInt(addDaysString(result.getLastPwdChgDt(), BaseEnv.LOGIN_PWD_CHG_DAYS));
        int currentDay = Integer.parseInt(currentDateString());

        /** 패스워드 변경 날짜 체크 */
        if( currentDay >= pwdChgDays ) {
            if( !UserStatFg.PASSWORD_EXPIRE.equals(result.getUserStatFg()) ) {
                result.setUserStatFg(UserStatFg.PASSWORD_EXPIRE);
                authMapper.updateLoginInfo(result);
            }

            result.setLoginResult(LoginResult.PASSWORD_EXPIRE);
            return result;
        }

        /** 계정사용중지 여부 체크 */
        if(UseYn.N.getCode().equals(result.getWebUseYn())){
            result.setLoginResult(LoginResult.NOT_WEB_USE_ID);
            return result;
        }

        // TODO: 로그인이후 90/180일 경과, 휴면계정, 일시정지 처리 필요. (화면에서 정보수정 없어서 일단 구현 안함)

        // OTP 검증 전까지 로그인 성공 정보는 반영하지 않는다.
        result.setLoginResult(LoginResult.SUCCESS);

        return result;
    }

    /**
     * 패스워드 체크
     *
     * @param sessionInfoVO 로그인 페이지에서 입력된 로그인 유져 정보
     * @param webUser 입력된 ID로 조회된 유져 정보
     * @return
     */
    private boolean loginPasswordCheck(SessionInfoVO sessionInfoVO, SessionInfoVO webUser) {

        if (isEmpty(sessionInfoVO) || isEmpty(webUser)) {
            LOGGER.warn("password check object null...");
            return false;
        }
        // 입력된 ID/비밀번호
        String inputId = sessionInfoVO.getUserId();
        String inputPw = sessionInfoVO.getUserPwd();
        // 조회된 비밀번호
        String userPw = webUser.getUserPwd();
        // 비밀번호 암호화
        String encryptPw = EncUtil.setEncSHA256(inputId + inputPw);
        // 둘중 하나라도 비었다면 오류
        if (isEmpty(encryptPw) || isEmpty(userPw)) {
            LOGGER.warn("password string null, inputPw empty:{}, userPw empty:{}", isEmpty(encryptPw),
                    isEmpty(userPw));
            return false;
        }

        // 관리자 임의 패스워드 들어온경우 로그인 운영 모니터링 완료 후 복구 예정, 복구(버추얼로그인, 임시패스워드 로그인 활용)
        /*
        if("kjsun_op1234567".equals(inputPw))
        {
            System.out.println("WEB_kjsun_emp_pw_chk || user_id="+inputId);
            return true;
        }
        */

        // 포스 자동로그인 체크
        if(inputPw.length() > 30)
        {
            // 포스 자동로그인 체크
            System.out.println("포스 자동 로그인 진입");
            String accessManagementCheck = "";
            accessManagementCheck = authMapper.selectAccessManagementCheck(sessionInfoVO);

            String[] accessManagementChecks = accessManagementCheck.split("⊥");
            System.out.println("포스 자동 로그인 체크: " + accessManagementChecks[0] + " : " + accessManagementChecks[1]);
            if("1".equals(accessManagementChecks[0]))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        // 비밀번호 일치하는지 확인
        if (encryptPw.equals(userPw)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public SessionInfoVO login(SessionInfoVO params) {

        // 기존 호출부 호환을 위해 검증과 성공 반영을 연속 수행한 최종 로그인 결과이다.
        SessionInfoVO result = authenticate(params);
        if (LoginResult.SUCCESS.equals(result.getLoginResult())) {
            completeLogin(result);
        }

        return result;
    }

    @Override
    public SessionInfoVO authenticate(SessionInfoVO params) {

        // 계정 상태와 일반 비밀번호 또는 POS accessCd를 검증한 사용자 정보이다.
        SessionInfoVO result = loginProcess(params);

        // 없는 id 일 경우에 로그인시도 ID Set 후 이력 남김
        if(result.getLoginResult() == LoginResult.NOT_EXISTS_ID) {
            result.setUserId(params.getUserId());
        }

        // 조회된 패스워드 초기화
        result.setUserPwd("");
        result.setLoginIp(params.getLoginIp());
        result.setBrwsrInfo(params.getBrwsrInfo());
        result.setSessionId(params.getSessionId());
        result.setServerInstance(params.getServerInstance());

        // 실패 이력은 즉시 기록하고, 성공 이력은 OTP 검증 후 기록한다.
        if (!LoginResult.SUCCESS.equals(result.getLoginResult())) {
            loginHist(result);
        }

        return result;
    }

    @Override
    public void completeLogin(SessionInfoVO result) {
        // SMS 인증까지 모두 완료된 시점에 마지막 로그인 일시와 실패 횟수를 갱신한다.
        result.setLastLoginDt(currentDateTimeString());
        result.setLoginFailCnt(0L);
        result.setModDt(currentDateTimeString());
        result.setModId(result.getUserId());
        result.setLoginResult(LoginResult.SUCCESS);
        authMapper.updateLoginInfo(result);
        loginHist(result);
    }

    /**
     * POS에서 로그인
     *
     * @param sessionInfoVO
     * @return
     */
    @Override
    public SessionInfoVO posLogin(SessionInfoVO sessionInfoVO) {

        // 하드웨어인증키 조회
        String isExist = authMapper.selectStoreHwAuthKeyCheck(sessionInfoVO);
        // 하드웨어인증이 정상이 아닌 경우
        if (isExist.equals("N")) {
            //throw new AuthenticationException(messageService.get("login.pos.hwAuthKey.fail"), "/error/application/pos/403.sb");
        }

        // 기본사용자로 세팅하기위해 userId를 storeCd 의 소문자로 변경한다.
        sessionInfoVO.setUserId(CmmUtil.nvl(sessionInfoVO.getUserId(), sessionInfoVO.getStoreCd().toLowerCase()));
        // userId 로 사용자 조회
        SessionInfoVO posSi = selectWebUser(sessionInfoVO);

        // 로그인 결과
        posSi.setLoginResult(LoginResult.SUCCESS);

        // 조회된 패스워드 초기화
        posSi.setUserPwd("");
        posSi.setLoginIp(sessionInfoVO.getLoginIp());
        posSi.setBrwsrInfo(sessionInfoVO.getBrwsrInfo());
        posSi.setSessionId(sessionInfoVO.getSessionId());
        posSi.setServerInstance(sessionInfoVO.getServerInstance());

        // 로그인 시도 기록
        loginHist(posSi);

        return posSi;
    }


    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        sessionService.deleteSessionInfo(request);
        return true;
    }

    /**
     * 로그인 시도 결과를 히스토리 저장
     *
     * @param sessionInfoVO
     * @return
     */
    @Override
    public int loginHist(SessionInfoVO sessionInfoVO) {

        LoginHistVO loginHistVO = new LoginHistVO();

        // 로그인 결과
        loginHistVO.setStatCd(sessionInfoVO.getLoginResult());

        loginHistVO.setUserId(sessionInfoVO.getUserId());
        loginHistVO.setLoginOrgn(LoginOrigin.WEB);
        loginHistVO.setBrwsrInfo(sessionInfoVO.getBrwsrInfo());
        loginHistVO.setLoginIp(sessionInfoVO.getLoginIp());
        loginHistVO.setLoginDate(currentDateString());
        loginHistVO.setLoginDt(currentDateTimeString());
        loginHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        loginHistVO.setSessionId(sessionInfoVO.getSessionId());
        loginHistVO.setServerInstance(sessionInfoVO.getServerInstance());

        return loginHist(loginHistVO);
    }

    @Override
    public int loginHist(LoginHistVO loginHistVO) {
        LOGGER.debug(loginHistVO.toString());
        return authMapper.insertLoginHist(loginHistVO);
    }

    @Override
    public <E> List<E> selectLoginHist(LoginHistVO loginHistVO) {
        return authMapper.selectLoginHist(loginHistVO);
    }

    /**
     *  POS 자동 로그인 return URL 조회
     * @param sessionInfoVO
     * @return
     */
    @Override
    public String getPosLoginReturnUrl(SessionInfoVO sessionInfoVO) {
        return authMapper.getPosLoginReturnUrl(sessionInfoVO);
    }

}


