package kr.co.solbipos.sale.moms.daySaleStoreMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DaySaleStoreMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 일별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.29  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DaySaleStoreMomsService {

    /** 일별매출(매장) - 조회 */
    List<DefaultMap<Object>> getDaySaleStoreMomsList(DaySaleStoreMomsVO daySaleStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 일별매출(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getDaySaleStoreMomsExcelList(DaySaleStoreMomsVO daySaleStoreMomsVO, SessionInfoVO sessionInfoVO);
}