package kr.co.solbipos.sale.benson.orderEmpBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : OrderEmpBensonService.java
 * @Description : 벤슨 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10 김유승        최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

public interface OrderEmpBensonService {
    /** 기간별탭 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 상세 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

    /** 기간별탭 상세 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpPeriodDtlExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

    /** 일자별 조회 */
    List<DefaultMap<String>> getOrderEmpDayList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

    /** 일자별 엑셀 조회 */
    List<DefaultMap<String>> getOrderEmpDayExcelList(OrderEmpBensonVO orderEmpBensonVO, SessionInfoVO sessionInfoVO);

}
