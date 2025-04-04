package kr.co.solbipos.stock.manage.view.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewService;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;

@Service("StockViewService")
public class StockManageViewServiceImpl implements StockManageViewService {
	private final StockManageViewMapper stockManageViewMapper;
	
	public StockManageViewServiceImpl(StockManageViewMapper stockManageViewMapper) {
		this.stockManageViewMapper = stockManageViewMapper;
	}

	/** 실사/조정/폐기 조회 - 실사/조정/폐기 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStockManageViewList(StockManageViewVO stockManageViewVO,
			SessionInfoVO sessionInfoVO) {
		
		stockManageViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockManageViewVO.setStoreCd(sessionInfoVO.getStoreCd());
		
		System.out.println("매장정보 : " + sessionInfoVO.getStoreCd());
		
		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockManageViewMapper.getStockManageViewList(stockManageViewVO); // 본사권한
		} else {
			return stockManageViewMapper.getStockManageViewStoreList(stockManageViewVO); // 매장권한
		}
	}

	/** 실사/조정/폐기 조회 - 실사/조정/폐기 엑셀 전체 리스트 조회 */
	@Override
	public List<DefaultMap<String>> getStockManageViewExcelList(StockManageViewVO stockManageViewVO,
			SessionInfoVO sessionInfoVO) {
		stockManageViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockManageViewVO.setStoreCd(sessionInfoVO.getStoreCd());
		
		if(sessionInfoVO.getOrgnFg() != null && sessionInfoVO.getOrgnFg().toString().equals("HQ")) {
			return stockManageViewMapper.getStockManageViewExcelList(stockManageViewVO); // 본사권한
		} else {
			return stockManageViewMapper.getStockManageViewStoreExcelList(stockManageViewVO); // 매장권한
		}
	}

	@Override
	public List<DefaultMap<String>> getReason(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO) {
		stockManageViewVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
		stockManageViewVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
		stockManageViewVO.setStoreCd(sessionInfoVO.getStoreCd());
		return stockManageViewMapper.getReason(stockManageViewVO);
	}

}
