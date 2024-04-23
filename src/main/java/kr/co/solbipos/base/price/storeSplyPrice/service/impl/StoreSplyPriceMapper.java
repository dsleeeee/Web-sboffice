package kr.co.solbipos.base.price.storeSplyPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.storeSplyPrice.service.StoreSplyPriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreSplyPriceMapper.java
 * @Description : 기초관리 - 가격관리 - 매장공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.16  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreSplyPriceMapper {

    /** 매장공급가관리 - 상품의 본사 가격정보 조회 */
    DefaultMap<String> getProdPriceInfo(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 상품별 공급가관리 리스트 조회 */
    List<DefaultMap<String>> getByProdSplyPriceList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 상품별 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByProdSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 매장별 공급가관리 리스트 조회 */
    List<DefaultMap<String>> getByStoreSplyPriceList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 매장별 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByStoreSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 매장공급가 저장 */
    int saveStoreSplyPrice(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 매장공급가 복사 */
    int copyStoreSplyPrice(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreSplyPriceExcelUploadSampleList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 전체 삭제 */
    int deleteSplyPriceExcelUploadCheckAll(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 삭제 */
    int deleteSplyPriceExcelUploadCheck(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 저장 */
    int saveSplyPriceExcelUploadCheck(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(StoreSplyPriceVO storeSplyPriceVO);

    /** 매장공급가관리 - 엑셀업로드 매장 상품코드 존재여부 체크 */
    int getStoreProdCdChk(StoreSplyPriceVO storeSplyPriceVO);

}
