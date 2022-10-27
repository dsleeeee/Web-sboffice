package kr.co.solbipos.excclc.excclc.dailyTableKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : DailyTableKwuMapper.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DailyTableKwuMapper {

    /** 일일일계표2 - 조회 */
    List<DefaultMap<String>> getSaleList(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getProdClassList(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getPayList(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getRtnList(DailyTableKwuVO dailyTableKwuVO);

//    List<DefaultMap<String>> getPayLineList(DailyTableKwuVO dailyTableKwuVO);

    List<DefaultMap<String>> getCourseStatusList(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getCourseTypeList(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getTuition1List(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getTuition2List(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getGroupCourseList(DailyTableKwuVO dailyTableKwuVO);

    List<DefaultMap<String>> getPaymentStatus1List(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getPaymentStatus2List(DailyTableKwuVO dailyTableKwuVO);
    List<DefaultMap<String>> getPaymentStatus3List(DailyTableKwuVO dailyTableKwuVO);
}