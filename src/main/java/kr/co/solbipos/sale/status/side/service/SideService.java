package kr.co.solbipos.sale.status.side.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SideService.java
 * @Description : 매출관리 > 매출현황2 > 상품별(사이드)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.21  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.01.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface SideService {
    /** 상품분류별 조회 */
    List<DefaultMap<String>> sideProdClass(SideVO sideVO, SessionInfoVO sessionInfoVO);

    /** 상품분류별 조회 */
    List<DefaultMap<String>> sideProdClassExcel(SideVO sideVO, SessionInfoVO sessionInfoVO);

}
