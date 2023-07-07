package kr.co.solbipos.sale.month.monthPos2.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.month.monthPos2.service.MonthPos2VO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthPos2Mapper.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황(포스별-영수건수)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.07.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MonthPos2Mapper {

    /** 조회 */
    List<DefaultMap<Object>> getMonthPos2List(MonthPos2VO monthPos2VO);
    List<DefaultMap<Object>> getMonthPos2ExcelList(MonthPos2VO monthPos2VO);
}