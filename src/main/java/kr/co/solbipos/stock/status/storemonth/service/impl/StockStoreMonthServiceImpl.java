package kr.co.solbipos.stock.status.storemonth.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
        private final PopupMapper popupMapper;
	    private final MessageService messageService;

	    @Autowired
	    public StockStoreMonthServiceImpl(StockStoreMonthMapper stockStoreMonthMapper, PopupMapper popupMapper, MessageService messageService) {
	        this.stockStoreMonthMapper = stockStoreMonthMapper;
	        this.popupMapper = popupMapper;
	        this.messageService = messageService;
	    }

	/** 매장월수불 리스트 조회 */
	@Override
	public List<DefaultMap<String>> stockStoreMonthList(StockStoreMonthVO stockStoreMonthVO, SessionInfoVO sessionInfoVO) {
		stockStoreMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockStoreMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(stockStoreMonthVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(stockStoreMonthVO.getStoreCd(), 3900));
            stockStoreMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
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

	@Override
	public List<DefaultMap<String>> stockStoreMonthExcelList(StockStoreMonthVO stockStoreMonthVO, SessionInfoVO sessionInfoVO) {
		stockStoreMonthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockStoreMonthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(!StringUtil.getOrBlank(stockStoreMonthVO.getStoreCd()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(stockStoreMonthVO.getStoreCd(), 3900));
            stockStoreMonthVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

		if (stockStoreMonthVO.getVendrCd() != null && !"".equals(stockStoreMonthVO.getVendrCd())) {
			String[] arrVendrCd = stockStoreMonthVO.getVendrCd().split(",");
			if (arrVendrCd.length > 0) {
				if (arrVendrCd[0] != null && !"".equals(arrVendrCd[0])) {
					stockStoreMonthVO.setArrVendrCd(arrVendrCd);
				}
			}
		}

		return stockStoreMonthMapper.stockStoreMonthExcelList(stockStoreMonthVO);
	}
}
