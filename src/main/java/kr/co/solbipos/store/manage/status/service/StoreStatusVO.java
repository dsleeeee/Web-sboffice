package kr.co.solbipos.store.manage.status.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreStatusVO.java
 * @Description : 기초관리 > 매장정보관리 > 매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreStatusVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 용도 */
    private String clsFg;

    /** 상태 */
    private String sysStatFg;

    /** 관리업체코드 */
    private String agencyCd;

    /** 관리업체명 */
    private String agencyNm;

    /** VAN사코드 */
    private String vanCd;

    /** VAN사명 */
    private String vanNm;

    /** 매장구분 */
    private String storeFg;

    /** 업체구분 */
    private String agencyFg;

    /** 현재상태 */
    private String instFg;

    /** 전체기간체크 */
    private String chkDt;

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

    public String getClsFg() { return clsFg; }

    public void setClsFg(String clsFg) { this.clsFg = clsFg; }

    public String getSysStatFg() { return sysStatFg; }

    public void setSysStatFg(String sysStatFg) { this.sysStatFg = sysStatFg; }

    public String getAgencyCd() { return agencyCd; }

    public void setAgencyCd(String agencyCd) { this.agencyCd = agencyCd; }

    public String getAgencyNm() { return agencyNm; }

    public void setAgencyNm(String agencyNm) { this.agencyNm = agencyNm; }

    public String getVanCd() { return vanCd; }

    public void setVanCd(String vanCd) { this.vanCd = vanCd; }

    public String getVanNm() { return vanNm; }

    public void setVanNm(String vanNm) { this.vanNm = vanNm; }

    public String getStoreFg() { return storeFg; }

    public void setStoreFg(String storeFg) { this.storeFg = storeFg; }

    public String getAgencyFg() { return agencyFg; }

    public void setAgencyFg(String agencyFg) { this.agencyFg = agencyFg; }

    public String getInstFg() { return instFg; }

    public void setInstFg(String instFg) { this.instFg = instFg; }

    public String getChkDt() { return chkDt; }

    public void setChkDt(String chkDt) { this.chkDt = chkDt; }
}
