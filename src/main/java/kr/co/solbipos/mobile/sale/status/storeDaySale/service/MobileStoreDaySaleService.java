package kr.co.solbipos.mobile.sale.status.storeDaySale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileStoreDaySaleService.java
 * @Description : 모바일 매장매출 > 일별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileStoreDaySaleService {
  /** 일자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileStoreDaySaleDtlList(MobileStoreDaySaleVO mobileStoreDaySaleVO, SessionInfoVO sessionInfoVO);
}