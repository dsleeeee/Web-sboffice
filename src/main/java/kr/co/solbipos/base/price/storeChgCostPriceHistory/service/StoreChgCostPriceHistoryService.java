package kr.co.solbipos.base.price.storeChgCostPriceHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreChgCostPriceHistoryService.java
 * @Description : 기초관리 - 가격관리 - 매장원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.24  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreChgCostPriceHistoryService {

    /** 매장원가변경History 조회 */
    List<DefaultMap<String>> getStoreChgCostPriceHistoryList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, SessionInfoVO sessionInfoVO);

    /** 매장원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getStoreChgCostPriceHistoryExcelList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, SessionInfoVO sessionInfoVO);
}
