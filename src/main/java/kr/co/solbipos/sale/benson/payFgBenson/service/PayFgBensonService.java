package kr.co.solbipos.sale.benson.payFgBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PayFgBensonService.java
 * @Description : 벤슨 > 결제수단별 매출 > 결제수단별 일 매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PayFgBensonService {

    /** 조회 */
    List<DefaultMap<Object>> getPayFgBensonList(PayFgBensonVO payFgBensonVO, SessionInfoVO sessionInfoVO);

    /** 엑셀 조회 */
    List<DefaultMap<Object>> getPayFgBensonExcelList(PayFgBensonVO payFgBensonVO, SessionInfoVO sessionInfoVO);

}
