package kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.orderTimeTracking.service.OrderTimeTrackingVO;
import kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.OrderTimeTrackingMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : OrderTimeTrackingMenuMapper.java
 * @Description : 매출관리 - 매출분석2 - 주문시간트레킹-메뉴별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.18  김유승       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface OrderTimeTrackingMenuMapper {

    /** 주문시간트레킹 리스트 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingMenuList(OrderTimeTrackingMenuVO orderTimeTrackingMenuVO);

    /** 주문시간트레킹 엑셀다운로드 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingMenuExcelList(OrderTimeTrackingMenuVO orderTimeTrackingMenuVO);
}
