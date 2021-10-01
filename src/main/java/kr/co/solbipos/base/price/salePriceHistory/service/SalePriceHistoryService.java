package kr.co.solbipos.base.price.salePriceHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePriceHistory.service.SalePriceHistoryVO;

import java.util.List;

/**
 * @Class Name : SalePriceHistoryService.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.28  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SalePriceHistoryService {

    /** 판매가변경이력 - 조회 */
    List<DefaultMap<String>> getSalePriceHistoryList(SalePriceHistoryVO salePriceHistoryVO, SessionInfoVO sessionInfoVO);
    
    /** 판매가변경이력 - 엑셀다운로드 */
    List<DefaultMap<String>> getSalePriceHistoryExcelList(SalePriceHistoryVO salePriceHistoryVO, SessionInfoVO sessionInfoVO);


}
