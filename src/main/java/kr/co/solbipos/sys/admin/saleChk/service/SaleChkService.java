package kr.co.solbipos.sys.admin.saleChk.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.logSend.service.LogSendVO;

import java.util.List;

/**
 * @Class Name : LogSendService.java
 * @Description : 시스템관리 > 관리자기능 > 매출점검
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.07  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface SaleChkService {

    /** 조회 */
    List<DefaultMap<String>> getSaleList(SaleChkVO saleChkVO, SessionInfoVO sessionInfoVO);

    /** [POS-DB] 간 로그 송신 구분을 등록 */
    int updateResultMsg(SaleChkVO[] saleChkVOs, SessionInfoVO sessionInfoVO);
}
