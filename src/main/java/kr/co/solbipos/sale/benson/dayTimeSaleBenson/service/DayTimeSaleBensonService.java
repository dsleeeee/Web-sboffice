package kr.co.solbipos.sale.benson.dayTimeSaleBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayTimeSaleBensonService.java
 * @Description : 벤슨 > 매출분석 > 일별시간대별매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayTimeSaleBensonService {

    /** 일별시간대별매출조회 - 조회 */
    List<DefaultMap<Object>> getDayTimeSaleBensonList(DayTimeSaleBensonVO dayTimeSaleBensonVO, SessionInfoVO sessionInfoVO);
}
