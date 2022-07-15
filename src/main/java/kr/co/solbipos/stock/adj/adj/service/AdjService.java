package kr.co.solbipos.stock.adj.adj.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service.ExcelUploadStoreVO;

import java.util.List;

public interface AdjService {
    /** 조정관리 - 조정관리 리스트 조회 */
    List<DefaultMap<String>> getAdjList(AdjVO adjVO, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정 삭제 */
    int deleteAdj(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(AdjVO adjVO, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정등록 상품 리스트 조회 */
    List<DefaultMap<String>> getAdjRegistList(AdjVO adjVO, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정상품 저장 */
    int saveAdjRegist(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(AdjVO adjVO, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getAdjDtlList(AdjVO adjVO, SessionInfoVO sessionInfoVO);

    /** 조정관리 - 조정 상세 상품 저장 */
    int saveAdjDtl(AdjVO[] adjVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getAdjReason(SessionInfoVO sessionInfoVO);
}
