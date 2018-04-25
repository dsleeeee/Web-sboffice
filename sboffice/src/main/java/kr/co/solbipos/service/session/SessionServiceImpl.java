package kr.co.solbipos.service.session;

import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.WebUtils;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.user.OrgnFg;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.service.cmm.CmmMenuService;
import kr.co.solbipos.service.redis.RedisConnService;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.system.RedisCustomTemplate;
import kr.co.solbipos.utils.spring.WebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길
 *
 */
@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    Prop prop;

    @Autowired
    RedisConnService redisConnService;

    @Autowired
    LoginService loginService;

    @Autowired
    CmmMenuService cmmMenuService;

    @Autowired
    private RedisCustomTemplate<String, SessionInfo> redisCustomTemplate;

    private static final String SESSION_KEY = "SBSESSIONID";

    @Override
    public String setSessionInfo(HttpServletRequest request, HttpServletResponse response,
            SessionInfo sessionInfo) {
        String sessionId = generateUUID();

        // sessionId 세팅
        sessionInfo.setSessionId(sessionId);
        // 권한 있는 메뉴 저장
        sessionInfo.setAuthMenu(loginService.selectAuthMenu(sessionInfo));
        // 고정 메뉴 리스트 저장
        sessionInfo.setFixMenu(cmmMenuService.selectFixingMenu(sessionInfo));
        // 즐겨찾기 메뉴 리스트 저장
        sessionInfo.setBkmkMenu(cmmMenuService.selectBkmkMenu(sessionInfo));
        // 전체메뉴 조회(리스트)
        sessionInfo.setMenuData(convertToJson(cmmMenuService.makeMenu(sessionInfo, "A")));
        // 즐겨찾기메뉴 조회 (리스트)
        sessionInfo.setBkmkData(convertToJson(cmmMenuService.makeMenu(sessionInfo, "F")));
        
        // 본사는 소속된 가맹점을 세션에 저장
        if(sessionInfo.getOrgnFg() == OrgnFg.HQ) {
            List<String> storeCdList = cmmMenuService.selectStoreCdList(sessionInfo.getOrgnCd());
            sessionInfo.setArrStoreCdList(storeCdList);
        }

        // redis에 세션 세팅
        setSessionInfo(sessionId, sessionInfo);

        // 쿠키 생성
        makeCookie(sessionId);
        return sessionId;
    }
    
    @Override
    public String setSessionInfo(SessionInfo sessionInfo) {
        String sessionId = sessionInfo.getSessionId();
        // redis에 세션 세팅
        setSessionInfo(sessionId, sessionInfo);
        return sessionId;
    }

    /**
     * 레디스에 sessionInfo 객체 저장
     * 
     * @param sessionId {@link String} 세션 ID
     * @param sessionInfo {@link SessionInfo}
     */
    private void setSessionInfo(String sessionId, SessionInfo sessionInfo) {
        if (redisConnService.isAvailable()) {
            try {
                redisCustomTemplate.set(redisCustomTemplate.makeKey(sessionId), sessionInfo,
                        prop.sessionTimeout, TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("Redis server not available!! setSessionInfo {}", e);
                redisConnService.disable();
            }
        }
    }

    @Override
    public SessionInfo getSessionInfo(String sessionId) {
        SessionInfo sessionInfo = null;
        if (redisConnService.isAvailable()) {
            try {
                sessionInfo = redisCustomTemplate.get(redisCustomTemplate.makeKey(sessionId));
                if (!ObjectUtils.isEmpty(sessionInfo)) {
                    // 세션 타임 연장
                    redisCustomTemplate.expire(redisCustomTemplate.makeKey(sessionId),
                            prop.sessionTimeout, TimeUnit.MINUTES);
                }
            } catch (Exception e) {
                log.error("Redis server not available!! getSessionInfo {}", e);
                redisConnService.disable();
            }
        }
        return sessionInfo;
    }

    @Override
    public SessionInfo getSessionInfo(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(request, SESSION_KEY);
        String sessionId = cookie == null ? request.getParameter(SESSION_KEY) : cookie.getValue();

        // HttpSession session = request.getSession();
        // String sessionId = session.getId();

        SessionInfo sessionInfo = getSessionInfo(sessionId);
        return sessionInfo;
    }

    @Override
    public SessionInfo getSessionInfo() {
        return getSessionInfo(WebUtil.getRequest());
    }

    @Override
    public boolean isValidSession(HttpServletRequest request) {
        SessionInfo sessionInfo = getSessionInfo(request);

        // 세션 객체가 없는 경우
        if (isEmpty(sessionInfo)) {
            return false;
        }
        // 세션 객체는 있지만 필수값들이 없는 경우
        else {
            if (isEmpty(sessionInfo.getUserId()) && isEmpty(sessionInfo.getAuthMenu())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValidSession(String sessionId) {
        return getSessionInfo(sessionId) != null;
    }

    @Override
    public void deleteSessionInfo(String sessionId) {
        if (redisConnService.isAvailable()) {
            try {
                redisCustomTemplate.delete(redisCustomTemplate.makeKey(sessionId));
            } catch (Exception e) {
                log.error("Redis server not available!! deleteSessionInfo {}", e);
                redisConnService.disable();
            }
        }
    }

    @Override
    public void deleteSessionInfo(HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(request)) {
            SessionInfo sessionInfo = getSessionInfo(request);
            if (!ObjectUtils.isEmpty(sessionInfo)) {

                // redis
                String sessionId = sessionInfo.getSessionId();
                deleteSessionInfo(sessionId);

                // cookie
                deleteCookie(request);
            }
        }
    }

    @Override
    public void deleteSessionInfo(SessionInfo sessionInfo) {
        if (!ObjectUtils.isEmpty(sessionInfo)) {
            deleteSessionInfo(sessionInfo.getSessionId());
            deleteCookie(WebUtil.getRequest());
        }
    }

    /**
     * 쿠키에 session id 삭제
     * 
     * @param request {@link HttpServletRequest}
     */
    private void deleteCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, SESSION_KEY);
        WebUtil.removeCookie(cookie);
    }

    /**
     * 쿠키 session id 생성
     * 
     * @param sessionId {@link String} 세션 ID
     */
    private void makeCookie(String sessionId) {
        WebUtil.setCookie(SESSION_KEY, sessionId, -1);
    }

}


