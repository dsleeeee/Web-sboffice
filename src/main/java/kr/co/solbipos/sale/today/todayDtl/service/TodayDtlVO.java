package kr.co.solbipos.sale.today.todayDtl.service;

import kr.co.solbipos.application.common.service.PageVO;

public class TodayDtlVO extends PageVO {

    private static final long serialVersionUID = 8344453699928909013L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 결제수단컬럼 */
    private String payCol;
    /** 결제수단 array */
    private String arrPayCol[];
    /** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
    private String pivotPayCol;
    /** 할인컬럼 */
    private String dcCol;
    /** 할인구분 array */
    private String arrDcCol[];
    /** 쿼리문의 PIVOT IN에 사용할 할인구분 컬럼 문자열 */
    private String pivotDcCol;
    /** 판매여부 */
    private String saleYn;
    /** POS번호 */
    private String posNo;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

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
     * @return the payCol
     */
    public String getPayCol() {
        return payCol;
    }

    /**
     * @param payCol the payCol to set
     */
    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }

    /**
     * @return the arrPayCol
     */
    public String[] getArrPayCol() {
        return arrPayCol;
    }

    /**
     * @param arrPayCol the arrPayCol to set
     */
    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
    }

    /**
     * @return the pivotPayCol
     */
    public String getPivotPayCol() {
        return pivotPayCol;
    }

    /**
     * @param pivotPayCol the pivotPayCol to set
     */
    public void setPivotPayCol(String pivotPayCol) {
        this.pivotPayCol = pivotPayCol;
    }

    /**
     * @return the dcCol
     */
    public String getDcCol() {
        return dcCol;
    }

    /**
     * @param dcCol the dcCol to set
     */
    public void setDcCol(String dcCol) {
        this.dcCol = dcCol;
    }

    /**
     * @return the arrDcCol
     */
    public String[] getArrDcCol() {
        return arrDcCol;
    }

    /**
     * @param arrDcCol the arrDcCol to set
     */
    public void setArrDcCol(String[] arrDcCol) {
        this.arrDcCol = arrDcCol;
    }

    /**
     * @return the pivotDcCol
     */
    public String getPivotDcCol() {
        return pivotDcCol;
    }

    /**
     * @param pivotDcCol the pivotDcCol to set
     */
    public void setPivotDcCol(String pivotDcCol) {
        this.pivotDcCol = pivotDcCol;
    }

    /**
     * @return the saleYn
     */
    public String getSaleYn() {
        return saleYn;
    }

    /**
     * @param saleYn the saleYn to set
     */
    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
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

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }
}
