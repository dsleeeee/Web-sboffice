package kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.OrderTimeTrackingMenuService;
import kr.co.solbipos.sale.anals.orderTimeTrackingMenu.service.OrderTimeTrackingMenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Class Name : OrderTimeTrackingMenuServiceImpl.java
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
@Service("orderTimeTrackingMenuService")
public class OrderTimeTrackingMenuServiceImpl implements OrderTimeTrackingMenuService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OrderTimeTrackingMenuMapper orderTimeTrackingMenuMapper;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public OrderTimeTrackingMenuServiceImpl(OrderTimeTrackingMenuMapper orderTimeTrackingMenuMapper, PopupMapper popupMapper) {
        this.orderTimeTrackingMenuMapper = orderTimeTrackingMenuMapper;
        this.popupMapper = popupMapper;
    }

    /** 주문시간트레킹 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderTimeTrackingMenuList(@RequestBody OrderTimeTrackingMenuVO orderTimeTrackingMenuVO, SessionInfoVO sessionInfoVO) {

        orderTimeTrackingMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            orderTimeTrackingMenuVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(orderTimeTrackingMenuVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderTimeTrackingMenuVO.getStoreCds(), 3900));
            orderTimeTrackingMenuVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (orderTimeTrackingMenuVO.getStoreHqBrandCd() == "" || orderTimeTrackingMenuVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = orderTimeTrackingMenuVO.getUserBrands().split(",");
                orderTimeTrackingMenuVO.setUserBrandList(userBrandList);
            }
        }

        return orderTimeTrackingMenuMapper.getOrderTimeTrackingMenuList(orderTimeTrackingMenuVO);
    }

    /** 주문시간트레킹 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<String>> getOrderTimeTrackingMenuExcelList(@RequestBody OrderTimeTrackingMenuVO orderTimeTrackingMenuVO, SessionInfoVO sessionInfoVO) {

        orderTimeTrackingMenuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            orderTimeTrackingMenuVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(orderTimeTrackingMenuVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderTimeTrackingMenuVO.getStoreCds(), 3900));
            orderTimeTrackingMenuVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (orderTimeTrackingMenuVO.getStoreHqBrandCd() == "" || orderTimeTrackingMenuVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = orderTimeTrackingMenuVO.getUserBrands().split(",");
                orderTimeTrackingMenuVO.setUserBrandList(userBrandList);
            }
        }

        return orderTimeTrackingMenuMapper.getOrderTimeTrackingMenuExcelList(orderTimeTrackingMenuVO);
    }


}
