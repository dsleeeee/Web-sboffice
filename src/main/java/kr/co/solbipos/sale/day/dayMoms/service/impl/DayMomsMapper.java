package kr.co.solbipos.sale.day.dayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayMomsMapper.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayMomsMapper {

    /** 상품별 매출 순위 */
    List<DefaultMap<Object>> getDayMomsList(DayMomsVO dayMomsVO);
    List<DefaultMap<Object>> getDayMomsExcelList(DayMomsVO dayMomsVO);
}