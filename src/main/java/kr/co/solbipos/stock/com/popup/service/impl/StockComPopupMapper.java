package kr.co.solbipos.stock.com.popup.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.stock.com.popup.service.StockComPopupVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

@Mapper
@Repository
public interface StockComPopupMapper {

	/** 일자별수불현황 - 일자별수불현황 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockHqInfoListVendrInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListVendrOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListHqOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListHqInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListStoreMoveInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListStoreMoveOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListDisuseQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListAdjQty(DailyIoStockVO dailyIoStockVO);
    /** 일자별수불현황 - 일자별수불현황 매장 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockStoreInfoListStoreInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListStoreOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListPurchsInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListPurchsOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListMoveInQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListMoveOutQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListDisuseQty(DailyIoStockVO dailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListAdjQty(DailyIoStockVO dailyIoStockVO);
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
    /** 실사/조정/폐기  - 창고선택모듈 리스트 조회 */
    List<DefaultMap<String>> selectStorageList(StockManageViewVO stockManageViewVO);

    /** 수량별 팝업리스트 */
    List<DefaultMap<String>> getVendrInQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getVendrOutQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getHqOutQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getHqInQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getMoveInQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getMoveOutQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getDisuseQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getAdjQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getStoreInQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getStoreOutQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getPurchsInQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getPurchsOutQtyDtlList(PeriodIostockVO periodIostockVO);
    List<DefaultMap<String>> getStoreSaleQtyDtlList(PeriodIostockVO periodIostockVO);

}
