package kr.co.solbipos.sys.stats.webLogin.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : WebLoginService.java
 * @Description : 시스템관리 > 통계 > 웹로그인 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.06.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface WebLoginService {

    /** 웹로그인 현황 조회 */
    List<DefaultMap<Object>> getWebLoginList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO);

    /** 일자별 현황 조회 */
    List<DefaultMap<Object>> getWebLoginDayDetailList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO);

    /** 로그인 현황 조회 */
    List<DefaultMap<Object>> getWebLoginLoginDetailList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO);
}