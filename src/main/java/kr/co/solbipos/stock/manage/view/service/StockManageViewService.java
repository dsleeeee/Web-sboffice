package kr.co.solbipos.stock.manage.view.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StockManageViewService {
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO);
	
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 엑셀 전체 리스트 조회 */
	public List<DefaultMap<String>> getStockManageViewExcelList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO);
}
