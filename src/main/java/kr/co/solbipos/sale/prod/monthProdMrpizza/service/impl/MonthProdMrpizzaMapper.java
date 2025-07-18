package kr.co.solbipos.sale.prod.monthProdMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.monthProdMrpizza.service.MonthProdMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthProdMrpizzaMapper.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황
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
public interface MonthProdMrpizzaMapper {

    /** 월별상품매출현황 리스트 조회 */
    List<DefaultMap<Object>> getMonthProdMrpizzaList(MonthProdMrpizzaVO monthProdMrpizzaVO);

    /** 월별상품매출현황 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getMonthProdMrpizzaExcelList(MonthProdMrpizzaVO monthProdMrpizzaVO);
}
