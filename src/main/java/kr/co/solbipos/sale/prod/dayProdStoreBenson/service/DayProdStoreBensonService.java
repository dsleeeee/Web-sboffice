package kr.co.solbipos.sale.prod.dayProdStoreBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayProdStoreBensonService.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황(매장별)
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
public interface DayProdStoreBensonService {

    /** 일별상품매출현황(매장별) 리스트 조회 */
    List<DefaultMap<Object>> getDayProdStoreBensonList(DayProdStoreBensonVO dayProdStoreBensonVO, SessionInfoVO sessionInfoVO);

    /** 일별상품매출현황(매장별) 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDayProdStoreBensonExcelList(DayProdStoreBensonVO dayProdStoreBensonVO, SessionInfoVO sessionInfoVO);

}
