package kr.co.solbipos.sale.prod.monthProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MonthProdService.java
 * @Description : 맘스터치 > 상품매출분석 > 월별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthProdService {

    /** 일별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getMonthProdList(MonthProdVO monthProdVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getMonthProdExcelList(MonthProdVO monthProdVO, SessionInfoVO sessionInfoVO);

}