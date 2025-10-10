package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByProductService.java
 * @Description : 국민대 > 매출분석 > 상품별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SaleAnalysisByProductService {

    /** 상품별 매출분석 조회 */
    List<DefaultMap<Object>> getSaleAnalysisByProductList(SaleAnalysisByProductVO saleAnalysisByProductVO, SessionInfoVO sessionInfoVO);
}
