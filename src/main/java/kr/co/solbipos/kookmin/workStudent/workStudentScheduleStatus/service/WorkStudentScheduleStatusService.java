package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface WorkStudentScheduleStatusService {
    List<DefaultMap<Object>> getWorkStudentScheduleStatusList(WorkStudentScheduleStatusVO workStudentScheduleStatusVO, SessionInfoVO sessionInfoVO);
}
