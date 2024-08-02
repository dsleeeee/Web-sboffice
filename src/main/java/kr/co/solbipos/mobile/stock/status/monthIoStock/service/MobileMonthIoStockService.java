package kr.co.solbipos.mobile.stock.status.monthIoStock.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : MobileMonthIoStockService.java
 * @Description : (모바일)재고현황 > 월수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.19  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileMonthIoStockService {

    /** 월수불현황 리스트 조회 */
    List<DefaultMap<String>> monthIoStockList(MobileMonthIoStockVO mobileMonthIoStockVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> monthIoStockExcelList(MobileMonthIoStockVO mobileMonthIoStockVO, SessionInfoVO sessionInfoVO);
}
