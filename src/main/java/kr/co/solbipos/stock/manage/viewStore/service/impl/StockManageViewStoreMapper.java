package kr.co.solbipos.stock.manage.viewStore.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.manage.viewStore.service.StockManageViewStoreVO;

public interface StockManageViewStoreMapper {
	/** 실사/조정/폐기 조회 - 매장-실사/조정/폐기 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewStoreList(StockManageViewStoreVO stockManageViewStoreVO);
	/** 실사/조정/폐기 조회 - 매장-실사/조정/폐기 리스트(엑셀) */
	public List<DefaultMap<String>> getStockManageViewStoreExcelList(StockManageViewStoreVO stockManageViewStoreVO);
}
