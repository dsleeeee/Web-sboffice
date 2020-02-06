package kr.co.solbipos.sale.anals.saletrnsitn.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : SaleTrnsitnDatesVO.java
 * @Description : 매출관리 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.15  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP 
 * @since 2020. 01.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleTrnsitnDatesVO extends PageVO {

	private static final long serialVersionUID = -579466860086044760L;

	
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
    //----------------------------------------------------------------------------------------------
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


    
}
