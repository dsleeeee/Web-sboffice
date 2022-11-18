package kr.co.solbipos.sale.prod.prodSaleRate2.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdSaleRate2Service.java
 * @Description : 맘스터치 > 상품매출분석 > 상품 판매 비율
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
public interface ProdSaleRate2Service {

    List<DefaultMap<Object>> getProdSaleRate2List(ProdSaleRate2VO prodSaleRate2VO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getProdSaleRate2ExcelList(ProdSaleRate2VO prodSaleRate2VO, SessionInfoVO sessionInfoVO);

}