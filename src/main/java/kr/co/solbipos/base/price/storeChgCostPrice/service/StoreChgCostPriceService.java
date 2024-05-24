package kr.co.solbipos.base.price.storeChgCostPrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreChgCostPriceService.java
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
public interface StoreChgCostPriceService {

    /** 매장원가임의변경 - 상품의 본사 가격정보 조회 */
    DefaultMap<String> getProdPriceInfo(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 상품별 원가관리 리스트 조회 */
    List<DefaultMap<String>> getByProdChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 상품별 원가관리 리스트 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByProdChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 매장 원가 변경 */
    int saveStoreCostPrice (StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 매장별 원가관리 리스트 조회 */
    List<DefaultMap<String>> getByStoreChgCostPriceList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 매장별 원가관리 리스트 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByStoreChgCostPriceExcelList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreChgCostPriceExcelUploadSampleList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 전체 삭제 */
    int deleteCostPriceExcelUploadCheckAll(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 엑셀업로드 원가 업로드 임시테이블 삭제 */
    int deleteCostPriceExcelUploadCheck(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 원매장원가임의변경 - 원가 업로드 임시테이블 저장 */
    int saveCostPriceExcelUploadCheck(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 원가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getCostPriceExcelUploadCheckList(StoreChgCostPriceVO storeChgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 원가 업로드 검증결과 저장 */
    int saveCostPriceExcelUploadCheckResult(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장원가임의변경 - 원가 엑셀업로드 저장 */
    int saveCostPriceExcelUpload(StoreChgCostPriceVO[] storeChgCostPriceVOs, SessionInfoVO sessionInfoVO);

}
