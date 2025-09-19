package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkStudentScheduleStatusMapper {
    List<DefaultMap<Object>> getWorkStudentScheduleStatusList(WorkStudentScheduleStatusVO workStudentScheduleStatusVO);
}
