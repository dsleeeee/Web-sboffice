package kr.co.solbipos.membr.anals.postpaidSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PostpaidSaleService.java
 * @Description : 국민대 > 매출처관리 > 외상매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PostpaidSaleService {

    /** 외상매출조회 - 조회 */
    List<DefaultMap<Object>> getPostpaidSaleList(PostpaidSaleVO postpaidSaleVO, SessionInfoVO sessionInfoVO);
}