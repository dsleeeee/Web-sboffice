package kr.co.solbipos.stock.manage.view.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;

public interface StockManageViewMapper {
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 본사 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewList(StockManageViewVO stockManageViewVO);
	
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 매장 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewStoreList(StockManageViewVO stockManageViewVO);
	
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 본사 엑셀 전체 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewExcelList(StockManageViewVO stockManageViewVO);
	
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 매장 엑셀 전체 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewStoreExcelList(StockManageViewVO stockManageViewVO);

	public List<DefaultMap<String>> getReason(StockManageViewVO stockManageViewVO);
}
