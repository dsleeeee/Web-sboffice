package kr.co.solbipos.excclc.excclc.saleRegistKmu.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SaleRegistKmuVO.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(일반)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleRegistKmuVO extends PageVO {

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
    private int paySeq;

    /** 결제구분 */
    private String saleGubunCombo;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 회원번호 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    /** 미사용컬럼 */
    private String bkMembrNm;

    /** 회원여부 */
    private String membrYn;

    /** 회원분류코드 */
    private String membrClassCd;

    /** 회원카드번호 */
    private String membrCardNo;

    /** 후불구분 */
    private String postpaidFg;

    /** 후불입금수단구분 */
    private String postpaidPayFg;

    /** 비매출승인번호 */
    private String nonsaleTypeApprNo;

    /** 원거래비매출승인번호 */
    private String orgNonsaleTypeApprNo;

    /** 비매출영수증번호 */
    private String nonsaleBillNo;

    /** 외상 금액 */
    private long postPaidAmt;

    private float postPaidPer;

    /** 후불번호 */
    private String postpaidNo;

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

    public int getPaySeq() {
        return paySeq;
    }

    public void setPaySeq(int paySeq) {
        this.paySeq = paySeq;
    }

    public String getSaleGubunCombo() { return saleGubunCombo; }

    public void setSaleGubunCombo(String saleGubunCombo) { this.saleGubunCombo = saleGubunCombo; }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public String getMembrNo() { return membrNo; }

    public void setMembrNo(String membrNo) { this.membrNo = membrNo; }

    public String getMembrNm() { return membrNm; }

    public void setMembrNm(String membrNm) { this.membrNm = membrNm; }

    public String getBkMembrNm() { return bkMembrNm; }

    public void setBkMembrNm(String bkMembrNm) { this.bkMembrNm = bkMembrNm; }

    public String getMembrYn() { return membrYn; }

    public void setMembrYn(String membrYn) { this.membrYn = membrYn; }

    public String getMembrClassCd() {
        return membrClassCd;
    }

    public void setMembrClassCd(String membrClassCd) { this.membrClassCd = membrClassCd; }

    public String getMembrCardNo() {
        return membrCardNo;
    }

    public void setMembrCardNo(String membrCardNo) { this.membrCardNo = membrCardNo; }

    public String getPostpaidFg() {
        return postpaidFg;
    }

    public void setPostpaidFg(String postpaidFg) { this.postpaidFg = postpaidFg; }

    public String getPostpaidPayFg() {
        return postpaidPayFg;
    }

    public void setPostpaidPayFg(String postpaidPayFg) { this.postpaidPayFg = postpaidPayFg; }

    public String getNonsaleTypeApprNo() { return nonsaleTypeApprNo; }

    public void setNonsaleTypeApprNo(String nonsaleTypeApprNo) { this.nonsaleTypeApprNo = nonsaleTypeApprNo; }

    public String getOrgNonsaleTypeApprNo() { return orgNonsaleTypeApprNo; }

    public void setOrgNonsaleTypeApprNo(String orgNonsaleTypeApprNo) { this.orgNonsaleTypeApprNo = orgNonsaleTypeApprNo; }

    public String getNonsaleBillNo() { return nonsaleBillNo; }

    public void setNonsaleBillNo(String nonsaleBillNo) { this.nonsaleBillNo = nonsaleBillNo; }

    public long getPostPaidAmt() { return postPaidAmt; }

    public void setPostPaidAmt(long postPaidAmt) { this.postPaidAmt = postPaidAmt; }

    public float getPostPaidPer() {
        return postPaidPer;
    }

    public void setPostPaidPer(float postPaidPer) {
        this.postPaidPer = postPaidPer;
    }

    public String getPostpaidNo() {
        return postpaidNo;
    }

    public void setPostpaidNo(String postpaidNo) {
        this.postpaidNo = postpaidNo;
    }

}