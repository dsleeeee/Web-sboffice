package kr.co.solbipos.base.store.emp.hq.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.store.emp.hq.validate.Dtl;
import kr.co.solbipos.base.store.emp.hq.validate.Mod;
import kr.co.solbipos.base.store.emp.hq.validate.Reg;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Class Name : HqEmpVO.java
 * @Description : 기초관리 > 매장관리 > 본사사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 *
 * @author NHN한국사이버결제 KCP 정상화
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqEmpVO extends PageVO {

    private static final long serialVersionUID = -5655407669545720607L;

    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 사원번호 */
    @Size(groups = {Reg.class,Mod.class,Dtl.class}, max=4, message = "{hqEmp.size.fix}")
    @NotBlank(groups = {Reg.class,Mod.class,Dtl.class},message = "{hqEmp.empNo}{cmm.require.text}")
    @Pattern(groups = {Reg.class,Mod.class,Dtl.class}, regexp = "^[0-9]*$",
            message = "{hqEmp.empNo}{cmm.require.number}")
    private String empNo;
    /** 사원명 */
    @Size(groups = {Reg.class}, max=10, message = "{cmm.size.max}")
    @NotBlank(groups = {Reg.class},message = "{hqEmp.empNm}{cmm.require.text}")
    private String empNm;
    /** 사원비밀번호 */
    private String empPwd;
    /** 사용자 비밀번호 */
    private String userPwd;
    /** 사용자 비밀번호 확인*/
    private String userPwdCfm;
    /** 신규 사용자 비밀번호 */
    private String newUserPwd;
    /** 이전 사용자 비밀번호 */
    private String priorPwd;
    /** 웹사용여부 */
    private String webUseYn;
    /** 사용자아이디 */
    @Size(groups = {Reg.class,Dtl.class}, max = 12, message = "{cmm.size.max}")
    private String userId;
    /** 휴대폰번호 */
    @Size(groups = {Reg.class}, max=15, message = "{cmm.size.max}")
    @NotBlank(groups = {Reg.class}, message = "{hqEmp.mpNo}{cmm.require.text}")
    private String mpNo;
    /** 재직구분 */
    private String serviceFg;
    /** 재직구분 */
    private String serviceFgNm;
    /** SMS수신여부 */
    private String smsRecvYn;
    /** 사용여부 */
    private String useYn;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 등록아이피 */
    private String regIp;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;
    /** 권한 그룹 코드 */
    private String authGrpCd;
    /** 전체기간 여부 */
    private boolean chkDt;

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    /**
     * @return the empNm
     */
    public String getEmpNm() {
        return empNm;
    }

    /**
     * @param empNm the empNm to set
     */
    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    /**
     * @return the empPwd
     */
    public String getEmpPwd() {
        return empPwd;
    }

    /**
     * @param empPwd the empPwd to set
     */
    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd;
    }

    /**
     * @return the userPwd
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * @param userPwd the userPwd to set
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * @return the userPwdCfm
     */
    public String getUserPwdCfm() {
        return userPwdCfm;
    }

    /**
     * @param userPwdCfm the userPwdCfm to set
     */
    public void setUserPwdCfm(String userPwdCfm) {
        this.userPwdCfm = userPwdCfm;
    }

    /**
     * @return the newUserPwd
     */
    public String getNewUserPwd() {
        return newUserPwd;
    }

    /**
     * @param newUserPwd the newUserPwd to set
     */
    public void setNewUserPwd(String newUserPwd) {
        this.newUserPwd = newUserPwd;
    }

    /**
     * @return the priorPwd
     */
    public String getPriorPwd() {
        return priorPwd;
    }

    /**
     * @param priorPwd the priorPwd to set
     */
    public void setPriorPwd(String priorPwd) {
        this.priorPwd = priorPwd;
    }

    /**
     * @return the webUseYn
     */
    public String getWebUseYn() {
        return webUseYn;
    }

    /**
     * @param webUseYn the webUseYn to set
     */
    public void setWebUseYn(String webUseYn) {
        this.webUseYn = webUseYn;
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
     * @return the mpNo
     */
    public String getMpNo() {
        return mpNo;
    }

    /**
     * @param mpNo the mpNo to set
     */
    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    /**
     * @return the serviceFg
     */
    public String getServiceFg() {
        return serviceFg;
    }

    /**
     * @param serviceFg the serviceFg to set
     */
    public void setServiceFg(String serviceFg) {
        this.serviceFg = serviceFg;
    }

    /**
     * @return the serviceFgNm
     */
    public String getServiceFgNm() {
        return serviceFgNm;
    }

    /**
     * @param serviceFgNm the serviceFgNm to set
     */
    public void setServiceFgNm(String serviceFgNm) {
        this.serviceFgNm = serviceFgNm;
    }

    /**
     * @return the smsRecvYn
     */
    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    /**
     * @param smsRecvYn the smsRecvYn to set
     */
    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the regDt
     */
    @Override public String getRegDt() {
        return regDt;
    }

    /**
     * @param regDt the regDt to set
     */
    @Override public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    /**
     * @return the regId
     */
    @Override public String getRegId() {
        return regId;
    }

    /**
     * @param regId the regId to set
     */
    @Override public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return the regIp
     */
    public String getRegIp() {
        return regIp;
    }

    /**
     * @param regIp the regIp to set
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    /**
     * @return the modDt
     */
    @Override public String getModDt() {
        return modDt;
    }

    /**
     * @param modDt the modDt to set
     */
    @Override public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    /**
     * @return the modId
     */
    @Override public String getModId() {
        return modId;
    }

    /**
     * @param modId the modId to set
     */
    @Override public void setModId(String modId) {
        this.modId = modId;
    }

    /**
     * @return the authGrpCd
     */
    public String getAuthGrpCd() {
        return authGrpCd;
    }

    /**
     * @param authGrpCd the authGrpCd to set
     */
    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }

    /**
     * @return the chkDt
     */
    public boolean isChkDt() {
        return chkDt;
    }

    /**
     * @param chkDt the chkDt to set
     */
    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }
}
