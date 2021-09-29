package kr.co.solbipos.mobile.sale.status.rtnStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileRtnStatsuService.java
 * @Description : (모바일) 매출현황 > 반품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileRtnStatusService {

    /** 반품현황 - 조회 */
    List<DefaultMap<Object>> getMobileRtnStatusList(MobileRtnStatusVO mobileRtnStatusVO, SessionInfoVO sessionInfoVO);

    /** 반품현황 상세 팝업 - 조회 */
    List<DefaultMap<Object>> getMobileRtnStatusDtlList(MobileRtnStatusVO mobileRtnStatusVO, SessionInfoVO sessionInfoVO);

}