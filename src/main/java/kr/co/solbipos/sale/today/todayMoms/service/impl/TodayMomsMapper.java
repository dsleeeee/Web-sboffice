package kr.co.solbipos.sale.today.todayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TodayMomsMapper.java
 * @Description : 맘스터치 > 매출분석 > 당일 매출 현황
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
public interface TodayMomsMapper {

    /** 조회 */
    List<DefaultMap<Object>> getTodayMomsList(TodayMomsVO todayMomsVO);
    List<DefaultMap<Object>> getTodayMomsExcelList(TodayMomsVO todayMomsVO);

}