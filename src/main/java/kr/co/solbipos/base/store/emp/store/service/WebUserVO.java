package kr.co.solbipos.base.store.emp.store.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.PwChgResult;

public class WebUserVO extends CmmVO{
    /** 웹 사용자 ID */
    private String userId;
    /** 웹 사용자 PASSWORD */
    private String userPwd;
    /** 웹 사용자 변경 전 PASSWORD */
    private String priorPwd;
    /** 마지막 패스워드 변경일자 */
    private String lastPwdChgDt;
    /** 사용여부 */
    private UseYn useYn;
    /** 등록자 IP */
    private String regIp;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userPwd
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * @param userPwd
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * @return the priorPwd
     */
    public String getPriorPwd() {
        return priorPwd;
    }

    /**
     * @param priorPwd
     */
    public void setPriorPwd(String priorPwd) {
        this.priorPwd = priorPwd;
    }

    /**
     * @return the lastPwdChgDt
     */
    public String getLastPwdChgDt() {
        return lastPwdChgDt;
    }

    /**
     * @param lastPwdChgDt
     */
    public void setLastPwdChgDt(String lastPwdChgDt) {
        this.lastPwdChgDt = lastPwdChgDt;
    }

    /**
     * @return the useYn
     */
    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the regIp
     */
    public String getRegIp() {
        return regIp;
    }

    /**
     * @param regIp
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }
}
