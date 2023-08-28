package kr.co.solbipos.sale.anals.orderTimeTracking.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.orderTimeTracking.service.OrderTimeTrackingVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderTimeTrackingMapper.java
 * @Description : 매출관리 - 매출분석2 - 주문시간트레킹
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.08.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.08.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface OrderTimeTrackingMapper {

    /** 주문시간트레킹 리스트 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingList(OrderTimeTrackingVO orderTimeTrackingVO);

    /** 주문시간트레킹 엑셀다운로드 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingExcelList(OrderTimeTrackingVO orderTimeTrackingVO);

}
