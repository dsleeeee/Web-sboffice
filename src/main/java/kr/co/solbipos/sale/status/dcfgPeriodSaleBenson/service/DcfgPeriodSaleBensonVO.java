package kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service;

/**
 * @Class Name : DcfgPeriodSaleBensonVO.java
 * @Description : 벤슨 > 매출분석 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.20
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class DcfgPeriodSaleBensonVO {

    private static final long serialVersionUID = -2758214962510391972L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 본사,매장 구분 */
    private String orgnFg;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;


    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
