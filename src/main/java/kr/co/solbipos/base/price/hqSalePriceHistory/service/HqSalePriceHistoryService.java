package kr.co.solbipos.base.price.hqSalePriceHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.storeSalePrice.service.StoreSalePriceVO;

import java.util.List;

/**
 * @Class Name : HqSalePriceHistoryService.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(본사)
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
public interface HqSalePriceHistoryService {

    /** 매장판매가변경이력 - 조회 */
    List<DefaultMap<String>> getStoreSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO);
    
    /** 매장판매가변경이력 - 엑셀다운로드 */
    List<DefaultMap<String>> getStoreSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO);

    /** 본사판매가변경이력 - 조회 */
    List<DefaultMap<String>> getHqSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO);

    /** 본사판매가변경이력 - 엑셀다운로드 */
    List<DefaultMap<String>> getHqSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, SessionInfoVO sessionInfoVO);

}
