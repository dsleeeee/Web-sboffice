package kr.co.solbipos.pos.license.oper.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : OperVO.java
 * @Description : 포스관리 > 라이선스관리 > 운영현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.10.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class OperVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 전체기간체크 */
    private boolean chkDt;

    /** 업체코드 */
    private String agencyCd;

    /** 업체명 */
    private String agencyNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 업체구분 */
    private String agencyFg;

    /** 소속코드 */
    private String orgnCd;

    /** 소속구분 */
    private String orgnFg;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;

    /** 대리점코드(검색용) */
    private String srchAgencyCd;

    /** 대리점명(검색용) */
    private String srchAgencyNm;

    /** 관리밴사 */
    private String manageVanCd;

    /** 관리밴사명 */
    private String manageVanNm;

    /** 사업자번호 */
    private String bizNo;

    /** 신규/전환 */
    private String srchShopMigFg;

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

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

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
    }

    public String getSrchAgencyNm() {
        return srchAgencyNm;
    }

    public void setSrchAgencyNm(String srchAgencyNm) {
        this.srchAgencyNm = srchAgencyNm;
    }

    public String getManageVanCd() {
        return manageVanCd;
    }

    public void setManageVanCd(String manageVanCd) {
        this.manageVanCd = manageVanCd;
    }

    public String getManageVanNm() {
        return manageVanNm;
    }

    public void setManageVanNm(String manageVanNm) {
        this.manageVanNm = manageVanNm;
    }

    public String getBizNo() { return bizNo; }

    public void setBizNo(String bizNo) { this.bizNo = bizNo;  }

    public String getSrchShopMigFg() { return srchShopMigFg; }

    public void setSrchShopMigFg(String srchShopMigFg) { this.srchShopMigFg = srchShopMigFg; }
}
