package kr.co.solbipos.store.manage.storemanage.service;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 가맹점관리 > 매장관리 > 매장정보관리 > 환경설정
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreProductVO extends CmmVO {

    /** 매장코드 */
    private String storeCd;
    
    /** 주방프린터번호 */
    private String prterNo;
    
    /** 상품코드 */
    private String prodCd;
    
    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;
    
    /** 상품분류명 */
    private String prodClassNm;
    
    /** 상품 상위분류코드 */
    private String pProdClassCd;
    
    /** 코너코드 */
    private String cornrCd;
    
    /** 원산지코드 */
    private String orgplceCd;
    
    /** 사이드속성분류코드 */
    private String sdattrClassCd;
    
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    
    /** 포인트적립여부 */
    private String pointSaveYn;
    
    /** 상품할인구분 */
    private String prodDcFg;
    
    /** 부가세구분 */
    private String vatFg;
    
    /** 상품봉사료여부 */
    private String prodTipYn;
    
    /** 상품포장금액 */
    private String prodPackAmt;
    
    /** 상품배달금액 */
    private String prodDlvrAmt;
    
    /** 상품유형구분 */
    private String prodTypeFg;
    
    /** 판매상품여부 */
    private String saleProdYn;
    
    /** 재고상품여부 */
    private String stockProdYn;
    
    /** 사이드상품여부 */
    private String sideProdYn;
    
    /** 세트상품구분 */
    private String setProdFg;
    
    /** 가격관리구분 */
    private String prcCtrlFg;
    
    /** 원가단가 */
    private String costUprc;
    
    /** 최종원가단가 */
    private String lastCostUprc;
    
    /** 공급단가 */
    private String splyUprc;
    
    /** 공급단가사용여부 */
    private String splyUprcUseYn;
    
    /** 발주상품구분 */
    private String poProdFg;
    
    /** 발주단위구분 */
    private String poUnitFg;
    
    /** 발주단위수량 */
    private String poUnitQty;
    
    /** 발주단위허용구분 */
    private String poUnitAllowFg;
    
    /** 발주최소수량 */
    private String poMinQty;
    
    /** 안전재고수량 */
    private String safeStockQty;
    
    /** 재고단위구분 */
    private String stockUnitFg;
    
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;
    
    /** 비고 */
    private String remark;
    
    /** 상품출력 여부 */
    private UseYn printYn;
    
    /** child items */
    private List<StoreProductVO> items;
    
}
