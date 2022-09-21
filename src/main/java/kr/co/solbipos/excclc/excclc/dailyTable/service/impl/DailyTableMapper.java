package kr.co.solbipos.excclc.excclc.dailyTable.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : DailyTableMapper.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface DailyTableMapper {

    /** 조회 */
    List<DefaultMap<String>> getSaleList(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getProdClassList(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getPayList(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getRtnList(DailyTableVO dailyTableVO);

    List<DefaultMap<String>> getPayLineList(DailyTableVO dailyTableVO);

    List<DefaultMap<String>> getCourseStatusList(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getCourseTypeList(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getTuition1List(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getTuition2List(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getGroupCourseList(DailyTableVO dailyTableVO);

    List<DefaultMap<String>> getPaymentStatus1List(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getPaymentStatus2List(DailyTableVO dailyTableVO);
    List<DefaultMap<String>> getPaymentStatus3List(DailyTableVO dailyTableVO);

}
