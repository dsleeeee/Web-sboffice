package kr.co.solbipos.application.service.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.PwdChg;
import kr.co.solbipos.application.domain.user.PwdChgHist;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.enums.user.PwChgResult;
import kr.co.solbipos.application.enums.user.PwFindResult;
import kr.co.solbipos.application.persistance.user.UserMapper;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.DateUtil;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Prop prop;

    @Autowired
    SessionService sessionService;

    @Autowired
    LoginService loginService;

    @Autowired
    UserMapper userMapper;

    @Override
    public PwChgResult processPwdChg(PwdChg pwdChg) {

        /**
         * 
         * 입력한 새비밀번호가 서로 다른 경우
         * 
         */
        if (!pwdChg.getNewPw().equals(pwdChg.getNewPwConf())) {
            return PwChgResult.PASSWORD_NOT_MATCH;
        }

        // uuid 체크
        OtpAuth findOtp = selectOtpBySeq(pwdChg);

        /**
         * 
         * uuid 로 발송한 ID 를 가져온다. uuid 검증
         * 
         */
        if (ObjectUtil.isEmpty(findOtp)) {
            return PwChgResult.UUID_NOT_MATCH;
        }

        /**
         * 
         * uuid 로 패스워드 변경 가능 시간은 발송후 10분
         * 
         */
        if (!checkOtpLimit(findOtp, 10)) {
            return PwChgResult.UUID_LIMIT_ERROR;
        } 


        // 조회를 위해 세팅
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setUserId(findOtp.getUserId());
        SessionInfo si = loginService.selectWebUser(sessionInfo);

        /**
         * 
         * uuid 의 userId 가 다른경우
         * 
         */
        if (si.getUserId().indexOf(pwdChg.getHalfId().replaceAll("\\*", "")) < 0) {
            return PwChgResult.ID_NOT_MATCH;
        }

        /**
         * 
         * uuid 로 조회한 userId 가 유효한지 확인
         * 
         */
        if (ObjectUtil.isEmpty(si)) {
            return PwChgResult.EMPTY_USER;
        }

        /**
         * 
         * 패스워드 변경 유져가 잠금 여부면 패스워드 변경 불가
         * 
         */
        if (si.getLockCd().equals("Y")) {
            return PwChgResult.LOCK_USER;
        }

        pwdChg.setUserId(si.getUserId());
        pwdChg.setOrginPwd(si.getUserPwd());

        return PwChgResult.CHECK_OK;
    }

    @Override
    public PwFindResult processPwFind(User user) {

        User findUser = selectUserByNmAndId(user);

        /**
         * 
         * 사용자 정보가 없음
         * 
         */
        if (ObjectUtil.isEmpty(findUser)) {
            return PwFindResult.EMPTY_USER;
        }

        OtpAuth otp = new OtpAuth();
        otp.setUserId(user.getUserId());
        otp.setAuthFg("001");
        otp.setReqDate(currentDateString());
        otp.setOtpLimit(prop.otpLimit);

        OtpAuth findOtp = selectOtpTopOne(otp);

        if (checkOtpLimit(findOtp, prop.otpLimit)) {
            /**
             * 
             * otp 체크 완료
             * 
             */
            if (user.getAuthNumber().equals(findOtp.getAuthNo())) {
                user.setAuthNumber(findOtp.getSeq());
                return PwFindResult.OTP_OK;
            }
            /**
             * 
             * 인증번호 틀림
             * 
             */
            else {
                return PwFindResult.OTP_ERROR;
            }
        }
        /**
         * 
         * 인증번호 통과 못함 : 분이 지났습니다.
         * 
         */
        else {
            return PwFindResult.OTP_LIMIT_ERROR;
        }
    }

    /**
     * otp limit 해당되는지 확인
     * 
     * @param otp : otp 번호
     * @param limit : 분 단위
     * @return 10:23(limit) >= 10:20(now) ? true : false 조회 못한 경우에도 false return
     */
    public boolean checkOtpLimit(OtpAuth otp, int limit) {
        if (ObjectUtil.isEmpty(otp)) {
            return false;
        }

        // otp 생성 시간
        String otpDateTime = otp.getReqDate() + otp.getReqDt();
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
    public String selectUserCheck(User user) {
        return userMapper.selectUserCheck(user);
    }

    @Override
    public User selectUserByNmAndId(User user) {
        return userMapper.selectUserByNmAndId(user);
    }

    @Override
    public int insertOtpAuth(OtpAuth otp) {

        otp.setSeq(generateUUID());
        otp.setAuthNo(getRandomNumber(prop.otpSize));
        otp.setReqDt(currentTimeString());

        return userMapper.insertOtpAuth(otp);
    }

    @Override
    public OtpAuth selectOtpTopOne(OtpAuth otp) {
        return userMapper.selectOtpTopOne(otp);
    }

    @Override
    public OtpAuth selectOtpBySeq(PwdChg pwdChg) {
        return userMapper.selectOtpBySeq(pwdChg);
    }

    @Override
    public int insertPwdChgHist(PwdChgHist pwdChgHist) {
        return userMapper.insertPwdChgHist(pwdChgHist);
    }

    @Override
    public int updateUserPwd(SessionInfo sessionInfo) {
        return userMapper.updateUserPwd(sessionInfo);
    }

}


