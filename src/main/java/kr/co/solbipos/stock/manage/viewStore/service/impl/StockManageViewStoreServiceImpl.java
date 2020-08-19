package kr.co.solbipos.stock.manage.viewStore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.viewStore.service.StockManageViewStoreService;
import kr.co.solbipos.stock.manage.viewStore.service.StockManageViewStoreVO;

@Service("StockManageViewStoreService")
public class StockManageViewStoreServiceImpl implements StockManageViewStoreService {
	private final StockManageViewStoreMapper StockManageViewStoreMapper;

	public StockManageViewStoreServiceImpl(StockManageViewStoreMapper StockManageViewStoreMapper) {
		this.StockManageViewStoreMapper = StockManageViewStoreMapper;
	}

	@Override
	public List<DefaultMap<String>> getStockManageViewStoreList(StockManageViewStoreVO StockManageViewStoreVO, SessionInfoVO sessionInfoVO) {

		StockManageViewStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(StockManageViewStoreVO.getStoreCd()).equals("")) {
        	StockManageViewStoreVO.setArrStoreCd(StockManageViewStoreVO.getStoreCd().split(","));
        }

        return StockManageViewStoreMapper.getStockManageViewStoreList(StockManageViewStoreVO);
	}
	
	@Override
	public List<DefaultMap<String>> getStockManageViewStoreExcelList(StockManageViewStoreVO StockManageViewStoreVO, SessionInfoVO sessionInfoVO) {

		StockManageViewStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

		if(!StringUtil.getOrBlank(StockManageViewStoreVO.getStoreCd()).equals("")) {
        	StockManageViewStoreVO.setArrStoreCd(StockManageViewStoreVO.getStoreCd().split(","));
        }

        return StockManageViewStoreMapper.getStockManageViewStoreExcelList(StockManageViewStoreVO);
	}

}
