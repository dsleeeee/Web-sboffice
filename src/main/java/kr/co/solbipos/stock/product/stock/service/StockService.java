package kr.co.solbipos.stock.product.stock.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StockService.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StockService {

    /** 재고현황(매장) - 조회 */
    List<DefaultMap<Object>> getStockList(StockVO stockVO, SessionInfoVO sessionInfoVO);
}
