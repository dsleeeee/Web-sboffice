package kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2VO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : DailyTableKwu2Mapper.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표3
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DailyTableKwu2Mapper {

    /** 일일일계표3 - 조회 */
    List<DefaultMap<String>> getSaleList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getProdClassList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getPayList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getRtnList(DailyTableKwu2VO dailyTableKwu2VO);

//    List<DefaultMap<String>> getPayLineList(DailyTableKwu2VO dailyTableKwu2VO);

    List<DefaultMap<String>> getCourseTypeList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getTuition1List(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getTuition2List(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getGroupCourseList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getStatusList(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getAccountStatusList(DailyTableKwu2VO dailyTableKwu2VO);

    List<DefaultMap<String>> getPaymentStatus1List(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getPaymentStatus2List(DailyTableKwu2VO dailyTableKwu2VO);
    List<DefaultMap<String>> getPaymentStatus3List(DailyTableKwu2VO dailyTableKwu2VO);
}