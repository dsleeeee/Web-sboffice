package kr.co.common.service.session.impl;

import kr.co.common.service.cmm.CmmMenuService;
import kr.co.common.service.redis.RedisConnService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.template.RedisCustomTemplate;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.spring.WebUtil;
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
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Class Name : SessionServiceImpl.java
 * @Description : 세션관련
 * @Modification Information
 * @
 * @ 수정일       수정자      수정내용
 * @ ----------  ---------  -------------------------------
 * @ 2018.10.23  노현수      부분수정 : 로직개선...
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {
    
    private static final String SESSION_KEY = "SBSESSIONID";

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RedisConnService redisConnService;
    private final CmmMenuService cmmMenuService;
    private final RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate;

    /** Constructor Injection */
    @Autowired
    public SessionServiceImpl(RedisConnService redisConnService,
        CmmMenuService cmmMenuService,
        RedisCustomTemplate<String, SessionInfoVO> redisCustomTemplate) {
        this.redisConnService = redisConnService;
        this.cmmMenuService = cmmMenuService;
        this.redisCustomTemplate = redisCustomTemplate;
    }

    /**
     * 레디스에 sessionInfo 객체 저장
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param sessionInfoVO SessionInfoVO
     */
    @Override
    public String setSessionInfo( HttpServletRequest request, HttpServletResponse response,
            SessionInfoVO sessionInfoVO ) {
        String sessionId = request.getSession().getId();

        // sessionId 세팅
        sessionInfoVO.setSessionId(sessionId);
        // 사용자의 메뉴 리스트 Set : 권한포함
        sessionInfoVO.setMenuData(cmmMenuService.getUserMenuList(sessionInfoVO));
        // 즐겨찾기 메뉴 리스트 Set
        sessionInfoVO.setBkmkMenuData(cmmMenuService.getBkmkMenuList(sessionInfoVO));
        // 고정 메뉴 리스트 Set
        sessionInfoVO.setFixedMenuData(cmmMenuService.getFixedMenuList(sessionInfoVO));

        // 로고이미지 구분(파일여부 체크)
        String path = BaseEnv.FILE_UPLOAD_DIR + "logo_img/";
//        String path = "D:\\" + "logo_img/";

        File file1 = new File(path + sessionInfoVO.getHqOfficeCd() + ".PNG");
        File file2 = new File(path + sessionInfoVO.getHqOfficeCd() + ".JPG");

        boolean isExists1 = file1.exists();
        boolean isExists2 = file2.exists();

        if(isExists1 ||isExists2) {
            sessionInfoVO.setLogoImg("Y");
        } else {
            sessionInfoVO.setLogoImg("N");
        }

        // 본사는 소속된 가맹점을 세션에 저장
        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            List<String> storeCdList =
                    cmmMenuService.getStoreCdList( sessionInfoVO.getOrgnCd() );
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

    /**
     * 레디스에 sessionInfo 객체 저장
     *
     * @param sessionInfoVO SessionInfoVO
     */
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

    /**
     * 세션정보 가져오기 : 레디스에서 sessionId 로 가져온다.
     *
     * @param sessionId String : 세션 ID
     */
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

    /**
     * 세션정보 가져오기 : 레디스에서 HttpServletRequest 로 가져온다.
     *
     * @param request HttpServletRequest : 세션 ID
     */
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

    /**
     * 세션정보 가져오기 : HttpServletRequest 취득 하여 레디스에서 가져온다.
     *
     */
    @Override
    public SessionInfoVO getSessionInfo() {
        return getSessionInfo( WebUtil.getRequest() );
    }

    /**
     * 세션정보 유효 여부 확인 : 세션 존재하는지 판단하여 true/false 반환
     *
     * @param request HttpServletRequest
     */
    @Override
    public boolean isValidSession( HttpServletRequest request ) {
        SessionInfoVO sessionInfoVO = getSessionInfo(request);

        // 세션 객체가 없는 경우
        if ( sessionInfoVO == null ) {
            return false;
        } else {
            // 세션 객체는 있지만 필수값들이 없는 경우
            if ( sessionInfoVO.getUserId() == null && sessionInfoVO.getMenuData() == null ) {
                return false;
            }
        }

        return true;
    }

    /**
     * 세션정보 유효 여부 확인 : 세션 존재하는지 판단하여 true/false 반환
     *
     * @param sessionId String
     */
    @Override
    public boolean isValidSession( String sessionId ) {
        return getSessionInfo( sessionId ) != null;
    }

    /**
     * 세션정보 삭제 : sessionId 이용하여 redis에서 삭제처리
     *
     * @param sessionId String
     */
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

    /**
     * 세션정보 삭제 : HttpServletRequest 이용하여 redis에서 삭제처리
     *
     * @param request HttpServletRequest
     */
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

    /**
     * 세션정보 삭제 : sessionInfoVO 이용하여 redis에서 삭제처리
     *
     * @param sessionInfoVO SessionInfoVO
     */
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
     * @param request HttpServletRequest
     */
    private void deleteCookie( HttpServletRequest request ) {
        Cookie cookie = WebUtils.getCookie( request, SESSION_KEY );
        WebUtil.removeCookie( cookie );
    }

    /**
     * 쿠키 session id 생성
     *
     * @param sessionId String : 세션 ID
     */
    private void makeCookie( String sessionId ) {
        WebUtil.setCookie( SESSION_KEY, sessionId, -1 );
    }

}


