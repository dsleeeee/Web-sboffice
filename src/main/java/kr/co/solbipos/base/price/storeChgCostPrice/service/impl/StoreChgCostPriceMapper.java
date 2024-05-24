package kr.co.solbipos.base.price.storeChgCostPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
import kr.co.solbipos.base.price.storeChgCostPrice.service.StoreChgCostPriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreChgCostPriceMapper.java
 * @Description : 기초관리 - 가격관리 - 매장원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.17  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreChgCostPriceMapper {

    /** 매장원가임의변경 - 선택상품 본사 상품 마스터 원가 조회 */
    DefaultMap<String> getProdPriceInfo(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 선택상품 본사 수불 원가 조회 */
    DefaultMap<String> getIostockProdPriceInfo(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 상품별 매장 상품 마스터 원가 조회 */
    List<DefaultMap<String>> getByProdStoreCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 상품별 매장 수불 원가 조회 */
    List<DefaultMap<String>> getByProdStoreIostockCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 상품별 매장 상품 마스터 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByProdStoreCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 상품별 매장 수불 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByProdStoreIostockCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 상품 마스터 원가 변경 */
    int saveStoreCostPrice (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 수불 원가 변경 */
    int saveStoreIostockCostPrice (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 수불 원가 변경에 따른 History 등록 */
    int saveStoreIostockCostPriceHistory (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장별 매장 상품 마스터 원가 조회 */
    List<DefaultMap<String>> getByStoreStoreCostPriceList (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장별 매장 수불 원가 조회 */
    List<DefaultMap<String>> getByStoreStoreIostockCostPriceList (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장별 매장 상품 마스터 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByStoreStoreCostPriceExcelList (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장별 매장 수불 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByStoreStoreIostockCostPriceExcelList (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 상품 마스터 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreCostPriceExcelUploadSampleList (StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 수불 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceExcelUploadSampleList (StoreChgCostPriceVO storeChgCostPriceVO);

    /*** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 전체 삭제 */
    int deleteCostPriceExcelUploadCheckAll(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 삭제 */
    int deleteCostPriceExcelUploadCheck(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 원가 업로드 임시테이블 저장 */
    int saveCostPriceExcelUploadCheck(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 상품 마스터 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getStoreCostPriceExcelUploadCheckList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 수불 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceExcelUploadCheckList(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 상품 상품코드 존재여부 체크*/
    int getStoreProdCdChk(StoreChgCostPriceVO storeChgCostPriceVO);

    /** 매장원가임의변경 - 매장 수불 상품코드 존재여부 체크*/
    int getStoreIostockProdCdChk(StoreChgCostPriceVO storeChgCostPriceVO);

}
