package kr.co.solbipos.mobile.application.session.auth.service.impl;

import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileAuthMapper.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileAuthMapper {

    /**
     * web user 조회
     *
     * @param sessionInfoVO
     * @return
     */
    SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO);

    /** 로그인 정보 업데이트 */
    int updateLoginInfo(SessionInfoVO sessionInfoVO);

    /**
     * 로그인 시도 저장
     *
     * @param loginHistVO
     * @return
     */
    int insertLoginHist(LoginHistVO loginHistVO);

    /** 자동로그인 Serial 업데이트 */
    int updateLoginAutoSerial(SessionInfoVO sessionInfoVO);

}
