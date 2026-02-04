package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : DlvrAgencyLinkService.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.14  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

public interface DlvrAgencyLinkService {

    /** 개발/운영 Api URL 조회 */
    DefaultMap<Object> getApiUrl(DlvrAgencyLinkVO dlvrAgencyLinkVO, SessionInfoVO sessionInfoVO);
}
