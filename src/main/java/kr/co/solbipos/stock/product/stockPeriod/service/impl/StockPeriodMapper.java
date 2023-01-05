package kr.co.solbipos.stock.product.stockPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.product.stockPeriod.service.StockPeriodVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StockPeriodMapper.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장-기간별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.22  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2022.12.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface StockPeriodMapper {

    /** 재고현황(매장-기간별) - 조회 */
    List<DefaultMap<Object>> getStockPeriodList(StockPeriodVO stockPeriodVO);
}