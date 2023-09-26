package kr.co.solbipos.sale.anals.orderTimeTracking.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.orderTimeTracking.service.OrderTimeTrackingService;
import kr.co.solbipos.sale.anals.orderTimeTracking.service.OrderTimeTrackingVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Class Name : OrderTimeTrackingServiceImpl.java
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
@Service("orderTimeTrackingService")
public class OrderTimeTrackingServiceImpl implements OrderTimeTrackingService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OrderTimeTrackingMapper orderTimeTrackingMapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public OrderTimeTrackingServiceImpl(OrderTimeTrackingMapper orderTimeTrackingMapper, PopupMapper popupMapper) {
        this.orderTimeTrackingMapper = orderTimeTrackingMapper;
        this.popupMapper = popupMapper;
    }

    /** 주문시간트레킹 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderTimeTrackingList(@RequestBody OrderTimeTrackingVO orderTimeTrackingVO, SessionInfoVO sessionInfoVO) {

        orderTimeTrackingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
          orderTimeTrackingVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(orderTimeTrackingVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderTimeTrackingVO.getStoreCds(), 3900));
            orderTimeTrackingVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
           if (orderTimeTrackingVO.getStoreHqBrandCd() == "" || orderTimeTrackingVO.getStoreHqBrandCd() == null) {
               // 사용자별 브랜드 array 값 세팅
               String[] userBrandList = orderTimeTrackingVO.getUserBrands().split(",");
               orderTimeTrackingVO.setUserBrandList(userBrandList);
           }
        }

       return orderTimeTrackingMapper.getOrderTimeTrackingList(orderTimeTrackingVO);
    }

    /** 주문시간트레킹 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getOrderTimeTrackingExcelList(@RequestBody OrderTimeTrackingVO orderTimeTrackingVO, SessionInfoVO sessionInfoVO) {

        orderTimeTrackingVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
          orderTimeTrackingVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(orderTimeTrackingVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderTimeTrackingVO.getStoreCds(), 3900));
            orderTimeTrackingVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
           if (orderTimeTrackingVO.getStoreHqBrandCd() == "" || orderTimeTrackingVO.getStoreHqBrandCd() == null) {
               // 사용자별 브랜드 array 값 세팅
               String[] userBrandList = orderTimeTrackingVO.getUserBrands().split(",");
               orderTimeTrackingVO.setUserBrandList(userBrandList);
           }
        }

       return orderTimeTrackingMapper.getOrderTimeTrackingExcelList(orderTimeTrackingVO);
    }

}
