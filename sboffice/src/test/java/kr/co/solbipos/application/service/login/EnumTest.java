package kr.co.solbipos.application.service.login;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.config.AbstractApplicationContextTest;

@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EnumTest extends AbstractApplicationContextTest {
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    AuthService authService;

    SessionInfoVO sessionInfoVO;
    LoginHistVO loginHistVO;

    @Before
    public void init() {
        sessionInfoVO = new SessionInfoVO();
        loginHistVO = new LoginHistVO();
    }

    @Test
    public void test() {
      assertTrue(true);
    }
    /**
     * 로그인 이력 Insert
     *
     */
    @Test
    //@Ignore
    //@Rollback(false)
    public void setLoginHistVO() {
        // return 객체
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("bjcho");
        s.setBrwsrInfo("brwsinfo");
        s.setLoginIp("127.0.0.1");
        s.setLoginResult(LoginResult.SUCCESS);

        int result = authService.loginHist(s);
        LOGGER.debug(String.valueOf(result));
        assertTrue(result == 1);
    }

    /**
     * 로그인 이력 select
     *
     */
    @Test
    //@Ignore
    public void getLoginHistVO() {
        loginHistVO.setUserId("bjcho");
        List<LoginHistVO> result = authService.selectLoginHist(loginHistVO);
        LOGGER.debug(result.toString());
        for(LoginHistVO loginHistVO : result) {
          if(loginHistVO.getSeq().equals("381")) {
            LOGGER.debug(loginHistVO.toString());
            assertTrue(loginHistVO.getLoginOrgn() == LoginOrigin.WEB);
            assertTrue(loginHistVO.getStatCd() == LoginResult.PASSWORD_CHANGE);
          }
        }
    }
}
