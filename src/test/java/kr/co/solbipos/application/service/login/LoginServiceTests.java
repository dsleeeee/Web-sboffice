package kr.co.solbipos.application.service.login;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.impl.AuthMapper;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginServiceTests {

    @Mock
    AuthMapper authMapper;

    @Mock
    SessionService sessionService;

    @InjectMocks
    AuthService authService;

    SessionInfoVO sessionInfoVO;

    @Before
    public void init() {
        sessionInfoVO = new SessionInfoVO();
        BaseEnv.LOGIN_PWD_CHG_DAYS = 90;
    }

    /**
     * 존재 하지 않는 아이디로 접근한 경우
     */
    @Test
    // @Ignore
    public void userIdEmpty() {
        // 존재하지 않는 아이디
        sessionInfoVO.setUserId("fff");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(new SessionInfoVO());

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertNull(sessionInfo.getUserId());
        assertTrue("".equals(sessionInfo.getUserPwd()));
        // 존재하지 않는 아이디로 결과 확인
        assertTrue(sessionInfo.getLoginResult() == LoginResult.NOT_EXISTS_ID);
    }

    /**
     * 사용자가 패스워드 입력 안한경우
     *
     */
    @Test
    // @Ignore
    public void pwdCheckEmpty() {
        // return 객체
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("20180101");

        sessionInfoVO.setUserId("ygjeong");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.PASSWORD_ERROR);
    }

    /**
     * 패스워드가 맞는 경우
     */
    @Test
    // @Ignore
    public void pwdCheckOk() {
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer"); // 동일한 패스워드
        s.setLockCd("N");
        s.setLastPwdChg("20180101");

        sessionInfoVO.setUserId("ygjeong");
        sessionInfoVO.setUserPwd("qwer");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.SUCCESS);
    }

    /**
     * 패스워드 틀린 경우
     */
    @Test
    // @Ignore
    public void pwdCheckFail() {
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwera"); // 다른 패스워드
        s.setLockCd("N");
        s.setLastPwdChg("20180101");

        sessionInfoVO.setUserId("ygjeong");
        sessionInfoVO.setUserPwd("qwer");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.PASSWORD_ERROR);
    }

    /**
      * 사용자 잠금 처리 였을때 경우
      */
    @Test
    // @Ignore
    public void userLock() {
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("Y"); // 사용자 잠금 처리 됨
        s.setLastPwdChg("20180101");

        sessionInfoVO.setUserId("ygjeong");
        sessionInfoVO.setUserPwd("qwer");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.LOCK);
    }

    /**
      * 패스워드 변경 날짜 체크
      */
    @Test
    // @Ignore
    public void pwdChgExpire() {
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("20170603"); // 마지막 패스워드 변경 날짜

        sessionInfoVO.setUserId("ygjeong");
        sessionInfoVO.setUserPwd("qwer");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.PASSWORD_EXPIRE);
    }

    /**
      * 패스워드 변경 한번도 없을때
      */
    @Test
    // @Ignore
    public void pwdChg() {
        SessionInfoVO s = new SessionInfoVO();
        s.setUserId("ygjeong");
        s.setUserPwd("qwer");
        s.setLockCd("N");
        s.setLastPwdChg("0"); // 패스워드 변경 한적 없음

        sessionInfoVO.setUserId("ygjeong");
        sessionInfoVO.setUserPwd("qwer");
        given(authService.selectWebUser(sessionInfoVO)).willReturn(s);

        SessionInfoVO sessionInfo = authService.login(sessionInfoVO);

        assertTrue("".equals(sessionInfo.getUserPwd()));
        assertTrue(sessionInfo.getLoginResult() == LoginResult.PASSWORD_CHANGE);
    }

}
