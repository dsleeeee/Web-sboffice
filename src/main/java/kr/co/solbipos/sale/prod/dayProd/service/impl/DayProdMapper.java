package kr.co.solbipos.sale.prod.dayProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayProdMapper.java
 * @Description : 맘스터치 > 승인관리2 > 일별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayProdMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getDayProdList(DayProdVO dayProdVO);
    List<DefaultMap<Object>> getDayProdExcelList(DayProdVO dayProdVO);
}