package kr.co.solbipos.application.service.user;

import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.User;

/**
 * 
 * @author 정용길
 *
 */
public interface UserService {
    /**
     * 담당자 이름, 핸드폰 번호로 userId 조회
     * 
     * @param user
     * @return
     */
    String selectUserCheck(User user);

    /**
     * 담당자 이름, id 로 유져 조회
     * 
     * @param user
     * @return
     */
    User selectUserByNmAndId(User user);

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
}
