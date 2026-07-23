package kr.co.solbipos.sale.anals.timeSaleBenson.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : TimeSaleBensonVO.java
 * @Description : 벤슨 > 매출분석 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.20
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TimeSaleBensonVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회 매장(멀티, CSV) */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열 */
    private String storeCdQuery;

    /** 시작일자 */
    private String startDate;

    /** 종료일자 */
    private String endDate;

    /** 시작월 */
    private String startMonth;

    /** 종료월 */
    private String endMonth;

    /** 일자옵션 (date:일자별/월별, period:기간합) */
    private String dayOption;

    /** 매장옵션 (all:전체, store:매장별) */
    private String storeOption;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
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

    public String getDayOption() {
        return dayOption;
    }

    public void setDayOption(String dayOption) {
        this.dayOption = dayOption;
    }

    public String getStoreOption() {
        return storeOption;
    }

    public void setStoreOption(String storeOption) {
        this.storeOption = storeOption;
    }
}
