package kr.co.solbipos.sale.appr.mcoupn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : McoupnService.java
 * @Description : 맘스터치 > 승인관리2 > 모바일쿠폰 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface McoupnService {

    /** 모바일쿠폰 승인 조회 */
    List<DefaultMap<Object>> getMcoupnList(McoupnVO mcoupnVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getMcoupnExcelList(McoupnVO mcoupnVO, SessionInfoVO sessionInfoVO);

}