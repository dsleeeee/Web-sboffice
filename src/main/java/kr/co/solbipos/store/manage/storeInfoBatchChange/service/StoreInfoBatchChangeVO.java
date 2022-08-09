package kr.co.solbipos.store.manage.storeInfoBatchChange.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreInfoBatchChangeVO.java
 * @Description : 기초관리 > 매장정보관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreInfoBatchChangeVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 매장상태 */
    private String sysStatFg;

    /** 대리점코드 */
    private String agencyCd;

    /** 대리점명 */
    private String agencyNm;

    /** 사업자매장명 */
    private String bizStoreNm;

    /** 대표자명 */
    private String ownerNm;

    /** 용도구분 */
    private String clsFg;

    /** 지역구분 */
    private String areaCd;

    /**  직영구분 */
    private String directManageYn;

    /** 전화번호 */
    private String telNo;

    /** 팩스번호 */
    private String faxNo;

    /** 밴사코드 */
    private String vanCd;

    /** 시스템비고 */
    private String sysRemark;

    /** 본사비고 */
    private String hdRemark;

    /** 비고 */
    private String remark;

    /** 매핑매장코드*/
    private String mapStoreCd;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() { return hqOfficeNm; }

    public void setHqOfficeNm(String hqOfficeNm) { this.hqOfficeNm = hqOfficeNm; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getSysStatFg() { return sysStatFg; }

    public void setSysStatFg(String sysStatFg) { this.sysStatFg = sysStatFg; }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getBizStoreNm() { return bizStoreNm; }

    public void setBizStoreNm(String bizStoreNm) { this.bizStoreNm = bizStoreNm; }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) { this.ownerNm = ownerNm; }

    public String getClsFg() { return clsFg; }

    public void setClsFg(String clsFg) { this.clsFg = clsFg; }

    public String getAreaCd() { return areaCd; }

    public void setAreaCd(String areaCd) { this.areaCd = areaCd; }

    public String getDirectManageYn() { return directManageYn; }

    public void setDirectManageYn(String directManageYn) { this.directManageYn = directManageYn; }

    public String getTelNo() { return telNo; }

    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getFaxNo() { return faxNo; }

    public void setFaxNo(String faxNo) { this.faxNo = faxNo; }

    public String getVanCd() { return vanCd; }

    public void setVanCd(String vanCd) { this.vanCd = vanCd; }

    public String getSysRemark() { return sysRemark; }

    public void setSysRemark(String sysRemark) { this.sysRemark = sysRemark; }

    public String getHdRemark() { return hdRemark; }

    public void setHdRemark(String hdRemark) { this.hdRemark = hdRemark; }

    public String getRemark() { return remark; }

    public void seRemark(String remark) { this.remark = remark; }

    public String getMapStoreCd() {
        return mapStoreCd;
    }

    public void setMapStoreCd(String mapStoreCd) {
        this.mapStoreCd = mapStoreCd;
    }
}