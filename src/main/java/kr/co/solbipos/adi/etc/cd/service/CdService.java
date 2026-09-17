package kr.co.solbipos.adi.etc.cd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CdService.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CdService {
    
    /** 대표명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeGrpCdList(CdVO cdVO);

    /** 세부명칭 코드목록 조회 */
    List<DefaultMap<String>> getNmcodeCdList(CdVO cdVO);
    
    /** 코드목록 저장 */
    int saveNmcodeCdList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO);

    /** 본사권한 공통코드 매장수정 허용 - 대표명칭(공통) 목록 조회 */
    List<DefaultMap<String>> getCdStoreAllowGrpList(CdVO cdVO);

    /** 본사권한 공통코드 매장수정 허용 - 매장목록/설정 조회 */
    List<DefaultMap<String>> getCdStoreAllowList(CdVO cdVO);

    /** 본사권한 공통코드 매장수정 허용 - 저장 */
    int saveCdStoreAllowList(CdVO[] cdVOs, SessionInfoVO sessionInfoVO);

    /** 본사권한 공통코드 매장수정 허용 - 매장 허용값 조회 */
    DefaultMap<String> getCdStoreAllowItem(CdVO cdVO);

}
