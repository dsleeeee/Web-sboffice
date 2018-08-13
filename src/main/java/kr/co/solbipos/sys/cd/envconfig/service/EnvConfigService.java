package kr.co.solbipos.sys.cd.envconfig.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : EnvConfigService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface EnvConfigService {
    
    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getEnvstList(EnvstVO envstVO);
    
    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getEnvstDtlList(EnvstDtlVO envstDtlVO);
    
    /** 대표명칭 코드 저장 */
    int saveEnvstList(EnvstVO[] envstVOs, SessionInfoVO sessionInfoVO);
    
    /** 세부명칭 코드 저장 */
    int saveEnvstDtlList(EnvstDtlVO[] envstDtlVO, SessionInfoVO sessionInfoVO);
    
}
