package kr.co.solbipos.sale.com.popup.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupVO;
import kr.co.solbipos.sale.status.prod.cls.service.ProdClassVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleComPopupMapper {
	
    /** 매출공통팝업 - 테이블별 매출현황 팝업(실매출 클릭) */
    List<DefaultMap<String>> getTablePopList(SaleComPopupVO saleComPopupVO);
        
    /** 매출공통팝업 - 상품매출내역 팝업(수량 클릭) */
    List<DefaultMap<String>> getProdPopList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품매출내역 팝업(판매자별) */
    List<DefaultMap<String>> getEmpPopList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품매출내역 팝업(포스별) */
    List<DefaultMap<String>> getPosPopList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품매출내역 팝업(포스별-시간대별) */
    List<DefaultMap<String>> getPosHourPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(카드) */
    List<DefaultMap<String>> getCardApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(현금) */
    List<DefaultMap<String>> getCashApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(payco) */
    List<DefaultMap<String>> getPaycoApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(Mpay) */
    List<DefaultMap<String>> getMpayApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(Mcoupon) */
    List<DefaultMap<String>> getMcouponApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(제휴카드) */
    List<DefaultMap<String>> getPartnerApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(비매출카드) */
    List<DefaultMap<String>> getNcardApprPopList(SaleComPopupVO saleComPopupVO);
    
	/** 매출공통팝업 - 승인현황(매장현황) 팝업(비매출현금) */
    List<DefaultMap<String>> getNcashApprPopList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품선택(대분류) 팝업 리스트 조회 */
    List<DefaultMap<String>> getClassProdList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품선택(상품) 팝업 리스트 조회 */
    List<DefaultMap<String>> getProdList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품선택(상품) - 결제수단별탭 팝업 리스트 조회 */
    List<DefaultMap<String>> getPayFgList(SaleComPopupVO saleComPopupVO);
    
    /** 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 매장정보,매출종합내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePop1(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 결제내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePop3(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 회원정보 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePop4(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 신용카드결제내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePopCard(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 현금결제내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePopCash(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 상품내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePop6(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 원거래매출정보 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillSalePop7(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 신용카드결제취소내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillRtnPopCard(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 현금결제취소내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillRtnPopCash(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 원 신용카드결제내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillRealRtnPopCard(SaleComPopupVO saleComPopupVO);
    
    /** 매출공통팝업 - 원 현금결제내역 - 영수증 팝업 조회 */
    List<DefaultMap<String>> selectBillRealRtnPopCash(SaleComPopupVO saleComPopupVO);
}
