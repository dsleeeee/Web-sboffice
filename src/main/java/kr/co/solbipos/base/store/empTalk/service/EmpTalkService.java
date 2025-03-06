package kr.co.solbipos.base.store.empTalk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpTalkService.java
 * @Description : 기초관리 > 매장관리 > 키오스크 직원대화
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.12  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EmpTalkService {

    /** 키오스크 직원대화 관리 - 조회 */
    List<DefaultMap<Object>> getEmpTalkList(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 직원대화 관리 - 저장 */
    int saveEmpTalk(EmpTalkVO[] empTalkVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 직원대화 관리 - 기준매장 상용구 적용 */
    int empTalkRegStore(EmpTalkVO empTalkVO, SessionInfoVO sessionInfoVO);
}
