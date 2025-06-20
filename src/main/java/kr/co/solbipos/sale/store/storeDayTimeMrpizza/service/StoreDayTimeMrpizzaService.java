package kr.co.solbipos.sale.store.storeDayTimeMrpizza.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreDayTimeMrpizzaService.java
 * @Description : 미스터피자 > 매장분석 > 매장-일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreDayTimeMrpizzaService {

    /** 매장-일별시간대 리스트 조회 */
    List<DefaultMap<Object>> getStoreDayTimeMrpizzaList(StoreDayTimeMrpizzaVO storeDayTimeMrpizzaVO, SessionInfoVO sessionInfoVO);
}
