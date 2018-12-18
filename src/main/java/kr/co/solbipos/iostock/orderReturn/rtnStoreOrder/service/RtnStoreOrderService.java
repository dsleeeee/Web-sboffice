package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;

import java.util.List;

public interface RtnStoreOrderService {
    /** 반품등록 HD 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderList(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 DT 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderDtlList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getRtnStoreOrderRegistList(RtnStoreOrderDtlVO rtnStoreOrderDtlVO);

    /** 반품등록 반품상품 저장 */
    int saveRtnStoreOrderRegist(RtnStoreOrderDtlVO[] rtnStoreOrderDtlVOs, SessionInfoVO sessionInfoVO);

    /** 반품등록 반품진행구분 조회 */
    DefaultMap<String> getOrderProcFgCheck(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 출고요청가능일 조회 */
    String getReqDate(RtnStoreOrderVO rtnStoreOrderVO);

    /** 반품등록 확정 */
    int saveRtnStoreOrderConfirm(RtnStoreOrderVO rtnStoreOrderVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

}
