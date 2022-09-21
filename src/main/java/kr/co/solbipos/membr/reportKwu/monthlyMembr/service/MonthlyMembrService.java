package kr.co.solbipos.membr.reportKwu.monthlyMembr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MonthlyMembrService.java
 * @Description : 광운대 > 리포트 > 월별회원등록현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface MonthlyMembrService {

    /** 본사 명칭 콤보조회 */
    List<DefaultMap<String>> selectHqCodeList(MonthlyMembrVO monthlyMembrVO, SessionInfoVO sessionInfoVO);

    /** 월별회원등록현황 - 리스트 조회 */
    List<DefaultMap<String>> getMonthlyMembrList(MonthlyMembrVO monthlyMembrVO, SessionInfoVO sessionInfoVO);
}
