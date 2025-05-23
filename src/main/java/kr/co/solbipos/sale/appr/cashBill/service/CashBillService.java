package kr.co.solbipos.sale.appr.cashBill.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CashBillService.java
 * @Description : 맘스터치 > 승인관리2 > 현금영수증 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CashBillService {

    /** 현금영수증 승인 조회 */
    List<DefaultMap<Object>> getCashBillList(CashBillVO cashBillVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getCashBillExcelList(CashBillVO cashBillVO, SessionInfoVO sessionInfoVO);

}