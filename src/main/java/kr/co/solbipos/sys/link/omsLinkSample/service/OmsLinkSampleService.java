package kr.co.solbipos.sys.link.omsLinkSample.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : OmsLinkSampleService.java
 * @Description : 시스템관리 > 연동 > OMS연동샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface OmsLinkSampleService {

    /** OMS연동샘플 API 호출 목록 조회 */
    List<DefaultMap<Object>> getOmsLinkSampleReqList(ApiLinkVO apiLinkVO);

    /** API 호출 로그 저장 */
    int saveApiLog(ApiLinkVO apiLinkVO, SessionInfoVO sessionInfoVO);
}
