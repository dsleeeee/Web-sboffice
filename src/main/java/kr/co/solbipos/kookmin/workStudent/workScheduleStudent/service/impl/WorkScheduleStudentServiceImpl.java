package kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service.WorkScheduleStudentService;
import kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service.WorkScheduleStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
/**
 * @Class Name  : WorkScheduleStudentServiceImpl.java
 * @Description : 국민대 > 근로학생관리 > 근로학생 배치
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("WorkScheduleStudentService")
@Transactional
public class WorkScheduleStudentServiceImpl implements WorkScheduleStudentService {

    private final WorkScheduleStudentMapper workScheduleStudentMapper;

    @Autowired
    public WorkScheduleStudentServiceImpl(WorkScheduleStudentMapper workScheduleStudentMapper) {
        this.workScheduleStudentMapper = workScheduleStudentMapper;
    }

    /** 근로학생 조회 */
    @Override
    public List<DefaultMap<String>> getWorkScheduleStudentList(WorkScheduleStudentVO workScheduleStudentVO, SessionInfoVO sessionInfoVO) {
        workScheduleStudentVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workScheduleStudentMapper.getWorkScheduleStudentList(workScheduleStudentVO);
    }

    /** 근로학생 등록 팝업 - 근로학생 조회 */
    @Override
    public List<DefaultMap<String>> getWorkStudentList(WorkScheduleStudentVO workScheduleStudentVO, SessionInfoVO sessionInfoVO) {
        workScheduleStudentVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return workScheduleStudentMapper.getWorkStudentList(workScheduleStudentVO);
    }

    /** 근로학생 근무배치 저장 */
    @Override
    public int saveWorkScheduleStudent(WorkScheduleStudentVO[] workScheduleStudentVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(WorkScheduleStudentVO workScheduleStudentVO : workScheduleStudentVOs){

            workScheduleStudentVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            workScheduleStudentVO.setRegId(sessionInfoVO.getUserId());
            workScheduleStudentVO.setRegDt(currentDt);
            workScheduleStudentVO.setModId(sessionInfoVO.getUserId());
            workScheduleStudentVO.setModDt(currentDt);

            //수정
            procCnt += workScheduleStudentMapper.updateWorkScheduleStudent(workScheduleStudentVO);

        }

        return procCnt;
    }
}
