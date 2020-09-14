package kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ExcelUploadMPSService.java
 * @Description : 공통팝업 수불/재고 엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ExcelUploadMPSService {

    /** 엑셀업로드 - 엑셀업로드 저장 */
    int saveExcelUpload(ExcelUploadMPSVO[] excelUploadMPSVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int saveUpdateProdCd(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);
}