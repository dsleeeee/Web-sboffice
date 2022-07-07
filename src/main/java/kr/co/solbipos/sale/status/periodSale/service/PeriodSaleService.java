package kr.co.solbipos.sale.status.periodSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PeriodSaleService.java
 * @Description : 매출관리 > 매출현황2 > 기간매출상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.01  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PeriodSaleService {

    /**
     * 기간매출상세 리스트 조회
     */
    List<DefaultMap<String>> getPeriodSaleList(PeriodSaleVO periodSaleVO, SessionInfoVO sessionInfoVO);
}
