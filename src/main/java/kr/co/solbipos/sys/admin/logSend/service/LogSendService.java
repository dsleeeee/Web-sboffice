package kr.co.solbipos.sys.admin.logSend.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;

import java.util.List;

/**
 * @Class Name : LogSendService.java
 * @Description : 시스템관리 > 관리자기능 > POS 시스템 로그 송신
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.25  이다솜      최초생성
 *
 * @author 솔비포스 백엔드 pt 이다솜
 * @since 2020. 08. 25
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface LogSendService {

    /** 매장별 포스목록 조회 */
    List<DefaultMap<String>> getPosList(LogSendVO logSendVO, SessionInfoVO sessionInfoVO);

    /** [POS-DB] 간 로그 송신 구분을 등록 */
    int updateLogSend(LogSendVO[] logSendVOs, SessionInfoVO sessionInfoVO);
}
