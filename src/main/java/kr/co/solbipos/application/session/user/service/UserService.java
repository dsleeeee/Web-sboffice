package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.application.session.user.enums.PwFindResult;

import java.util.List;

/**
 * @Class Name : UserService.java
 * @Description : 어플리케이션 > 세션 > 사용자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
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
