package kr.co.solbipos.iostock.frnchs.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.order.service.OrderService;
import kr.co.solbipos.iostock.frnchs.order.service.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /** 본사-매장간 입출고 내역 - 주문대비 입고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
        orderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        
        if(orderVO.getStoreCd() != null && !"".equals(orderVO.getStoreCd())) {
        	String[] arrStoreCd = orderVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				orderVO.setArrStoreCd(arrStoreCd);
    			}
    		}
        }

        return orderMapper.getOrderList(orderVO);
    }


    /** 본사-매장간 입출고 내역 - 주문대비 입고현황 상세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderDtlList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
    	orderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	orderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        result = orderMapper.getOrderDtlList(orderVO);
        
        if(orderVO.getSlipNo() != null && !"".equals(orderVO.getSlipNo())) {
        	result = orderMapper.getOrderDtlList(orderVO);
        }

        return result;
    }

	@Override
	public List<DefaultMap<String>> getSrchOrderProcFgList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
		return orderMapper.getSrchOrderProcFgList(orderVO);
	}
}
