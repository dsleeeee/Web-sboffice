package kr.co.solbipos.iostock.order.dstbCloseStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;

import java.util.List;

public interface DstbCloseStoreService {

    /** 분배마감 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseStoreList(DstbCloseStoreVO dstbCloseStoreVO);

    /** 분배마감 리스트 확정 */
    int saveDstbCloseStoreConfirm(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseStoreDtlList(DstbCloseStoreVO dstbCloseStoreVO);

    /** 분배마감 상세 리스트 저장 */
    int saveDstbCloseStoreDtl(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 - 추가분배시 주문가능여부 조회 */
    DefaultMap<String> getOrderFg(DstbCloseStoreVO dstbCloseStoreVO);

    /** 분배마감 - 추가분배 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbAddList(DstbCloseStoreVO dstbCloseStoreVO);

    /** 분배마감 - 추가분배 상세 리스트 저장 */
    int saveDstbAdd(DstbCloseStoreVO[] dstbCloseStoreVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

}
