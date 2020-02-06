package kr.co.solbipos.sale.com.popup.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.com.popup.service.SaleComPopupVO;

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
}
