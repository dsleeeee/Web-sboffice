package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : WorkStudentScheduleStatusMapper.java
 * @Description : 국민대 > 근로학생관리 > 근로학생 근무배치 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface WorkStudentScheduleStatusMapper {
    List<DefaultMap<Object>> getWorkStudentScheduleStatusList(WorkStudentScheduleStatusVO workStudentScheduleStatusVO);
}
