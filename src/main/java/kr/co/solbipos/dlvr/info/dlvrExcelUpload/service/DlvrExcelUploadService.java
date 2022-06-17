package kr.co.solbipos.dlvr.info.dlvrExcelUpload.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrExcelUploadService {
    /** 배달지엑셀업로드 임시테이블 데이터 전체 삭제 */
    int getDlvrExcelUploadDeleteAll(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO);

    /** 업로드시 임시테이블 저장 */
    int getDlvrExcelUploadCheckSave(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 임시테이블 조회 */
    List<DefaultMap<Object>> getDlvrExcelUploadCheckList(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO);

    /** 배달지엑셀업로드 임시테이블 데이터 삭제 */
    int getDlvrExcelUploadCheckDelete(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 검증완료로 저장된 값 임시테이블에서 삭제 */
    int getDlvrExcelUploadCheckDelete2(DlvrExcelUploadVO dlvrExcelUploadVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getDlvrExcelUploadCheckSaveAdd(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 결과 저장 */
    int getDeliveryTelNoSave(DlvrExcelUploadVO[] dlvrExcelUploadVOs, SessionInfoVO sessionInfoVO);

}
