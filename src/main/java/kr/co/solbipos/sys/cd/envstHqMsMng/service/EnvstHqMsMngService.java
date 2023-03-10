package kr.co.solbipos.sys.cd.envstHqMsMng.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : EnvstHqMsMngService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정사용설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EnvstHqMsMngService {

    /** 사용 환경설정 조회 */
    List<DefaultMap<String>> getRegEnvstList(EnvstHqMsMngVO envstHqMsMngVO);

    /** 미사용 환경설정 조회 */
    List<DefaultMap<String>> getNoRegEnvstList(EnvstHqMsMngVO envstHqMsMngVO);

    /** 대표명칭 코드 저장 */
    int saveEnvstHqMsMng(EnvstHqMsMngVO[] envstHqMsMngVOs, SessionInfoVO sessionInfoVO);

    /** 미사용 환경설정 등록 */
    int deleteEnvstHqMsMng(EnvstHqMsMngVO[] envstHqMsMngVOs, SessionInfoVO sessionInfoVO);

}
