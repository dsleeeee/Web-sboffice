package kr.co.solbipos.stock.manage.dtlView.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.dtlView.service.StockManageDtlViewService;
import kr.co.solbipos.stock.manage.dtlView.service.StockManageDtlViewVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StockDtlViewService")
public class StockManageDtlViewServiceImpl implements StockManageDtlViewService {
	private final StockManageDtlViewMapper stockManageDtlViewMapper;

	public StockManageDtlViewServiceImpl(StockManageDtlViewMapper stockManageDtlViewMapper) {
		this.stockManageDtlViewMapper = stockManageDtlViewMapper;
	}


	/** 실사/조정/폐기 조회 - 실사/조정/폐기 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStockManageDtlViewList(StockManageDtlViewVO stockManageDtlViewVO,
			SessionInfoVO sessionInfoVO) {
		
		stockManageDtlViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockManageDtlViewVO.setStoreCd(sessionInfoVO.getStoreCd());
		
		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockManageDtlViewMapper.getStockManageDtlViewList(stockManageDtlViewVO); // 본사권한
		} else {
			return stockManageDtlViewMapper.getStockManageDtlViewStoreList(stockManageDtlViewVO); // 매장권한
		}
	}


	@Override
	public List<DefaultMap<String>> getReason(StockManageDtlViewVO stockManageDtlViewVO, SessionInfoVO sessionInfoVO) {
		stockManageDtlViewVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		stockManageDtlViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockManageDtlViewVO.setStoreCd(sessionInfoVO.getStoreCd());
		return stockManageDtlViewMapper.getReason(stockManageDtlViewVO);
	}

}
