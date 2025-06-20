package kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : DayProdStoreMrpizzaService.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황(매장별)
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 * @since 2025.06.16
 */
public interface DayProdStoreMrpizzaService {

    /** 일별상품매출현황(매장별) 리스트 조회 */
    List<DefaultMap<Object>> getDayProdStoreMrpizzaList(DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황(매장별) 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDayProdStoreMrpizzaExcelList(DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO);
}
