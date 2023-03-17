package kr.co.solbipos.sale.month.monthPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.month.monthPos.service.MonthPosVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MonthPosMapper.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황(포스별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface MonthPosMapper {

    /** 조회 */
    List<DefaultMap<Object>> getMonthPosList(MonthPosVO monthPosVO);
    List<DefaultMap<Object>> getMonthPosExcelList(MonthPosVO monthPosVO);
}