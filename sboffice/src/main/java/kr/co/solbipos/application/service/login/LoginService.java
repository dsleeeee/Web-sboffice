package kr.co.solbipos.application.service.login;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.solbipos.application.domain.login.LoginHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.enums.login.LoginResult;

/**
 * 
 * @author 정용길
 *
 */
public interface LoginService {

    /**
     * web user 조회
     * 
     * @param sessionInfo
     * @return
     */
    SessionInfo selectWebUser(SessionInfo sessionInfo);

    /**
     * 로그인
     * 
     * @param request
     * @param response
     * @param sessionInfo
     * @return
     */
    SessionInfo login(SessionInfo sessionInfo);

    /**
     * 로그아웃
     * 
     * @param request
     * @param response
     * @return
     */
    boolean logout(HttpServletRequest request, HttpServletResponse response);

    /**
      * 로그인 히스토리 저장
      * 
      * @param loginHist
      * @return
      */
    int loginHist(LoginHist loginHist);

    /**
      * 로그인 히스토리 조회
      * @param loginHist
      * @return
      */
    <E> List<E> selectLoginHist(LoginHist loginHist);
    
    /**
      * 권한 있는 메뉴
      * 
      * @param sessionInfo
      * @return
      */
    List<ResrceInfo> selectAuthMenu(SessionInfo sessionInfo);
}
