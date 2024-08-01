package kr.co.solbipos.mobile.stock.status.storePeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name : MobileStorePeriodService.java
 * @Description : (모바일)재고현황 > 매장기간수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileStorePeriodService {

    /** 매장기간수불 - 매장기간수불 리스트 조회 */
    public List<DefaultMap<String>> getStorePeriodList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO);

    /** 매장기간수불 - 매장기간수불 상품코드 선택 상세 리스트 조회 */
    public List<DefaultMap<String>> getStorePeriodDtlList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO);

    /** 매장기간수불 - 매장기간수불 수량 선택 상세 리스트 조회 */
    public List<DefaultMap<String>> getStorePeriodQtyDtlList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO);

    /** 매장기간수불 - 매장기간수불 엑셀 전체 리스트 조회 */
    public List<DefaultMap<String>> getStoreperiodExcelList(MobileStorePeriodVO mobileStorePeriodVO, SessionInfoVO sessionInfoVO);
}
