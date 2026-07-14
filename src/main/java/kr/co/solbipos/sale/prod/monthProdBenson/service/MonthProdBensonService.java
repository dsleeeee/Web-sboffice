package kr.co.solbipos.sale.prod.monthProdBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.monthProd.service.MonthProdVO;

import java.util.List;

/**
 * @Class Name : MonthProdBensonService.java
 * @Description : 벤슨 > 상품매출분석 > 월별상품매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface MonthProdBensonService {

    /** 월별상품매출현황 리스트 조회 */
    List<DefaultMap<Object>> getMonthProdBensonList(MonthProdBensonVO monthProdBensonVO, SessionInfoVO sessionInfoVO);
    /** 월별상품매출현황 엑셀 다운로드 조회 */
    List<DefaultMap<Object>> getMonthProdBensonExcelList(MonthProdBensonVO monthProdBensonVO, SessionInfoVO sessionInfoVO);

}
