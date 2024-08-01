package kr.co.solbipos.mobile.stock.com.popup.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.mobile.stock.com.popup.service.MobileStockComPopupVO;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileStockComPopupMapper.java
 * @Description : (모바일)재고현황공통팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.30  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MobileStockComPopupMapper {

    /** 일자별수불현황 - 일자별수불현황 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockHqInfoListVendrInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListVendrOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListHqOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListHqInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListStoreMoveInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListStoreMoveOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListDisuseQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockHqInfoListAdjQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    /** 일자별수불현황 - 일자별수불현황 매장 상세 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockStoreInfoListStoreInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListStoreOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListPurchsInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListPurchsOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListMoveInQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListMoveOutQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListDisuseQty(MobileDailyIoStockVO mobileDailyIoStockVO);
    List<DefaultMap<String>> getDailyIoStockStoreInfoListAdjQty(MobileDailyIoStockVO mobileDailyIoStockVO);

    /** 각 상품코드별 팝업 리스트 조회(본사) */
    List<DefaultMap<String>> getCmmProdCodeHqDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    /** 각 상품코드별 팝업 리스트 조회(매장) */
    List<DefaultMap<String>> getCmmProdCodeStoreDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);

    /** 수량별 팝업리스트 */
    List<DefaultMap<String>> getVendrInQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getVendrOutQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getHqOutQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getHqInQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getMoveInQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getMoveOutQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getDisuseQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getAdjQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getStoreInQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getStoreOutQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getPurchsInQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getPurchsOutQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);
    List<DefaultMap<String>> getStoreSaleQtyDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO);

    /** 거래처 선택모듈 리스트 조회  */
    List<DefaultMap<String>> getVendrList(MobileStockComPopupVO mobileStockComPopupVO);
}
