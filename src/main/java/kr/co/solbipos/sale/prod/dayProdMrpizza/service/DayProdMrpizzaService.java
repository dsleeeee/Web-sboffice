package kr.co.solbipos.sale.prod.dayProdMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;

import java.util.List;

/**
 * @Class Name : DayProdMrpizzaService.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayProdMrpizzaService {

    /** 일별상품매출현황 리스트 조회 */
    List<DefaultMap<Object>> getDayProdMrpizzaList(DayProdMrpizzaVO dayProdMrpizzaVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDayProdMrpizzaExcelList(DayProdMrpizzaVO dayProdMrpizzaVO, SessionInfoVO sessionInfoVO);
}
