package kr.co.solbipos.sale.pay.payMonth.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.pay.payMonth.service.PayMonthVO;

import java.util.List;

/**
 * @Class Name : PayMonthService.java
 * @Description : 맘스터치 > 결제수단매출 > 월별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PayMonthService {

    /** 조회 */
    List<DefaultMap<Object>> getPayMonthList(PayMonthVO payMonthVO, SessionInfoVO sessionInfoVO);

}