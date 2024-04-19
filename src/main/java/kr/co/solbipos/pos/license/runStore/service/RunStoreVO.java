package kr.co.solbipos.pos.license.runStore.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : RunStoreVO.java
 * @Description : 포스관리 > 라이선스 관리 > 런닝매장현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.11  김유승      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.04.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RunStoreVO extends PageVO {

    private static final long serialVersionUID = 7248739322790767591L;

    /** 시작날짜(일) */
    private String startDate;

    /** 종료날짜(일) */
    private String endDate;

    /** 시작날짜(월) */
    private String startMonth;

    /** 종료날짜(월) */
    private String endMonth;

    /** 폐점 구분 날짜(월) */
    private String lastMonth;

    /** 폐점 구분 날짜(월) */
    private String lastMonth2;

    /** 일/월 구분 */
    private String dayGubun;

    /** 대리점코드(검색용) */
    private String srchAgencyCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 사업자번호 */
    private String bizNo;

    /** 신규/전환 */
    private String srchShopMigFg;

    /** 전체기간체크 */
    private String chkDt;

    /** 소속구분 */
    private String orgnFg;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;

    /** 업체코드 */
    private String agencyCd;

    /** 관리밴사 */
    private String manageVanCd;

    /** 하위대리점 포함 여부 */
    private String includeFg;

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(String lastMonth) {
        this.lastMonth = lastMonth;
    }

    public String getLastMonth2() {
        return lastMonth2;
    }

    public void setLastMonth2(String lastMonth2) {
        this.lastMonth2 = lastMonth2;
    }

    public String getDayGubun() {
        return dayGubun;
    }

    public void setDayGubun(String dayGubun) {
        this.dayGubun = dayGubun;
    }

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
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

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getSrchShopMigFg() {
        return srchShopMigFg;
    }

    public void setSrchShopMigFg(String srchShopMigFg) {
        this.srchShopMigFg = srchShopMigFg;
    }

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

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

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getManageVanCd() {
        return manageVanCd;
    }

    public void setManageVanCd(String manageVanCd) {
        this.manageVanCd = manageVanCd;
    }

    public String getIncludeFg() {
        return includeFg;
    }

    public void setIncludeFg(String includeFg) {
        this.includeFg = includeFg;
    }


}
