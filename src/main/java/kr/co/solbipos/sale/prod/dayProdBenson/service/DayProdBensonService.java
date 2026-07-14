package kr.co.solbipos.sale.prod.dayProdBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayProdBensonService.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황
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
public interface DayProdBensonService {

    /** 일별상품매출현황 리스트 조회 */
    List<DefaultMap<Object>> getDayProdBensonList(DayProdBensonVO dayProdBensonVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황 엑셀 다운로드 조회 */
    List<DefaultMap<Object>> getDayProdBensonExcelList(DayProdBensonVO dayProdMrpizzaVO, SessionInfoVO sessionInfoVO);

}
