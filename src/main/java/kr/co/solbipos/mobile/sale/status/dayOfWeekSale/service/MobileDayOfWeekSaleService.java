package kr.co.solbipos.mobile.sale.status.dayOfWeekSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleVO;

import java.util.List;

/**
 * @Class Name : MobileDayOfWeekSaleService.java
 * @Description : (모바일) 매출현황 > 요일별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileDayOfWeekSaleService {

    /** 요일별 - 조회 */
    List<DefaultMap<Object>> getMobileDayOfWeekSaleList(MobileDayOfWeekSaleVO mobileDayOfWeekSaleVO, SessionInfoVO sessionInfoVO);

    /** 차트 - 조회 */
    List<DefaultMap<Object>> getMobileDayOfWeekSaleChartList(MobileDayOfWeekSaleVO mobileDayOfWeekSaleVO, SessionInfoVO sessionInfoVO);
}