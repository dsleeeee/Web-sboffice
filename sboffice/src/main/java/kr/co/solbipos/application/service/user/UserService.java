package kr.co.solbipos.application.service.user;

import java.util.List;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.PwdChg;
import kr.co.solbipos.application.domain.user.PwdChgHist;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.enums.user.PwChgResult;
import kr.co.solbipos.application.enums.user.PwFindResult;

/**
 * 
 * @author 정용길
 *
 */
public interface UserService {

    /**
     * 인증 번호 확인
     * 
     * @param user
     * @return
     */
    PwFindResult processPwFind(User user);


    /**
     * 패스워드 변경 : uuid + pw 체크
     * 
     * @param pwdChg
     * @return
     */
    PwChgResult processPwdChg(PwdChg pwdChg);


    /**
      * 레이어 팝업에서 패스워드 변경
      * 
      * @param pwdChg
      * @return
      */
    PwChgResult processLayerPwdChg(SessionInfo sessionInfo, PwdChg pwdChg);
    

    /**
     * userId 조회
     * 
     * @param param User
     * @param maskId boolean
     * @return
     */
    List<User> selectUserList(User param, boolean maskId);

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
