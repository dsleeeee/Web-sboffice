package kr.co.solbipos.stock.com.popup.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.com.popup.service.StockComPopupVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

@Mapper
@Repository
public interface StockComPopupMapper {

	/** 일자별수불현황 - 일자별수불현황 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockHqInfoList(DailyIoStockVO dailyIoStockVO);
    /** 일자별수불현황 - 일자별수불현황 매장 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockStoreInfoList(DailyIoStockVO dailyIoStockVO);
    /** 현재고현황 - 본사 현재고현황 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getCmmStockStatusList(HqCurrVO hqCurrVO);
    /** 현재고현황 - 매장 현재고현황 본사 상세 리스트 조회 */
	List<DefaultMap<String>> getCmmStoreStockStatusList(HqCurrVO hqCurrVO);
    /** 각 상품코드별 팝업 리스트 조회(본사) */
    List<DefaultMap<String>> getCmmProdCodeHqDtlList(PeriodIostockVO periodIostockVO);
    /** 각 상품코드별 팝업 리스트 조회(매장) */
    List<DefaultMap<String>> getCmmProdCodeStoreDtlList(PeriodIostockVO periodIostockVO);
    /** 각 수량별 팝업 리스트 조회 */
	List<DefaultMap<String>> getCmmQtyDtlList(PeriodIostockVO periodIostockVO);
    /** 수량 선택 상세 리스트 조회 */
    List<DefaultMap<String>> getPeriodiostockProdQtyDtlList(PeriodIostockVO periodIostockVO);
    /** 실사/조정/폐기 상세 리스트 조회 */
    List<DefaultMap<String>> getCmmViewDtlList(StockManageViewVO stockManageViewVO);

}
