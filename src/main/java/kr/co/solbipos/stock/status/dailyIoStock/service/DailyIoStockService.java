package kr.co.solbipos.stock.status.dailyIoStock.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface DailyIoStockService {

	/** 일자별수불현황 - 일자별수불현황 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO);
	/** 일자별수불현황 - 일자별수불현황 리스트(엑셀) 조회 */
    List<DefaultMap<String>> getDailyIoStockExcelList(DailyIoStockVO dailyIoStockVO, SessionInfoVO sessionInfoVO);

}
