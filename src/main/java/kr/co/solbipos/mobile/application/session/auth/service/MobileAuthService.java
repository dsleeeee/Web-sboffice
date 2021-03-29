package kr.co.solbipos.mobile.application.session.auth.service;

import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileAuthService.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileAuthService {

    /**
     * web user 조회
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO);

    /**
     * 로그인
     *
     * @param sessionInfoVO SessionInfoVO
     * @return
     */
    SessionInfoVO login(SessionInfoVO sessionInfoVO);

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
     * 자동로그인 Serial 업데이트
     * @param sessionInfoVO
     * @return
     */
    int updateLoginAutoSerial(SessionInfoVO sessionInfoVO);

}
