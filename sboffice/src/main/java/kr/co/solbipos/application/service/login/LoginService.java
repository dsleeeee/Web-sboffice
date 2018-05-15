package kr.co.solbipos.application.service.login;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.solbipos.application.domain.login.LoginHistVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.domain.resource.ResrceInfoVO;

/**
 *
 * @author 정용길
 *
 */
public interface LoginService {

    /**
     * web user 조회
     *
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO);

    /**
     * 로그인
     *
     * @param request
     * @param response
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO login(SessionInfoVO sessionInfoVO);

    /**
     * 로그아웃
     *
     * @param request
     * @param response
     * @return
     */
    boolean logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 로그인 시도 결과를 히스토리 저장
     *
     * @param sessionInfoVO
     * @return
     */
    public int loginHist(SessionInfoVO sessionInfoVO);

    /**
      * 로그인 히스토리 저장
      *
      * @param loginHistVO
      * @return
      */
    int loginHist(LoginHistVO loginHistVO);

    /**
      * 로그인 히스토리 조회
      * @param loginHistVO
      * @return
      */
    <E> List<E> selectLoginHist(LoginHistVO loginHistVO);

    /**
      * 권한 있는 메뉴
      *
      * @param sessionInfoVO
      * @return
      */
    List<ResrceInfoVO> selectAuthMenu(SessionInfoVO sessionInfoVO);
}
