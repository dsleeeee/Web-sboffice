package kr.co.solbipos.application.session.auth.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.solbipos.application.common.service.ResrceInfoVO;

/**
 * @Class Name : AuthService.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AuthService {

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
