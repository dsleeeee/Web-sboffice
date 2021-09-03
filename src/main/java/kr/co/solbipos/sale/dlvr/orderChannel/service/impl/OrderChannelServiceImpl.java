package kr.co.solbipos.sale.dlvr.orderChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.orderChannelSale.service.MobileOrderChannelSaleVO;
import kr.co.solbipos.sale.dlvr.orderChannel.service.OrderChannelService;
import kr.co.solbipos.sale.dlvr.orderChannel.service.OrderChannelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : OrderChannelServiceImpl.java
 * @Description : 매출관리 > 배달현황 > 주문채널별현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.01  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.09.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("orderChannelService")
@Transactional
public class OrderChannelServiceImpl implements OrderChannelService {

    private final OrderChannelMapper orderChannelMapper;

    public OrderChannelServiceImpl(OrderChannelMapper orderChannelMapper) {
        this.orderChannelMapper = orderChannelMapper;
    }

    /** 주문채널별현황 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO){
        return orderChannelMapper.getPayColList(orderChannelVO);
    }

    /** 주문채널별현황 - 할인수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO){
        return orderChannelMapper.getDcColList(orderChannelVO);
    }

    /** 주문채널별현황 - 객수 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getGuestColList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO){
        orderChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            orderChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return orderChannelMapper.getGuestColList(orderChannelVO);
    }

    /** 주문채널별현황 - 주문채널 구분자 조회 */
    @Override
    public List<DefaultMap<String>> getDlvrInFgColList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO) {
        orderChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            orderChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return orderChannelMapper.getDlvrInFgColList(orderChannelVO);
    }

    /** 주문채널별현황 - 기간별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getOrderChannelPeriodList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO) {
        orderChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            orderChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(orderChannelVO.getSrchStoreCd()).equals("")) {
            orderChannelVO.setArrStoreCd(orderChannelVO.getSrchStoreCd().split(","));
        }

        // 결제수단 array 값 세팅
        orderChannelVO.setArrPayCol(orderChannelVO.getPayCol().split(","));
        // 할인구분 array 값 세팅
        orderChannelVO.setArrDcCol(orderChannelVO.getDcCol().split(","));
        // 객수컬럼 array 값 세팅
        orderChannelVO.setArrGuestCol(orderChannelVO.getGuestCol().split(","));

        return orderChannelMapper.getOrderChannelPeriodList(orderChannelVO);
    }

    /** 주문채널별현황 - 일별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getOrderChannelDayList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO) {

        orderChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            orderChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(orderChannelVO.getSrchStoreCd()).equals("")) {
            orderChannelVO.setArrStoreCd(orderChannelVO.getSrchStoreCd().split(","));
        }

        // 주문채널 구분자 array 값 세팅
        orderChannelVO.setArrDlvrInFgCol(orderChannelVO.getDlvrInFgCol().split(","));

        return orderChannelMapper.getOrderChannelDayList(orderChannelVO);
    }

    /** 주문채널별현황 - 월별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getOrderChannelMonthList(OrderChannelVO orderChannelVO, SessionInfoVO sessionInfoVO) {

        orderChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        orderChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            orderChannelVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 매장코드
        if(!StringUtil.getOrBlank(orderChannelVO.getSrchStoreCd()).equals("")) {
            orderChannelVO.setArrStoreCd(orderChannelVO.getSrchStoreCd().split(","));
        }

        // 주문채널 구분자 array 값 세팅
        orderChannelVO.setArrDlvrInFgCol(orderChannelVO.getDlvrInFgCol().split(","));

        return orderChannelMapper.getOrderChannelMonthList(orderChannelVO);
    }
}
