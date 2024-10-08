package kr.co.solbipos.base.prod.dlvrProdMulti.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.dlvrProdMulti.service.DlvrProdMultiVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DlvrProdMultiMapper.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 매핑2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.25  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DlvrProdMultiMapper {

    /** 배달시스템 상품 명칭 매핑2 - 배달앱구분코드 */
    List<DefaultMap<String>> getDlvrColList(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(DlvrProdMultiVO dlvrProdMultiVO);
    
    /** 배달시스템 상품 명칭 매핑2 - 전체엑셀 다운로드 */
    List<DefaultMap<String>> getDlvrProdMultiExcelList(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 복사 전 기존데이터 삭제 */
    int deleteStoreDlvrProdMultiNm(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 복사 */
    int copyStoreDlvrProdMultiNm(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 팝업 조회 */
    List<DefaultMap<Object>> getDlvrProdMultiNmStoreRegistList(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 저장 delete */
    int getDlvrProdMultiNmStoreRegistSaveDelete(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 상품명칭 매장적용 저장 insert */
    int getDlvrProdMultiNmStoreRegistSaveInsert(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 임시 데이터 delete */
    int getDlvrProdMultiNmMappingChkSaveDeleteAll(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 임시 데이터 insert */
    int getDlvrProdMultiNmMappingChkSaveInsert(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드 전 상품코드 유효여부 체크 */
    int chkDlvrProdMulti(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 중복 체크 */
    String getDlvrProdMultiNmMappingChk(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드(적용) 배민 입력 확인 */
    int getProdApplyChk(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 엑셀 업로드(추가) 배민 입력 확인 */
    int getProdAddChk(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 배달상품명칭 저장 배민 입력 확인 */
    int getProdSaveChk(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 삭제(엑셀업로드 적용, 화면 저장) */
    int excelDeleteDlvrProdNm(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 저장(엑셀업로드 적용, 화면 저장) */
    int excelInsertDlvrProdNm(DlvrProdMultiVO dlvrProdMultiVO);

    /** 배달시스템 상품 명칭 매핑2 - 저장(엑셀업로드 추가) */
    int excelInsertDlvrProdAdd(DlvrProdMultiVO dlvrProdMultiVO);
}
