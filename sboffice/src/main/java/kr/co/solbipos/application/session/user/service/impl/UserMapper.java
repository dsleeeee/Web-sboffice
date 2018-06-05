package kr.co.solbipos.application.session.user.service.impl;

import java.util.List;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.service.OtpAuthVO;
import kr.co.solbipos.application.session.user.service.PwdChgHistVO;
import kr.co.solbipos.application.session.user.service.PwdChgVO;
import kr.co.solbipos.application.session.user.service.UserVO;

/**
 *
 * @author 정용길
 *
 */
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
