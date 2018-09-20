package kr.co.solbipos.application.session.user.service;

import kr.co.common.validate.AuthNumber;
import kr.co.common.validate.IdFind;
import kr.co.common.validate.PwFind;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @Class Name : UserVO.java
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
public class UserVO extends CmmVO {

    private static final long serialVersionUID = -939432068345883788L;
    /** 소속코드 */
    private String orgnCd;
    /** 사원번호 */
    private String empNo;
    /** 사원명 */
    @NotBlank(groups = {IdFind.class, AuthNumber.class, PwFind.class},
            message = "{cmm.name}{cmm.require.text}")
    @Size(groups = {IdFind.class, AuthNumber.class, PwFind.class}, max = 10, message = "{cmm.size.max}")
    private String empNm;
    /** 사원비밀번호 */
    private String empPwd;
    /** 소속구분_M:시스템,A:대리점,H:본사,S:매장 */
    private OrgnFg orgnFg;
    /** 웹사용여부 */
    private String webUseYn;
    /** 사용자아이디 */
    @NotBlank(groups = {AuthNumber.class, PwFind.class}, message = "{login.userId}{cmm.require.text}")
    @Size(groups = {AuthNumber.class, PwFind.class}, max = 20, message = "{cmm.size.max}")
    private String userId;
    /** 휴대폰번호 */
    @NotBlank(groups = {IdFind.class}, message = "{login.userId}{cmm.require.text}")
    @Size(groups = {IdFind.class}, max = 15, message = "{cmm.size.max}")
    private String mpNo;
    /** 이메일주소 */
    private String emailAddr;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 주소상세 */
    private String addrDtl;
    /** 고용구분_1:재직,2:휴직,9:퇴직 */
    private String serviceFg;
    /** SMS수신여부 */
    private String smsRecvYn;
    /** 사용여부 */
    private String useYn;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;
    /** 인증번호 */
    @NotBlank(groups = {PwFind.class}, message = "{login.pw.find.auth.number}{cmm.require.text}")
    @Size(groups = {PwFind.class}, max = 4, message = "{cmm.size.max}")
    private String authNumber;
    /** 소속명 */
    private String orgnNm;
    
    
    /**
     * @return the orgnCd
     */
    public String getOrgnCd() {
        return orgnCd;
    }
    /**
     * @param orgnCd the orgnCd to set
     */
    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
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
     * @return the orgnFg
     */
    public OrgnFg getOrgnFg() {
        return orgnFg;
    }
    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
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
     * @return the emailAddr
     */
    public String getEmailAddr() {
        return emailAddr;
    }
    /**
     * @param emailAddr the emailAddr to set
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
    /**
     * @return the postNo
     */
    public String getPostNo() {
        return postNo;
    }
    /**
     * @param postNo the postNo to set
     */
    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }
    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }
    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }
    /**
     * @return the addrDtl
     */
    public String getAddrDtl() {
        return addrDtl;
    }
    /**
     * @param addrDtl the addrDtl to set
     */
    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
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
    public String getRegDt() {
        return regDt;
    }
    /**
     * @param regDt the regDt to set
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }
    /**
     * @return the regId
     */
    public String getRegId() {
        return regId;
    }
    /**
     * @param regId the regId to set
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }
    /**
     * @return the modDt
     */
    public String getModDt() {
        return modDt;
    }
    /**
     * @param modDt the modDt to set
     */
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }
    /**
     * @return the modId
     */
    public String getModId() {
        return modId;
    }
    /**
     * @param modId the modId to set
     */
    public void setModId(String modId) {
        this.modId = modId;
    }
    /**
     * @return the authNumber
     */
    public String getAuthNumber() {
        return authNumber;
    }
    /**
     * @param authNumber the authNumber to set
     */
    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }
    /**
     * @return the orgnNm
     */
    public String getOrgnNm() {
        return orgnNm;
    }
    /**
     * @param orgnNm the orgnNm to set
     */
    public void setOrgnNm(String orgnNm) {
        this.orgnNm = orgnNm;
    }
    
}







