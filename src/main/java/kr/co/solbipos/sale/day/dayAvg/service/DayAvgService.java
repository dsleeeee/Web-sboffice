package kr.co.solbipos.sale.day.dayAvg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayAvg.service.DayAvgVO;

import java.util.List;

/**
 * @Class Name : DayAvgService.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(합산평균)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayAvgService {

    /** 조회 */
    List<DefaultMap<Object>> getDayAvgList(DayAvgVO dayAvgVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getDayAvgExcelList(DayAvgVO dayAvgVO, SessionInfoVO sessionInfoVO);

}