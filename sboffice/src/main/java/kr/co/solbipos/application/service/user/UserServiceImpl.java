package kr.co.solbipos.application.service.user;

import static kr.co.solbipos.utils.DateUtil.*;
import static kr.co.solbipos.utils.spring.StringUtil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.user.OtpAuth;
import kr.co.solbipos.application.domain.user.PwdChg;
import kr.co.solbipos.application.domain.user.PwdChgHist;
import kr.co.solbipos.application.domain.user.User;
import kr.co.solbipos.application.persistance.user.UserMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Prop prop;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    UserMapper userMapper;
    
    final static int OTP_SIZE = 4;

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
        otp.setAuthNo(getRandomNumber(OTP_SIZE));
        otp.setReqDt(currentTimeString());
        
        return userMapper.insertOtpAuth(otp);
    }

    @Override
    public OtpAuth selectOtpTopOne(OtpAuth otp) {
        return userMapper.selectOtpTopOne(otp);
    }

    @Override
    public String selectOtpCheck(PwdChg pwdChg) {
        return userMapper.selectOtpCheck(pwdChg);
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


