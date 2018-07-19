package kr.co.common.utils;

import javax.servlet.http.HttpSession;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

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
    public static SessionInfoVO getEnv(HttpSession session, String loginId) {
        return (SessionInfoVO) session.getAttribute(loginId);
    }
    
    /** 세션에 가상로그인 ID 환경정보 저장. */
    public static void setEnv(HttpSession session, String loginId, SessionInfoVO env) {
        session.setAttribute(loginId, env);
    }
    
    /** 세션에 가상로그인 ID 환경정보 삭제. */
    public static void removeEnv(HttpSession session, String loginId) {
        session.removeAttribute(loginId);
    }
    
}
