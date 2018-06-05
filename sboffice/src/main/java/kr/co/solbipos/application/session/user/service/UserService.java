package kr.co.solbipos.application.session.user.service;

import java.util.List;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.application.session.user.enums.PwFindResult;

/**
 *
 * @author 정용길
 *
 */
public interface UserService {

    /**
     * 인증 번호 확인
     *
     * @param userVO
     * @return
     */
    PwFindResult processPwFind(UserVO userVO);


    /**
     * 패스워드 변경 : uuid + pw 체크
     *
     * @param pwdChgVO
     * @return
     */
    PwChgResult processPwdChg(PwdChgVO pwdChgVO);


    /**
      * 레이어 팝업에서 패스워드 변경
      *
      * @param pwdChgVO
      * @return
      */
    PwChgResult processLayerPwdChg(SessionInfoVO sessionInfoVO, PwdChgVO pwdChgVO);


    /**
     * userId 조회
     *
     * @param userVO
     * @param maskId boolean
     * @return
     */
    List<UserVO> selectUserList(UserVO userVO, boolean maskId);

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
