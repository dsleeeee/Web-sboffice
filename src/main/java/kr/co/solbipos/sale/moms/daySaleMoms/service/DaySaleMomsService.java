package kr.co.solbipos.sale.moms.daySaleMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DaySaleMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 일별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DaySaleMomsService {

    /** 일별매출 - 조회 */
    List<DefaultMap<Object>> getDaySaleMomsList(DaySaleMomsVO daySaleMomsVO, SessionInfoVO sessionInfoVO);

    /** 일별매출 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDaySaleMomsExcelList(DaySaleMomsVO daySaleMomsVO, SessionInfoVO sessionInfoVO);
}