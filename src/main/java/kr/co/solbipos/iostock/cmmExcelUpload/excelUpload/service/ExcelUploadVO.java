package kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ExcelUploadVO extends PageVO {

    private static final long serialVersionUID = -1275206354878077873L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 세션ID */
    private String sessionId;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 상품코드/바코드 */
    private String prodBarcdCd;
    /** 상품코드 */
    private String prodCd;
    /** 바코드 */
    private String barcdCd;
    /** 단가 */
    private Float uprc;
    /** 단위수량 */
    private Long unitQty;
    /** 낱개수량 */
    private Long etcQty;
    /** 수량 */
    private Long qty;
    /** HD 비고 */
    private String hdRemark;
    /** 비고 */
    private String remark;
    /** 업로드 구분 */
    private String uploadFg;
    /** 일자 */
    private String date;
    /** 전표구분 ( 1:주문, -1:반품 ) */
    private Integer slipFg;
    /** 수량적용/추가여부 */
    private String addQtyFg;
    /** 시퀀스 */
    private Integer seq;
    /** 시퀀스 인덱스 */
    private Integer seqNo;
    /** 제목 */
    private String title;
    /** 전표번호 */
    private String slipNo;
    /** 거래처코드 */
    private String vendrCd;
    /** 부가세여부 */
    private Integer vatFg;

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

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

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
     * @return the prodBarcdCd
     */
    public String getProdBarcdCd() {
        return prodBarcdCd;
    }

    /**
     * @param prodBarcdCd the prodBarcdCd to set
     */
    public void setProdBarcdCd(String prodBarcdCd) {
        this.prodBarcdCd = prodBarcdCd;
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
     * @return the barcdCd
     */
    public String getBarcdCd() {
        return barcdCd;
    }

    /**
     * @param barcdCd the barcdCd to set
     */
    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    /**
     * @return the uprc
     */
    public Float getUprc() {
        return uprc;
    }

    /**
     * @param uprc the uprc to set
     */
    public void setUprc(Float uprc) {
        this.uprc = uprc;
    }

    /**
     * @return the unitQty
     */
    public Long getUnitQty() {
        return unitQty;
    }

    /**
     * @param unitQty the unitQty to set
     */
    public void setUnitQty(Long unitQty) {
        this.unitQty = unitQty;
    }

    /**
     * @return the etcQty
     */
    public Long getEtcQty() {
        return etcQty;
    }

    /**
     * @param etcQty the etcQty to set
     */
    public void setEtcQty(Long etcQty) {
        this.etcQty = etcQty;
    }

    /**
     * @return the qty
     */
    public Long getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(Long qty) {
        this.qty = qty;
    }

    /**
     * @return the hdRemark
     */
    public String getHdRemark() {
        return hdRemark;
    }

    /**
     * @param hdRemark the hdRemark to set
     */
    public void setHdRemark(String hdRemark) {
        this.hdRemark = hdRemark;
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
     * @return the uploadFg
     */
    public String getUploadFg() {
        return uploadFg;
    }

    /**
     * @param uploadFg the uploadFg to set
     */
    public void setUploadFg(String uploadFg) {
        this.uploadFg = uploadFg;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the slipFg
     */
    public Integer getSlipFg() {
        return slipFg;
    }

    /**
     * @param slipFg the slipFg to set
     */
    public void setSlipFg(Integer slipFg) {
        this.slipFg = slipFg;
    }

    /**
     * @return the addQtyFg
     */
    public String getAddQtyFg() {
        return addQtyFg;
    }

    /**
     * @param addQtyFg the addQtyFg to set
     */
    public void setAddQtyFg(String addQtyFg) {
        this.addQtyFg = addQtyFg;
    }

    /**
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the vatFg
     */
    public Integer getVatFg() {
        return vatFg;
    }

    /**
     * @param vatFg the vatFg to set
     */
    public void setVatFg(Integer vatFg) {
        this.vatFg = vatFg;
    }
}
