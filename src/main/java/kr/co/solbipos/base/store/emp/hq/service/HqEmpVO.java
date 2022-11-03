package kr.co.solbipos.base.store.emp.hq.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;


/**
 * @Class Name : HqEmpVO.java
 * @Description : 기초관리 > 사원관리 > 사원정보관리(이전 : 기초관리 > 매장관리 > 본사사원정보관리)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  정상화      최초생성
 * @ 2018.11.21  김지은      UseYn 수정
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
    private String hqOfficeCd;

    /** 사원번호 */
    private String empNo;

    /** 사원명 */
    private String empNm;

    /** 사원비밀번호(포스에서 사용) */
    private String empPwd;

    /** 현재 비밀번호 (비밀번호 변경시 사용) */
    private String currentPwd;

    /** 사용자 비밀번호(웹에서 사용) */
    private String userPwd;

    /** 사용자 비밀번호 확인*/
    private String userPwdCfm;

    /** 사용자 비밀번호 변경여부 */
    private Boolean pwdChgFg;

    /** 변경전 사용자 비밀번호 */
    private String priorPwd;

    /** 웹사용여부 */
    private UseYn webUseYn;

    /** 사용자아이디 */
    private String userId;

    /** 휴대폰번호 */
    private String mpNo;

    /** 재직구분 */
    private String serviceFg;

    /** 재직구분 */
    private String serviceFgNm;

    /** SMS수신여부 */
    private String smsRecvYn;

    /** 사용여부 */
    private UseYn useYn;

    /** 등록아이피 */
    private String regIp;

    /** 권한 그룹 코드 */
    private String authGrpCd;

    /** 전체기간 체크 */
    private boolean chkDt;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 본사브랜드코드 */
    private String chkHqBrandCd;

    /** 본사코드 */
    private String[] hqBrandCdList;

    /** 메인화면매출표시 */
    private String mainSaleFg;

    /** 거래처 코드 */
    private String vendrCd;

    /** 지사 코드 */
    private String branchCd;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

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
     * @return the currentPwd
     */

    public String getCurrentPwd() {
        return currentPwd;
    }

    /**
     * @param currentPwd the currentPwd to set
     */
    public void setCurrentPwd(String currentPwd) {
        this.currentPwd = currentPwd;
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
     * @return the pwdChgFg
     */

    public Boolean getPwdChgFg() {
        return pwdChgFg;
    }

    /**
     * @param pwdChgFg the pwdChgFg to set
     */
    public void setPwdChgFg(Boolean pwdChgFg) {
        this.pwdChgFg = pwdChgFg;
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

    public UseYn getWebUseYn() {
        return webUseYn;
    }

    /**
     * @param webUseYn the webUseYn to set
     */
    public void setWebUseYn(UseYn webUseYn) {
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

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
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
     * @param regIp the regIp to set
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
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

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String getChkHqBrandCd() { return chkHqBrandCd; }

    public void setChkHqBrandCd(String chkHqBrandCd) { this.chkHqBrandCd = chkHqBrandCd; }

    public String[] getHqBrandCdList() {
        return hqBrandCdList;
    }

    public void setHqBrandCdList(String[] hqBrandCdList) { this.hqBrandCdList = hqBrandCdList; }

    public String getMainSaleFg() {
        return mainSaleFg;
    }

    public void setMainSaleFg(String mainSaleFg) {
        this.mainSaleFg = mainSaleFg;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }
}