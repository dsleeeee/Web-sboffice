package kr.co.solbipos.base.store.timeSlot.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.timeSlot.service.TimeSlotVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : TimeSlotMapper.java
 * @Description : 기초관리 - 매장관리 - 시간대분류관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.20  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface TimeSlotMapper {

    /** 시간대분류관리 - 시간대분류조회 */
    List<DefaultMap<Object>> getTimeSlot(TimeSlotVO timeSlotVO);

    /** 시간대분류관리 - 시간대분류저장 */
    int saveTimeSlot(TimeSlotVO timeSlotVO);

    /** 시간대분류관리 - 시간대분류삭제 */
    int deleteTimeSlot(TimeSlotVO timeSlotVO);

}
