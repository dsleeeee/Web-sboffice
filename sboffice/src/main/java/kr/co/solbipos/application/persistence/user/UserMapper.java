package kr.co.solbipos.application.persistence.user;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.PwdChg;
import kr.co.solbipos.application.domain.user.PwdChgHist;
import kr.co.solbipos.application.domain.user.User;

/**
 * 
 * @author 정용길
 *
 */
public interface UserMapper {
    /**
     * 담당자 이름, 핸드폰 번호로 userId 조회
     * 
     * @param user
     * @return
     */
    List<User> selectUserList(User user);

    /**
     * 인증번호 저장
     * 
     * @param otp
     * @return
     */
    int insertOtpAuth(OtpAuth otp);

    /**
     * 인증번호 하나 가져오기
     * 
     * @param otp
     * @return
     */
    OtpAuth selectOtpTopOne(OtpAuth otp);

    /**
     * seq 로 userId 조회
     * 
     * @param pwdChg
     * @return
     */
    OtpAuth selectOtpBySeq(PwdChg pwdChg);

    /**
     * 패스워드 변경 히스토리 저장
     * 
     * @param pwdChgHist
     * @return
     */
    int insertPwdChgHist(PwdChgHist pwdChgHist);

    /**
     * 유져 패스워드 설정
     * 
     * @param sessionInfo
     * @return
     */
    int updateUserPwd(SessionInfo sessionInfo);
}
