package kr.co.solbipos.base.price.chgCostPrice.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.chgCostPrice.service.ChgCostPriceVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ChgCostPriceMapper.java
 * @Description : 기초관리 - 가격관리 - 원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.29  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ChgCostPriceMapper {

    /** 원가임의변경 본사 상품 마스터 원가 조회 */
    List<DefaultMap<String>> getHqCostPriceList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 상품 마스터 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqCostPriceExcelList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceExcelList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 상품 마스터 원가 변경 */
    int saveHqCostPrice(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 변경 */
    int saveHqIostockCostPrice(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 변경에 따른 History 등록 */
    int saveHqIostockCostPriceHistory(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 마스터 원가 조회 */
    List<DefaultMap<String>> getStoreCostPriceList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 마스터 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreCostPriceExcelList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceExcelList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 마스터 원가 변경 */
    int saveStoreCostPrice(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 변경 */
    int saveStoreIostockCostPrice(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 변경에 따른 History 등록 */
    int saveStoreIostockCostPriceHistory(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 상품 마스터 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getHqCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 마스터 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 엑셀업로드 원가 업로드 임시테이블 전체 삭제 */
    int deleteCostPriceExcelUploadCheckAll(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 엑셀업로드 원가 업로드 임시테이블 삭제 */
    int deleteCostPriceExcelUploadCheck(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 원가 업로드 임시테이블 저장 */
    int saveCostPriceExcelUploadCheck(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 상품 마스터 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getHqCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 마스터 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getStoreCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 원가 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 상품 상품코드 존재여부 체크*/
    int getHqProdCdChk(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 본사 수불 상품코드 존재여부 체크*/
    int getHqIostockProdCdChk(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 상품 상품코드 존재여부 체크*/
    int getStoreProdCdChk(ChgCostPriceVO chgCostPriceVO);

    /** 원가임의변경 매장 수불 상품코드 존재여부 체크*/
    int getStoreIostockProdCdChk(ChgCostPriceVO chgCostPriceVO);

}
