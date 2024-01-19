package kr.co.solbipos.sale.moms.timeSaleMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeSaleMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeSaleMomsService {

    /** 시간대매출 - 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO);

    /** 시간대매출 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsExcelList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO);

    /** 시간대매출 - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeSaleMomsExcelDivisionList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO);
}