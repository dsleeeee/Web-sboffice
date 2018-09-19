package kr.co.common.service.session.impl;

import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static kr.co.common.utils.spring.StringUtil.convertToJson;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author 정용길
 *
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisConnService redisConnService;
    @Autowired
    AuthService authService;
    @Autowired
    CmmMenuService cmmMenuService;
    @Autowired
    private RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate;

    private static final String SESSION_KEY = "SBSESSIONID";

    @Override
    public String setSessionInfo( HttpServletRequest request, HttpServletResponse response,
            SessionInfoVO sessionInfoVO ) {
        String sessionId = request.getSession().getId();

        // sessionId 세팅
        sessionInfoVO.setSessionId( sessionId );
        // 권한 있는 메뉴 저장
        sessionInfoVO.setAuthMenu( authService.selectAuthMenu( sessionInfoVO ) );
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
        // 세션 담기
        SessionUtil.setEnv(request.getSession(), sessionInfoVO.getSessionId(), sessionInfoVO);

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
     * @param sessionInfoVO {@link SessionInfoVO}
     */
    private void setSessionInfo( String sessionId, SessionInfoVO sessionInfoVO ) {
        if ( redisConnService.isAvailable() ) {
            try {
                redisCustomTemplate.set( redisCustomTemplate.makeKey( sessionId ), sessionInfoVO,
                        BaseEnv.SESSION_TIMEOUT_MIN, TimeUnit.MINUTES );
            } catch ( Exception e ) {
                LOGGER.error( "Redis server not available!! setSessionInfo {}", e );
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
                            BaseEnv.SESSION_TIMEOUT_MIN, TimeUnit.MINUTES );
                }
            } catch ( Exception e ) {
                LOGGER.error( "Redis server not available!! getSessionInfo {}", e );
                redisConnService.disable();
            }
        }
        return sessionInfoVO;
    }

    @Override
    public SessionInfoVO getSessionInfo( HttpServletRequest request ) {
        Cookie cookie = WebUtils.getCookie( request, SESSION_KEY );
        String sessionId = cookie == null ? request.getParameter( SESSION_KEY ) : cookie.getValue();
        // 가상로그인 사용시에는 파라미터로 세션ID를 달고 다니기 때문에 별도 체크로직 추가 : 20180817 노현수
        // 가상로그인 사용시 세션ID 파라미터로 체크하여 메인세션정보를 무엇으로 할지 지정한다 : 20180904 노현수
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            sessionId = request.getParameter("sid");
        }
        SessionInfoVO sessionInfoVO = getSessionInfo(sessionId);

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
                LOGGER.error( "Redis server not available!! deleteSessionInfo {}", e );
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


