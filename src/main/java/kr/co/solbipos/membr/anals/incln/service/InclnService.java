package kr.co.solbipos.membr.anals.incln.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @author
 * @version 1.0
 * @Class Name : MnclnService.java
 * @Description : 회원관리 > 회원분석 > 회원 구매성향 분석
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.10  Daniel      최초생성
 * @since 2020.06.10
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface InclnService {

  /** 회원 구매성향 분석 조회 */
  List<DefaultMap<Object>> getInclnList(InclnVO inclnVO, SessionInfoVO sessionInfoVO);
}
