package kr.co.solbipos.stock.manage.dtlView.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.manage.dtlView.service.StockManageDtlViewVO;

import java.util.List;

public interface StockManageDtlViewMapper {
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 본사 리스트 조회 */
	public List<DefaultMap<String>> getStockManageDtlViewList(StockManageDtlViewVO stockManageDtlViewVO);
	
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 매장 리스트 조회 */
	public List<DefaultMap<String>> getStockManageDtlViewStoreList(StockManageDtlViewVO stockManageDtlViewVO);

	public List<DefaultMap<String>> getReason(StockManageDtlViewVO stockManageDtlViewVO);
}
