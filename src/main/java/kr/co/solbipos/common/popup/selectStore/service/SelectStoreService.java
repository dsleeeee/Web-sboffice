package kr.co.solbipos.common.popup.selectStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SelectStoreService.java
 * @Description : (공통) 매장 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SelectStoreService {

    /** 매장 공통 - 매장 리스트 조회 */
    List<DefaultMap<String>> getSelectStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 브랜드 콤보박스 조회 */
    List<DefaultMap<String>> getSelectBrandMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<String>> getSelectHqNmcodeMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 그룹 콤보박스 조회 */
    List<DefaultMap<String>> getSelectBranchMomsList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 업로드매장 공통 - 업로드매장 리스트 조회 */
    List<DefaultMap<String>> getSelectUploadStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 업로드매장 공통 - 검증결과 저장 */
    int getSelectUploadStoreExcelUploadSave(SelectStoreVO[] selectStoreVOs, SessionInfoVO sessionInfoVO);

    /** 업로드매장 공통 - 검증결과 전체 삭제 */
    int getSelectUploadStoreExcelUploadDeleteAll(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 업로드매장 공통 - 업로드매장 텍스트박스 조회 */
    DefaultMap<Object> getSelectUploadStoreText(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);
}