package kr.co.solbipos.orderkit.orderkit.orderkit.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.orderkit.orderkit.orderkit.service.OrderkitService;
import kr.co.solbipos.orderkit.orderkit.orderkit.service.OrderkitVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Class Name  : OrderkitServiceImpl.java
 * @Description : 오더킷 > 오더킷 > 오더킷
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("OrderkitService")
@Transactional
public class OrderkitServiceImpl implements OrderkitService {

    private final OrderkitMapper orderkitMapper;

    /**
     * Constructor Injection
     */
    public OrderkitServiceImpl(OrderkitMapper orderkitMapper) {
        this.orderkitMapper = orderkitMapper;
    }

    /** 매장정보 조회 */
    @Override
    public DefaultMap<Object> getStoreInfo(OrderkitVO orderkitVO, SessionInfoVO sessionInfoVO) {

        orderkitVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderkitVO.setStoreCd(sessionInfoVO.getStoreCd());
        return orderkitMapper.getStoreInfo(orderkitVO);
    }
}
