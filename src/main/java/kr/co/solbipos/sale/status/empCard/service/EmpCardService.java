package kr.co.solbipos.sale.status.empCard.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpCardService.java
 * @Description : 매출관리 > 매출현황2 > 사원카드매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EmpCardService {

    /** 조회 */
    List<DefaultMap<Object>> getEmpCardList(EmpCardVO empCardVO, SessionInfoVO sessionInfoVO);

}