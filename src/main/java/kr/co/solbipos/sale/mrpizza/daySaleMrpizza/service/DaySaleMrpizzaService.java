package kr.co.solbipos.sale.mrpizza.daySaleMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DaySaleMrpizzaService.java
 * @Description : 미스터피자 > 마케팅조회 > 일자별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.25  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.25
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface DaySaleMrpizzaService {

    /** 일자별매출 리스트 조회 */
    List<DefaultMap<Object>> getDaySaleMrpizzaList(DaySaleMrpizzaVO daySaleMrpizzaVO, SessionInfoVO sessionInfoVO);

    /** 일자별매출 상세 리스트 조회 */
    List<DefaultMap<Object>> getDaySaleMrpizzaDtlList(DaySaleMrpizzaVO daySaleMrpizzaVO, SessionInfoVO sessionInfoVO);
}
