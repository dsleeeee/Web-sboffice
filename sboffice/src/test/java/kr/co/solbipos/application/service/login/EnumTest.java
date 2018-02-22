package kr.co.solbipos.application.service.login;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.domain.login.LoginHist;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.login.LoginOrigin;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.config.AbstractApplicationContextTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnumTest extends AbstractApplicationContextTest {

    @Autowired
    LoginServiceImpl loginService;

    SessionInfo sessionInfo;
    LoginHist loginHist;

    @Before
    public void init() {
        sessionInfo = new SessionInfo();
        loginHist = new LoginHist();
    }

    /**
     * 로그인 이력 Insert
     * 
     */
    //@Test
    //@Ignore
    //@Rollback(false)
    public void setLoginHist() {
        // return 객체
        SessionInfo s = new SessionInfo();
        s.setUserId("bjcho");
        s.setBrwsrInfo("brwsinfo");
        s.setLoginIp("127.0.0.1");
        s.setLoginResult(LoginResult.SUCCESS);
        
        int result = loginService.loginHist(s);
        log.debug(String.valueOf(result));
        assertTrue(result == 1);
    }

    /**
     * 로그인 이력 select
     * 
     */
    //@Test
    //@Ignore
    public void getLoginHist() {
        loginHist.setUserId("bjcho");
        List<LoginHist> result = loginService.selectLoginHist(loginHist);
        log.debug(result.toString());
        for(LoginHist loginHist : result) {
          if(loginHist.getSeq().equals("381")) {
            log.debug(loginHist.toString());
            assertTrue(loginHist.getLoginOrgn() == LoginOrigin.WEB);
            assertTrue(loginHist.getStatCd() == LoginResult.PASSWORD_CHANGE);
          }
        }
    }
}
