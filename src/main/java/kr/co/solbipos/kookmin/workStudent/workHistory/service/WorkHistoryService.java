package kr.co.solbipos.kookmin.workStudent.workHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface WorkHistoryService {

    /** 근무내역 조회 */
    List<DefaultMap<Object>> getWorkHistoryList(WorkHistoryVO workHistoryVO, SessionInfoVO sessionInfoVO);

    /** 근무내역 - 근무시간 저장 */
    int saveWorkHistory(WorkHistoryVO[] workHistoryVOS, SessionInfoVO sessionInfoVO);

    /** 근무내역 - 근무시간 일괄등록 */
    int saveRegCommuteAll(WorkHistoryVO[] workHistoryVOS, SessionInfoVO sessionInfoVO);
}
