package kr.co.solbipos.sys.admin.momsBatch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MomsBatchService.java
 * @Description : 시스템관리 > 관리자기능 > 맘스터치일괄처리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2023.02.21
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface MomsBatchService {

    /** 일괄처리 */
    String batchProc(MomsBatchVO momsBatchVO, SessionInfoVO sessionInfoVO);

    /** 매장코드 조회 */
    List<DefaultMap<String>> selectStoreList(MomsBatchVO momsBatchVO, SessionInfoVO sessionInfoVO);
}
