package kr.co.solbipos.iostock.vendr.vendrInstock.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;

import java.util.List;

public interface VendrInstockService {
    /** 거래처 입고/반출등록 - 거래처 입고/반출 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 거래처 입고/반출 등록시 발주번호 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockOrderSlipList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 상세 조회 */
    DefaultMap<String> getSlipInfo(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 저장 */
    DefaultMap<String> saveVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 삭제 */
    int deleteVendrInstockDtl(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출정보 진행상태 변경 */
    int saveProcFg(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockProdList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 진행구분 조회 */
    DefaultMap<String> getProcFgCheck(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockProdRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 등록 리스트 저장 */
    int saveVendrInstockProdReg(VendrInstockVO[] vendrInstockVOs, SessionInfoVO sessionInfoVO);

    /** 거래처 입고/반출등록 - 입고/반출상품 발주내역으로 세팅 리스트 조회 */
    List<DefaultMap<String>> getVendrInstockOrderInfoRegList(VendrInstockVO vendrInstockVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

}
