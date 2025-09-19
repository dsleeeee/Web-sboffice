package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusService;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : WorkStudentScheduleStatusServiceImpl.java
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
