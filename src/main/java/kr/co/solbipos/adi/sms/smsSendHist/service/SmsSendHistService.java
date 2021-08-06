package kr.co.solbipos.adi.sms.smsSendHist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsSendHistService.java
 * @Description : 부가서비스 > SMS관리 > SMS전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SmsSendHistService {

    /** SMS전송이력 - 조회 */
    List<DefaultMap<Object>> getSmsSendHistList(SmsSendHistVO smsSendHistVO, SessionInfoVO sessionInfoVO);
}