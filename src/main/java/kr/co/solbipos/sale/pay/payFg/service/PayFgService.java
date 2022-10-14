package kr.co.solbipos.sale.pay.payFg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PayFgService.java
 * @Description : 맘스터치 > 결제수단별 매출 > 결제수단별 일 매출현황
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
public interface PayFgService {

    /** 조회 */
    List<DefaultMap<Object>> getPayFgList(PayFgVO payFgVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getPayFgExcelList(PayFgVO payFgVO, SessionInfoVO sessionInfoVO);

}