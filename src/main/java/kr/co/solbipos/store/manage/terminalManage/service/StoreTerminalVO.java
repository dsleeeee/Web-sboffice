package kr.co.solbipos.store.manage.terminalManage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreTerminalVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.30  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.30
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreTerminalVO extends CmmVO {

    /** [매장코드] */
    private String storeCd;
    /** [포스번호] */
    private String posNo;
    /** [터미널번호] */
    private String cornrCd;
    /** [벤더구분] */
    private String vendorFg;
    /** [벤더구분명] */
    private String vendorFgNm;
    /** [벤더코드] */
    private String vendorCd;
    /** [벤더명] */
    private String vendorNm;
    /** [벤더 터미널번호] */
    private String vendorTermnlNo;
    /** [벤더시리얼번호] */
    private String vendorSerNo;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장명 */
    private String storeNm;

    /** 용도 */
    private String clsFg;

    /** 상태 */
    private String sysStatFg;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 업체코드 */
    private String agencyCd;

    /** 복사할 포스번호 */
    private String copyPosNo;
    /** 붙여넣을 포스번호 */
    private String pastePosNo;

    /** 대표 VAN*/
    private String baseVanYn;

    /** 터미널-코너 별 정렬 순서 */
    private String cornrRnum;

    /** [코너명] */
    private String cornrNm;
    /** [대표자명] */
    private String ownerNm;
    /** [사업자버호] */
    private String bizNo;
    /** [전화번호] */
    private String telNo;
    /** [대표코너구분] 대표:Y 미대표:N */
    private String baseYn;

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the posNo
     */

    public String getPosNo() {
        return posNo;
    }

    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    /**
     * @return the cornrCd
     */

    public String getCornrCd() {
        return cornrCd;
    }

    /**
     * @param cornrCd the cornrCd to set
     */
    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    /**
     * @return the vendorFg
     */

    public String getVendorFg() {
        return vendorFg;
    }

    /**
     * @param vendorFg the vendorFg to set
     */
    public void setVendorFg(String vendorFg) {
        this.vendorFg = vendorFg;
    }


    /**
     * @return the vendorFgNm
     */

    public String getVendorFgNm() {
        return vendorFgNm;
    }

    /**
     * @param vendorFgNm the vendorFgNm to set
     */
    public void setVendorFgNm(String vendorFgNm) {
        this.vendorFgNm = vendorFgNm;
    }

    /**
     * @return the vendorCd
     */

    public String getVendorCd() {
        return vendorCd;
    }

    /**
     * @param vendorCd the vendorCd to set
     */
    public void setVendorCd(String vendorCd) {
        this.vendorCd = vendorCd;
    }

    /**
     * @return the vendorNm
     */

    public String getVendorNm() {
        return vendorNm;
    }

    /**
     * @param vendorNm the vendorNm to set
     */
    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    /**
     * @return the vendorTermnlNo
     */

    public String getVendorTermnlNo() {
        return vendorTermnlNo;
    }

    /**
     * @param vendorTermnlNo the vendorTermnlNo to set
     */
    public void setVendorTermnlNo(String vendorTermnlNo) {
        this.vendorTermnlNo = vendorTermnlNo;
    }

    /**
     * @return the vendorSerNo
     */

    public String getVendorSerNo() {
        return vendorSerNo;
    }

    /**
     * @param vendorSerNo the vendorSerNo to set
     */
    public void setVendorSerNo(String vendorSerNo) {
        this.vendorSerNo = vendorSerNo;
    }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getHqOfficeNm() { return hqOfficeNm; }

    public void setHqOfficeNm(String hqOfficeNm) { this.hqOfficeNm = hqOfficeNm; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getCopyPosNo() {
        return copyPosNo;
    }

    public void setCopyPosNo(String copyPosNo) {
        this.copyPosNo = copyPosNo;
    }

    public String getPastePosNo() {
        return pastePosNo;
    }

    public void setPastePosNo(String pastePosNo) {
        this.pastePosNo = pastePosNo;
    }

    public String getBaseVanYn() {
        return baseVanYn;
    }

    public void setBaseVanYn(String baseVanYn) {
        this.baseVanYn = baseVanYn;
    }

    public String getCornrRnum() {
        return cornrRnum;
    }

    public void setCornrRnum(String cornrRnum) {
        this.cornrRnum = cornrRnum;
    }

    public String getCornrNm() {
        return cornrNm;
    }

    public void setCornrNm(String cornrNm) {
        this.cornrNm = cornrNm;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getBaseYn() {
        return baseYn;
    }

    public void setBaseYn(String baseYn) {
        this.baseYn = baseYn;
    }
}
