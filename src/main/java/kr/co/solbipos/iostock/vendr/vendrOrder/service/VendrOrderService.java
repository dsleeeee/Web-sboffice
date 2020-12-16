package kr.co.solbipos.iostock.vendr.vendrOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.etc.cd.service.CdVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;

import java.util.List;

public interface VendrOrderService {
    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 상세 조회 */
    DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 저장 */
    DefaultMap<String> saveVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 삭제 */
    int deleteVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 진행상태 변경 */
    int saveProcFg(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주상품 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderProdList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 진행구분 조회 */
    DefaultMap<String> getProcFgCheck(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderProdRegList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주상품 추가/변경 등록 리스트 저장 */
    int saveVendrOrderProdReg(VendrOrderVO[] vendrOrderVOs, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주서 발주정보 조회(발주처, 공급자 정보) */
    DefaultMap<String> getVendrOrderReportInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주타입관리 - 조회 */
    List<DefaultMap<Object>> getVendrOrderTypeCdList(CdVO cdVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주타입관리 - 저장 */
    int saveVendrOrderTypeCdList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO);

}
