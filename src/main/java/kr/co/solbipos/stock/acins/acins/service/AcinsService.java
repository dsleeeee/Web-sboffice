package kr.co.solbipos.stock.acins.acins.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;

import java.util.List;

public interface AcinsService {
    /** 실사관리 - 실사관리 리스트 조회 */
    List<DefaultMap<String>> getAcinsList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사 삭제 */
    int deleteAcins(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(AcinsVO acinsVO, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사등록 상품 리스트 조회 */
    List<DefaultMap<String>> getAcinsRegistList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사상품 저장 */
    int saveAcinsRegist(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(AcinsVO acinsVO, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getAcinsDtlList(AcinsVO acinsVO, SessionInfoVO sessionInfoVO);

    /** 실사관리 - 실사 상세 상품 저장 */
    int saveAcinsDtl(AcinsVO[] acinsVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

}
