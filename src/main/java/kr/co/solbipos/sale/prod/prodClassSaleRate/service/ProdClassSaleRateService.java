package kr.co.solbipos.sale.prod.prodClassSaleRate.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.ProdClassSaleRateVO;

import java.util.List;

/**
 * @Class Name : ProdClassSaleRateService.java
 * @Description : 맘스터치 > 승인관리2 > 상품군 판매비율
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
public interface ProdClassSaleRateService {

    /** 조회 */
    List<DefaultMap<Object>> getProdClassSaleRateList(ProdClassSaleRateVO prodClassSaleRateVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getProdClassSaleRateExcelList(ProdClassSaleRateVO prodClassSaleRateVO, SessionInfoVO sessionInfoVO);

}