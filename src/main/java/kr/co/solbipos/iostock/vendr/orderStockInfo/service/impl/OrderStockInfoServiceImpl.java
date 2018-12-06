package kr.co.solbipos.iostock.vendr.orderStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoService;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("orderStockInfoService")
public class OrderStockInfoServiceImpl implements OrderStockInfoService {
    private final OrderStockInfoMapper orderStockInfoMapper;
    private final MessageService messageService;

    @Autowired
    public OrderStockInfoServiceImpl(OrderStockInfoMapper orderStockInfoMapper, MessageService messageService) {
        this.orderStockInfoMapper = orderStockInfoMapper;
        this.messageService = messageService;
    }

    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderStockInfoList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO) {
        orderStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        orderStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 거래처 멀티 선택
        if(!StringUtil.getOrBlank(orderStockInfoVO.getVendrCd()).equals("")) {
            orderStockInfoVO.setArrVendrCd(orderStockInfoVO.getVendrCd().split(","));
        }

        return orderStockInfoMapper.getOrderStockInfoList(orderStockInfoVO);
    }


    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderStockInfoDtlList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO) {
        orderStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        orderStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        // 무발주 조회
        if(orderStockInfoVO.getSlipNo().equals("무발주")) {
            result = orderStockInfoMapper.getNoSlipOrderStockInfoDtlList(orderStockInfoVO);
        }
        else {
            result = orderStockInfoMapper.getOrderStockInfoDtlList(orderStockInfoVO);
        }

        return result;
    }


    /** 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getProdInstockInfoList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO) {
        orderStockInfoVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderStockInfoVO.setStoreCd(sessionInfoVO.getStoreCd());
        orderStockInfoVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        return orderStockInfoMapper.getProdInstockInfoList(orderStockInfoVO);
    }
}
