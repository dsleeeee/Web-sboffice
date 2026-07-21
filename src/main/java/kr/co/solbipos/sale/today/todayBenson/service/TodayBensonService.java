package kr.co.solbipos.sale.today.todayBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TodayBensonService.java
 * @Description : 벤슨 > 매출분석 > 당일매출현황(영수증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface TodayBensonService {

    /** 당일매출현황(영수증) 리스트 조회 */
    List<DefaultMap<Object>> getTodayBensonList(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO);

    /** 당일매출현황(영수증) 엑셀 다운로드 조회*/
    List<DefaultMap<Object>> getTodayBensonExcelList(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO);

    /** 영수증조회팝업 - 영수증 출력데이터 조회 */
    DefaultMap<String> getBillPrintData(TodayBensonVO todayBensonVO, SessionInfoVO sessionInfoVO);
}
