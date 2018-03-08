package kr.co.solbipos.service.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.solbipos.application.domain.login.SessionInfo;

/**
 * @author 정용길 세션 관리 서비스
 */
/**
 * @author 정용길
 *
 */
public interface SessionService {

    /**
     * sessionInfo save
     * 
     * @param request
     * @param sessionInfo
     * @return
     */
    String setSessionInfo(HttpServletRequest request, HttpServletResponse response,
            SessionInfo sessionInfo);
    
    /**
      * sessionInfo update
      * 
      * @param sessionInfo
      * @return
      */
    String setSessionInfo(SessionInfo sessionInfo);

    /**
     * sessionInfo get
     * 
     * @param sessionId
     * @return
     */
    SessionInfo getSessionInfo(String sessionId);

    /**
     * sessionInfo get
     * 
     * @param sessionInfo
     * @return
     */
    SessionInfo getSessionInfo(SessionInfo sessionInfo);

    /**
     * 
     * @param request
     * @return
     */
    SessionInfo getSessionInfo(HttpServletRequest request);
    
    /**
      * 
      * @return
      */
    SessionInfo getSessionInfo();

    /**
     * 
     * @param request
     * @return
     */
    boolean isValidSession(HttpServletRequest request);

    /**
     * 
     * @param sessionId
     * @return
     */
    boolean isValidSession(String sessionId);

    /**
     * 
     * @param sessionId
     */
    void deleteSessionInfo(String sessionId);

    /**
     * 
     * @param request
     */
    void deleteSessionInfo(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * @param sessionInfo
     */
    void deleteSessionInfo(SessionInfo sessionInfo);

}
