package kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.MonthProdSaleRateMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthProdSaleRateMomsMapper.java
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
@Mapper
@Repository
public interface MonthProdSaleRateMomsMapper {

    /** 상품판매비율 조회 */
    List<DefaultMap<String>> getProdSaleRateList(MonthProdSaleRateMomsVO monthProdSaleRateMomsVO);

    /** 상품판매비율 조회(엑셀용) */
    List<DefaultMap<String>> getProdSaleRateExcelList(MonthProdSaleRateMomsVO monthProdSaleRateMomsVO);
}
