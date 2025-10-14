package kr.co.solbipos.base.prod.purchaser.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PurchaserService.java
 * @Description : 국민대 > 매입처관리 > 매입처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PurchaserService {

    /** 매입처조회 - 조회 */
    List<DefaultMap<Object>> getPurchaserList(PurchaserVO purchaserVO, SessionInfoVO sessionInfoVO);
}