package kr.co.solbipos.mobile.stock.com.popup.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.dailyIoStock.service.MobileDailyIoStockVO;
import kr.co.solbipos.mobile.stock.status.periodIoStock.service.MobilePeriodIoStockVO;

import java.util.List;

/**
 * @Class Name : MobileStockComPopupService.java
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
public interface MobileStockComPopupService {

    /** 일자별수불현황 - 일자별수불현황 팝업 리스트 조회 */
    List<DefaultMap<String>> getDailyIoStockInfoList(MobileDailyIoStockVO mobileDailyIoStockVO, SessionInfoVO sessionInfoVO);

    /** 각 상품코드별 팝업 리스트 조회 */
    List<DefaultMap<String>> getCmmProdCodeDtlList(MobilePeriodIoStockVO mobilePeriodIoStockVO, SessionInfoVO sessionInfoVO);

    /** 각 수량별 팝업 리스트 조회 */
    List<DefaultMap<String>> getCmmQtyDtlList(MobilePeriodIoStockVO mobilePeriodIostockVO, SessionInfoVO sessionInfoVO);

    /** 거래처 선택모듈 리스트 조회 */
    List<DefaultMap<String>> getVendrList(MobileStockComPopupVO mobileStockComPopupVO, SessionInfoVO sessionInfoVO);
}
