package kr.co.solbipos.excclc.excclc.dailyTableKwn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name : DailyTableKwnMapper.java
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
public interface DailyTableKwnMapper {

    /** 일일일계표2 - 조회 */
    List<DefaultMap<String>> getSaleList(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getProdClassList(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getPayList(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getRtnList(DailyTableKwnVO dailyTableKwnVO);

//    List<DefaultMap<String>> getPayLineList(DailyTableKwnVO dailyTableKwnVO);

    List<DefaultMap<String>> getCourseStatusList(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getCourseTypeList(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getTuition1List(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getTuition2List(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getGroupCourseList(DailyTableKwnVO dailyTableKwnVO);

    List<DefaultMap<String>> getPaymentStatus1List(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getPaymentStatus2List(DailyTableKwnVO dailyTableKwnVO);
    List<DefaultMap<String>> getPaymentStatus3List(DailyTableKwnVO dailyTableKwnVO);
}