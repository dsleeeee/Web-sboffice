package kr.co.solbipos.sale.orderStatus.orderStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderStatusMapper.java
 * @Description : 매출관리 - 매출현황 - 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface OrderStatusMapper {
    /** 주문현황 - 조회 */
    List<DefaultMap<String>> getOrderStatusList(OrderStatusVO orderStatusVO);

    /** 주문현황 상세 팝업 - 조회 */
    List<DefaultMap<String>> getOrderStatusDtlList(OrderStatusVO orderStatusVO);

}
