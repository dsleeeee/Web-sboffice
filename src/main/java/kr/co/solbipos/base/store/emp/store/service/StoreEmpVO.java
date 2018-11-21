package kr.co.solbipos.base.store.emp.store.service;

import kr.co.common.data.enums.UseYn;
import kr.co.common.validate.UserPwChange;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.store.emp.store.service.enums.ServiceFg;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Class Name : StoreEmpVO.java
 * @Description : 기초관리 > 매장관리 > 매장사원관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.16  hblee      최초생성
 *
 * @author NHN한국사이버결제 이한빈
 * @since 2018.08.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreEmpVO extends PageVO {
    /** 매장코드 */
    private String storeCd;
    /** 사원번호 */
    @NotBlank(message = "{storeEmp.empNo}{cmm.require.text}")
    @Size(min = 4, max = 4, message = "{storeEmp.empNo.size}")
    @Pattern(regexp = "^\\d+$", message = "{storeEmp.empNo}{cmm.require.number}")
    private String empNo;
    /** 사원명 */
    @NotBlank(message = "{storeEmp.empNm}{cmm.require.text}")
    private String empNm;
    /** 사원ID */
    private String userId;
    /** 사원비밀번호 */
    private String empPwd;
    /** 변경비밀번호 */
    @NotBlank(groups = UserPwChange.class, message = "{storeEmp.pwd}{cmm.require.text}")
    private String newPwd;
    /** 변경비밀번호확인 */
    @NotBlank(groups = UserPwChange.class, message = "{storeEmp.pwdConfirm}{cmm.require.text}")
    private String newPwdConfirm;
    /** 재직여부 */
    private ServiceFg serviceFg;
    /** 휴대폰번호 */
    @NotBlank(message = "{storeEmp.mpNo}{cmm.require.text}")
    @Pattern(regexp = "^\\d+$", message = "{storeEmp.mpNo}{cmm.require.number}")
    private String mpNo;
    /** 웹사용여부 */
    private UseYn webUseYn;
    /** SMS수신여부 */
    private UseYn smsRecvYn;
    /** 사용여부 */
    private UseYn useYn;
    /** 매장사원 등록 여부 */
    private Boolean empRegist = false;
    /** 웹 사용자 등록 여부 */
    private Boolean webUserRegist;
    /** 전체기간 체크 */
    private boolean chkDt;

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo
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
     * @param empNm
     */
    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

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
     * @return the empPwd
     */
    public String getEmpPwd() {
        return empPwd;
    }

    /**
     * @param empPwd
     */
    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd;
    }

    /**
     * @return the newPwd
     */
    public String getNewPwd() {
        return newPwd;
    }

    /**
     * @param newPwd
     */
    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    /**
     * @return the newPwdConfirm
     */
    public String getNewPwdConfirm() {
        return newPwdConfirm;
    }

    /**
     * @param newPwdConfirm
     */
    public void setNewPwdConfirm(String newPwdConfirm) {
        this.newPwdConfirm = newPwdConfirm;
    }

    /**
     * @return the ServiceFg
     */
    public ServiceFg getServiceFg() {
        return serviceFg;
    }

    /**
     * @param serviceFg
     */
    public void setServiceFg(ServiceFg serviceFg) {
        this.serviceFg = serviceFg;
    }

    /**
     * @return the mpNo
     */
    public String getMpNo() {
        return mpNo;
    }

    /**
     * @param mpNo
     */
    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    /**
     * @return the webUseYn
     */
    public UseYn getWebUseYn() {
        return webUseYn;
    }

    /**
     * @param webUseYn
     */
    public void setWebUseYn(UseYn webUseYn) {
        this.webUseYn = webUseYn;
    }

    /**
     * @return the smsRecvYn
     */
    public UseYn getSmsRecvYn() {
        return smsRecvYn;
    }

    /**
     * @param smsRecvYn
     */
    public void setSmsRecvYn(UseYn smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
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
     * @return the empRegist
     */
    public Boolean getEmpRegist() {
        return empRegist;
    }

    /**
     * @param empRegist
     */
    public void setEmpRegist(Boolean empRegist) {
        this.empRegist = empRegist;
    }

    /**
     * @return webUserRegist
     */
    public Boolean getWebUserRegist() {
        return webUserRegist;
    }

    /**
     * @param webUserRegist
     */
    public void setWebUserRegist(Boolean webUserRegist) {
        this.webUserRegist = webUserRegist;
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
