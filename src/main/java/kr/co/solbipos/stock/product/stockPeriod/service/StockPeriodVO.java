package kr.co.solbipos.stock.product.stockPeriod.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StockPeriodVO.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장-기간별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.22  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2022.12.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StockPeriodVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

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

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 array */
    private String arrStoreCd[];

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 바코드 */
    private String barcdCd;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 단위구분 */
    private String unitFg;

    /** 일자표시옵션 */
    private String dayOption;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

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

    public void setArrStoreCd(String arrStoreCd[]) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getBarcdCd() {
        return barcdCd;
    }

    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getUnitFg() {
        return unitFg;
    }

    public void setUnitFg(String unitFg) {
        this.unitFg = unitFg;
    }

    public String getDayOption() {
        return dayOption;
    }

    public void setDayOption(String dayOption) { this.dayOption = dayOption; }
}