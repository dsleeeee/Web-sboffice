package kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MonthProdSaleRateMomsServiceService.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매비율(월별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.03   권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.07.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthProdSaleRateMomsService {

    /** 상품판매비율 조회 */
    List<DefaultMap<String>> getProdSaleRateList(MonthProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품판매비율 조회(엑셀용) */
    List<DefaultMap<String>> getProdSaleRateExcelList(MonthProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

}
