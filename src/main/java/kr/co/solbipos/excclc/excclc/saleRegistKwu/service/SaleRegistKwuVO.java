package kr.co.solbipos.excclc.excclc.saleRegistKwu.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleRegistKwuVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매출일자 */
    private String saleDate;
    /** 포스번호 */
    private String posNo;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 영수번호 */
    private String billNo;
    private String billDtlNo;
    /** 판매여부 */
    private String saleYn;
    /** 판매구분 */
    private String saleFg;
    private String prodSaleFg;
    private String prodTypeFg;
    /** 결제코드 */
    private String payCd;
    private String dcCd;
    /** 부가세구분 */
    private String vatFg;
    private String prodVatFg;
    private String membrNo;
    private String membrNm;

    private String depositAmt;
    private String postpaidAmt;
    private String businessAmt;
    private String teacherAmt;
    private String teacherCnt;
    private String transportAmt;
    private String qty;
    private String remainAmt;
    private String remark;

    private long saleUprc;
    private long saleQty;
    private long saleAmt;
    private long dcAmt;
    private long realSaleAmt;
    private long vatAmt;
    private long totSaleAmt;
    private long totDcAmt;
    private long totRealSaleAmt;
    private long totVatAmt;
    private long cashAmt;
    private long cardAmt;
    private long payAmt;
    private long recvPayAmt;
    private long taxSaleAmt;
    private long noTaxSaleAmt;
    private float cashPer;
    private float cardPer;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillDtlNo() {
        return billDtlNo;
    }

    public void setBillDtlNo(String billDtlNo) {
        this.billDtlNo = billDtlNo;
    }

    public String getSaleYn() {
        return saleYn;
    }

    public void setSaleYn(String saleYn) {
        this.saleYn = saleYn;
    }

    public String getSaleFg() {
        return saleFg;
    }

    public void setSaleFg(String saleFg) {
        this.saleFg = saleFg;
    }

    public String getProdSaleFg() {
        return prodSaleFg;
    }

    public void setProdSaleFg(String prodSaleFg) {
        this.prodSaleFg = prodSaleFg;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getPayCd() {
        return payCd;
    }

    public void setPayCd(String payCd) {
        this.payCd = payCd;
    }

    public String getDcCd() {
        return dcCd;
    }

    public void setDcCd(String dcCd) {
        this.dcCd = dcCd;
    }

    public String getVatFg() {
        return vatFg;
    }

    public void setVatFg(String vatFg) {
        this.vatFg = vatFg;
    }

    public String getProdVatFg() {
        return prodVatFg;
    }

    public void setProdVatFg(String prodVatFg) {
        this.prodVatFg = prodVatFg;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    public String getMembrNm() {
        return membrNm;
    }

    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    public String getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(String depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getPostpaidAmt() {
        return postpaidAmt;
    }

    public void setPostpaidAmt(String postpaidAmt) {
        this.postpaidAmt = postpaidAmt;
    }

    public String getBusinessAmt() {
        return businessAmt;
    }

    public void setBusinessAmt(String businessAmt) {
        this.businessAmt = businessAmt;
    }

    public String getTeacherAmt() {
        return teacherAmt;
    }

    public void setTeacherAmt(String teacherAmt) {
        this.teacherAmt = teacherAmt;
    }

    public String getTeacherCnt() {
        return teacherCnt;
    }

    public void setTeacherCnt(String teacherCnt) {
        this.teacherCnt = teacherCnt;
    }

    public String getTransportAmt() {
        return transportAmt;
    }

    public void setTransportAmt(String transportAmt) {
        this.transportAmt = transportAmt;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(String remainAmt) {
        this.remainAmt = remainAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(long saleUprc) {
        this.saleUprc = saleUprc;
    }

    public long getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(long saleQty) {
        this.saleQty = saleQty;
    }

    public long getSaleAmt() {
        return saleAmt;
    }

    public void setSaleAmt(long saleAmt) {
        this.saleAmt = saleAmt;
    }

    public long getDcAmt() {
        return dcAmt;
    }

    public void setDcAmt(long dcAmt) {
        this.dcAmt = dcAmt;
    }

    public long getRealSaleAmt() {
        return realSaleAmt;
    }

    public void setRealSaleAmt(long realSaleAmt) {
        this.realSaleAmt = realSaleAmt;
    }

    public long getVatAmt() {
        return vatAmt;
    }

    public void setVatAmt(long vatAmt) {
        this.vatAmt = vatAmt;
    }

    public long getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(long totSaleAmt) {
        this.totSaleAmt = totSaleAmt;
    }

    public long getTotDcAmt() {
        return totDcAmt;
    }

    public void setTotDcAmt(long totDcAmt) {
        this.totDcAmt = totDcAmt;
    }

    public long getTotRealSaleAmt() {
        return totRealSaleAmt;
    }

    public void setTotRealSaleAmt(long totRealSaleAmt) {
        this.totRealSaleAmt = totRealSaleAmt;
    }

    public long getTotVatAmt() {
        return totVatAmt;
    }

    public void setTotVatAmt(long totVatAmt) {
        this.totVatAmt = totVatAmt;
    }

    public long getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(long cashAmt) {
        this.cashAmt = cashAmt;
    }

    public long getCardAmt() {
        return cardAmt;
    }

    public void setCardAmt(long cardAmt) {
        this.cardAmt = cardAmt;
    }

    public long getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(long payAmt) {
        this.payAmt = payAmt;
    }

    public long getRecvPayAmt() {
        return recvPayAmt;
    }

    public void setRecvPayAmt(long recvPayAmt) {
        this.recvPayAmt = recvPayAmt;
    }

    public long getTaxSaleAmt() {
        return taxSaleAmt;
    }

    public void setTaxSaleAmt(long taxSaleAmt) {
        this.taxSaleAmt = taxSaleAmt;
    }

    public long getNoTaxSaleAmt() {
        return noTaxSaleAmt;
    }

    public void setNoTaxSaleAmt(long noTaxSaleAmt) {
        this.noTaxSaleAmt = noTaxSaleAmt;
    }

    public float getCashPer() {
        return cashPer;
    }

    public void setCashPer(float cashPer) {
        this.cashPer = cashPer;
    }

    public float getCardPer() {
        return cardPer;
    }

    public void setCardPer(float cardPer) {
        this.cardPer = cardPer;
    }
}
