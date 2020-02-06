package kr.co.solbipos.sale.anals.saletrnsitn.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : SaleTrnsitnVO.java
 * @Description : 매출관리 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.14  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP 
 * @since 2020. 01.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleTrnsitnVO extends PageVO {

	private static final long serialVersionUID = 1589956086558819399L;

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
    
    /** 상품분류코드 */
    private String prodClassCd;
    
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    
    
    
    
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 브랜드명 */
    private String hqBrandNm;
    /** 매장명 */
    private String storeNm;    
    //----------------------------------------------------------------------------------------------
    /** 기준일(14일 이전의 값을 구하는데 있어서의 기준일자) */
    private String	baseDate;
    
    private String	startDate;
    
	//----------------------------------------------------------------------------------------------
    /** 정상가 (공급단가) */
    private Double splyUprc;



    
    
    /** 2주평균 */
    private Double 	twoWeekAvr;

    /** 2주합계 */
    private Double 	twoWeekTot;

    
    
    /** 입고-반출 */
    private Integer inWhCarryOut;

    /** 출고-반입 */
    private Integer outWhCarryIn;
    
    /** 본사 현재고 */
    private Integer hqCurrentStk;

    /** 판매 */
    private Integer sale;

    /** 매장 총재고 */
    private Integer storeTotStk;

    
    
//	/** 13일전 판매수량 */
//    private Integer	dateBefore13;
//	
//	/** 12일전 판매수량 */
//    private Integer	dateBefore12;
//	
//	/** 11일전 판매수량 */
//    private Integer	dateBefore11;
//	
//	/** 10일전 판매수량 */
//    private Integer	dateBefore10;
//	
//	/** 9일전 판매수량 */
//    private Integer	dateBefore9;
//	
//	/** 8일전 판매수량 */
//    private Integer	dateBefore8;
//	
//	/** 7일전 판매수량 */
//    private Integer	dateBefore7;
//	
//	/** 6일전 판매수량 */
//    private Integer	dateBefore6;
//	
//	/** 5일전 판매수량 */
//    private Integer	dateBefore5;
//	
//	/** 4일전 판매수량 */
//    private Integer	dateBefore4;
//	
//	/** 3일전 판매수량 */
//    private Integer	dateBefore3;
//	
//	/** 2일전 판매수량 */
//    private Integer	dateBefore2;
//	
//	/** 1일전 판매수량 */
//    private Integer	dateBefore1;
//	
//	/** 금일 판매수량 */
//    private Integer	dateBefore0;
    
    

//    /** 금일 날짜 */
//    private String	dateBeforeZero;	
//	/** 1일전 날짜 */
//    private String	dateBeforeOne;	
//	/** 2일전 날짜 */
//    private String	dateBeforeTwo;	
//	/** 3일전 날짜 */
//    private String	dateBeforeThree;	
//	/** 4일전 날짜 */
//    private String	dateBeforeFour;	
//	/** 5일전 날짜 */
//    private String	dateBeforeFive;	
//	/** 6일전 날짜 */
//    private String	dateBeforeSix;	
//	/** 7일전 날짜 */
//    private String	dateBeforeSeven;	
//	/** 8일전 날짜 */
//    private String	dateBeforeEight;	
//	/** 9일전 날짜 */
//    private String	dateBeforeNine;	
//	/** 10일전 날짜 */
//    private String	dateBeforeTen;	
//	/** 11일전 날짜 */
//    private String	dateBeforeEleven;	
//	/** 12일전 날짜 */
//    private String	dateBeforeTwelve;    
//	/** 13일전 날짜 */
//    private String	dateBeforeThirteen;
    
    
    /** 금일 날짜 */
    private String	dateBefore0;	
	/** 1일전 날짜 */
    private String	dateBefore1;	
	/** 2일전 날짜 */
    private String	dateBefore2;	
	/** 3일전 날짜 */
    private String	dateBefore3;	
	/** 4일전 날짜 */
    private String	dateBefore4;	
	/** 5일전 날짜 */
    private String	dateBefore5;	
	/** 6일전 날짜 */
    private String	dateBefore6;	
	/** 7일전 날짜 */
    private String	dateBefore7;	
	/** 8일전 날짜 */
    private String	dateBefore8;	
	/** 9일전 날짜 */
    private String	dateBefore9;	
	/** 10일전 날짜 */
    private String	dateBefore10;	
	/** 11일전 날짜 */
    private String	dateBefore11;	
	/** 12일전 날짜 */
    private String	dateBefore12;    
	/** 13일전 날짜 */
    private String	dateBefore13;
    
//    /** 금일 판매수량 */
//    private Integer	dateBeforeZeroQty;	
//	/** 1일전 판매수량 */
//    private Integer	dateBeforeOneQty;	
//	/** 2일전 판매수량 */
//    private Integer	dateBeforeTwoQty;	
//	/** 3일전 판매수량 */
//    private Integer	dateBeforeThreeQty;	
//	/** 4일전 판매수량 */
//    private Integer	dateBeforeFourQty;	
//	/** 5일전 판매수량 */
//    private Integer	dateBeforeFiveQty;	
//	/** 6일전 판매수량 */
//    private Integer	dateBeforeSixQty;	
//	/** 7일전 판매수량 */
//    private Integer	dateBeforeSevenQty;	
//	/** 8일전 판매수량 */
//    private Integer	dateBeforeEightQty;	
//	/** 9일전 판매수량 */
//    private Integer	dateBeforeNineQty;	
//	/** 10일전 판매수량 */
//    private Integer	dateBeforeTenQty;	
//	/** 11일전 판매수량 */
//    private Integer	dateBeforeElevenQty;	
//	/** 12일전 판매수량 */
//    private Integer	dateBeforeTwelveQty;    
//	/** 13일전 판매수량 */
//    private Integer	dateBeforeThirteenQty;

    /** 금일 판매수량 */
    private Integer	dateBefore0Qty;	
	/** 1일전 판매수량 */
    private Integer	dateBefore1Qty;	
	/** 2일전 판매수량 */
    private Integer	dateBefore2Qty;	
	/** 3일전 판매수량 */
    private Integer	dateBefore3Qty;	
	/** 4일전 판매수량 */
    private Integer	dateBefore4Qty;	
	/** 5일전 판매수량 */
    private Integer	dateBefore5Qty;	
	/** 6일전 판매수량 */
    private Integer	dateBefore6Qty;	
	/** 7일전 판매수량 */
    private Integer	dateBefore7Qty;	
	/** 8일전 판매수량 */
    private Integer	dateBefore8Qty;	
	/** 9일전 판매수량 */
    private Integer	dateBefore9Qty;	
	/** 10일전 판매수량 */
    private Integer	dateBefore10Qty;	
	/** 11일전 판매수량 */
    private Integer	dateBefore11Qty;	
	/** 12일전 판매수량 */
    private Integer	dateBefore12Qty;    
	/** 13일전 판매수량 */
    private Integer	dateBefore13Qty;
    
    
    /** 판매율 */
    private Double 	saleRatio;

    /** 최초판매일 */
    private String firstSaleDate;

    /** 최종판매일 */
    private String lastSaleDate;

    /** 판매일수 */
    private Integer saleNumberOfDays;

    /** 소진기관 */
    private String exhaustionOrg;
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

	public String getHqBrandCd() {
		return hqBrandCd;
	}

	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
	}

	public String getHqBrandNm() {
		return hqBrandNm;
	}

	public void setHqBrandNm(String hqBrandNm) {
		this.hqBrandNm = hqBrandNm;
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

	public String getProdClassCd() {
		return prodClassCd;
	}

	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Double getSplyUprc() {
		return splyUprc;
	}

	public void setSplyUprc(Double splyUprc) {
		this.splyUprc = splyUprc;
	}

	public Double getTwoWeekAvr() {
		return twoWeekAvr;
	}

	public void setTwoWeekAvr(Double twoWeekAvr) {
		this.twoWeekAvr = twoWeekAvr;
	}

	public Double getTwoWeekTot() {
		return twoWeekTot;
	}

	public void setTwoWeekTot(Double twoWeekTot) {
		this.twoWeekTot = twoWeekTot;
	}

	public Integer getInWhCarryOut() {
		return inWhCarryOut;
	}

	public void setInWhCarryOut(Integer inWhCarryOut) {
		this.inWhCarryOut = inWhCarryOut;
	}

	public Integer getOutWhCarryIn() {
		return outWhCarryIn;
	}

	public void setOutWhCarryIn(Integer outWhCarryIn) {
		this.outWhCarryIn = outWhCarryIn;
	}

	public Integer getHqCurrentStk() {
		return hqCurrentStk;
	}

	public void setHqCurrentStk(Integer hqCurrentStk) {
		this.hqCurrentStk = hqCurrentStk;
	}

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	public Integer getStoreTotStk() {
		return storeTotStk;
	}

	public void setStoreTotStk(Integer storeTotStk) {
		this.storeTotStk = storeTotStk;
	}

	public String getDateBefore0() {
		return dateBefore0;
	}

	public void setDateBefore0(String dateBefore0) {
		this.dateBefore0 = dateBefore0;
	}

	public String getDateBefore1() {
		return dateBefore1;
	}

	public void setDateBefore1(String dateBefore1) {
		this.dateBefore1 = dateBefore1;
	}

	public String getDateBefore2() {
		return dateBefore2;
	}

	public void setDateBefore2(String dateBefore2) {
		this.dateBefore2 = dateBefore2;
	}

	public String getDateBefore3() {
		return dateBefore3;
	}

	public void setDateBefore3(String dateBefore3) {
		this.dateBefore3 = dateBefore3;
	}

	public String getDateBefore4() {
		return dateBefore4;
	}

	public void setDateBefore4(String dateBefore4) {
		this.dateBefore4 = dateBefore4;
	}

	public String getDateBefore5() {
		return dateBefore5;
	}

	public void setDateBefore5(String dateBefore5) {
		this.dateBefore5 = dateBefore5;
	}

	public String getDateBefore6() {
		return dateBefore6;
	}

	public void setDateBefore6(String dateBefore6) {
		this.dateBefore6 = dateBefore6;
	}

	public String getDateBefore7() {
		return dateBefore7;
	}

	public void setDateBefore7(String dateBefore7) {
		this.dateBefore7 = dateBefore7;
	}

	public String getDateBefore8() {
		return dateBefore8;
	}

	public void setDateBefore8(String dateBefore8) {
		this.dateBefore8 = dateBefore8;
	}

	public String getDateBefore9() {
		return dateBefore9;
	}

	public void setDateBefore9(String dateBefore9) {
		this.dateBefore9 = dateBefore9;
	}

	public String getDateBefore10() {
		return dateBefore10;
	}

	public void setDateBefore10(String dateBefore10) {
		this.dateBefore10 = dateBefore10;
	}

	public String getDateBefore11() {
		return dateBefore11;
	}

	public void setDateBefore11(String dateBefore11) {
		this.dateBefore11 = dateBefore11;
	}

	public String getDateBefore12() {
		return dateBefore12;
	}

	public void setDateBefore12(String dateBefore12) {
		this.dateBefore12 = dateBefore12;
	}

	public String getDateBefore13() {
		return dateBefore13;
	}

	public void setDateBefore13(String dateBefore13) {
		this.dateBefore13 = dateBefore13;
	}

	public Integer getDateBefore0Qty() {
		return dateBefore0Qty;
	}

	public void setDateBefore0Qty(Integer dateBefore0Qty) {
		this.dateBefore0Qty = dateBefore0Qty;
	}

	public Integer getDateBefore1Qty() {
		return dateBefore1Qty;
	}

	public void setDateBefore1Qty(Integer dateBefore1Qty) {
		this.dateBefore1Qty = dateBefore1Qty;
	}

	public Integer getDateBefore2Qty() {
		return dateBefore2Qty;
	}

	public void setDateBefore2Qty(Integer dateBefore2Qty) {
		this.dateBefore2Qty = dateBefore2Qty;
	}

	public Integer getDateBefore3Qty() {
		return dateBefore3Qty;
	}

	public void setDateBefore3Qty(Integer dateBefore3Qty) {
		this.dateBefore3Qty = dateBefore3Qty;
	}

	public Integer getDateBefore4Qty() {
		return dateBefore4Qty;
	}

	public void setDateBefore4Qty(Integer dateBefore4Qty) {
		this.dateBefore4Qty = dateBefore4Qty;
	}

	public Integer getDateBefore5Qty() {
		return dateBefore5Qty;
	}

	public void setDateBefore5Qty(Integer dateBefore5Qty) {
		this.dateBefore5Qty = dateBefore5Qty;
	}

	public Integer getDateBefore6Qty() {
		return dateBefore6Qty;
	}

	public void setDateBefore6Qty(Integer dateBefore6Qty) {
		this.dateBefore6Qty = dateBefore6Qty;
	}

	public Integer getDateBefore7Qty() {
		return dateBefore7Qty;
	}

	public void setDateBefore7Qty(Integer dateBefore7Qty) {
		this.dateBefore7Qty = dateBefore7Qty;
	}

	public Integer getDateBefore8Qty() {
		return dateBefore8Qty;
	}

	public void setDateBefore8Qty(Integer dateBefore8Qty) {
		this.dateBefore8Qty = dateBefore8Qty;
	}

	public Integer getDateBefore9Qty() {
		return dateBefore9Qty;
	}

	public void setDateBefore9Qty(Integer dateBefore9Qty) {
		this.dateBefore9Qty = dateBefore9Qty;
	}

	public Integer getDateBefore10Qty() {
		return dateBefore10Qty;
	}

	public void setDateBefore10Qty(Integer dateBefore10Qty) {
		this.dateBefore10Qty = dateBefore10Qty;
	}

	public Integer getDateBefore11Qty() {
		return dateBefore11Qty;
	}

	public void setDateBefore11Qty(Integer dateBefore11Qty) {
		this.dateBefore11Qty = dateBefore11Qty;
	}

	public Integer getDateBefore12Qty() {
		return dateBefore12Qty;
	}

	public void setDateBefore12Qty(Integer dateBefore12Qty) {
		this.dateBefore12Qty = dateBefore12Qty;
	}

	public Integer getDateBefore13Qty() {
		return dateBefore13Qty;
	}

	public void setDateBefore13Qty(Integer dateBefore13Qty) {
		this.dateBefore13Qty = dateBefore13Qty;
	}

	public Double getSaleRatio() {
		return saleRatio;
	}

	public void setSaleRatio(Double saleRatio) {
		this.saleRatio = saleRatio;
	}

	public String getFirstSaleDate() {
		return firstSaleDate;
	}

	public void setFirstSaleDate(String firstSaleDate) {
		this.firstSaleDate = firstSaleDate;
	}

	public String getLastSaleDate() {
		return lastSaleDate;
	}

	public void setLastSaleDate(String lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

	public Integer getSaleNumberOfDays() {
		return saleNumberOfDays;
	}

	public void setSaleNumberOfDays(Integer saleNumberOfDays) {
		this.saleNumberOfDays = saleNumberOfDays;
	}

	public String getExhaustionOrg() {
		return exhaustionOrg;
	}

	public void setExhaustionOrg(String exhaustionOrg) {
		this.exhaustionOrg = exhaustionOrg;
	}

    
}
