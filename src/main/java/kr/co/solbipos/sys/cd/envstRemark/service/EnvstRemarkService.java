package kr.co.solbipos.sys.cd.envstRemark.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EnvstRemarkService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정기능설명
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EnvstRemarkService {

    /** 환경그룹 목록 조회 */
    List<DefaultMap<String>> getEnvstGrpList();

    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getEnvstList(EnvstRemarkVO envstRemarkVO);

    /** 대표명칭 코드 저장 */
    int saveEnvstRemark(EnvstRemarkVO envstRemarkVO, SessionInfoVO sessionInfoVO);

}
