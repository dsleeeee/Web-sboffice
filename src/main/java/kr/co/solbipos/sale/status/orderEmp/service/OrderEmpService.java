package kr.co.solbipos.sale.status.orderEmp.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : OrderEmpService.java
 * @Description : 매출관리 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.10 권지현        최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface OrderEmpService {
    /** 기간별탭 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 상세 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 상세 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

    /** 일자별 조회 */
    List<DefaultMap<String>> getOrderEmpDayList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

    /** 일자별 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpVO orderEmpVO, SessionInfoVO sessionInfoVO);

}
