package kr.co.solbipos.sys.cd.systemcd.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : SystemCdService.java
 * @Description : 시스템관리 > 코드관리 > 시스템 명칭관리
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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SystemCdService {
    
    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdList(SystemCdVO systemCdVO);
    
    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdList(SystemCdVO systemCdVO);
    
    /** 코드목록 저장 */
    int saveNmcodeCdList(SystemCdVO[] systemCdVOs, SessionInfoVO sessionInfoVO);
    
}
