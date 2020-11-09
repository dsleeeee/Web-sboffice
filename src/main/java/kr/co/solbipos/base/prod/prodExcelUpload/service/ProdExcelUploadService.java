package kr.co.solbipos.base.prod.prodExcelUpload.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import java.util.List;

/**
 * @Class Name : ProdExcelUploadService.java
 * @Description : 기초관리 > 상품관리 > 상품엑셀업로드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdExcelUploadService {

    /** 상품분류 콤보 조회 */
    List<DefaultMap<String>> prodClassComboList(SessionInfoVO sessionInfoVO);

    /** 검증결과 전체 삭제 */
    int getProdExcelUploadCheckDeleteAll(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<Object>> getProdExcelUploadCheckList(ProdExcelUploadVO prodExcelUploadVO, SessionInfoVO sessionInfoVO);

    /** 업로드시 임시테이블 저장 */
    int getProdExcelUploadCheckSave(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getProdExcelUploadCheckSaveAdd(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getProdExcelUploadCheckDelete(ProdExcelUploadVO[] prodExcelUploadVOs, SessionInfoVO sessionInfoVO);
}