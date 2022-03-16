package kr.co.solbipos.sale.status.offAdd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.side.service.SideVO;

import java.util.List;

/**
 * @Class Name : OffAddService.java
 * @Description : 매출관리 > 매출현황2 > 오프라인추가매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.14  권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface OffAddService {
    /** 일별 조회 */
    List<DefaultMap<String>> getOffAddDayList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

    /** 일별상세 조회 */
    List<DefaultMap<String>> getOffAddDayDetailList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

    /** 월별 조회 */
    List<DefaultMap<String>> getOffAddMonthList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

    /** 월별상세 조회 */
    List<DefaultMap<String>> getOffAddMonthDetailList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

    /** 상품별 조회 */
    List<DefaultMap<String>> getOffAddProdList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

    /** 상품별 엑셀 조회 */
    List<DefaultMap<String>> getOffAddProdExcelList(OffAddVO offAddVO, SessionInfoVO sessionInfoVO);

}
