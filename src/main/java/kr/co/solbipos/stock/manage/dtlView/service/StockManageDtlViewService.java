package kr.co.solbipos.stock.manage.dtlView.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StockManageDtlViewService {
	/** 실사/조정/폐기 조회 - 실사/조정/폐기 리스트 조회 */
	public List<DefaultMap<String>> getStockManageDtlViewList(StockManageDtlViewVO stockManageDtlViewVO, SessionInfoVO sessionInfoVO);

	public List<DefaultMap<String>> getReason(StockManageDtlViewVO stockManageDtlViewVO, SessionInfoVO sessionInfoVO);
}
