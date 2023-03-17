package kr.co.solbipos.sale.day.dayPos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayPosService.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(포스별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayPosService {

    /** 조회 */
    List<DefaultMap<Object>> getDayPosList(DayPosVO dayPosVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getDayPosExcelList(DayPosVO dayPosVO, SessionInfoVO sessionInfoVO);

}