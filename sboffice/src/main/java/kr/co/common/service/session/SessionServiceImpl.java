package kr.co.common.service.session;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static kr.co.common.utils.spring.StringUtil.generateUUID;
import static org.springframework.util.StringUtils.isEmpty;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.WebUtils;
import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.system.Prop;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.enums.user.OrgnFg;
import kr.co.solbipos.application.service.login.LoginService;
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
    private RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate;

    private static final String SESSION_KEY = "SBSESSIONID";

    @Override
    public String setSessionInfo( HttpServletRequest request, HttpServletResponse response,
            SessionInfoVO sessionInfoVO ) {
        String sessionId = generateUUID();

        // sessionId 세팅
        sessionInfoVO.setSessionId( sessionId );
        // 권한 있는 메뉴 저장
        sessionInfoVO.setAuthMenu( loginService.selectAuthMenu( sessionInfoVO ) );
        // 고정 메뉴 리스트 저장
        sessionInfoVO.setFixMenu( cmmMenuService.selectFixingMenu( sessionInfoVO ) );
        // 즐겨찾기 메뉴 리스트 저장
        sessionInfoVO.setBkmkMenu( cmmMenuService.selectBkmkMenu( sessionInfoVO ) );
        // 전체메뉴 조회(리스트)
        sessionInfoVO.setMenuData( convertToJson( cmmMenuService.makeMenu( sessionInfoVO, "A" ) ) );
        // 즐겨찾기메뉴 조회 (리스트)
        sessionInfoVO.setBkmkData( convertToJson( cmmMenuService.makeMenu( sessionInfoVO, "F" ) ) );
        // 고정 메뉴 조회 (리스트)
        sessionInfoVO.setFixData( convertToJson(sessionInfoVO.getFixMenu()) );

        // 본사는 소속된 가맹점을 세션에 저장
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            List<String> storeCdList =
                    cmmMenuService.selectStoreCdList( sessionInfoVO.getOrgnCd() );
            sessionInfoVO.setArrStoreCdList( storeCdList );
        }

        // redis에 세션 세팅
        setSessionInfo( sessionId, sessionInfoVO );

        // 쿠키 생성
        makeCookie( sessionId );
        return sessionId;
    }

    @Override
    public String setSessionInfo( SessionInfoVO sessionInfoVO ) {
        String sessionId = sessionInfoVO.getSessionId();
        // redis에 세션 세팅
        setSessionInfo( sessionId, sessionInfoVO );
        return sessionId;
    }

    /**
     * 레디스에 sessionInfo 객체 저장
     *
     * @param sessionId {@link String} 세션 ID
     * @param sessionInfo {@link SessionInfoVO}
     */
    private void setSessionInfo( String sessionId, SessionInfoVO sessionInfoVO ) {
        if ( redisConnService.isAvailable() ) {
            try {
                redisCustomTemplate.set( redisCustomTemplate.makeKey( sessionId ), sessionInfoVO,
                        prop.sessionTimeout, TimeUnit.MINUTES );
            } catch ( Exception e ) {
                log.error( "Redis server not available!! setSessionInfo {}", e );
                redisConnService.disable();
            }
        }
    }

    @Override
    public SessionInfoVO getSessionInfo( String sessionId ) {
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        if ( redisConnService.isAvailable() ) {
            try {
                sessionInfoVO = redisCustomTemplate.get( redisCustomTemplate.makeKey( sessionId ) );
                if ( !ObjectUtils.isEmpty( sessionInfoVO ) ) {
                    // 세션 타임 연장
                    redisCustomTemplate.expire( redisCustomTemplate.makeKey( sessionId ),
                            prop.sessionTimeout, TimeUnit.MINUTES );
                }
            } catch ( Exception e ) {
                log.error( "Redis server not available!! getSessionInfo {}", e );
                redisConnService.disable();
            }
        }
        return sessionInfoVO;
    }

    @Override
    public SessionInfoVO getSessionInfo( HttpServletRequest request ) {

        Cookie cookie = WebUtils.getCookie( request, SESSION_KEY );
        String sessionId = cookie == null ? request.getParameter( SESSION_KEY ) : cookie.getValue();

        // HttpSession session = request.getSession();
        // String sessionId = session.getId();

        SessionInfoVO sessionInfoVO = getSessionInfo( sessionId );
        return sessionInfoVO;
    }

    @Override
    public SessionInfoVO getSessionInfo() {
        return getSessionInfo( WebUtil.getRequest() );
    }

    @Override
    public boolean isValidSession( HttpServletRequest request ) {
        SessionInfoVO sessionInfoVO = getSessionInfo( request );

        // 세션 객체가 없는 경우
        if ( isEmpty( sessionInfoVO ) ) {
            return false;
        }
        // 세션 객체는 있지만 필수값들이 없는 경우
        else {
            if ( isEmpty( sessionInfoVO.getUserId() ) && isEmpty( sessionInfoVO.getAuthMenu() ) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValidSession( String sessionId ) {
        return getSessionInfo( sessionId ) != null;
    }

    @Override
    public void deleteSessionInfo( String sessionId ) {
        if ( redisConnService.isAvailable() ) {
            try {
                redisCustomTemplate.delete( redisCustomTemplate.makeKey( sessionId ) );
            } catch ( Exception e ) {
                log.error( "Redis server not available!! deleteSessionInfo {}", e );
                redisConnService.disable();
            }
        }
    }

    @Override
    public void deleteSessionInfo( HttpServletRequest request ) {
        if ( !ObjectUtils.isEmpty( request ) ) {
            SessionInfoVO sessionInfoVO = getSessionInfo( request );
            if ( !ObjectUtils.isEmpty( sessionInfoVO ) ) {

                // redis
                String sessionId = sessionInfoVO.getSessionId();
                deleteSessionInfo( sessionId );

                // cookie
                deleteCookie( request );
            }
        }
    }

    @Override
    public void deleteSessionInfo( SessionInfoVO sessionInfoVO ) {
        if ( !ObjectUtils.isEmpty( sessionInfoVO ) ) {
            deleteSessionInfo( sessionInfoVO.getSessionId() );
            deleteCookie( WebUtil.getRequest() );
        }
    }

    /**
     * 쿠키에 session id 삭제
     *
     * @param request {@link HttpServletRequest}
     */
    private void deleteCookie( HttpServletRequest request ) {
        Cookie cookie = WebUtils.getCookie( request, SESSION_KEY );
        WebUtil.removeCookie( cookie );
    }

    /**
     * 쿠키 session id 생성
     *
     * @param sessionId {@link String} 세션 ID
     */
    private void makeCookie( String sessionId ) {
        WebUtil.setCookie( SESSION_KEY, sessionId, -1 );
    }

}


