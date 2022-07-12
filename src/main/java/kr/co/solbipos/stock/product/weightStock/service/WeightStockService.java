package kr.co.solbipos.stock.product.weightStock.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : WeightStockService.java
 * @Description : 재고관리 > 생산관리 > 중량재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface WeightStockService {

    /** 중량재고현황(매장) - 조회 */
    List<DefaultMap<Object>> getWeightStockList(WeightStockVO weightStockVO, SessionInfoVO sessionInfoVO);
}