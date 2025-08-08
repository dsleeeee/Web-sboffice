package kr.co.solbipos.store.manage.pwdManageSaleChk.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : PwdManageSaleChkVO.java
 * @Description : 기초관리 > 매출조회 비밀번호 관리 > 매출조회 비밀번호 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class PwdManageSaleChkVO extends PageVO {
    private static final long serialVersionUID = -8922775310268216397L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 사용자 아이디 */
    private String userId;

    /** 매출화면 접근비밀번호 */
    private String salePwd;

    /** 매출화면 접근비밀번호 사용여부*/
    private String salePwdYn;

    /** 비밀번호 */
    private String password;

    /** New 매출화면 접근비밀번호 */
    private String newPassword;

    /** 매출화면 접근비밀번호 확인 */
    private String confirmPassword;


    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSalePwd() {
        return salePwd;
    }

    public void setSalePwd(String salePwd) {
        this.salePwd = salePwd;
    }

    public String getSalePwdYn() {
        return salePwdYn;
    }

    public void setSalePwdYn(String salePwdYn) {
        this.salePwdYn = salePwdYn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
