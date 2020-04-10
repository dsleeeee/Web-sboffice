package kr.co.solbipos.stock.manage.view.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;

public interface StockManageViewMapper {
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewList(StockManageViewVO stockManageViewVO);
	
	public List<DefaultMap<String>> getStockManageViewStoreList(StockManageViewVO stockManageViewVO);
}
