package kr.co.solbipos.base.price.chgCostPriceHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ChgCostPriceHistoryMapper.java
 * @Description : 기초관리 - 가격관리 - 원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ChgCostPriceHistoryMapper {

    /** 본사 상품 마스터 원가변경History 조회 */
    List<DefaultMap<String>> getHqCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 본사 수불 원가변경History 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 본사 상품 마스터 원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 본사 수불 원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqIostockCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 매장 상품 마스터 원가변경History 조회 */
    List<DefaultMap<String>> getStoreCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 매장 수불 원가변경History 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 매장 상품 마스터 원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);

    /** 매장 수불 원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreIostockCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO);
}
