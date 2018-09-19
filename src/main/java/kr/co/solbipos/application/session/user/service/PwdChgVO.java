package kr.co.solbipos.application.session.user.service;

import kr.co.common.validate.Login;
import kr.co.common.validate.PwChange;
import kr.co.common.validate.UserPwChange;
import kr.co.solbipos.application.common.service.CmmVO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @Class Name : PwdChgVO.java
 * @Description : 어플리케이션 > 세션 > 사용자
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
public class PwdChgVO extends CmmVO {
    
    private static final long serialVersionUID = -4842995979467618739L;
    /** 기존 비밀번호 */
    @NotBlank(groups = {UserPwChange.class, Login.class},
            message = "{login.userPasswd}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    private String currentPw;
    /** 새 비밀번호 */
    @NotBlank(groups = {PwChange.class, UserPwChange.class},
            message = "{login.pw.chg.new}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    private String newPw;
    /** 새 비밀번호 확인 */
    @NotBlank(groups = {PwChange.class, UserPwChange.class},
            message = "{login.pw.chg.new}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    private String newPwConf;
    /** uuid */
    private String uuid;
    /** 사용자 아이디 */
    @NotBlank( groups={ Login.class }, message = "{login.userId}{cmm.require.text}" )
    @Size( groups={ Login.class }, max = 20, message = "{cmm.size.max}" )
    private String userId;
    /** orginPwd */
    private String orginPwd;
    /** halfId */
    private String halfId;
    
    
    /**
     * @return the currentPw
     */
    public String getCurrentPw() {
        return currentPw;
    }
    /**
     * @param currentPw the currentPw to set
     */
    public void setCurrentPw(String currentPw) {
        this.currentPw = currentPw;
    }
    /**
     * @return the newPw
     */
    public String getNewPw() {
        return newPw;
    }
    /**
     * @param newPw the newPw to set
     */
    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }
    /**
     * @return the newPwConf
     */
    public String getNewPwConf() {
        return newPwConf;
    }
    /**
     * @param newPwConf the newPwConf to set
     */
    public void setNewPwConf(String newPwConf) {
        this.newPwConf = newPwConf;
    }
    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }
    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * @return the orginPwd
     */
    public String getOrginPwd() {
        return orginPwd;
    }
    /**
     * @param orginPwd the orginPwd to set
     */
    public void setOrginPwd(String orginPwd) {
        this.orginPwd = orginPwd;
    }
    /**
     * @return the halfId
     */
    public String getHalfId() {
        return halfId;
    }
    /**
     * @param halfId the halfId to set
     */
    public void setHalfId(String halfId) {
        this.halfId = halfId;
    }
    
}
