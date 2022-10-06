package kr.co.solbipos.sale.prod.prodSaleRate.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateVO;

import java.util.List;

/**
 * @Class Name : ProdSaleRateService.java
 * @Description : 맘스터치 > 승인관리2 > 상품 판매 비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSaleRateService {

    /** 일별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getProdSaleRateList(ProdSaleRateVO prodSaleRateVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getProdSaleRateExcelList(ProdSaleRateVO prodSaleRateVO, SessionInfoVO sessionInfoVO);

}