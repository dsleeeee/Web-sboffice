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

    /** 본사사업장코드 */
    String hqOfficeCd;

    /** 사원번호 */
    @Size(groups = {Reg.class,Mod.class,Dtl.class}, max=4, message = "{hqEmp.size.fix}")
    @NotBlank(groups = {Reg.class,Mod.class,Dtl.class},message = "{hqEmp.empNo}{cmm.require.text}")
    @Pattern(groups = {Reg.class,Mod.class,Dtl.class}, regexp = "^[0-9]*$",
            message = "{hqEmp.empNo}{cmm.require.number}")
    String empNo;

    /** 사원명 */
    @Size(groups = {Reg.class}, max=10, message = "{cmm.size.max}")
    @NotBlank(groups = {Reg.class},message = "{hqEmp.empNm}{cmm.require.text}")
    String empNm;

    /** 사원비밀번호 */
    String empPwd;

    /** 사용자 비밀번호 */
    String userPwd;

    /** 사용자 비밀번호 확인*/
    String userPwdCfm;

    /** 신규 사용자 비밀번호 */
    String newUserPwd;

    /** 이전 사용자 비밀번호 */
    String priorPwd;

    /** 웹사용여부 */
    String webUseYn;

    /** 사용자아이디 */
    @Size(groups = {Reg.class,Dtl.class}, max = 12, message = "{cmm.size.max}")
    String userId;

    /** 휴대폰번호 */
    @Size(groups = {Reg.class}, max=15, message = "{cmm.size.max}")
    @NotBlank(groups = {Reg.class}, message = "{hqEmp.mpNo}{cmm.require.text}")
    String mpNo;

    /** 재직구분 */
    String serviceFg;

    /** 재직구분 */
    String serviceFgNm;

    /** SMS수신여부 */
    String smsRecvYn;

    /** 사용여부 */
    String useYn;

    /** 등록일시 */
    String regDt;

    /** 등록아이디 */
    String regId;

    /** 등록아이피 */
    String regIp;

    /** 수정일시 */
    String modDt;

    /** 수정아이디 */
    String modId;

    /** 권한 그룹 코드 */
    String authGrpCd;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getEmpPwd() {
        return empPwd;
    }

    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserPwdCfm() {
        return userPwdCfm;
    }

    public void setUserPwdCfm(String userPwdCfm) {
        this.userPwdCfm = userPwdCfm;
    }

    public String getNewUserPwd() {
        return newUserPwd;
    }

    public void setNewUserPwd(String newUserPwd) {
        this.newUserPwd = newUserPwd;
    }

    public String getPriorPwd() {
        return priorPwd;
    }

    public void setPriorPwd(String priorPwd) {
        this.priorPwd = priorPwd;
    }

    public String getWebUseYn() {
        return webUseYn;
    }

    public void setWebUseYn(String webUseYn) {
        this.webUseYn = webUseYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getServiceFg() {
        return serviceFg;
    }

    public void setServiceFg(String serviceFg) {
        this.serviceFg = serviceFg;
    }

    public String getServiceFgNm() {
        return serviceFgNm;
    }

    public void setServiceFgNm(String serviceFgNm) {
        this.serviceFgNm = serviceFgNm;
    }

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getAuthGrpCd() {
        return authGrpCd;
    }

    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }

}
