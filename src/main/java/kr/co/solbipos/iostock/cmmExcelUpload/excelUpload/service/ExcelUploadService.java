package kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ExcelUploadService {
    /** 엑셀업로드 - 엑셀업로드 저장 */
    int saveExcelUpload(ExcelUploadVO[] excelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int saveUpdateProdCd(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

}
