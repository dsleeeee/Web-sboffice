package kr.co.solbipos.application.session.auth.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : AuthService.java
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
public interface AuthService {

    /**
     * web user 조회
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO);

    /** USER_ID 기준 SMS 사용 등록 여부를 CHK 함수로 조회 */
    SmsVfcResultVO checkSmsUser(String userId);

    /** 로그인 SMS 인증번호를 C10 함수로 생성 및 발송 */
    SmsVfcResultVO requestLoginSmsVfcCode(String userId);

    /** 사용자가 입력한 로그인 SMS 인증번호를 C11 함수로 검증 */
    SmsVfcResultVO verifyLoginSmsVfcCode(String userId, String smsVfcNo);

    /** 마케팅 SMS 전송 전 추가 인증번호를 C20 함수로 생성 및 발송 */
    SmsVfcResultVO requestAdditionalSmsVfcCode(String userId);

    /** 사용자가 입력한 마케팅 SMS 전송 추가 인증번호를 C21 함수로 검증 */
    SmsVfcResultVO verifyAdditionalSmsVfcCode(String userId, String smsVfcNo);

    /** 로그인 성공 정보는 반영하지 않고 계정 및 비밀번호/accessCd만 검증 */
    SessionInfoVO authenticate(SessionInfoVO sessionInfoVO);

    /** SMS 인증까지 완료된 로그인 성공 정보와 성공 이력을 최종 반영 */
    void completeLogin(SessionInfoVO sessionInfoVO);

    /**
     * 로그인
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    SessionInfoVO login(SessionInfoVO sessionInfoVO);

    /**
     * POS 로그인
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    SessionInfoVO posLogin(SessionInfoVO sessionInfoVO);

    /**
     * 로그아웃
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return
     */
    boolean logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 로그인 시도 결과를 히스토리 저장
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    public int loginHist(SessionInfoVO sessionInfoVO);

    /**
      * 로그인 히스토리 저장
      *
      * @param loginHistVO LoginHistVO
      * @return
      */
    int loginHist(LoginHistVO loginHistVO);

    /**
      * 로그인 히스토리 조회
      * @param loginHistVO LoginHistVO
      * @return
      */
    <E> List<E> selectLoginHist(LoginHistVO loginHistVO);

    /**
     *  POS 자동 로그인 return URL 조회
     * @param sessionInfoVO
     * @return
     */
    String getPosLoginReturnUrl(SessionInfoVO sessionInfoVO);

}
