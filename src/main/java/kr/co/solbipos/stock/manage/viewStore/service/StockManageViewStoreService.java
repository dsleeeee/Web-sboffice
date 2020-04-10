package kr.co.solbipos.stock.manage.viewStore.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;

public interface StockManageViewStoreService {
	/** 실사/조정/폐기 조회 - 매장 - 실사/조정/폐기 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewStoreList(StockManageViewStoreVO stockManageViewStoreVO, SessionInfoVO sessionInfoVO);
}
