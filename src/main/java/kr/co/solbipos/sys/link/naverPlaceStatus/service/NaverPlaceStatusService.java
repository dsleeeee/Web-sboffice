package kr.co.solbipos.sys.link.naverPlaceStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : NaverPlaceStatusService.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverPlaceStatusService {

    /** 사용자현황 조회 */
    List<DefaultMap<Object>> getUserStatusList(NaverPlaceStatusVO naverPlaceStatusVO, SessionInfoVO sessionInfoVO);

    /** 접속현황 조회 */
    List<DefaultMap<Object>> getConnectStatusList(NaverPlaceStatusVO naverPlaceStatusVO, SessionInfoVO sessionInfoVO);
}
