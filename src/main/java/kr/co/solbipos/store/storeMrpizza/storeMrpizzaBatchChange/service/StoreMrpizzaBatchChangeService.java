package kr.co.solbipos.store.storeMrpizza.storeMrpizzaBatchChange.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreMrpizzaBatchChangeService.java
 * @Description : 미스터피자 > 매장관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreMrpizzaBatchChangeService {

    /** 매장목록 조회 */
    List<DefaultMap<String>> getStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장정보 저장 */
    int getStoreBatchChangeSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 업로드시 임시테이블 삭제 */
    int getStoreExcelUploadCheckDeleteAll(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 삭제 */
    int getStoreExcelUploadCheckDelete(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 업로드시 임시테이블 저장 */
    int getStoreExcelUploadCheckSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getStoreExcelUploadCheckList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 엑셀 저장 */
    int getSimpleSave(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 검증결과 조회 */
    List<DefaultMap<String>> getTmpStoreList(StoreMrpizzaBatchChangeVO storeMrpizzaBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 변경된 값만 임시테이블 저장 */
    int getDiffValSave(StoreMrpizzaBatchChangeVO[] storeMrpizzaBatchChangeVOs, SessionInfoVO sessionInfoVO);
}
