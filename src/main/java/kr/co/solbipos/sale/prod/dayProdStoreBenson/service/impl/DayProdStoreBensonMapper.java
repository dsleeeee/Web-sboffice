package kr.co.solbipos.sale.prod.dayProdStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import kr.co.solbipos.sale.prod.dayProdStoreBenson.service.DayProdStoreBensonVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayProdStoreBensonMapper.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황(매장별)
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
public interface DayProdStoreBensonMapper {

    /** 일별상품매출현황(매장별) 리스트 조회 */
    List<DefaultMap<Object>> getDayProdStoreBensonList(DayProdStoreBensonVO dayProdStoreBensonVO);

    /** 일별상품매출현황(매장별) 엑셀 다운로드 조회*/
    List<DefaultMap<Object>> getDayProdStoreBensonExcelList(DayProdStoreBensonVO dayProdStoreBensonVO);

}
