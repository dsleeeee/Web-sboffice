package kr.co.solbipos.excclc.excclc.saleRegist.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleRegistVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

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
    private String prodTypeFg;
    /** 결제코드 */
    private String payCd;
    /** 부가세구분 */
    private String vatFg;
    private int saleUprc;
    private int saleQty;
    private int saleAmt;
    private int dcAmt;
    private int realSaleAmt;
    private int vatAmt;
    private int totSaleAmt;
    private int totDcAmt;
    private int totRealSaleAmt;
    private int totVatAmt;
    private int cashAmt;
    private int cardAmt;
    private int payAmt;
    private int recvPayAmt;
    private int taxSaleAmt;
    private int noTaxSaleAmt;
    private float cashPer;
    private float cardPer;

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

    public String getVatFg() {
        return vatFg;
    }

    public void setVatFg(String vatFg) {
        this.vatFg = vatFg;
    }

    public int getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(int saleUprc) {
        this.saleUprc = saleUprc;
    }

    public int getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(int saleQty) {
        this.saleQty = saleQty;
    }

    public int getSaleAmt() {
        return saleAmt;
    }

    public void setSaleAmt(int saleAmt) {
        this.saleAmt = saleAmt;
    }

    public int getDcAmt() {
        return dcAmt;
    }

    public void setDcAmt(int dcAmt) {
        this.dcAmt = dcAmt;
    }

    public int getRealSaleAmt() {
        return realSaleAmt;
    }

    public void setRealSaleAmt(int realSaleAmt) {
        this.realSaleAmt = realSaleAmt;
    }

    public int getVatAmt() {
        return vatAmt;
    }

    public void setVatAmt(int vatAmt) {
        this.vatAmt = vatAmt;
    }

    public int getTotSaleAmt() {
        return totSaleAmt;
    }

    public void setTotSaleAmt(int totSaleAmt) {
        this.totSaleAmt = totSaleAmt;
    }

    public int getTotDcAmt() {
        return totDcAmt;
    }

    public void setTotDcAmt(int totDcAmt) {
        this.totDcAmt = totDcAmt;
    }

    public int getTotRealSaleAmt() {
        return totRealSaleAmt;
    }

    public void setTotRealSaleAmt(int totRealSaleAmt) {
        this.totRealSaleAmt = totRealSaleAmt;
    }

    public int getTotVatAmt() {
        return totVatAmt;
    }

    public void setTotVatAmt(int totVatAmt) {
        this.totVatAmt = totVatAmt;
    }

    public int getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(int cashAmt) {
        this.cashAmt = cashAmt;
    }

    public int getCardAmt() {
        return cardAmt;
    }

    public void setCardAmt(int cardAmt) {
        this.cardAmt = cardAmt;
    }

    public int getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(int payAmt) {
        this.payAmt = payAmt;
    }

    public int getRecvPayAmt() {
        return recvPayAmt;
    }

    public void setRecvPayAmt(int recvPayAmt) {
        this.recvPayAmt = recvPayAmt;
    }

    public int getTaxSaleAmt() {
        return taxSaleAmt;
    }

    public void setTaxSaleAmt(int taxSaleAmt) {
        this.taxSaleAmt = taxSaleAmt;
    }

    public int getNoTaxSaleAmt() {
        return noTaxSaleAmt;
    }

    public void setNoTaxSaleAmt(int noTaxSaleAmt) {
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
