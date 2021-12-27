package kr.co.solbipos.application.session.user.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.security.EncUtil;
import kr.co.common.utils.spring.ObjectUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;
import kr.co.solbipos.application.session.user.enums.PwFindResult;
import kr.co.solbipos.application.session.user.service.*;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.adi.sms.smsSend.service.impl.SmsSendMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;
import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.spring.StringUtil.*;

/**
 * @Class Name : UserServiceImpl.java
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
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    SessionService sessionService;
    @Autowired
    AuthService authService;
    @Autowired
    MessageService messageService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SmsSendMapper smsSendMapper;

    @SuppressWarnings( "unused" )
    @Override
    public PwChgResult processLayerPwdChg(SessionInfoVO sessionInfoVO, PwdChgVO pwdChgVO) {
        // 사용자정보 조회
        SessionInfoVO userInfo = authService.selectWebUser(sessionInfoVO);

        // 현재 비밀번호 암호화
        String curPassword = EncUtil.setEncSHA256(userInfo.getUserId() + pwdChgVO.getCurrentPw());
        // 신규 비밀번호 암호화
        String newPassword = EncUtil.setEncSHA256(userInfo.getUserId() + pwdChgVO.getNewPw());

        /**기존 패스워드 비교 */
        if (!userInfo.getUserPwd().equals(curPassword)) {
            return PwChgResult.PASSWORD_NOT_MATCH;
        }

        /** 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인 */
        if (!pwdChgVO.getNewPw().equals(pwdChgVO.getNewPwConf())) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }

        /** 변경 패스워드가 기존 비밀번호가 같은지 체크 */
        if (userInfo.getUserPwd().equals(newPassword)) {
            return PwChgResult.PASSWORD_NEW_OLD_MATH;
        }

        /**패스워드 정책 체크 */
        PwChgResult pwdChk ;
        try {
            pwdChk = CmmUtil.checkPasswd(pwdChgVO.getNewPw());
            if(pwdChk != PwChgResult.CHECK_OK) {
                return pwdChk;
            }
        } catch (Exception e) {
            throw new AuthenticationException(messageService.get("login.pwchg.error"), "");
        }
        // 조회한 사용자 ID
        String userId = userInfo.getUserId();

        // 패스워드 세팅 및 변경
        userInfo.setUserId(userId);
        userInfo.setUserPwd(newPassword);
        userInfo.setUserStatFg(UserStatFg.NORMAL);
        userInfo.setLastPwdChgDt(currentDateTimeString());
        int r1 = updateUserPwd(userInfo);

        // 패스워드 변경 내역 저장
        PwdChgHistVO pwdChgHistVO = new PwdChgHistVO();
        pwdChgHistVO.setUserId(userId);
        pwdChgHistVO.setPriorPwd(curPassword);
        pwdChgHistVO.setRegDt(currentDateTimeString());
        pwdChgHistVO.setRegId(getClientIp(WebUtil.getRequest()));
        int r2 = insertPwdChgHist(pwdChgHistVO);

        return PwChgResult.CHECK_OK;
    }

    @Override
    public PwChgResult processPwdChg(PwdChgVO pwdChgVO) {

        /** 입력한 새비밀번호가 서로 다른 경우 */
        if (!pwdChgVO.getNewPw().equals(pwdChgVO.getNewPwConf())) {
            return PwChgResult.PASSWORD_NOT_MATCH;
        }

        // uuid 체크
        OtpAuthVO findOtp = selectOtpBySeq(pwdChgVO);

        /** uuid 로 발송한 ID 를 가져온다. uuid 검증 */
        if (ObjectUtil.isEmpty(findOtp)) {
            return PwChgResult.UUID_NOT_MATCH;
        }

        /** uuid 로 패스워드 변경 가능 시간은 발송후 10분 */
        if (!checkOtpLimit(findOtp, 10)) {
            return PwChgResult.UUID_LIMIT_ERROR;
        }

        // 조회를 위해 세팅
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        sessionInfoVO.setUserId(findOtp.getUserId());
        SessionInfoVO userInfo = authService.selectWebUser(sessionInfoVO);

        /** uuid 의 userId 가 다른경우 */
        if (userInfo.getUserId().indexOf(pwdChgVO.getHalfId().replaceAll("\\*", "")) < 0) {
            return PwChgResult.ID_NOT_MATCH;
        }

        /** uuid 로 조회한 userId 가 유효한지 확인 */
        if (ObjectUtil.isEmpty(userInfo)) {
            return PwChgResult.EMPTY_USER;
        }

        /** 사용하지 않는 계정이면 패스워드 변경 불가 */
        if ("Y".equals(userInfo.getUseYn())) {
            return PwChgResult.NOT_USE;
        }

        /** 조회된 사용자 ID */
        String userId = userInfo.getUserId();

        // 비밀번호 암호화
        String newPassword = EncUtil.setEncSHA256(userId + pwdChgVO.getNewPw());

        // 패스워드 세팅 및 변경
        sessionInfoVO.setUserId(userId);
        sessionInfoVO.setUserStatFg(UserStatFg.NORMAL);
        sessionInfoVO.setLastPwdChgDt(currentDateTimeString());
        sessionInfoVO.setUserPwd(newPassword);
        int r1 = updateUserPwd(sessionInfoVO);

        // 패스워드 변경 내역 저장
        int r2 = 0;
        if(r1 == 1) {
            PwdChgHistVO pwdChgHistVO = new PwdChgHistVO();
            pwdChgHistVO.setUserId(userId);
            pwdChgHistVO.setPriorPwd(pwdChgVO.getOrginPwd());
            pwdChgHistVO.setRegDt(currentDateTimeString());
            pwdChgHistVO.setRegIp(getClientIp(WebUtil.getRequest()));
            r2 = insertPwdChgHist(pwdChgHistVO);
        }
        if(r2 == 1) {
            return PwChgResult.CHECK_OK;
        }
        return PwChgResult.CHECK_NOK;
    }

    @Override
    public PwFindResult processPwFind(UserVO userVO) {

        List<UserVO> findUsers = selectUserList(userVO, false);

        /**사용자 정보가 없음, 2개 이상 조회될 경우 오류 */
        if (ObjectUtil.isEmpty(findUsers)) {
            return PwFindResult.EMPTY_USER;
        }
        if (findUsers.size() > 1) {
          return PwFindResult.TO_MANY_USER;
        }

        OtpAuthVO otpAuthVO = new OtpAuthVO();
        otpAuthVO.setUserId(userVO.getUserId());
        otpAuthVO.setAuthFg("001");
        otpAuthVO.setReqDate(currentDateString());
        otpAuthVO.setOtpLimit(BaseEnv.OTP_LIMIT_MINUTE);

        OtpAuthVO findOtp = selectOtpTopOne(otpAuthVO);

        if (checkOtpLimit(findOtp, BaseEnv.OTP_LIMIT_MINUTE)) {
            /** otp 체크 완료 */
            if (userVO.getAuthNumber().equals(findOtp.getAuthNo())) {
                userVO.setAuthNumber(findOtp.getSeq());
                return PwFindResult.OTP_OK;
            }
            /** 인증번호 틀림 */
            else {
                return PwFindResult.OTP_ERROR;
            }
        }
        /** 인증번호 통과 못함 : 분이 지났습니다. */
        else {
            return PwFindResult.OTP_LIMIT_ERROR;
        }
    }

    /**
     * otp limit 해당되는지 확인
     *
     * @param otpAuthVO : otp 번호
     * @param limit : 분 단위
     * @return 10:23(limit) >= 10:20(now) ? true : false 조회 못한 경우에도 false return
     */
    public boolean checkOtpLimit(OtpAuthVO otpAuthVO, int limit) {
        if (ObjectUtil.isEmpty(otpAuthVO)) {
            return false;
        }

        // otp 생성 시간
        String otpDateTime = otpAuthVO.getReqDate() + otpAuthVO.getReqDt();
        Date otpDt = DateUtil.getDatetime(otpDateTime);
        // otp 리미티드 시간 더해줌
        otpDt = DateUtils.addMinutes(otpDt, limit);

        // 현재 시간
        Date current = new Date();

        if (otpDt.getTime() >= current.getTime()) {
            return true;
        }
        return false;
    }



    @Override
    public List<UserVO> selectUserList(UserVO params, boolean maskId) {

        List<UserVO> users = userMapper.selectUserList(params);
        //아이디 마스킹 처리
        if(maskId) {
          List<UserVO> result = new ArrayList<UserVO>();
          for(UserVO userVO : users) {
              userVO.setUserId(strMaskingHalf(userVO.getUserId()));
            result.add(userVO);
          }
          return result;
        }
        return users;
    }

    @Override
    public int insertOtpAuth(OtpAuthVO otpAuthVO) {

        otpAuthVO.setSeq(generateUUID());
        otpAuthVO.setAuthNo(getRandomNumber(BaseEnv.OTP_SIZE));
        otpAuthVO.setReqDt(currentTimeString());

        //TODO OTP 이력 테이블에 암호화된 휴대폰번호로 저장

        return userMapper.insertOtpAuth(otpAuthVO);
    }

    @Override
    public OtpAuthVO selectOtpTopOne(OtpAuthVO otpAuthVO) {
        return userMapper.selectOtpTopOne(otpAuthVO);
    }

    @Override
    public OtpAuthVO selectOtpBySeq(PwdChgVO pwdChgVO) {
        return userMapper.selectOtpBySeq(pwdChgVO);
    }

    @Override
    public int insertPwdChgHist(PwdChgHistVO pwdChgHistVO) {
        return userMapper.insertPwdChgHist(pwdChgHistVO);
    }

    @Override
    public int updateUserPwd(SessionInfoVO sessionInfoVO) {
        return userMapper.updateUserPwd(sessionInfoVO);
    }

    /** SMS 전송 저장 */
    @Override
    public int getSmsSendSave(OtpAuthVO otpAuthVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        SmsSendVO smsSendVO = new SmsSendVO();

        smsSendVO.setRegDt(currentDt);
        smsSendVO.setRegId("sysAdmin");
        smsSendVO.setModDt(currentDt);
        smsSendVO.setModId("sysAdmin");

        smsSendVO.setOrgnCd("00001");

        // 송신자
        smsSendVO.setSsOrgnCd("00001");
        smsSendVO.setSsOrgnFg("M");
        smsSendVO.setSsUserId("sysAdmin");

        // 수신자
        smsSendVO.setRrOrgnCd("");
        smsSendVO.setRrOrgnFg("");
        smsSendVO.setRrUserId(otpAuthVO.getUserId());

        // 송신주체 소속코드
        smsSendVO.setSmsOgnCd("00001");

        // 회원관리주체 소속코드
        smsSendVO.setCstOgnCd("*");

        // 전송일시
        smsSendVO.setSendDate(currentDt);

        // 전송이력시퀀스
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        String smsSendSeq = smsSendMapper.getSmsSendSeq(sessionInfoVO);
        smsSendVO.setSmsSendSeq(smsSendSeq);

        smsSendVO.setReserveYn("0");
        smsSendVO.setMsgType("1");
        smsSendVO.setTitle("NXPOS 인증번호");
        smsSendVO.setContent("인증번호는 " + otpAuthVO.getAuthNo() + " 입니다.");
        smsSendVO.setCallback("16445195");
        smsSendVO.setPhoneNumber(otpAuthVO.getRecvMpNo().replaceAll("-",""));
        smsSendVO.setCstNo("");

        // 전송건수
        smsSendVO.setSmsSendCount("0");
        // 전송이력 저장
        procCnt = smsSendMapper.getSmsSendSeqSaveInsert(smsSendVO);

        // SMS
        procCnt = smsSendMapper.getSmsSendReserveSaveInsert(smsSendVO); // SDK_SMS_SEND_ENC

        DefaultMap<Object> result = smsSendMapper.getSmsAmtList(smsSendVO);
        // 현재 잔여금액
        String smsAmt = result.getStr("smsAmt");
        // SMS 건당금액
        String smsOneAmt = result.getStr("smsOneAmt");
        // 잔여금액 - 사용금액
        smsSendVO.setSmsAmt(String.valueOf( Integer.parseInt(smsAmt) - Integer.parseInt(smsOneAmt) ));

        // 잔여금액 저장 update
        procCnt = smsSendMapper.getSmsAmtSaveUpdate(smsSendVO);

        return procCnt;
    }
}