package kr.co.solbipos.store.storeMoms.storeBatchChange.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.storeBatchChange.service.StoreBatchChangeVO;

import java.util.List;

/**
 * @Class Name : StoreBatchChangeService.java
 * @Description : 맘스터치 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.17  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.01.17
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface StoreBatchChangeService {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 저장 */
    int getStoreBatchChangeSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getStoreExcelUploadCheckDeleteAll(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getStoreExcelUploadCheckDelete(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 저장 */
    int getStoreExcelUploadCheckSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 저장 */
    int getSimpleSave(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getTmpStoreList(StoreBatchChangeVO storeBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 변경된 값만 임시테이블 저장 */
    int getDiffValSave(StoreBatchChangeVO[] storeBatchChangeVOs, SessionInfoVO sessionInfoVO);
}
