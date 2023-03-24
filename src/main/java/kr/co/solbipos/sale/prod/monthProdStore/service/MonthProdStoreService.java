package kr.co.solbipos.sale.prod.monthProdStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.monthProdStore.service.MonthProdStoreVO;

import java.util.List;

/**
 * @Class Name : MonthProdStoreService.java
 * @Description : 맘스터치 > 상품매출분석 > 월별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthProdStoreService {

    /** 월별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getMonthProdStoreList(MonthProdStoreVO monthProdStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getMonthProdStoreExcelList(MonthProdStoreVO monthProdStoreVO, SessionInfoVO sessionInfoVO);

}