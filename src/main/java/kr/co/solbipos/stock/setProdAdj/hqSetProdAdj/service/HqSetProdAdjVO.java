package kr.co.solbipos.stock.setProdAdj.hqSetProdAdj.service;

import kr.co.solbipos.application.common.service.PageVO;

public class HqSetProdAdjVO extends PageVO {

    private static final long serialVersionUID = 4619627174595585565L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 구성일자 */
    private String setDate;
    /** 헤드인덱스(CCD_COMSQ_S 이용) */
    private Integer seqNo;
    /** 구성/해체구분 (1:구성, 2:해체) */
    private String setMakeFg;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 세트상품코드 */
    private String prodCd;
    /** 세트상품명 */
    private String prodNm;
    /** 세트상품수량 */
    private Integer setProdQty;
    /** 세트상품금액 */
    private Long setProdAmt;
    /** 처리구분 (0:미처리 1:처리완료) */
    private String procFg;

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
     * @return the setDate
     */
    public String getSetDate() {
        return setDate;
    }

    /**
     * @param setDate the setDate to set
     */
    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    /**
     * @return the seqNo
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * @return the setMakeFg
     */
    public String getSetMakeFg() {
        return setMakeFg;
    }

    /**
     * @param setMakeFg the setMakeFg to set
     */
    public void setSetMakeFg(String setMakeFg) {
        this.setMakeFg = setMakeFg;
    }

    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }

    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    /**
     * @return the prodCd
     */
    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    /**
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    /**
     * @return the setProdQty
     */
    public Integer getSetProdQty() {
        return setProdQty;
    }

    /**
     * @param setProdQty the setProdQty to set
     */
    public void setSetProdQty(Integer setProdQty) {
        this.setProdQty = setProdQty;
    }

    /**
     * @return the setProdAmt
     */
    public Long getSetProdAmt() {
        return setProdAmt;
    }

    /**
     * @param setProdAmt the setProdAmt to set
     */
    public void setSetProdAmt(Long setProdAmt) {
        this.setProdAmt = setProdAmt;
    }

    /**
     * @return the procFg
     */
    public String getProcFg() {
        return procFg;
    }

    /**
     * @param procFg the procFg to set
     */
    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

}
