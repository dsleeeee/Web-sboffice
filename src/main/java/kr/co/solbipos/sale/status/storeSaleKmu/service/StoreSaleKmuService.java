package kr.co.solbipos.sale.status.storeSaleKmu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreSaleKmuService.java
 * @Description : 국민대 > 매출관리 > 점소별매출일보 (국민대)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreSaleKmuService {

    /** 점소별매출일보 - 조회 */
    List<DefaultMap<Object>> getStoreSaleKmuList(StoreSaleKmuVO storeSaleKmuVO, SessionInfoVO sessionInfoVO);
}