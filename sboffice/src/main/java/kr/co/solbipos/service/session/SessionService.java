package kr.co.solbipos.service.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * 세션 관리 서비스
 * 
 * @author 정용길
 */
public interface SessionService {

    /**
      * 로그인 성공시에 세션을 세팅해서 레디스에 저장
      * 
      * @param request {@link HttpServletRequest}
      * @param response {@link HttpServletResponse}
      * @param sessionInfo {@link SessionInfo}
      * @return 생성된 {@code String} 세션 ID
      */
    String setSessionInfo(HttpServletRequest request, HttpServletResponse response,
            SessionInfo sessionInfo);

    /**
     * sessionInfo update
     * 
      * @param sessionInfo {@link SessionInfo}
     * @return 저장된 {@code String} 세션 ID
     */
    String setSessionInfo(SessionInfo sessionInfo);

    /**
     * sessionInfo get
     * 
     * @param sessionId {@code String} 세션 ID
     * @return {@link SessionInfo}
     */
    SessionInfo getSessionInfo(String sessionId);

    /**
     * sessionInfo get
     * 
     * @param request {@link HttpServletRequest}
     * @return {@link SessionInfo}
     */
    SessionInfo getSessionInfo(HttpServletRequest request);

    /**
     * sessionInfo get
     * 
     * @return {@link SessionInfo} 
     */
    SessionInfo getSessionInfo();

    /**
     * 세션 유효성 검증
     * 
     * @param request {@link HttpServletRequest}
     * @return {@link boolean} <br> {@code true} 유효한 세션 <br> {@code false} 유효하지 않은 세션
     */
    boolean isValidSession(HttpServletRequest request);

    /**
     * 세션 유효성 검증
     * 
     * @param sessionId {@code }
     * @return {@link boolean} <br> {@code true} 유효한 세션 <br> {@code false} 유효하지 않은 세션
     * @see SessionService#getSessionInfo(String)
     */
    boolean isValidSession(String sessionId);

    /**
     * 세션 삭제
     * 
     * @param sessionId {@link String}
     */
    void deleteSessionInfo(String sessionId);

    /**
      * 세션 삭제
      * 
      * @param request {@link HttpServletRequest}
      */
    void deleteSessionInfo(HttpServletRequest request);

    /**
     * 세션 삭제
     * 
     * @param sessionInfo {@link SessionInfo}
     */
    void deleteSessionInfo(SessionInfo sessionInfo);

}
