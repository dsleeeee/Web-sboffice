package kr.co.solbipos.application.persistence.login;

import java.util.List;
import kr.co.solbipos.application.domain.login.LoginHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;

/**
 * 
 * @author 정용길
 *
 */
public interface LoginMapper {

    /**
     * web user 조회
     * 
     * @param sessionInfo
     * @return
     */
    SessionInfo selectWebUser(SessionInfo sessionInfo);

    /**
     * 로그인 시도 저장
     * 
     * @param loginHist
     * @return
     */
    int insertLoginHist(LoginHist loginHist);

    /**
     * 
     * @param loginHist
     * @return
     */
    <E> List<E> selectLoginHist(LoginHist loginHist);
    
    
    /**
      * 권한 있는 메뉴 리스트 조회
      * @param sessionInfo
      * @return
      */
    List<ResrceInfo> selectAuthMenu(SessionInfo sessionInfo);
}
