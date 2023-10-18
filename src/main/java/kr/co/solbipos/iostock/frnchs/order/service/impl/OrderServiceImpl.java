package kr.co.solbipos.iostock.frnchs.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.order.service.OrderService;
import kr.co.solbipos.iostock.frnchs.order.service.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper, PopupMapper popupMapper) {
        this.orderMapper = orderMapper;
        this.popupMapper = popupMapper;
    }

    /** 본사-매장간 입출고 내역 - 주문대비 입고현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getOrderList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
        orderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            orderVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(orderVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderVO.getStoreCd(), 3900));
            orderVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
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

	/** 본사-매장간 입출고 내역 - 주문대비 입고현황 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getOrderExcelList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
        orderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        orderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            orderVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        
        if(!StringUtil.getOrBlank(orderVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(orderVO.getStoreCd(), 3900));
            orderVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return orderMapper.getOrderExcelList(orderVO);
	}

	/** 본사-매장간 입출고 내역 - 주문대비 입고현황 상세 엑셀리스트 조회 */
	@Override
	public List<DefaultMap<String>> getOrderDtlExcelList(OrderVO orderVO, SessionInfoVO sessionInfoVO) {
    	orderVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	orderVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        result = orderMapper.getOrderDtlList(orderVO);
        
        if(orderVO.getSlipNo() != null && !"".equals(orderVO.getSlipNo())) {
        	result = orderMapper.getOrderDtlExcelList(orderVO);
        }

        return result;
	}
}
