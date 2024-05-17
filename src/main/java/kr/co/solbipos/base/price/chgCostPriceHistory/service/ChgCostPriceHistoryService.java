package kr.co.solbipos.base.price.chgCostPriceHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ChgCostPriceHistoryService.java
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
public interface ChgCostPriceHistoryService {

    /** 원가변경History 조회 */
    List<DefaultMap<String>> getChgCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, SessionInfoVO sessionInfoVO);

    /** 원가변경History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getChgCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, SessionInfoVO sessionInfoVO);
}
