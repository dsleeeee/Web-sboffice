package kr.co.solbipos.application.session.auth.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;

/**
 * @Class Name : LoginHistVO.java
 * @Description : 어플리케이션 > 세션 > 인증
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
public class LoginHistVO extends CmmVO {

    private static final long serialVersionUID = 8864930887231052954L;
    /** 순서 */
    private String seq;
    /** 사용자 아이디 */
    private String userId;
    /** 상태 코드 */
    private LoginResult statCd;
    /** 로그인 소속 */
    private LoginOrigin loginOrgn;
    /** 로그인 IP */
    private String loginIp;
    /** 브라우저 정보 */
    private String brwsrInfo;
    /** 로그인 일자 */
    private String loginDate;
    /** 로그인 일시 */
    private String loginDt;
    
    
    /**
     * @return the seq
     */
    public String getSeq() {
        return seq;
    }
    /**
     * @param seq the seq to set
     */
    public void setSeq(String seq) {
        this.seq = seq;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the statCd
     */
    public LoginResult getStatCd() {
        return statCd;
    }
    /**
     * @param statCd the statCd to set
     */
    public void setStatCd(LoginResult statCd) {
        this.statCd = statCd;
    }
    /**
     * @return the loginOrgn
     */
    public LoginOrigin getLoginOrgn() {
        return loginOrgn;
    }
    /**
     * @param loginOrgn the loginOrgn to set
     */
    public void setLoginOrgn(LoginOrigin loginOrgn) {
        this.loginOrgn = loginOrgn;
    }
    /**
     * @return the loginIp
     */
    public String getLoginIp() {
        return loginIp;
    }
    /**
     * @param loginIp the loginIp to set
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    /**
     * @return the brwsrInfo
     */
    public String getBrwsrInfo() {
        return brwsrInfo;
    }
    /**
     * @param brwsrInfo the brwsrInfo to set
     */
    public void setBrwsrInfo(String brwsrInfo) {
        this.brwsrInfo = brwsrInfo;
    }
    /**
     * @return the loginDate
     */
    public String getLoginDate() {
        return loginDate;
    }
    /**
     * @param loginDate the loginDate to set
     */
    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }
    /**
     * @return the loginDt
     */
    public String getLoginDt() {
        return loginDt;
    }
    /**
     * @param loginDt the loginDt to set
     */
    public void setLoginDt(String loginDt) {
        this.loginDt = loginDt;
    }
    
}
