package kr.co.solbipos.application.session.auth.service.impl;

import static kr.co.common.utils.DateUtil.*;
import static org.springframework.util.ObjectUtils.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.Prop;
import kr.co.solbipos.application.common.service.ResrceInfoVO;
import kr.co.solbipos.application.session.auth.service.LoginHistVO;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    Prop prop;

    @Autowired
    AuthMapper authMapper;

    @Autowired
    SessionService sessionService;

    @Override
    public SessionInfoVO selectWebUser(SessionInfoVO sessionInfoVO) {
        SessionInfoVO si = authMapper.selectWebUser(sessionInfoVO);
        return isEmpty(si) ? new SessionInfoVO() : si;
    }

    /**
     * 로그인 성공 여부
     *
     * @param sessionInfo : 사용자가 입력 객체
     * @param webUser : 디비에서 조회한 객체
     * @return
     */
    private LoginResult loginProcess(SessionInfoVO sessionInfoVO, SessionInfoVO webUser) {

        /**
         * 존재하는 id 인지 체크
         */
        if (isEmpty(webUser) || isEmpty(webUser.getUserId())) {
            return LoginResult.NOT_EXISTS_ID;
        }

        /**
         * 패스워드 체크
         */
        if (!loginPasswordCheck(sessionInfoVO, webUser)) {
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
    private boolean loginPasswordCheck(SessionInfoVO sessionInfoVO, SessionInfoVO webUser) {

        if (isEmpty(sessionInfoVO) || isEmpty(webUser)) {
            log.warn("password check object null...");
            return false;
        }

        String loginPw = sessionInfoVO.getUserPwd();
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
    public SessionInfoVO login(SessionInfoVO sessionInfoVO) {

        // userId 로 사용자 조회
        SessionInfoVO si = selectWebUser(sessionInfoVO);

        // 로그인 과정
        LoginResult result = loginProcess(sessionInfoVO, si);

        // 없는 id 일 경우에
        if(result == LoginResult.NOT_EXISTS_ID) {
            si.setUserId(sessionInfoVO.getUserId());
        }

        // 로그인 결과
        si.setLoginResult(result);

        // 조회된 패스워드 초기화
        si.setUserPwd("");
        si.setLoginIp(sessionInfoVO.getLoginIp());
        si.setBrwsrInfo(sessionInfoVO.getBrwsrInfo());

        // 로그인 시도 기록
        loginHist(si);

        return si;
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        sessionService.deleteSessionInfo(request);
        return true;
    }

    /**
     * 로그인 시도 결과를 히스토리 저장
     *
     * @param sessionInfo
     * @return
     */
    @Override
    public int loginHist(SessionInfoVO sessionInfoVO) {

        LoginHistVO loginHistVO = new LoginHistVO();

        // 로그인 결과
        loginHistVO.setStatCd(sessionInfoVO.getLoginResult());

        loginHistVO.setUserId(sessionInfoVO.getUserId());
        loginHistVO.setLoginOrgn(LoginOrigin.WEB);
        loginHistVO.setBrwsrInfo(sessionInfoVO.getBrwsrInfo());
        loginHistVO.setLoginIp(sessionInfoVO.getLoginIp());
        loginHistVO.setLoginDate(currentDateString());
        loginHistVO.setLoginDt(currentDateTimeString());

        return loginHist(loginHistVO);
    }

    @Override
    public int loginHist(LoginHistVO loginHistVO) {
        log.debug(loginHistVO.toString());
        return authMapper.insertLoginHist(loginHistVO);
    }

    @Override
    public <E> List<E> selectLoginHist(LoginHistVO loginHistVO) {
        return authMapper.selectLoginHist(loginHistVO);
    }

    @Override
    public List<ResrceInfoVO> selectAuthMenu(SessionInfoVO sessionInfoVO) {
        return authMapper.selectAuthMenu(sessionInfoVO);
    }

}


