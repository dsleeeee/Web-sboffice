package kr.co.solbipos.sale.anals.dailyreport.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : DailyReportVO.java
 * @Description : 매출관리 > 매출분석 > 영업일보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.28  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP
 * @since 2020. 01.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DailyReportVO extends PageVO {

	private static final long serialVersionUID = -4762149989013703841L;

//    /**
//     * workMode<br>
//     * 1 : 상품정보수정<br>
//     * 2 : 신규상품등록<br>
//     * 3 : 매장등록<br>
//     */
//    private WorkModeFg workMode;

	/**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

	/** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 array */
    private String arrStoreCd[];


//    /** 매장명 */
//    private String storeNm;
//
//    /** 상품분류코드 */
//    private String prodClassCd;
//    /** 상품코드 */
//    private String prodCd;
//    /** 상품명 */
//    private String prodNm;
//
//    /** 브랜드코드 */
//    private String hqBrandCd;
//    /** 브랜드명 */
//    private String hqBrandNm;



    //조회조건              ----------------------------------------------------------------------------
    //[조회 시작일자] & [조회종료일자]는 PageVO 것 사용
    /** 조회 시작일자 */
    //private String	searchStartDate;
    /** 조회 종료일자 */
    //private String	searchEndDate;
    /** 조회 매장코드 */
    private String	searchStoreCd;


	//매출종합              ----------------------------------------------------------------------------
    /** 판매구분 (1:판매 -1:반품) */
    private String 	slSaleFg;
    /** 총매출 */
    private Double 	slTotSaleAmt;
    /** 할인 */
    private Double 	slTotDcAmt;
    /** 실매출 */
    private Double 	slRealSaleAmt;
    /** 가액 */
    private Double 	slGaAmt;
    /** 부가세 */
    private Double 	slVatAmt;
    /** 봉사료 */
    private Double 	slTotTipAmt;
    /** 에누리 */
    private Double 	slTotEtcAmt;
    /** 영수건수 */
    private Integer	slTotBillCnt;
    /** 객수 */
    private Integer	slTotGuestCnt;
    /** 영수단가 */
    private Double 	slBillUnprc;
    /** 객단가 */
    private Double 	slGuestUnprc;


    //결제수단              ----------------------------------------------------------------------------
    /** 실매출 */
    private Double 	payRealSaleAmt;
    /** 봉사료 */
    private Double 	payTotTipAmt;
    /** 에누리 */
    private Double 	payTotEtcAmt;
    /** 신용카드 */
    private Double 	payCard;
    /** 현금 */
    private Double 	payCash;
    /** 페이코 */
    private Double 	payPayco;
    /** VMEM 포인트 */
    private Double 	payVpoint;
    /** VMEM 쿠폰 */
    private Double 	payVcoupn;
    /** VMEM 전자상품권 */
    private Double 	payVcharge;
    /** 모바일페이 */
    private Double 	payMpay;
    /** 모바일쿠폰 */
    private Double 	payMcoupn;
    /** 포인트 */
    private Double 	payMembr;
    /** 선불 */
    private Double 	payPrepaid;
    /** 후불 */
    private Double 	payPostpaid;
    /** 쿠폰할인 */
    private Double 	payCoupn;
    /** 상품권 */
    private Double 	payGift;
    /** 식권 */
    private Double 	payFstmp;
    /** 제휴할인 */
    private Double 	payPartner;
    /** OK캐쉬백 */
    private Double 	payOkcsb;
    /** 사원카드 */
    private Double 	payEmpCard;
    /** 가승인 */
    private Double 	payTemporary;
    /** 스마트오더 */
    private Double 	paySmartOrderAmt;



	//비매출종합              ----------------------------------------------------------------------------
    /** 판매구분 (1:판매 -1:반품) */
    private String 	nslSaleFg;
    /** 총매출 */
    private Double 	nslTotSaleAmt;
    /** 할인 */
    private Double 	nslTotDcAmt;
    /** 실매출 */
    private Double 	nslRealSaleAmt;
    /** 가액 */
    private Double 	nslGaAmt;
    /** 부가세 */
    private Double 	nslVatAmt;
    /** 봉사료 */
    private Double 	nslTotTipAmt;
    /** 에누리 */
    private Double 	nslTotEtcAmt;
    /** 영수건수 */
    private Integer	nslTotBillCnt;
    /** 영수단가 */
    private Double 	nslBillUnprc;


    //비매출 결제수단              ----------------------------------------------------------------------------
    /** 실매출 */
    private Double 	npayRealSaleAmt;
    /** 봉사료 */
    private Double 	npayTotTipAmt;
    /** 에누리 */
    private Double 	npayTotEtcAmt;
    /** 신용카드 */
    private Double 	npayCard;
    /** 현금 */
    private Double 	npayCash;



    //포스정산              ----------------------------------------------------------------------------
    /** 준비금 */
    private Double 	posFundAmt;
    /** 현금매출 */
    private Double 	posCashExactAmt;
    /** 시재입금 */
    private Double 	posIomoneyInAmt;
    /** 시재출금 */
    private Double 	posIomoneyOutAmt;

    /** 외상입금 */

    /** 상품권환불액 */
    private Double 	posGiftAmt;

    /** 현금시재 */

    /** 마감시재 */

    /** 과부족 */
    private Double 	posLostAmt;
    /** 기타매출 */
    private Double 	posNonsaleAmt;



    //판매원별 매출           ----------------------------------------------------------------------------
    /**  */
    private String 	empNo;
    /** 판매원 */
    private String 	empNm;
    /** 판매건수 */
    private Integer	empSalCnt;
    /** 판매카드 */
    private Double 	empSalTotalCard;
    /** 판매현금 */
    private Double 	empSalTotalCash;
    /** 판매기타 */
    private Double 	empSalTotalEtc;
    /** 반품건수 */
    private String 	empRtnCnt;
    /** 반품카드 */
    private Integer	empRtnTotalCard;
    /** 반품현금 */
    private Double 	empRtnTotalCash;
    /** 반품기타 */
    private Double 	empRtnTotalEtc;
    /** 주문취소건수 */


    //할인내역              ----------------------------------------------------------------------------
    /**  */
    private String  dcCd;
    /** 항목 */
    private String  dcNm;
    /** 수량 */
    private Integer dcSaleQty;
    /** 총매출 */
    private Double 	dcSaleAmt;
    /** 할인액 */
    private Double 	dcAmt;
    /** 총할인액 */
    private Double 	dcTotDcAmt;
     /** 실매출액 */
    private Double 	dcRealSaleAmt;



    //할인상세내역            ----------------------------------------------------------------------------
    /** 할인내역 */
    private String 	dcdtlDcCd;
    /** 상세내역 */
    private String 	dcdtlDcNm;
    /** 수량 */
    private Integer	dcdtlCnt;
    /** 총매출 */
    private Double 	dcdtlTotSaleAmt;
    /** 할인액 */
    private Double 	dcdtlDcAmt;
    /** 총할인액 */
    private Double 	dcdtlTotDcAmt;
     /** 실매출액 */
    private Double 	dcdtlRealSaleAmt;


    //상품권 판매 및 회수내역     ----------------------------------------------------------------------------
    /** 분류 */
    private String 	giftCd;
    /** 권종 */
    private String 	giftNm;
    /** 액면가 */
    private Double 	giftUprc;
    /** 출고수량 */
    /** 출고금액 */

    /** 판매수량 */
    /** 판매금액 */

    /** 회수수량 */
    /** 회수금액 */

    /** 회수반입수량 */
    /** 회수반입금액 */


    //수발주 내역            ----------------------------------------------------------------------------



    //대분류별 매출           ----------------------------------------------------------------------------
    /** 대분류명 */
    private String  lv1Nm;
    /** 대분류 총매출 */
    private Double  lv1TotSaleAmt;
    /** 대분류 할인 */
    private Double  lv1TotDcAmt;
    /** 대분류 실매출 */
    private Double  lv1RealSaleAmt;
    /** 대분류 수량 */
    private Integer lv1TotSaleQty;


    //중분류별 매출           ----------------------------------------------------------------------------
    /** 중분류명 */
    private String  lv2Nm;
    /** 중분류 총매출 */
    private Double  lv2TotSaleAmt;
    /** 중분류 할인 */
    private Double  lv2TotDcAmt;
    /** 중분류 실매출 */
    private Double  lv2RealSaleAmt;
    /** 중분류 수량 */
    private Integer lv2TotSaleQty;


    //소분류별 매출           ----------------------------------------------------------------------------
    /** 소분류명 */
    private String  lv3Nm;
    /** 소분류 총매출 */
    private Double  lv3TotSaleAmt;
    /** 소분류 할인 */
    private Double  lv3TotDcAmt;
    /** 소분류 실매출 */
    private Double  lv3RealSaleAmt;
    /** 소분류 수량 */
    private Integer lv3TotSaleQty;


    //상품별 매출            ----------------------------------------------------------------------------
    /** 상품 */
    private String  prodNm;
    /** 수량 */
    private Integer prodTotSaleQty;
    /** 실매출 */
    private Double  prodRealSaleAmt;


    //경쟁사 매출            ----------------------------------------------------------------------------



    //승인현황              ----------------------------------------------------------------------------
    /** 승인구분(승인,취소) */
    private String  apprNm;

    /** 총건수 */
    private Integer apprCntCard;
    /** 승인금액 */
    private Double 	apprApCard;
    /** 할인금액 */
    private Double 	apprDcCard;

    /** 총건수 */
    private Integer apprCntCash;
    /** 승인금액 */
    private Double 	apprApCash;
    /** 할인금액 */
    private Double 	apprDcCash;

    /** 총건수 */
    private Integer apprCntPayco;
    /** 승인금액 */
    private Double 	apprApPayco;
    /** 할인금액 */
    private Double 	apprDcPayco;

    /** 총건수 */
    private Integer apprCntMpay;
    /** 승인금액 */
    private Double 	apprApMpay;
    /** 할인금액 */
    private Double 	apprDcMpay;

    /** 총건수 */
    private Integer apprCntMcoupn;
    /** 승인금액 */
    private Double 	apprApMcoupn;
    /** 할인금액 */
    private Double 	apprDcMcoupn;

    /** 총건수 */
    private Integer apprCntPartner;
    /** 승인금액 */
    private Double 	apprApPartner;
    /** 할인금액 */
    private Double 	apprDcPartner;


    /** 총건수 */
    private Integer apprCntNcard;
    /** 승인금액 */
    private Double 	apprApNcard;
    /** 할인금액 */
    private Double 	apprDcNcard;

    /** 총건수 */
    private Integer apprCntNcash;
    /** 승인금액 */
    private Double 	apprApNcash;
    /** 할인금액 */
    private Double 	apprDcNcash;


    //회원                ----------------------------------------------------------------------------
    /** 총회원         */
    private Integer membrTotal;
    /** 신규회원       */
    private Integer membrNew;
    /** 정보미입력회원  */
    private Integer membrNoInfo;
    /** 실매출         */
    private Double  membrRealSaleAmt;
    /** 회원매출       */
    private Double  membrSaleAmt;
    /** 매출점유       */
    private Double  membrSalePrctg;
    /** 포인트누적      */
    private Integer	membrPointAccum;
    /** 포인트사용      */
    private Integer	membrPointUse;


    //근태관리              ----------------------------------------------------------------------------
    /** 사원 */
    private String  workEmpNm;
    /** 출근횟수 */
    private Integer workCnt;
    /** 근무시간 */
    private Integer workTime;



    //결재라인              ----------------------------------------------------------------------------
    /** 본사코드 - Key */
    private String	cfgHqOfficeCd;
    /** 본사브랜드코드 - Key */
    private String	cfgHqBrandCd;
    /** 매장코드 - Key */
    private String	cfgStoreCd;
    /** 결재라인번호 - Key */
    private String	cfgPayLineNo;
    /** 결재라인 코드(순서) */
    private String	cfgPayLineSeq;
    /** 결재라인 명칭 */
    private String	cfgPayLineNm;
    /** 사용여부(삭제한 경우 'N') */
    private String	cfgUseYn;
    /** 상태(I:삽입, U:수정, D:삭제) -> Grid의 itemsRemoved이 잘 되지 않아서 명시적 변수 사용함  */
    private String	cfgStatus;


    //영업일보구성	 ----------------------------------------------------------------------------
    /** 본사코드 - Key */
    //private String	cfgHqOfficeCd;
    /** 본사브랜드코드 - Key */
    //private String	cfgHqBrandCd;
    /** 매장코드 - Key */
   // private String	cfgStoreCd;
    /** 영업일보구성 코드 */
    private String	cfgSaleDailyReportCd;
    /** 영업일보구성 코드 (From 공통코드 > TB_CM_NMCODE.NMCODE_GRP_CD='200' - 영업일보 구성 코드) */
    private String	cfgSaleDailyReportNm;
    /** 선택여부 */
    private String	cfgSelYn;
    /** 영업일보구성 코드 약어 (From 공통코드 > TB_CM_NMCODE.NMCODE_ITEM_1) */
    private String	cfgCd;
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
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
	public String[] getArrStoreCd() {
		return arrStoreCd;
	}
	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}
	public String getSearchStoreCd() {
		return searchStoreCd;
	}
	public void setSearchStoreCd(String searchStoreCd) {
		this.searchStoreCd = searchStoreCd;
	}
	public String getSlSaleFg() {
		return slSaleFg;
	}
	public void setSlSaleFg(String slSaleFg) {
		this.slSaleFg = slSaleFg;
	}
	public Double getSlTotSaleAmt() {
		return slTotSaleAmt;
	}
	public void setSlTotSaleAmt(Double slTotSaleAmt) {
		this.slTotSaleAmt = slTotSaleAmt;
	}
	public Double getSlTotDcAmt() {
		return slTotDcAmt;
	}
	public void setSlTotDcAmt(Double slTotDcAmt) {
		this.slTotDcAmt = slTotDcAmt;
	}
	public Double getSlRealSaleAmt() {
		return slRealSaleAmt;
	}
	public void setSlRealSaleAmt(Double slRealSaleAmt) {
		this.slRealSaleAmt = slRealSaleAmt;
	}
	public Double getSlGaAmt() {
		return slGaAmt;
	}
	public void setSlGaAmt(Double slGaAmt) {
		this.slGaAmt = slGaAmt;
	}
	public Double getSlVatAmt() {
		return slVatAmt;
	}
	public void setSlVatAmt(Double slVatAmt) {
		this.slVatAmt = slVatAmt;
	}
	public Double getSlTotTipAmt() {
		return slTotTipAmt;
	}
	public void setSlTotTipAmt(Double slTotTipAmt) {
		this.slTotTipAmt = slTotTipAmt;
	}
	public Double getSlTotEtcAmt() {
		return slTotEtcAmt;
	}
	public void setSlTotEtcAmt(Double slTotEtcAmt) {
		this.slTotEtcAmt = slTotEtcAmt;
	}
	public Integer getSlTotBillCnt() {
		return slTotBillCnt;
	}
	public void setSlTotBillCnt(Integer slTotBillCnt) {
		this.slTotBillCnt = slTotBillCnt;
	}
	public Integer getSlTotGuestCnt() {
		return slTotGuestCnt;
	}
	public void setSlTotGuestCnt(Integer slTotGuestCnt) {
		this.slTotGuestCnt = slTotGuestCnt;
	}
	public Double getSlBillUnprc() {
		return slBillUnprc;
	}
	public void setSlBillUnprc(Double slBillUnprc) {
		this.slBillUnprc = slBillUnprc;
	}
	public Double getSlGuestUnprc() {
		return slGuestUnprc;
	}
	public void setSlGuestUnprc(Double slGuestUnprc) {
		this.slGuestUnprc = slGuestUnprc;
	}
	public Double getPayRealSaleAmt() {
		return payRealSaleAmt;
	}
	public void setPayRealSaleAmt(Double payRealSaleAmt) {
		this.payRealSaleAmt = payRealSaleAmt;
	}
	public Double getPayTotTipAmt() {
		return payTotTipAmt;
	}
	public void setPayTotTipAmt(Double payTotTipAmt) {
		this.payTotTipAmt = payTotTipAmt;
	}
	public Double getPayTotEtcAmt() {
		return payTotEtcAmt;
	}
	public void setPayTotEtcAmt(Double payTotEtcAmt) {
		this.payTotEtcAmt = payTotEtcAmt;
	}
	public Double getPayCard() {
		return payCard;
	}
	public void setPayCard(Double payCard) {
		this.payCard = payCard;
	}
	public Double getPayCash() {
		return payCash;
	}
	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}
	public Double getPayPayco() {
		return payPayco;
	}
	public void setPayPayco(Double payPayco) {
		this.payPayco = payPayco;
	}
	public Double getPayVpoint() {
		return payVpoint;
	}
	public void setPayVpoint(Double payVpoint) {
		this.payVpoint = payVpoint;
	}
	public Double getPayVcoupn() {
		return payVcoupn;
	}
	public void setPayVcoupn(Double payVcoupn) {
		this.payVcoupn = payVcoupn;
	}
	public Double getPayVcharge() {
		return payVcharge;
	}
	public void setPayVcharge(Double payVcharge) {
		this.payVcharge = payVcharge;
	}
	public Double getPayMpay() {
		return payMpay;
	}
	public void setPayMpay(Double payMpay) {
		this.payMpay = payMpay;
	}
	public Double getPayMcoupn() {
		return payMcoupn;
	}
	public void setPayMcoupn(Double payMcoupn) {
		this.payMcoupn = payMcoupn;
	}
	public Double getPayMembr() {
		return payMembr;
	}
	public void setPayMembr(Double payMembr) {
		this.payMembr = payMembr;
	}
	public Double getPayPrepaid() {
		return payPrepaid;
	}
	public void setPayPrepaid(Double payPrepaid) {
		this.payPrepaid = payPrepaid;
	}
	public Double getPayPostpaid() {
		return payPostpaid;
	}
	public void setPayPostpaid(Double payPostpaid) {
		this.payPostpaid = payPostpaid;
	}
	public Double getPayCoupn() {
		return payCoupn;
	}
	public void setPayCoupn(Double payCoupn) {
		this.payCoupn = payCoupn;
	}
	public Double getPayGift() {
		return payGift;
	}
	public void setPayGift(Double payGift) {
		this.payGift = payGift;
	}
	public Double getPayFstmp() {
		return payFstmp;
	}
	public void setPayFstmp(Double payFstmp) {
		this.payFstmp = payFstmp;
	}
	public Double getPayPartner() {
		return payPartner;
	}
	public void setPayPartner(Double payPartner) {
		this.payPartner = payPartner;
	}
	public Double getPayOkcsb() {
		return payOkcsb;
	}
	public void setPayOkcsb(Double payOkcsb) {
		this.payOkcsb = payOkcsb;
	}
	public Double getPayEmpCard() {
		return payEmpCard;
	}
	public void setPayEmpCard(Double payEmpCard) {
		this.payEmpCard = payEmpCard;
	}
	public Double getPayTemporary() {
		return payTemporary;
	}
	public void setPayTemporary(Double payTemporary) {
		this.payTemporary = payTemporary;
	}
	public Double getPaySmartOrderAmt() {
		return paySmartOrderAmt;
	}
	public void setPaySmartOrderAmt(Double paySmartOrderAmt) {
		this.paySmartOrderAmt = paySmartOrderAmt;
	}
	public String getNslSaleFg() {
		return nslSaleFg;
	}
	public void setNslSaleFg(String nslSaleFg) {
		this.nslSaleFg = nslSaleFg;
	}
	public Double getNslTotSaleAmt() {
		return nslTotSaleAmt;
	}
	public void setNslTotSaleAmt(Double nslTotSaleAmt) {
		this.nslTotSaleAmt = nslTotSaleAmt;
	}
	public Double getNslTotDcAmt() {
		return nslTotDcAmt;
	}
	public void setNslTotDcAmt(Double nslTotDcAmt) {
		this.nslTotDcAmt = nslTotDcAmt;
	}
	public Double getNslRealSaleAmt() {
		return nslRealSaleAmt;
	}
	public void setNslRealSaleAmt(Double nslRealSaleAmt) {
		this.nslRealSaleAmt = nslRealSaleAmt;
	}
	public Double getNslGaAmt() {
		return nslGaAmt;
	}
	public void setNslGaAmt(Double nslGaAmt) {
		this.nslGaAmt = nslGaAmt;
	}
	public Double getNslVatAmt() {
		return nslVatAmt;
	}
	public void setNslVatAmt(Double nslVatAmt) {
		this.nslVatAmt = nslVatAmt;
	}
	public Double getNslTotTipAmt() {
		return nslTotTipAmt;
	}
	public void setNslTotTipAmt(Double nslTotTipAmt) {
		this.nslTotTipAmt = nslTotTipAmt;
	}
	public Double getNslTotEtcAmt() {
		return nslTotEtcAmt;
	}
	public void setNslTotEtcAmt(Double nslTotEtcAmt) {
		this.nslTotEtcAmt = nslTotEtcAmt;
	}
	public Integer getNslTotBillCnt() {
		return nslTotBillCnt;
	}
	public void setNslTotBillCnt(Integer nslTotBillCnt) {
		this.nslTotBillCnt = nslTotBillCnt;
	}
	public Double getNslBillUnprc() {
		return nslBillUnprc;
	}
	public void setNslBillUnprc(Double nslBillUnprc) {
		this.nslBillUnprc = nslBillUnprc;
	}
	public Double getNpayRealSaleAmt() {
		return npayRealSaleAmt;
	}
	public void setNpayRealSaleAmt(Double npayRealSaleAmt) {
		this.npayRealSaleAmt = npayRealSaleAmt;
	}
	public Double getNpayTotTipAmt() {
		return npayTotTipAmt;
	}
	public void setNpayTotTipAmt(Double npayTotTipAmt) {
		this.npayTotTipAmt = npayTotTipAmt;
	}
	public Double getNpayTotEtcAmt() {
		return npayTotEtcAmt;
	}
	public void setNpayTotEtcAmt(Double npayTotEtcAmt) {
		this.npayTotEtcAmt = npayTotEtcAmt;
	}
	public Double getNpayCard() {
		return npayCard;
	}
	public void setNpayCard(Double npayCard) {
		this.npayCard = npayCard;
	}
	public Double getNpayCash() {
		return npayCash;
	}
	public void setNpayCash(Double npayCash) {
		this.npayCash = npayCash;
	}
	public Double getPosFundAmt() {
		return posFundAmt;
	}
	public void setPosFundAmt(Double posFundAmt) {
		this.posFundAmt = posFundAmt;
	}
	public Double getPosCashExactAmt() {
		return posCashExactAmt;
	}
	public void setPosCashExactAmt(Double posCashExactAmt) {
		this.posCashExactAmt = posCashExactAmt;
	}
	public Double getPosIomoneyInAmt() {
		return posIomoneyInAmt;
	}
	public void setPosIomoneyInAmt(Double posIomoneyInAmt) {
		this.posIomoneyInAmt = posIomoneyInAmt;
	}
	public Double getPosIomoneyOutAmt() {
		return posIomoneyOutAmt;
	}
	public void setPosIomoneyOutAmt(Double posIomoneyOutAmt) {
		this.posIomoneyOutAmt = posIomoneyOutAmt;
	}
	public Double getPosGiftAmt() {
		return posGiftAmt;
	}
	public void setPosGiftAmt(Double posGiftAmt) {
		this.posGiftAmt = posGiftAmt;
	}
	public Double getPosLostAmt() {
		return posLostAmt;
	}
	public void setPosLostAmt(Double posLostAmt) {
		this.posLostAmt = posLostAmt;
	}
	public Double getPosNonsaleAmt() {
		return posNonsaleAmt;
	}
	public void setPosNonsaleAmt(Double posNonsaleAmt) {
		this.posNonsaleAmt = posNonsaleAmt;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public Integer getEmpSalCnt() {
		return empSalCnt;
	}
	public void setEmpSalCnt(Integer empSalCnt) {
		this.empSalCnt = empSalCnt;
	}
	public Double getEmpSalTotalCard() {
		return empSalTotalCard;
	}
	public void setEmpSalTotalCard(Double empSalTotalCard) {
		this.empSalTotalCard = empSalTotalCard;
	}
	public Double getEmpSalTotalCash() {
		return empSalTotalCash;
	}
	public void setEmpSalTotalCash(Double empSalTotalCash) {
		this.empSalTotalCash = empSalTotalCash;
	}
	public Double getEmpSalTotalEtc() {
		return empSalTotalEtc;
	}
	public void setEmpSalTotalEtc(Double empSalTotalEtc) {
		this.empSalTotalEtc = empSalTotalEtc;
	}
	public String getEmpRtnCnt() {
		return empRtnCnt;
	}
	public void setEmpRtnCnt(String empRtnCnt) {
		this.empRtnCnt = empRtnCnt;
	}
	public Integer getEmpRtnTotalCard() {
		return empRtnTotalCard;
	}
	public void setEmpRtnTotalCard(Integer empRtnTotalCard) {
		this.empRtnTotalCard = empRtnTotalCard;
	}
	public Double getEmpRtnTotalCash() {
		return empRtnTotalCash;
	}
	public void setEmpRtnTotalCash(Double empRtnTotalCash) {
		this.empRtnTotalCash = empRtnTotalCash;
	}
	public Double getEmpRtnTotalEtc() {
		return empRtnTotalEtc;
	}
	public void setEmpRtnTotalEtc(Double empRtnTotalEtc) {
		this.empRtnTotalEtc = empRtnTotalEtc;
	}
	public String getDcCd() {
		return dcCd;
	}
	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}
	public String getDcNm() {
		return dcNm;
	}
	public void setDcNm(String dcNm) {
		this.dcNm = dcNm;
	}
	public Integer getDcSaleQty() {
		return dcSaleQty;
	}
	public void setDcSaleQty(Integer dcSaleQty) {
		this.dcSaleQty = dcSaleQty;
	}
	public Double getDcSaleAmt() {
		return dcSaleAmt;
	}
	public void setDcSaleAmt(Double dcSaleAmt) {
		this.dcSaleAmt = dcSaleAmt;
	}
	public Double getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(Double dcAmt) {
		this.dcAmt = dcAmt;
	}
	public Double getDcTotDcAmt() {
		return dcTotDcAmt;
	}
	public void setDcTotDcAmt(Double dcTotDcAmt) {
		this.dcTotDcAmt = dcTotDcAmt;
	}
	public Double getDcRealSaleAmt() {
		return dcRealSaleAmt;
	}
	public void setDcRealSaleAmt(Double dcRealSaleAmt) {
		this.dcRealSaleAmt = dcRealSaleAmt;
	}
	public String getDcdtlDcCd() {
		return dcdtlDcCd;
	}
	public void setDcdtlDcCd(String dcdtlDcCd) {
		this.dcdtlDcCd = dcdtlDcCd;
	}
	public String getDcdtlDcNm() {
		return dcdtlDcNm;
	}
	public void setDcdtlDcNm(String dcdtlDcNm) {
		this.dcdtlDcNm = dcdtlDcNm;
	}
	public Integer getDcdtlCnt() {
		return dcdtlCnt;
	}
	public void setDcdtlCnt(Integer dcdtlCnt) {
		this.dcdtlCnt = dcdtlCnt;
	}
	public Double getDcdtlTotSaleAmt() {
		return dcdtlTotSaleAmt;
	}
	public void setDcdtlTotSaleAmt(Double dcdtlTotSaleAmt) {
		this.dcdtlTotSaleAmt = dcdtlTotSaleAmt;
	}
	public Double getDcdtlDcAmt() {
		return dcdtlDcAmt;
	}
	public void setDcdtlDcAmt(Double dcdtlDcAmt) {
		this.dcdtlDcAmt = dcdtlDcAmt;
	}
	public Double getDcdtlTotDcAmt() {
		return dcdtlTotDcAmt;
	}
	public void setDcdtlTotDcAmt(Double dcdtlTotDcAmt) {
		this.dcdtlTotDcAmt = dcdtlTotDcAmt;
	}
	public Double getDcdtlRealSaleAmt() {
		return dcdtlRealSaleAmt;
	}
	public void setDcdtlRealSaleAmt(Double dcdtlRealSaleAmt) {
		this.dcdtlRealSaleAmt = dcdtlRealSaleAmt;
	}
	public String getGiftCd() {
		return giftCd;
	}
	public void setGiftCd(String giftCd) {
		this.giftCd = giftCd;
	}
	public String getGiftNm() {
		return giftNm;
	}
	public void setGiftNm(String giftNm) {
		this.giftNm = giftNm;
	}
	public Double getGiftUprc() {
		return giftUprc;
	}
	public void setGiftUprc(Double giftUprc) {
		this.giftUprc = giftUprc;
	}
	public String getLv1Nm() {
		return lv1Nm;
	}
	public void setLv1Nm(String lv1Nm) {
		this.lv1Nm = lv1Nm;
	}
	public Double getLv1TotSaleAmt() {
		return lv1TotSaleAmt;
	}
	public void setLv1TotSaleAmt(Double lv1TotSaleAmt) {
		this.lv1TotSaleAmt = lv1TotSaleAmt;
	}
	public Double getLv1TotDcAmt() {
		return lv1TotDcAmt;
	}
	public void setLv1TotDcAmt(Double lv1TotDcAmt) {
		this.lv1TotDcAmt = lv1TotDcAmt;
	}
	public Double getLv1RealSaleAmt() {
		return lv1RealSaleAmt;
	}
	public void setLv1RealSaleAmt(Double lv1RealSaleAmt) {
		this.lv1RealSaleAmt = lv1RealSaleAmt;
	}
	public Integer getLv1TotSaleQty() {
		return lv1TotSaleQty;
	}
	public void setLv1TotSaleQty(Integer lv1TotSaleQty) {
		this.lv1TotSaleQty = lv1TotSaleQty;
	}
	public String getLv2Nm() {
		return lv2Nm;
	}
	public void setLv2Nm(String lv2Nm) {
		this.lv2Nm = lv2Nm;
	}
	public Double getLv2TotSaleAmt() {
		return lv2TotSaleAmt;
	}
	public void setLv2TotSaleAmt(Double lv2TotSaleAmt) {
		this.lv2TotSaleAmt = lv2TotSaleAmt;
	}
	public Double getLv2TotDcAmt() {
		return lv2TotDcAmt;
	}
	public void setLv2TotDcAmt(Double lv2TotDcAmt) {
		this.lv2TotDcAmt = lv2TotDcAmt;
	}
	public Double getLv2RealSaleAmt() {
		return lv2RealSaleAmt;
	}
	public void setLv2RealSaleAmt(Double lv2RealSaleAmt) {
		this.lv2RealSaleAmt = lv2RealSaleAmt;
	}
	public Integer getLv2TotSaleQty() {
		return lv2TotSaleQty;
	}
	public void setLv2TotSaleQty(Integer lv2TotSaleQty) {
		this.lv2TotSaleQty = lv2TotSaleQty;
	}
	public String getLv3Nm() {
		return lv3Nm;
	}
	public void setLv3Nm(String lv3Nm) {
		this.lv3Nm = lv3Nm;
	}
	public Double getLv3TotSaleAmt() {
		return lv3TotSaleAmt;
	}
	public void setLv3TotSaleAmt(Double lv3TotSaleAmt) {
		this.lv3TotSaleAmt = lv3TotSaleAmt;
	}
	public Double getLv3TotDcAmt() {
		return lv3TotDcAmt;
	}
	public void setLv3TotDcAmt(Double lv3TotDcAmt) {
		this.lv3TotDcAmt = lv3TotDcAmt;
	}
	public Double getLv3RealSaleAmt() {
		return lv3RealSaleAmt;
	}
	public void setLv3RealSaleAmt(Double lv3RealSaleAmt) {
		this.lv3RealSaleAmt = lv3RealSaleAmt;
	}
	public Integer getLv3TotSaleQty() {
		return lv3TotSaleQty;
	}
	public void setLv3TotSaleQty(Integer lv3TotSaleQty) {
		this.lv3TotSaleQty = lv3TotSaleQty;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public Integer getProdTotSaleQty() {
		return prodTotSaleQty;
	}
	public void setProdTotSaleQty(Integer prodTotSaleQty) {
		this.prodTotSaleQty = prodTotSaleQty;
	}
	public Double getProdRealSaleAmt() {
		return prodRealSaleAmt;
	}
	public void setProdRealSaleAmt(Double prodRealSaleAmt) {
		this.prodRealSaleAmt = prodRealSaleAmt;
	}
	public String getApprNm() {
		return apprNm;
	}
	public void setApprNm(String apprNm) {
		this.apprNm = apprNm;
	}
	public Integer getApprCntCard() {
		return apprCntCard;
	}
	public void setApprCntCard(Integer apprCntCard) {
		this.apprCntCard = apprCntCard;
	}
	public Double getApprApCard() {
		return apprApCard;
	}
	public void setApprApCard(Double apprApCard) {
		this.apprApCard = apprApCard;
	}
	public Double getApprDcCard() {
		return apprDcCard;
	}
	public void setApprDcCard(Double apprDcCard) {
		this.apprDcCard = apprDcCard;
	}
	public Integer getApprCntCash() {
		return apprCntCash;
	}
	public void setApprCntCash(Integer apprCntCash) {
		this.apprCntCash = apprCntCash;
	}
	public Double getApprApCash() {
		return apprApCash;
	}
	public void setApprApCash(Double apprApCash) {
		this.apprApCash = apprApCash;
	}
	public Double getApprDcCash() {
		return apprDcCash;
	}
	public void setApprDcCash(Double apprDcCash) {
		this.apprDcCash = apprDcCash;
	}
	public Integer getApprCntPayco() {
		return apprCntPayco;
	}
	public void setApprCntPayco(Integer apprCntPayco) {
		this.apprCntPayco = apprCntPayco;
	}
	public Double getApprApPayco() {
		return apprApPayco;
	}
	public void setApprApPayco(Double apprApPayco) {
		this.apprApPayco = apprApPayco;
	}
	public Double getApprDcPayco() {
		return apprDcPayco;
	}
	public void setApprDcPayco(Double apprDcPayco) {
		this.apprDcPayco = apprDcPayco;
	}
	public Integer getApprCntMpay() {
		return apprCntMpay;
	}
	public void setApprCntMpay(Integer apprCntMpay) {
		this.apprCntMpay = apprCntMpay;
	}
	public Double getApprApMpay() {
		return apprApMpay;
	}
	public void setApprApMpay(Double apprApMpay) {
		this.apprApMpay = apprApMpay;
	}
	public Double getApprDcMpay() {
		return apprDcMpay;
	}
	public void setApprDcMpay(Double apprDcMpay) {
		this.apprDcMpay = apprDcMpay;
	}
	public Integer getApprCntMcoupn() {
		return apprCntMcoupn;
	}
	public void setApprCntMcoupn(Integer apprCntMcoupn) {
		this.apprCntMcoupn = apprCntMcoupn;
	}
	public Double getApprApMcoupn() {
		return apprApMcoupn;
	}
	public void setApprApMcoupn(Double apprApMcoupn) {
		this.apprApMcoupn = apprApMcoupn;
	}
	public Double getApprDcMcoupn() {
		return apprDcMcoupn;
	}
	public void setApprDcMcoupn(Double apprDcMcoupn) {
		this.apprDcMcoupn = apprDcMcoupn;
	}
	public Integer getApprCntPartner() {
		return apprCntPartner;
	}
	public void setApprCntPartner(Integer apprCntPartner) {
		this.apprCntPartner = apprCntPartner;
	}
	public Double getApprApPartner() {
		return apprApPartner;
	}
	public void setApprApPartner(Double apprApPartner) {
		this.apprApPartner = apprApPartner;
	}
	public Double getApprDcPartner() {
		return apprDcPartner;
	}
	public void setApprDcPartner(Double apprDcPartner) {
		this.apprDcPartner = apprDcPartner;
	}
	public Integer getApprCntNcard() {
		return apprCntNcard;
	}
	public void setApprCntNcard(Integer apprCntNcard) {
		this.apprCntNcard = apprCntNcard;
	}
	public Double getApprApNcard() {
		return apprApNcard;
	}
	public void setApprApNcard(Double apprApNcard) {
		this.apprApNcard = apprApNcard;
	}
	public Double getApprDcNcard() {
		return apprDcNcard;
	}
	public void setApprDcNcard(Double apprDcNcard) {
		this.apprDcNcard = apprDcNcard;
	}
	public Integer getApprCntNcash() {
		return apprCntNcash;
	}
	public void setApprCntNcash(Integer apprCntNcash) {
		this.apprCntNcash = apprCntNcash;
	}
	public Double getApprApNcash() {
		return apprApNcash;
	}
	public void setApprApNcash(Double apprApNcash) {
		this.apprApNcash = apprApNcash;
	}
	public Double getApprDcNcash() {
		return apprDcNcash;
	}
	public void setApprDcNcash(Double apprDcNcash) {
		this.apprDcNcash = apprDcNcash;
	}
	public Integer getMembrTotal() {
		return membrTotal;
	}
	public void setMembrTotal(Integer membrTotal) {
		this.membrTotal = membrTotal;
	}
	public Integer getMembrNew() {
		return membrNew;
	}
	public void setMembrNew(Integer membrNew) {
		this.membrNew = membrNew;
	}
	public Integer getMembrNoInfo() {
		return membrNoInfo;
	}
	public void setMembrNoInfo(Integer membrNoInfo) {
		this.membrNoInfo = membrNoInfo;
	}
	public Double getMembrRealSaleAmt() {
		return membrRealSaleAmt;
	}
	public void setMembrRealSaleAmt(Double membrRealSaleAmt) {
		this.membrRealSaleAmt = membrRealSaleAmt;
	}
	public Double getMembrSaleAmt() {
		return membrSaleAmt;
	}
	public void setMembrSaleAmt(Double membrSaleAmt) {
		this.membrSaleAmt = membrSaleAmt;
	}
	public Double getMembrSalePrctg() {
		return membrSalePrctg;
	}
	public void setMembrSalePrctg(Double membrSalePrctg) {
		this.membrSalePrctg = membrSalePrctg;
	}
	public Integer getMembrPointAccum() {
		return membrPointAccum;
	}
	public void setMembrPointAccum(Integer membrPointAccum) {
		this.membrPointAccum = membrPointAccum;
	}
	public Integer getMembrPointUse() {
		return membrPointUse;
	}
	public void setMembrPointUse(Integer membrPointUse) {
		this.membrPointUse = membrPointUse;
	}
	public String getWorkEmpNm() {
		return workEmpNm;
	}
	public void setWorkEmpNm(String workEmpNm) {
		this.workEmpNm = workEmpNm;
	}
	public Integer getWorkCnt() {
		return workCnt;
	}
	public void setWorkCnt(Integer workCnt) {
		this.workCnt = workCnt;
	}
	public Integer getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}
	public String getCfgHqOfficeCd() {
		return cfgHqOfficeCd;
	}
	public void setCfgHqOfficeCd(String cfgHqOfficeCd) {
		this.cfgHqOfficeCd = cfgHqOfficeCd;
	}
	public String getCfgHqBrandCd() {
		return cfgHqBrandCd;
	}
	public void setCfgHqBrandCd(String cfgHqBrandCd) {
		this.cfgHqBrandCd = cfgHqBrandCd;
	}
	public String getCfgStoreCd() {
		return cfgStoreCd;
	}
	public void setCfgStoreCd(String cfgStoreCd) {
		this.cfgStoreCd = cfgStoreCd;
	}
	public String getCfgPayLineNo() {
		return cfgPayLineNo;
	}
	public void setCfgPayLineNo(String cfgPayLineNo) {
		this.cfgPayLineNo = cfgPayLineNo;
	}
	public String getCfgPayLineSeq() {
		return cfgPayLineSeq;
	}
	public void setCfgPayLineSeq(String cfgPayLineSeq) {
		this.cfgPayLineSeq = cfgPayLineSeq;
	}
	public String getCfgPayLineNm() {
		return cfgPayLineNm;
	}
	public void setCfgPayLineNm(String cfgPayLineNm) {
		this.cfgPayLineNm = cfgPayLineNm;
	}
	public String getCfgUseYn() {
		return cfgUseYn;
	}
	public void setCfgUseYn(String cfgUseYn) {
		this.cfgUseYn = cfgUseYn;
	}
	public String getCfgStatus() {
		return cfgStatus;
	}
	public void setCfgStatus(String cfgStatus) {
		this.cfgStatus = cfgStatus;
	}
	public String getCfgSaleDailyReportCd() {
		return cfgSaleDailyReportCd;
	}
	public void setCfgSaleDailyReportCd(String cfgSaleDailyReportCd) {
		this.cfgSaleDailyReportCd = cfgSaleDailyReportCd;
	}
	public String getCfgSaleDailyReportNm() {
		return cfgSaleDailyReportNm;
	}
	public void setCfgSaleDailyReportNm(String cfgSaleDailyReportNm) {
		this.cfgSaleDailyReportNm = cfgSaleDailyReportNm;
	}
	public String getCfgSelYn() {
		return cfgSelYn;
	}
	public void setCfgSelYn(String cfgSelYn) {
		this.cfgSelYn = cfgSelYn;
	}
	public String getCfgCd() {
		return cfgCd;
	}
	public void setCfgCd(String cfgCd) {
		this.cfgCd = cfgCd;
	}

}