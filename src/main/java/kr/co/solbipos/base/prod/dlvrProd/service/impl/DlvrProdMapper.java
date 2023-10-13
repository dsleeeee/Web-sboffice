package kr.co.solbipos.base.prod.dlvrProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.dlvrProd.service.DlvrProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DlvrProdMapper.java
 * @Description : 기초관리 - 상품관리 - 배달시스템 상품 명칭 맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 10. 14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DlvrProdMapper {

    /** 배달시스템 상품 명칭 매핑 - 배달앱구분코드 */
    List<DefaultMap<String>> getDlvrColList(DlvrProdVO dlvrProdVO);

    /**  배달시스템 상품 명칭 매핑 - 상품목록조회 */
    List<DefaultMap<String>> getProdList(DlvrProdVO dlvrProdVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭삭제 */
    int deleteDlvrProdNm(DlvrProdVO dlvrProdVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭등록 */
    int insertDlvrProdNm(DlvrProdVO dlvrProdVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 복사 전 기존데이터 삭제 */
    int deleteStoreDlvrProdNm(DlvrProdVO dlvrProdVO);

    /** 배달시스템 상품 명칭 매핑 - 배달상품명칭 복사 */
    int copyStoreDlvrProdNm(DlvrProdVO dlvrProdVO);

    /** 배달시스템 상품 명칭 매핑 - 상품명칭 엑셀 업로드 전 상품코드 유효여부 체크 */
    int chkDlvrProd(DlvrProdVO dlvrProdVO);

    /** 상품명칭 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getDlvrProdNmStoreRegistList(DlvrProdVO dlvrProdVO);

    /** 상품명칭 매장적용 팝업 - 저장 delete */
    int getDlvrProdNmStoreRegistSaveDelete(DlvrProdVO dlvrProdVO);

    /** 상품명칭 매장적용 팝업 - 저장 insert */
    int getDlvrProdNmStoreRegistSaveInsert(DlvrProdVO dlvrProdVO);

    /**  배달시스템 상품 명칭 매핑 - 상품목록조회 */
    List<DefaultMap<String>> getDlvrProdNmExcelList(DlvrProdVO dlvrProdVO);
}
