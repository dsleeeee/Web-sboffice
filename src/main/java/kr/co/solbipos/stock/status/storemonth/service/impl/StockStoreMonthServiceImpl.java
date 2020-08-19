package kr.co.solbipos.stock.status.storemonth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.status.storemonth.service.StockStoreMonthService;
import kr.co.solbipos.stock.status.storemonth.service.StockStoreMonthVO;


@Service("StockStoreMonthService")
public class StockStoreMonthServiceImpl implements StockStoreMonthService {
	 private final StockStoreMonthMapper stockStoreMonthMapper;
	    private final MessageService messageService;

	    @Autowired
	    public StockStoreMonthServiceImpl(StockStoreMonthMapper stockStoreMonthMapper, MessageService messageService) {
	        this.stockStoreMonthMapper = stockStoreMonthMapper;
	        this.messageService = messageService;
	    }

	/** 매장월수불 리스트 조회 */
	@Override
	public List<DefaultMap<String>> stockStoreMonthList(StockStoreMonthVO stockStoreMonthVO, SessionInfoVO sessionInfoVO) {
		stockStoreMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockStoreMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

		if (stockStoreMonthVO.getStoreCd() != null && !"".equals(stockStoreMonthVO.getStoreCd())) {
    		String[] arrStoreCd = stockStoreMonthVO.getStoreCd().split(",");
    		if (arrStoreCd.length > 0) {
    			if (arrStoreCd[0] != null && !"".equals(arrStoreCd[0])) {
    				stockStoreMonthVO.setArrStoreCd(arrStoreCd);
    			}
    		}
		}

		if (stockStoreMonthVO.getVendrCd() != null && !"".equals(stockStoreMonthVO.getVendrCd())) {
    		String[] arrVendrCd = stockStoreMonthVO.getVendrCd().split(",");
    		if (arrVendrCd.length > 0) {
    			if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
    				stockStoreMonthVO.setArrVendrCd(arrVendrCd);
    			}
    		}
		}

		return stockStoreMonthMapper.stockStoreMonthList(stockStoreMonthVO);
	}
}
