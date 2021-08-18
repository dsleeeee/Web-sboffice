package kr.co.solbipos.base.store.emp.cardInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EmpCardInfoService.java
 * @Description : 기초관리 - 사원관리 - 사원카드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EmpCardInfoService {

    /** 사원카드정보 조회 */
    List<DefaultMap<Object>> getEmpCardInfo(EmpCardInfoVO empCardInfoVO, SessionInfoVO sessionInfoVO);

    /** 사원카드정보 전체 삭제 */
    int deleteEmpCardInfo(EmpCardInfoVO empCardInfoVO, SessionInfoVO sessionInfoVO);

    /** 사원카드정보 저장 */
    int saveEmpCardInfo(EmpCardInfoVO[] empCardInfoVOs, SessionInfoVO sessionInfoVO);
}
