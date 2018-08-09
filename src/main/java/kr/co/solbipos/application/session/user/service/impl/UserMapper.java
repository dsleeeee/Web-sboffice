package kr.co.solbipos.application.session.user.service.impl;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.service.OtpAuthVO;
import kr.co.solbipos.application.session.user.service.PwdChgHistVO;
import kr.co.solbipos.application.session.user.service.PwdChgVO;
import kr.co.solbipos.application.session.user.service.UserVO;

/**
 * @Class Name : UserMapper.java
 * @Description : 어플리케이션 > 세션 > 사용자
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
@Mapper
public interface UserMapper {
    /**
     * 담당자 이름, 핸드폰 번호로 userId 조회
     *
     * @param userVO
     * @return
     */
    List<UserVO> selectUserList(UserVO userVO);

    /**
     * 인증번호 저장
     *
     * @param otpAuthVO
     * @return
     */
    int insertOtpAuth(OtpAuthVO otpAuthVO);

    /**
     * 인증번호 하나 가져오기
     *
     * @param otpAuthVO
     * @return
     */
    OtpAuthVO selectOtpTopOne(OtpAuthVO otpAuthVO);

    /**
     * seq 로 userId 조회
     *
     * @param pwdChgVO
     * @return
     */
    OtpAuthVO selectOtpBySeq(PwdChgVO pwdChgVO);

    /**
     * 패스워드 변경 히스토리 저장
     *
     * @param pwdChgHistVO
     * @return
     */
    int insertPwdChgHist(PwdChgHistVO pwdChgHistVO);

    /**
     * 유져 패스워드 설정
     *
     * @param sessionInfoVO
     * @return
     */
    int updateUserPwd(SessionInfoVO sessionInfoVO);
}
