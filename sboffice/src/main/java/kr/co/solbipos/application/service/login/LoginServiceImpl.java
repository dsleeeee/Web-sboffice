package kr.co.solbipos.application.service.login;

import static kr.co.solbipos.utils.DateUtil.*;
import static org.springframework.util.ObjectUtils.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.solbipos.application.domain.login.LoginHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.domain.resource.ResrceInfo;
import kr.co.solbipos.application.enums.login.LoginOrigin;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.application.persistence.login.LoginMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    Prop prop;
    
    @Autowired
    LoginMapper loginMapper;

    @Autowired
    SessionService sessionService;

    @Override
    public SessionInfo selectWebUser(SessionInfo sessionInfo) {
        return loginMapper.selectWebUser(sessionInfo);
    }

    /**
     * 로그인 성공 여부
     * 
     * @param sessionInfo : 사용자가 입력 객체
     * @param webUser : 디비에서 조회한 객체
     * @return
     */
    private LoginResult loginProcess(SessionInfo sessionInfo, SessionInfo webUser) {

        /**
         * 존재하는 id 인지 체크
         */
        if (isEmpty(webUser) || isEmpty(webUser.getUserId())) {
            return LoginResult.NOT_EXISTS_ID;
        }

        /**
         * 패스워드 체크
         */
        if (!loginPasswordCheck(sessionInfo, webUser)) {
            return LoginResult.PASSWORD_ERROR;
        }
        
        /** 
         * 사용자 잠금 여부
         * */
        if(webUser.getLockCd().equals("Y")) {
            return LoginResult.LOCK;
        }
        
        /** 
         * 패스워드 초기 변경 인지 체크 
         * */
        if( webUser.getLastPwdChg().equals("0") ) {
            return LoginResult.PASSWORD_CHANGE;
        }
        
        
        int pwdChgDays = Integer.parseInt(addDaysString(webUser.getLastPwdChg(), prop.loginPwdChgDays));
        int currentDay = Integer.parseInt(currentDateString());
        
        /** 
         * 패스워드 변경 날짜 체크
         * */
        if( currentDay >= pwdChgDays ) {
            return LoginResult.PASSWORD_EXPIRE;
        }

        return LoginResult.SUCCESS;
    }

    /**
     * 패스워드 체크 암호화 후 비교 하는 과정은 아직 안들어감
     * 
     * @param sessionInfo 로그인 페이지에서 입력된 로그인 유져 정보
     * @param webUser 입력된 ID로 조회된 유져 정보
     * @return
     */
    private boolean loginPasswordCheck(SessionInfo sessionInfo, SessionInfo webUser) {

        if (isEmpty(sessionInfo) || isEmpty(webUser)) {
            log.warn("password check object null...");
            return false;
        }

        String loginPw = sessionInfo.getUserPwd();
        String userPw = webUser.getUserPwd();

        if (isEmpty(loginPw) || isEmpty(userPw)) {
            log.warn("password string null, loginPw empty:{}, userPw empty:{}", isEmpty(loginPw),
                    isEmpty(userPw));
            return false;
        }

        if (loginPw.equals(userPw)) {
            return true;
        }

        return false;
    }


    @Override
    public SessionInfo login(SessionInfo sessionInfo) {

        SessionInfo si = selectWebUser(sessionInfo);

        log.debug(si.toString());
        
        LoginResult result = loginProcess(sessionInfo, si);

        // 로그인 결과
        si.setLoginResult(result);
        // 조회된 패스워드 초기화
        
        si.setUserPwd("");
        si.setLoginIp(sessionInfo.getLoginIp());
        si.setBrwsrInfo(sessionInfo.getBrwsrInfo());

        // 로그인 시도 기록
        loginHist(si);

        return si;
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        sessionService.deleteSessionInfo(request, response);
        return true;
    }

    /**
     * 로그인 시도 결과를 히스토리 저장
     * 
     * @param sessionInfo
     * @return
     */
    @Override
    public int loginHist(SessionInfo sessionInfo) {

        LoginHist loginHist = new LoginHist();

        // 로그인 결과
        loginHist.setStatCd(sessionInfo.getLoginResult());

        loginHist.setUserId(sessionInfo.getUserId());
        loginHist.setLoginOrgn(LoginOrigin.WEB);
        loginHist.setBrwsrInfo(sessionInfo.getBrwsrInfo());
        loginHist.setLoginIp(sessionInfo.getLoginIp());
        loginHist.setLoginDate(currentDateString());
        loginHist.setLoginDt(currentDateTimeString());

        return loginHist(loginHist);
    }

    @Override
    public int loginHist(LoginHist loginHist) {
        log.debug(loginHist.toString());
        return loginMapper.insertLoginHist(loginHist);
    }

    @Override
    public <E> List<E> selectLoginHist(LoginHist loginHist) {
        return loginMapper.selectLoginHist(loginHist);
    }

    @Override
    public List<ResrceInfo> selectAuthMenu(SessionInfo sessionInfo) {
        return loginMapper.selectAuthMenu(sessionInfo);
    }

}


