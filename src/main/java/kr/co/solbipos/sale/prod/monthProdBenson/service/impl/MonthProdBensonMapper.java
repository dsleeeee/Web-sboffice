package kr.co.solbipos.sale.prod.monthProdBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonVO;
import kr.co.solbipos.sale.prod.monthProdBenson.service.MonthProdBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthProdBensonMapper.java
 * @Description : 벤슨 > 상품매출분석 > 월별상품매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface MonthProdBensonMapper {

    /** 월별상품매출현황 리스트 조회 */
    List<DefaultMap<Object>> getMonthProdBensonList(MonthProdBensonVO monthProdBensonVO);

    /** 월별상품매출현황 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getMonthProdBensonExcelList(MonthProdBensonVO monthProdBensonVO);
}
