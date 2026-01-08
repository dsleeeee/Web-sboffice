package kr.co.solbipos.sale.anals.nonTaxSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : NonTaxSaleService.java
 * @Description : 미스터피자 > 매출분석 > 비과세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NonTaxSaleService {
    /** 비과세매출 - 조회 */
    List<DefaultMap<Object>> getNonTaxSaleList(NonTaxSaleVO nonTaxSaleVO, SessionInfoVO sessionInfoVO);
}
