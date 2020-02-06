package kr.co.solbipos.sale.status.posExcclc.posExcclc.service;

import kr.co.solbipos.application.common.service.PageVO;

public class PosExcclcVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    private String hqOfficeCd; //본사사업장코드
	private String hqBrandCd; //본사브랜드코드

    private String regSeq; //등록차수

	private String closeFgSeq; //마감구분
	private String closeFgNm; //마감구분명

    private String storeCd; //매장코드
    private String storeNm; //매장명
    private String saleDate; //영업일자
    private String posNo; //POS번호
    private String closeFg; //마감구분 (1:개점 2:마감)
    private String regDt; //등록일시
    private String totSaleAmt; //총매출액
    private String totDcAmt; //총할인액
    private String realSaleAmt; //실매출액
    private String totFundAmt; //영업준비금
    private String cashExactAmt; //현금매출액
    private String accntInAmt; //입금액
    private String accntOutAmt; //출금액
    private String cashTicketAmt; //현금시재액
    private String lostAmt; //현금과부족

    private String openDt; //오픈일시
	private String closeDt; //마감일시
    private String totBillCnt; //영수건수
    private String totBillTran; //영수단가
    private String totGuestCnt; //객단가
    private String totTipAmt; //봉사료
    private String totEtcAmt; //에누리금액
    private String rtnBillCnt; //취소매출건수
    private String rtnBillAmt; //취소매출금액
    private String payAmtCard; //결제금액(신용카드)
    private String payAmtCash; //결제금액(현금)
    private String payAmtPayco; //결제금액(페이코)
    private String payAmtVpoint; //결제금액(VMEM 포인트)
    private String payAmtVcoupn; //결제금액(VMEM 전자상품권)
    private String payAmtMpay; //결제금액(모바일페이)
    private String payAmtMcoupn; //결제금액(모바일쿠폰)
    private String payAmtMembr; //결제금액(포인트)
    private String payAmtPrepaid; //결제금액(선불)
    private String payAmtPostpaid; //결제금액(후불)
    private String payAmtCoupn; //결제금액(쿠폰할인)
    private String payAmtGift; //결제금액(상품권)
    private String payAmtFstmp; //결제금액(식권)
    private String payAmtPartner; //결제금액(제휴할인)
    private String payAmtOkcsb; //결제금액(OK캐쉬백)
    private String payAmtEmpCard; //결제금액(사원카드)
    private String payAmtEmpTemporary; //결제금액(가승인)
    private String payAmtPrev; //결제금액(이전월자료)
    private String dcAmtGeneral; //할인금액(일반)
    private String dcAmtCoupn; //할인금액(쿠폰)
    private String dcAmtMembr; //할인금액(회원)
    private String dcAmtPartner; //할인금액(제휴)
    private String dcAmtService; //할인금액(서비스)
    private String dcAmtPromtn; //할인금액(프로모션)
    private String dcAmtPack; //할인금액(포장할인)
    private String dcAmtSite; //할인금액(현장할인)
    private String dcAmtVcoupn; //할인금액(쿠폰할인)
    private String cashTicketAmt10; //현금시재내역(십원)
    private String cashTicketAmt50; //현금시재내역(오십원)
    private String cashTicketAmt100; //현금시재내역(백원)
    private String cashTicketAmt500; //현금시재내역(오백원)
    private String cashTicketAmt1000; //현금시재내역(천원)
    private String cashTicketAmt5000; //현금시재내역(오천원)
    private String cashTicketAmt10000; //현금시재내역(만원)
    private String cashTicketAmt50000; //현금시재내역(오만원)
    private String cashTicketAmt100000; //현금시재내역(십만원)
    private String cashTicketAmtEct; //현금시재내역(기타)
    private String exactAmtCash; //현금시재금액
    private String exactAmtGift; //상품권시재금액
    private String lostAmtCash; //현금과부족
    private String lostAmtGift; //상품권과부족

    public String getHqOfficeCd() {
		return hqOfficeCd;
	}
	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}
	public String getHqBrandCd() {
		return hqBrandCd;
	}
	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
	}
    public String getRegSeq() {
		return regSeq;
	}
	public void setRegSeq(String regSeq) {
		this.regSeq = regSeq;
	}
    public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String getStoreNm() {
		return storeNm;
	}
	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
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
	public String getCloseFg() {
		return closeFg;
	}
	public void setCloseFg(String closeFg) {
		this.closeFg = closeFg;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getTotSaleAmt() {
		return totSaleAmt;
	}
	public void setTotSaleAmt(String totSaleAmt) {
		this.totSaleAmt = totSaleAmt;
	}
	public String getTotDcAmt() {
		return totDcAmt;
	}
	public void setTotDcAmt(String totDcAmt) {
		this.totDcAmt = totDcAmt;
	}
	public String getRealSaleAmt() {
		return realSaleAmt;
	}
	public void setRealSaleAmt(String realSaleAmt) {
		this.realSaleAmt = realSaleAmt;
	}
	public String getTotFundAmt() {
		return totFundAmt;
	}
	public void setTotFundAmt(String totFundAmt) {
		this.totFundAmt = totFundAmt;
	}
	public String getCashExactAmt() {
		return cashExactAmt;
	}
	public void setCashExactAmt(String cashExactAmt) {
		this.cashExactAmt = cashExactAmt;
	}
	public String getAccntInAmt() {
		return accntInAmt;
	}
	public void setAccntInAmt(String accntInAmt) {
		this.accntInAmt = accntInAmt;
	}
	public String getAccntOutAmt() {
		return accntOutAmt;
	}
	public void setAccntOutAmt(String accntOutAmt) {
		this.accntOutAmt = accntOutAmt;
	}
	public String getCashTicketAmt() {
		return cashTicketAmt;
	}
	public void setCashTicketAmt(String cashTicketAmt) {
		this.cashTicketAmt = cashTicketAmt;
	}
	public String getLostAmt() {
		return lostAmt;
	}
	public void setLostAmt(String lostAmt) {
		this.lostAmt = lostAmt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOpenDt() {
		return openDt;
	}
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}
	public String getCloseDt() {
		return closeDt;
	}
	public void setCloseDt(String closeDt) {
		this.closeDt = closeDt;
	}
	public String getTotBillCnt() {
		return totBillCnt;
	}
	public void setTotBillCnt(String totBillCnt) {
		this.totBillCnt = totBillCnt;
	}
	public String getTotBillTran() {
		return totBillTran;
	}
	public void setTotBillTran(String totBillTran) {
		this.totBillTran = totBillTran;
	}
	public String getTotGuestCnt() {
		return totGuestCnt;
	}
	public void setTotGuestCnt(String totGuestCnt) {
		this.totGuestCnt = totGuestCnt;
	}
	public String getTotTipAmt() {
		return totTipAmt;
	}
	public void setTotTipAmt(String totTipAmt) {
		this.totTipAmt = totTipAmt;
	}
	public String getTotEtcAmt() {
		return totEtcAmt;
	}
	public void setTotEtcAmt(String totEtcAmt) {
		this.totEtcAmt = totEtcAmt;
	}
	public String getRtnBillCnt() {
		return rtnBillCnt;
	}
	public void setRtnBillCnt(String rtnBillCnt) {
		this.rtnBillCnt = rtnBillCnt;
	}
	public String getRtnBillAmt() {
		return rtnBillAmt;
	}
	public void setRtnBillAmt(String rtnBillAmt) {
		this.rtnBillAmt = rtnBillAmt;
	}
	public String getPayAmtCard() {
		return payAmtCard;
	}
	public void setPayAmtCard(String payAmtCard) {
		this.payAmtCard = payAmtCard;
	}
	public String getPayAmtCash() {
		return payAmtCash;
	}
	public void setPayAmtCash(String payAmtCash) {
		this.payAmtCash = payAmtCash;
	}
	public String getPayAmtPayco() {
		return payAmtPayco;
	}
	public void setPayAmtPayco(String payAmtPayco) {
		this.payAmtPayco = payAmtPayco;
	}
	public String getPayAmtVpoint() {
		return payAmtVpoint;
	}
	public void setPayAmtVpoint(String payAmtVpoint) {
		this.payAmtVpoint = payAmtVpoint;
	}
	public String getPayAmtVcoupn() {
		return payAmtVcoupn;
	}
	public void setPayAmtVcoupn(String payAmtVcoupn) {
		this.payAmtVcoupn = payAmtVcoupn;
	}
	public String getPayAmtMpay() {
		return payAmtMpay;
	}
	public void setPayAmtMpay(String payAmtMpay) {
		this.payAmtMpay = payAmtMpay;
	}
	public String getPayAmtMcoupn() {
		return payAmtMcoupn;
	}
	public void setPayAmtMcoupn(String payAmtMcoupn) {
		this.payAmtMcoupn = payAmtMcoupn;
	}
	public String getPayAmtMembr() {
		return payAmtMembr;
	}
	public void setPayAmtMembr(String payAmtMembr) {
		this.payAmtMembr = payAmtMembr;
	}
	public String getPayAmtPrepaid() {
		return payAmtPrepaid;
	}
	public void setPayAmtPrepaid(String payAmtPrepaid) {
		this.payAmtPrepaid = payAmtPrepaid;
	}
	public String getPayAmtPostpaid() {
		return payAmtPostpaid;
	}
	public void setPayAmtPostpaid(String payAmtPostpaid) {
		this.payAmtPostpaid = payAmtPostpaid;
	}
	public String getPayAmtCoupn() {
		return payAmtCoupn;
	}
	public void setPayAmtCoupn(String payAmtCoupn) {
		this.payAmtCoupn = payAmtCoupn;
	}
	public String getPayAmtGift() {
		return payAmtGift;
	}
	public void setPayAmtGift(String payAmtGift) {
		this.payAmtGift = payAmtGift;
	}
	public String getPayAmtFstmp() {
		return payAmtFstmp;
	}
	public void setPayAmtFstmp(String payAmtFstmp) {
		this.payAmtFstmp = payAmtFstmp;
	}
	public String getPayAmtPartner() {
		return payAmtPartner;
	}
	public void setPayAmtPartner(String payAmtPartner) {
		this.payAmtPartner = payAmtPartner;
	}
	public String getPayAmtOkcsb() {
		return payAmtOkcsb;
	}
	public void setPayAmtOkcsb(String payAmtOkcsb) {
		this.payAmtOkcsb = payAmtOkcsb;
	}
	public String getPayAmtEmpCard() {
		return payAmtEmpCard;
	}
	public void setPayAmtEmpCard(String payAmtEmpCard) {
		this.payAmtEmpCard = payAmtEmpCard;
	}
	public String getPayAmtEmpTemporary() {
		return payAmtEmpTemporary;
	}
	public void setPayAmtEmpTemporary(String payAmtEmpTemporary) {
		this.payAmtEmpTemporary = payAmtEmpTemporary;
	}
	public String getPayAmtPrev() {
		return payAmtPrev;
	}
	public void setPayAmtPrev(String payAmtPrev) {
		this.payAmtPrev = payAmtPrev;
	}
	public String getDcAmtGeneral() {
		return dcAmtGeneral;
	}
	public void setDcAmtGeneral(String dcAmtGeneral) {
		this.dcAmtGeneral = dcAmtGeneral;
	}
	public String getDcAmtCoupn() {
		return dcAmtCoupn;
	}
	public void setDcAmtCoupn(String dcAmtCoupn) {
		this.dcAmtCoupn = dcAmtCoupn;
	}
	public String getDcAmtMembr() {
		return dcAmtMembr;
	}
	public void setDcAmtMembr(String dcAmtMembr) {
		this.dcAmtMembr = dcAmtMembr;
	}
	public String getDcAmtPartner() {
		return dcAmtPartner;
	}
	public void setDcAmtPartner(String dcAmtPartner) {
		this.dcAmtPartner = dcAmtPartner;
	}
	public String getDcAmtService() {
		return dcAmtService;
	}
	public void setDcAmtService(String dcAmtService) {
		this.dcAmtService = dcAmtService;
	}
	public String getDcAmtPromtn() {
		return dcAmtPromtn;
	}
	public void setDcAmtPromtn(String dcAmtPromtn) {
		this.dcAmtPromtn = dcAmtPromtn;
	}
	public String getDcAmtPack() {
		return dcAmtPack;
	}
	public void setDcAmtPack(String dcAmtPack) {
		this.dcAmtPack = dcAmtPack;
	}
	public String getDcAmtSite() {
		return dcAmtSite;
	}
	public void setDcAmtSite(String dcAmtSite) {
		this.dcAmtSite = dcAmtSite;
	}
	public String getDcAmtVcoupn() {
		return dcAmtVcoupn;
	}
	public void setDcAmtVcoupn(String dcAmtVcoupn) {
		this.dcAmtVcoupn = dcAmtVcoupn;
	}
	public String getCashTicketAmt10() {
		return cashTicketAmt10;
	}
	public void setCashTicketAmt10(String cashTicketAmt10) {
		this.cashTicketAmt10 = cashTicketAmt10;
	}
	public String getCashTicketAmt50() {
		return cashTicketAmt50;
	}
	public void setCashTicketAmt50(String cashTicketAmt50) {
		this.cashTicketAmt50 = cashTicketAmt50;
	}
	public String getCashTicketAmt100() {
		return cashTicketAmt100;
	}
	public void setCashTicketAmt100(String cashTicketAmt100) {
		this.cashTicketAmt100 = cashTicketAmt100;
	}
	public String getCashTicketAmt500() {
		return cashTicketAmt500;
	}
	public void setCashTicketAmt500(String cashTicketAmt500) {
		this.cashTicketAmt500 = cashTicketAmt500;
	}
	public String getCashTicketAmt1000() {
		return cashTicketAmt1000;
	}
	public void setCashTicketAmt1000(String cashTicketAmt1000) {
		this.cashTicketAmt1000 = cashTicketAmt1000;
	}
	public String getCashTicketAmt5000() {
		return cashTicketAmt5000;
	}
	public void setCashTicketAmt5000(String cashTicketAmt5000) {
		this.cashTicketAmt5000 = cashTicketAmt5000;
	}
	public String getCashTicketAmt10000() {
		return cashTicketAmt10000;
	}
	public void setCashTicketAmt10000(String cashTicketAmt10000) {
		this.cashTicketAmt10000 = cashTicketAmt10000;
	}
	public String getCashTicketAmt50000() {
		return cashTicketAmt50000;
	}
	public void setCashTicketAmt50000(String cashTicketAmt50000) {
		this.cashTicketAmt50000 = cashTicketAmt50000;
	}
	public String getCashTicketAmt100000() {
		return cashTicketAmt100000;
	}
	public void setCashTicketAmt100000(String cashTicketAmt100000) {
		this.cashTicketAmt100000 = cashTicketAmt100000;
	}
	public String getCashTicketAmtEct() {
		return cashTicketAmtEct;
	}
	public void setCashTicketAmtEct(String cashTicketAmtEct) {
		this.cashTicketAmtEct = cashTicketAmtEct;
	}
	public String getExactAmtCash() {
		return exactAmtCash;
	}
	public void setExactAmtCash(String exactAmtCash) {
		this.exactAmtCash = exactAmtCash;
	}
	public String getExactAmtGift() {
		return exactAmtGift;
	}
	public void setExactAmtGift(String exactAmtGift) {
		this.exactAmtGift = exactAmtGift;
	}
	public String getLostAmtCash() {
		return lostAmtCash;
	}
	public void setLostAmtCash(String lostAmtCash) {
		this.lostAmtCash = lostAmtCash;
	}
	public String getLostAmtGift() {
		return lostAmtGift;
	}
	public void setLostAmtGift(String lostAmtGift) {
		this.lostAmtGift = lostAmtGift;
	}
	public String getCloseFgSeq() {
		return closeFgSeq;
	}
	public void setCloseFgSeq(String closeFgSeq) {
		this.closeFgSeq = closeFgSeq;
	}
	public String getCloseFgNm() {
		return closeFgNm;
	}
	public void setCloseFgNm(String closeFgNm) {
		this.closeFgNm = closeFgNm;
	}

}
