package kr.co.solbipos.base.price.hqSplyPriceHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : HqSplyPriceHistoryService.java
 * @Description : 기초관리 - 가격관리 - 본사공급가History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface HqSplyPriceHistoryService {

    /** 본사 공급가 History 조회 */
    List<DefaultMap<String>> getHqSplyPriceHistoryList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, SessionInfoVO sessionInfoVO);

    /** 본사 공급가 History 엑셀다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceHistoryExcelList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, SessionInfoVO sessionInfoVO);
}
