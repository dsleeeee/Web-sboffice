package kr.co.solbipos.application.persistence.login;

import java.util.List;
import kr.co.solbipos.application.domain.login.LoginHistVO;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.domain.resource.ResrceInfoVO;

/**
 *
 * @author 정용길
 *
 */
public interface LoginMapper {

    /**
     * web user 조회
     *
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO);

    /**
     * 로그인 시도 저장
     *
     * @param loginHistVO
     * @return
     */
    int insertLoginHist(LoginHistVO loginHistVO);

    /**
     *
     * @param loginHistVO
     * @return
     */
    <E> List<E> selectLoginHist(LoginHistVO loginHistVO);


    /**
      * 권한 있는 메뉴 리스트 조회
      * @param sessionInfoVO
      * @return
      */
    List<ResrceInfoVO> selectAuthMenu(SessionInfoVO sessionInfoVO);
}
