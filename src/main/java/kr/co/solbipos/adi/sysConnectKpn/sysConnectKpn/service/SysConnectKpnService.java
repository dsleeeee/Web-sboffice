package kr.co.solbipos.adi.sysConnectKpn.sysConnectKpn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.omsLinkSample.service.ApiLinkVO;

/**
 * @Class Name : SysConnectKpnService.java
 * @Description : 부가서비스 > 정산 > KPN시스템접속
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SysConnectKpnService {

    /** KPN API 연동정보(URL/키/캐시토큰) 조회 */
    DefaultMap<Object> getKpnApiInfo(SysConnectKpnVO sysConnectKpnVO, SessionInfoVO sessionInfoVO);

    /** 신규 발급받은 KPN 토큰 정보 업데이트 */
    int updateKpnToken(SysConnectKpnVO sysConnectKpnVO, SessionInfoVO sessionInfoVO);

    /** KPN API 호출 로그 저장 (TB_CM_API_LINK) */
    int saveApiLog(ApiLinkVO apiLinkVO, SessionInfoVO sessionInfoVO);
}
