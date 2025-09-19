package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusService;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("WorkStudentScheduleStatusService")
@Transactional
public class WorkStudentScheduleStatusServiceImpl implements WorkStudentScheduleStatusService {

    private final WorkStudentScheduleStatusMapper workStudentScheduleStatusMapper;

    @Autowired
    public WorkStudentScheduleStatusServiceImpl(WorkStudentScheduleStatusMapper workStudentScheduleStatusMapper) {
        this.workStudentScheduleStatusMapper = workStudentScheduleStatusMapper;
    }


    @Override
    public List<DefaultMap<Object>> getWorkStudentScheduleStatusList(WorkStudentScheduleStatusVO workStudentScheduleStatusVO, SessionInfoVO sessionInfoVO) {
        workStudentScheduleStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workStudentScheduleStatusMapper.getWorkStudentScheduleStatusList(workStudentScheduleStatusVO);
    }
}
