package kr.co.solbipos.stock.product.weightStock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : WeightStockMapper.java
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
@Mapper
@Repository
public interface WeightStockMapper {

    /** 중량재고현황(매장) - 조회 */
    List<DefaultMap<Object>> getWeightStockList(WeightStockVO weightStockVO);
}