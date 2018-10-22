package kr.co.solbipos.application.session.auth.service.impl;

import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : AuthMapper.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface AuthMapper {

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

    /**
     *
     * @param loginHistVO
     * @return
     */
    <E> List<E> selectLoginHist(LoginHistVO loginHistVO);

    /**
     * POS 로그인시 하드웨어인증키 체크
     *
     * @param sessionInfoVO
     * @return
     */
    String selectStoreHwAuthKeyCheck(SessionInfoVO sessionInfoVO);
}
