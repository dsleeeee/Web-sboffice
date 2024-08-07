package kr.co.solbipos.iostock.order.storeOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;

import java.util.List;

/**
 * @Class Name : StoreOrderService.java
 * @Description : 수불관리 > 수주관리 > 주문등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.10  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 09.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreOrderService {

    /** 주문등록 HD 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO);

    /** 주문등록 DT 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 저장 */
    String saveStoreOrderRegist(StoreOrderDtlVO[] storeOrderDtlVOs, SessionInfoVO sessionInfoVO);

    /** 주문등록 매장마감여부 조회 */
    DefaultMap<String> getStoreCloseCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 주문진행구분 조회 */
    DefaultMap<String> getOrderProcFgCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 매장여신 조회 */
    DefaultMap<String> getStoreLoan(StoreOrderVO storeOrderVO);

    /** 주문등록 출고요청가능일인지 여부 조회 */
    DefaultMap<String> getStoreOrderDateCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 출고요청가능일 조회 */
    String getReqDate(StoreOrderVO storeOrderVO);

    /** 주문등록 확정 */
    int saveStoreOrderConfirm(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    String excelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 주문등록 출고요청일자에 등록한 주문 총 합계 금액 조회 */
    String getOrderTotAmt(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO);

    /** 본사 거래처 조회(콤보박스용) */
    List<DefaultMap<String>> getHqVendrCombo(StoreOrderVO storeOrderVO, SessionInfoVO sessionInfoVO);

}
