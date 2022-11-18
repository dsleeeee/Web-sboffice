package kr.co.solbipos.sale.prod.prodSaleRate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ProdSaleRateMapper.java
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
@Mapper
@Repository
public interface ProdSaleRateMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getProdSaleRateList(ProdSaleRateVO prodSaleRateVO);
    List<DefaultMap<Object>> getProdSaleRateExcelList(ProdSaleRateVO prodSaleRateVO);
}