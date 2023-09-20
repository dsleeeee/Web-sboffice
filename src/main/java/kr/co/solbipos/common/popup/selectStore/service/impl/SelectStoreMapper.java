package kr.co.solbipos.common.popup.selectStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SelectStoreMapper.java
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
@Mapper
@Repository
public interface SelectStoreMapper {

    /** 매장 공통 - 매장 리스트 조회 */
    List<DefaultMap<String>> getSelectStoreList(SelectStoreVO selectStoreVO);

    /** 매장 공통 - 회사 구분 조회 */
    DefaultMap<Object> getSelectStoreCompanyFg(SelectStoreVO selectStoreVO);

    /** 사용자별 브랜드 사용 조회 */
    String getUserBrandCdList(SelectStoreVO selectStoreVO);

    /** 사용자별 브랜드 콤보박스 조회 */
    List<DefaultMap<String>> getSelectBrandMomsList(SelectStoreVO selectStoreVO);

    /** 사용자별 코드별 공통코드 조회 */
    String getUserHqNmcodeCdList(SelectStoreVO selectStoreVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<String>> getSelectHqNmcodeMomsList(SelectStoreVO selectStoreVO);

    /** 사용자별 그룹 조회 */
    String getUserBranchCdList(SelectStoreVO selectStoreVO);

    /** 사용자별 그룹 콤보박스 조회 */
    List<DefaultMap<String>> getSelectBranchMomsList(SelectStoreVO selectStoreVO);

    /** 업로드매장 공통 - 업로드매장 리스트 조회 */
    List<DefaultMap<String>> getSelectUploadStoreList(SelectStoreVO selectStoreVO);

    /** 업로드매장 공통 - 검증결과 저장 */
    int getSelectUploadStoreExcelUploadSave(SelectStoreVO selectStoreVO);

    /** 업로드매장 공통 - 검증결과 전체 삭제 */
    int getSelectUploadStoreExcelUploadDeleteAll(SelectStoreVO selectStoreVO);

    /** 업로드매장 공통 - 업로드매장 텍스트박스 조회 */
    DefaultMap<Object> getSelectUploadStoreText(SelectStoreVO selectStoreVO);
}