package kr.co.solbipos.stock.com.popup.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

public interface StockComPopupService {

	/** 일자별수불현황 - 일자별수불현황 팝업 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockInfoList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO);
    /** 현재고현황 - 현재고현황 팝업 리스트 조회 */
	List<DefaultMap<String>> getCmmStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO);
	/** 현재고현황 - 매장 현재고현황 팝업 리스트 조회 */
	List<DefaultMap<String>> getCmmStoreStockStatusList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO);
	/** 각 상품코드별 팝업 리스트 조회 */
	List<DefaultMap<String>> getCmmProdCodeDtlList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO);
	/** 각 수량별 팝업 리스트 조회 */
	List<DefaultMap<String>> getCmmQtyDtlList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO);
	/** 실사/조정/폐기 조회 팝업 */
	List<DefaultMap<String>> getCmmViewDtlList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO);
    /** 실사폐기조정 공통 - 창고선택모듈 리스트 조회 */
    List<DefaultMap<String>> selectStorageList(StockManageViewVO stockManageViewVO, SessionInfoVO sessionInfoVO);

}
