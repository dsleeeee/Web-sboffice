package kr.co.solbipos.sale.prod.prodSaleRateMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdSaleRateMomsService.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.13   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSaleRateMomsService {

    /** 상품판매비율 조회 */
    List<DefaultMap<String>> getProdSaleRateList(ProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

    /** 상품판매비율 조회(엑셀용) */
    List<DefaultMap<String>> getProdSaleRateExcelList(ProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO);

}
