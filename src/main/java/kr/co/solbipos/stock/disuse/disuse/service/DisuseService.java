package kr.co.solbipos.stock.disuse.disuse.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUpload.service.ExcelUploadVO;

import java.util.List;

public interface DisuseService {
    /** 폐기관리 - 폐기관리 리스트 조회 */
    List<DefaultMap<String>> getDisuseList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기 삭제 */
    int deleteDisuse(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(DisuseVO disuseVO, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기등록 상품 리스트 조회 */
    List<DefaultMap<String>> getDisuseRegistList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기상품 저장 */
    int saveDisuseRegist(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기등록시 상품정보 조회 */
    DefaultMap<String> getProdInfo(DisuseVO disuseVO, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getDisuseDtlList(DisuseVO disuseVO, SessionInfoVO sessionInfoVO);

    /** 폐기관리 - 폐기 상세 상품 저장 */
    int saveDisuseDtl(DisuseVO[] disuseVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 */
    int excelUpload(ExcelUploadVO excelUploadVO, SessionInfoVO sessionInfoVO);

}
