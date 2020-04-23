package kr.co.solbipos.stock.com.popup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.stock.com.popup.service.StockComPopupService;
import kr.co.solbipos.stock.com.popup.service.StockComPopupVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

@Service("StockComPopupService")
public class StockComPopupServiceImpl implements StockComPopupService {
    private final StockComPopupMapper stockComPopupMapper;
    private final MessageService messageService;

    @Autowired
    public StockComPopupServiceImpl(StockComPopupMapper stockComPopupMapper, MessageService messageService) {
    	this.stockComPopupMapper = stockComPopupMapper;
        this.messageService = messageService;
    }

    @Override
	public List<DefaultMap<String>> getDailyIoStockInfoList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO) {

		dailyIoStockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		dailyIoStockVO.setStoreCd(sessionInfoVO.getStoreCd());
		if("S".equals(dailyIoStockVO.getOrgnFg())) {
			return stockComPopupMapper.getDailyIoStockStoreInfoList(dailyIoStockVO);
		}else {
			return stockComPopupMapper.getDailyIoStockHqInfoList(dailyIoStockVO);
		}
    }

    @Override
	public List<DefaultMap<String>> getCmmStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {

    	hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	if( sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockComPopupMapper.getCmmStockStatusList(hqCurrVO);
		} else {
			hqCurrVO.setStoreCd(sessionInfoVO.getStoreCd());
			return stockComPopupMapper.getCmmStoreStockStatusList(hqCurrVO);
		}
    }
    
    @Override
	public List<DefaultMap<String>> getCmmStoreStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO) {
		hqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		return stockComPopupMapper.getCmmStoreStockStatusList(hqCurrVO);
	}

    @Override
	public List<DefaultMap<String>> getCmmProdCodeDtlList(PeriodIostockVO periodIostockVO,
			SessionInfoVO sessionInfoVO) {
		if( sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockComPopupMapper.getCmmProdCodeHqDtlList(periodIostockVO);
		} else {
			return stockComPopupMapper.getCmmProdCodeStoreDtlList(periodIostockVO);
		}
	}

	@Override
	public List<DefaultMap<String>> getCmmQtyDtlList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO) {

		return stockComPopupMapper.getCmmQtyDtlList(periodIostockVO);
	}

	@Override
	public List<DefaultMap<String>> getCmmViewDtlList(StockManageViewVO stockManageViewVO,
			SessionInfoVO sessionInfoVO) {
		return stockComPopupMapper.getCmmViewDtlList(stockManageViewVO);
	}
	
    @Override
    public List<DefaultMap<String>> selectStorageList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO) {
    	stockManageViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
    	stockManageViewVO.setStoreCd(sessionInfoVO.getStoreCd());
        return stockComPopupMapper.selectStorageList(stockManageViewVO);
    }
}
