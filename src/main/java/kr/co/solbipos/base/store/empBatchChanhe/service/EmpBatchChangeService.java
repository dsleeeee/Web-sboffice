package kr.co.solbipos.base.store.empBatchChanhe.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpBatchChangeService.java
 * @Description : 기초관리 > 사원관리 > 사원정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.16  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.02.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface EmpBatchChangeService {

    /** 사원목록 조회 */
    List<DefaultMap<String>> getEmpList(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getEmpList2(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 사원정보 저장 */
    int getEmpBatchChangeSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getEmpExcelUploadCheckDeleteAll(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getEmpExcelUploadCheckSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 사원목록 조회 */
    List<DefaultMap<String>> getEmpExcelUploadCheckList(EmpBatchChangeVO empBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getEmpExcelUploadCheckSaveAdd(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 사원정보 저장 */
    int getSimpleSave(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 사원 권한 복사 */
    int copyAuthorExcept(EmpBatchChangeVO[] empBatchChangeVOs, SessionInfoVO sessionInfoVO);

}
