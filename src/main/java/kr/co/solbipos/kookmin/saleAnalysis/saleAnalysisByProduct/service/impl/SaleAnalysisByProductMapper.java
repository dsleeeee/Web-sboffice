package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByProductMapper.java
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
@Mapper
@Repository
public interface SaleAnalysisByProductMapper {

    /** 상품별 매출분석 조회 */
    List<DefaultMap<Object>> getSaleAnalysisByProductList(SaleAnalysisByProductVO saleAnalysisByProductVO);
}
