package kr.co.solbipos.sale.day.dayTimeMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.dayTimeMrpizza.service.DayTimeMrpizzaVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DayTimeMrpizzaMapper.java
 * @Description : 미스터피자 > 매출분석 > 일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DayTimeMrpizzaMapper {

    /** 일별시간대 리스트 조회 */
    List<DefaultMap<Object>> getDayTimeMrpizzaList(DayTimeMrpizzaVO dayTimeMrpizzaVO);
}
