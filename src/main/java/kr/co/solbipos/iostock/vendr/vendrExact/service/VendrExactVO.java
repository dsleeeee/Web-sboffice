package kr.co.solbipos.iostock.vendr.vendrExact.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VendrExactVO extends PageVO {

    private static final long serialVersionUID = -2819721742558286098L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처코드 array */
    private String arrVendrCd[];
    /** 정산일 */
    private String excclcDate;
    /** 일련번호 일자_매장코드_거래처코드에 따른 일련번호 */
    private Integer seqNo;
    /** 정산구분 1:입고 -1:반출 2:지급액 */
    private String excclcFg;
    /** 정산금액 */
    private Integer excclcAmt;
    /** 정산VAT */
    private Integer excclcVat;
    /** 정산합계금액 */
    private Integer excclcTot;
    /** 전표번호 */
    private String slipNo;
    /** 비고 */
    private String remark;
    /** COL 클릭 구분 */
    private String colbindFg;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
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
     * @return the vendrCd
     */
    public String getVendrCd() {
        return vendrCd;
    }

    /**
     * @param vendrCd the vendrCd to set
     */
    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    /**
     * @return the arrVendrCd
     */
    public String[] getArrVendrCd() {
        return arrVendrCd;
    }

    /**
     * @param arrVendrCd the arrVendrCd to set
     */
    public void setArrVendrCd(String[] arrVendrCd) {
        this.arrVendrCd = arrVendrCd;
    }

    /**
     * @return the excclcDate
     */
    public String getExcclcDate() {
        return excclcDate;
    }

    /**
     * @param excclcDate the excclcDate to set
     */
    public void setExcclcDate(String excclcDate) {
        this.excclcDate = excclcDate;
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
     * @return the excclcFg
     */
    public String getExcclcFg() {
        return excclcFg;
    }

    /**
     * @param excclcFg the excclcFg to set
     */
    public void setExcclcFg(String excclcFg) {
        this.excclcFg = excclcFg;
    }

    /**
     * @return the excclcAmt
     */
    public Integer getExcclcAmt() {
        return excclcAmt;
    }

    /**
     * @param excclcAmt the excclcAmt to set
     */
    public void setExcclcAmt(Integer excclcAmt) {
        this.excclcAmt = excclcAmt;
    }

    /**
     * @return the excclcVat
     */
    public Integer getExcclcVat() {
        return excclcVat;
    }

    /**
     * @param excclcVat the excclcVat to set
     */
    public void setExcclcVat(Integer excclcVat) {
        this.excclcVat = excclcVat;
    }

    /**
     * @return the excclcTot
     */
    public Integer getExcclcTot() {
        return excclcTot;
    }

    /**
     * @param excclcTot the excclcTot to set
     */
    public void setExcclcTot(Integer excclcTot) {
        this.excclcTot = excclcTot;
    }

    /**
     * @return the slipNo
     */
    public String getSlipNo() {
        return slipNo;
    }

    /**
     * @param slipNo the slipNo to set
     */
    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the colbindFg
     */
    public String getColbindFg() {
        return colbindFg;
    }

    /**
     * @param colbindFg the colbindFg to set
     */
    public void setColbindFg(String colbindFg) {
        this.colbindFg = colbindFg;
    }

    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
}
