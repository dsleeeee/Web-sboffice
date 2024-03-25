package kr.co.solbipos.iostock.order.storeOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreOrderMapper {
    /** 주문등록 HD 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO);

    /** 주문등록 DT 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 수정 */
    int updateStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 등록 */
    int insertStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 삭제 */
    int deleteStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품HD 수정 */
    int updateStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문등록 주문상품HD 등록 */
    int insertStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문등록 주문상품HD 삭제 */
    int deleteStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문요청일의 상품건수 조회 */
    int getDtlCnt(StoreOrderVO storeOrderVO);

    /** 주문요청일의 HD 내용이 존재하는지 여부 조회 */
    String getHdExist(StoreOrderVO storeOrderVO);

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

    /** 매장 주문마감 및 발주중지 여부 조회 */
    String getOrderCloseCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 확정 - MD 수량을 주문수량으로 수정 */
    int updateOrderQtyMdQty(StoreOrderVO storeOrderVO);

    /** 주문등록 확정 - 분배자료 생성 */
    int insertDstbRegist(DstbReqVO dstbReqVO);

    /** 주문등록 엑셀업로드 - 엑셀업로드 수량추가 */
    int insertExcelUploadAddQty(ExcelUploadMPSVO excelUploadMPSVO);

    /** 주문등록 엑셀업로드 - 기존 주문데이터중 엑셀업로드 한 데이터와 같은 상품은 삭제 */
    int deleteStoreOrderToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 주문등록 엑셀업로드 - 엑셀업로드시 여신 체크 */
    DefaultMap<String> storeLoanCheck(ExcelUploadMPSVO excelUploadMPSVO);

    /** 주문등록 엑셀업로드 - 엑셀업로드 한 수량을 주문수량으로 입력 */
    int mergeStoreOrderToExcelUploadData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 주문등록 엑셀업로드 - 주문수량으로 정상 입력된 데이터 TEMP 테이블에서 삭제 */
    int deleteExcelUploadCompleteData(ExcelUploadMPSVO excelUploadMPSVO);

    /** 주문등록 출고요청일자에 등록한 주문 총 합계 금액 조회 */
    String getOrderTotAmt(StoreOrderVO storeOrderVO);

    /** 본사 거래처 조회(콤보박스용) */
    List<DefaultMap<String>> getHqVendrCombo(StoreOrderVO storeOrderVO);
}
