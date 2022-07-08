package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ExcelUploadStoreService.java
 * @Description : 공통팝업 실사/조정/폐기 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ExcelUploadStoreService {

    /** 엑셀업로드 - 엑셀업로드 저장 */
    int saveExcelUpload(ExcelUploadStoreVO[] excelUploadStoreVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int saveUpdateProdCd(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadStoreVO excelUploadStoreVO, SessionInfoVO sessionInfoVO);
}