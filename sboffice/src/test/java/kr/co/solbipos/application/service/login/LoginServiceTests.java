package kr.co.solbipos.application.service.login;

import static kr.co.solbipos.utils.DateUtil.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.application.persistence.login.LoginMapper;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginServiceTests {

    @Mock
    LoginMapper loginMapper;

    @Mock
    SessionService sessionService;

    @Mock
    Prop prop;
    
    @InjectMocks
    LoginServiceImpl loginService;

    SessionInfo sessionInfo;

    @Before
    public void init() {
        sessionInfo = new SessionInfo();
        prop.loginPwdChgDays = 90;
    }

    /**
     * 존재 하지 않는 아이디로 접근한 경우
     */
    @Test
    // @Ignore
    public void userIdEmpty() {
        // 존재하지 않는 아이디
        sessionInfo.setUserId("fff");
        given(loginService.selectWebUser(sessionInfo)).willReturn(new SessionInfo());

        SessionInfo si = loginService.login(sessionInfo);

        assertNull(si.getUserId());
        assertTrue("".equals(si.getUserPwd()));
        // 존재하지 않는 아이디로 결과 확인
        assertTrue(si.getLoginResult() == LoginResult.NOT_EXISTS_ID);
    }

    /**
     * 사용자가 패스워드 입력 안한경우
     * 
     */
    @Test
    // @Ignore
    public void pwdCheckEmpty() {
        // return 객체
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("20180101");
        
        sessionInfo.setUserId("ygjeong");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.PASSWORD_ERROR);
    }

    /**
     * 패스워드가 맞는 경우
     */
    @Test
    // @Ignore
    public void pwdCheckOk() {
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer"); // 동일한 패스워드
        s.setLockCd("N");
        s.setLastPwdChg("20180101");

        sessionInfo.setUserId("ygjeong");
        sessionInfo.setUserPwd("qwer");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.SUCCESS);
    }

    /**
     * 패스워드 틀린 경우
     */
    @Test
    // @Ignore
    public void pwdCheckFail() {
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwera"); // 다른 패스워드
        s.setLockCd("N");
        s.setLastPwdChg("20180101");

        sessionInfo.setUserId("ygjeong");
        sessionInfo.setUserPwd("qwer");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.PASSWORD_ERROR);
    }

    /**
      * 사용자 잠금 처리 였을때 경우
      */
    @Test
    // @Ignore
    public void userLock() {
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("Y"); // 사용자 잠금 처리 됨
        s.setLastPwdChg("20180101");

        sessionInfo.setUserId("ygjeong");
        sessionInfo.setUserPwd("qwer");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.LOCK);
    }
    
    /**
      * 패스워드 변경 날짜 체크
      */
    @Test
    // @Ignore
    public void pwdChgExpire() {
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("20170603"); // 마지막 패스워드 변경 날짜
        
        sessionInfo.setUserId("ygjeong");
        sessionInfo.setUserPwd("qwer");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.PASSWORD_EXPIRE);
    }
    
    /**
      * 패스워드 변경 한번도 없을때
      */
    @Test
    // @Ignore
    public void pwdChg() {
        SessionInfo s = new SessionInfo();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("0"); // 패스워드 변경 한적 없음
        
        sessionInfo.setUserId("ygjeong");
        sessionInfo.setUserPwd("qwer");
        given(loginService.selectWebUser(sessionInfo)).willReturn(s);

        SessionInfo si = loginService.login(sessionInfo);

        assertTrue("".equals(si.getUserPwd()));
        assertTrue(si.getLoginResult() == LoginResult.PASSWORD_CHANGE);
    }
    
}













