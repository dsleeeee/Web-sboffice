package kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.recpOrigin.service.RecpOriginVO;
import kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.OrderkitRecpOriginService;
import kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.OrderkitRecpOriginVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : OrderkitRecpOriginServiceImpl.java
 * @Description : 오더킷 > 오더킷 > 원산지 정보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.11.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("OrderkitRecpOriginService")
@Transactional
public class OrderkitRecpOriginServiceImpl implements OrderkitRecpOriginService {

    private final OrderkitRecpOriginMapper orderkitRecpOriginMapper;

    /**
     * Constructor Injection
     */
    public OrderkitRecpOriginServiceImpl(OrderkitRecpOriginMapper orderkitRecpOriginMapper) {
        this.orderkitRecpOriginMapper = orderkitRecpOriginMapper;
    }

    /** 원산지 정보 조회 */
    @Override
    public List<DefaultMap<Object>> getOrderkitRecpOrigin(OrderkitRecpOriginVO orderkitRecpOriginVO, SessionInfoVO sessionInfoVO) {

        orderkitRecpOriginVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderkitRecpOriginVO.setStoreCd(sessionInfoVO.getStoreCd());
        return orderkitRecpOriginMapper.getOrderkitRecpOrigin(orderkitRecpOriginVO);
    }
}
