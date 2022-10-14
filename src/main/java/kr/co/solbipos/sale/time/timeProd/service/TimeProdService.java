package kr.co.solbipos.sale.time.timeProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeService.java
 * @Description : 맘스터치 > 시간대별매출 > 상품별 시간대 매출
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
public interface TimeProdService {

    /** 조회 */
    List<DefaultMap<Object>> getTimeProdList(TimeProdVO timeProdVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTimeProdExcelList(TimeProdVO timeVProdO, SessionInfoVO sessionInfoVO);

}