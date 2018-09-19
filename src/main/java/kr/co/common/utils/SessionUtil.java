package kr.co.common.utils;

import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    
    static SessionUtil sessionUtil = new SessionUtil();
    
    /** 세션의 사용자 환경정보 반환 */
    public static SessionInfoVO getEnv(HttpSession session) {
        return (SessionInfoVO) session.getAttribute(BaseEnv.PROP_ENV);
    }
    
    /** 세션에 사용자 환경정보 저장. */
    public static void setEnv(HttpSession session, SessionInfoVO env) {
        session.setAttribute(BaseEnv.PROP_ENV, env);
    }

    /** 세션의 가상로그인 ID 환경정보 반환 */
    public static SessionInfoVO getEnv(HttpSession session, String sessionId) {
        return (SessionInfoVO) session.getAttribute(sessionId);
    }
    
    /** 세션에 가상로그인 ID 환경정보 저장. */
    public static void setEnv(HttpSession session, String sessionId, SessionInfoVO env) {
        session.setAttribute(sessionId, env);
    }
    
    /** 세션에 가상로그인 ID 환경정보 삭제. */
    public static void removeEnv(HttpSession session, String sessionId) {
        session.removeAttribute(sessionId);
    }
    
}
