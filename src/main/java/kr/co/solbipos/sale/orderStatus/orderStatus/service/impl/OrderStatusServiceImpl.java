package kr.co.solbipos.sale.orderStatus.orderStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusService;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : OrderStatusServiceImpl.java
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

@Service("orderStatusService")
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusMapper orderStatusMapper;
    private final MessageService messageService;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusMapper orderStatusMapper, MessageService messageService) {
        this.orderStatusMapper = orderStatusMapper;
        this.messageService = messageService;
    }

    /** 주문현황 - 조회 */
    @Override
    public List<DefaultMap<String>> getOrderStatusList(OrderStatusVO orderStatusVO, SessionInfoVO sessionInfoVO) {
        orderStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderStatusVO.setStoreCd(sessionInfoVO.getStoreCd());
        return orderStatusMapper.getOrderStatusList(orderStatusVO);
    }

    /** 주문현황 상세 팝업 - 조회 */
    @Override
    public List<DefaultMap<String>> getOrderStatusDtlList(OrderStatusVO orderStatusVO, SessionInfoVO sessionInfoVO) {
        orderStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderStatusVO.setStoreCd(sessionInfoVO.getStoreCd());
        return orderStatusMapper.getOrderStatusDtlList(orderStatusVO);
    }
}
