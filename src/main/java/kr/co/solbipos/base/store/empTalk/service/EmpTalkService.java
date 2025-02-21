package kr.co.solbipos.base.store.empTalk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface EmpTalkService {

    /** 키오스크 직원대화 관리 - 조회 */
    List<DefaultMap<Object>> getEmpTalkList(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 직원대화 관리 - 저장 */
    int saveEmpTalk(EmpTalkVO[] empTalkVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 직원대화 관리 - 기준매장 상용구 적용 */
    int empTalkRegStore(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO);
}
