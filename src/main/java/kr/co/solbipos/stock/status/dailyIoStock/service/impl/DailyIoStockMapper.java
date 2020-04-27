package kr.co.solbipos.stock.status.dailyIoStock.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;

public interface DailyIoStockMapper {

	/** 일수불현황 - 일수불현황 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockList(DailyIoStockVO dailyIoStockVO);
	/** 일수불현황 - 일수불현황 리스트(엑셀) 조회 */
    List<DefaultMap<String>> getDailyIoStockExcelList(DailyIoStockVO dailyIoStockVO);

}
