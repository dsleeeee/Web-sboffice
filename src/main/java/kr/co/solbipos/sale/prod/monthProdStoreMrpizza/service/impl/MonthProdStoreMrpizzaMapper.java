package kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.MonthProdStoreMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthProdStoreMrpizzaMapper.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MonthProdStoreMrpizzaMapper {


    List<DefaultMap<Object>> getMonthProdStoreMrpizzaList(MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO);

    List<DefaultMap<Object>> getMonthProdStoreMrpizzaExcelList(MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO);
}
