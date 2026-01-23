package kr.co.solbipos.sale.store.orderAcceptHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.orderAcceptHistory.service.OrderAcceptHistoryService;
import kr.co.solbipos.sale.store.orderAcceptHistory.service.OrderAcceptHistoryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : OrderAcceptHistoryServiceImpl.java
 * @Description : 맘스터치 > 매장분석 > 온라인주문-수신상태이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("OrderAcceptHistoryService")
@Transactional
public class OrderAcceptHistoryServiceImpl implements OrderAcceptHistoryService {

    private final PopupMapper popupMapper;
    private final OrderAcceptHistoryMapper orderAcceptHistoryMapper;

    public OrderAcceptHistoryServiceImpl(PopupMapper popupMapper, OrderAcceptHistoryMapper orderAcceptHistoryMapper) {
        this.popupMapper = popupMapper;
        this.orderAcceptHistoryMapper = orderAcceptHistoryMapper;
    }

    /** 온라인주문-수신상태이력 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchOrderAcceptHistory(OrderAcceptHistoryVO orderAcceptHistoryVO, SessionInfoVO sessionInfoVO) {

        orderAcceptHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE){
            orderAcceptHistoryVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(orderAcceptHistoryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderAcceptHistoryVO.getStoreCds(), 3900));
            orderAcceptHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (orderAcceptHistoryVO.getStoreHqBrandCd() == "" || orderAcceptHistoryVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = orderAcceptHistoryVO.getUserBrands().split(",");
                orderAcceptHistoryVO.setUserBrandList(userBrandList);
            }
        }

        return orderAcceptHistoryMapper.getSearchOrderAcceptHistory(orderAcceptHistoryVO);
    }
}
