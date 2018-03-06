package kr.co.solbipos.application.service.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.HttpUtils.*;
import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import kr.co.solbipos.application.persistence.user.UserMapper;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.Result.Status;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.DateUtil;
import kr.co.solbipos.utils.security.EncUtil;
import kr.co.solbipos.utils.spring.ObjectUtil;
import kr.co.solbipos.utils.spring.WebUtil;
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
    public PwChgResult processLayerPwdChg(SessionInfo sessionInfo, PwdChg pwdChg) {

        SessionInfo si = loginService.selectWebUser(sessionInfo);

        /**
         * 기존 패스워드 비교
         */
        if (!si.getUserPwd().equals(pwdChg.getCurrentPw())) {
            return PwChgResult.PASSWORD_NOT_MATCH;
        }

        /**
         * 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
         */
        if (!pwdChg.getNewPw().equals(pwdChg.getNewPwConf())) {
            return PwChgResult.NEW_PASSWORD_NOT_MATCH;
        }

        /**
         * 변경 패스워드가 기존 비밀번호가 같은지 체크
         */
        if (si.getUserPwd().equals(pwdChg.getNewPw())) {
            return PwChgResult.PASSWORD_NEW_OLD_MATH;
        }

        /**
         * 패스워드 정책 체크
         */
        if (!EncUtil.passwordPolicyCheck(pwdChg.getNewPw())
                || !EncUtil.passwordPolicyCheck(pwdChg.getNewPwConf())) {
            return PwChgResult.PASSWORD_REGEXP;
        }
        
        String userId = si.getUserId();

        // 패스워드 세팅 및 변경
        si.setUserId(userId);
        si.setUserPwd(pwdChg.getNewPw());
        int r1 = updateUserPwd(si);

        // 패스워드 변경 내역 저장
        PwdChgHist pch = new PwdChgHist();
        pch.setUserId(userId);
        pch.setPriorPwd(pwdChg.getCurrentPw());
        pch.setRegDt(currentDateTimeString());
        pch.setRegIp(getClientIp(WebUtil.getRequest()));
        int r2 = insertPwdChgHist(pch);

        return PwChgResult.CHECK_OK;
    }

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

        /**
         * 
         * 패스워드 변경 성공
         * 
         */
        String userId = si.getUserId();

        // 패스워드 세팅 및 변경
        sessionInfo.setUserId(userId);
        sessionInfo.setUserPwd(pwdChg.getNewPw());
        int r1 = updateUserPwd(sessionInfo);

        // 패스워드 변경 내역 저장
        int r2 = 0;
        if(r1 == 1) {
            PwdChgHist pch = new PwdChgHist();
            pch.setUserId(userId);
            pch.setPriorPwd(pwdChg.getOrginPwd());
            pch.setRegDt(currentDateTimeString());
            pch.setRegIp(getClientIp(WebUtil.getRequest()));
            r2 = insertPwdChgHist(pch);
        }
        if(r2 == 1) {
            return PwChgResult.CHECK_OK;
        }
        return PwChgResult.CHECK_NOK;
    }

    @Override
    public PwFindResult processPwFind(User user) {

        List<User> findUsers = selectUserList(user, false);

        /**
         * 
         * 사용자 정보가 없음, 2개 이상 조회될 경우 오류
         * 
         */
        if (ObjectUtil.isEmpty(findUsers)) {
            return PwFindResult.EMPTY_USER;
        }
        if (findUsers.size() > 1) {
          return PwFindResult.TO_MANY_USER;
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
    public List<User> selectUserList(User param, boolean maskId) {
      
        List<User> users = userMapper.selectUserList(param);
        //아이디 마스킹 처리
        if(maskId) {
          List<User> result = new ArrayList<User>();
          for(User user : users) {
            user.setUserId(strMaskingHalf(user.getUserId()));
            result.add(user);
          }
          return result;
        }
        return users;
    }

    @Override
    public int insertOtpAuth(OtpAuth otp) {

        otp.setSeq(generateUUID());
        otp.setAuthNo(getRandomNumber(prop.otpSize));
        otp.setReqDt(currentTimeString());
        
        //TODO OTP 이력 테이블에 암호화된 휴대폰번호로 저장
        
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
        //TODO 비밀번호 암호화 적용 필요
        return userMapper.insertPwdChgHist(pwdChgHist);
    }

    @Override
    public int updateUserPwd(SessionInfo sessionInfo) {
        
        //TODO 비밀번호 암호화 적용 필요
        
        return userMapper.updateUserPwd(sessionInfo);
    }

}


