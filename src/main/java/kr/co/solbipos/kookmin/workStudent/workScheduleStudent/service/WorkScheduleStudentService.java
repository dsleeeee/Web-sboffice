package kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : WorkScheduleStudentService.java
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
public interface WorkScheduleStudentService {

    /** 근로학생 조회 */
    List<DefaultMap<String>> getWorkScheduleStudentList(WorkScheduleStudentVO workScheduleStudentVO, SessionInfoVO sessionInfoVO);

    /** 근로학생 등록 팝업 - 근로학생 조회 */
    List<DefaultMap<String>> getWorkStudentList(WorkScheduleStudentVO workScheduleStudentVO, SessionInfoVO sessionInfoVO);

    /** 근로학생 근무배치 저장 */
    int saveWorkScheduleStudent(WorkScheduleStudentVO[] workScheduleStudentVOs, SessionInfoVO sessionInfoVO);
}
