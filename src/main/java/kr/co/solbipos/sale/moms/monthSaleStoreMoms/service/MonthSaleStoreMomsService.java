package kr.co.solbipos.sale.moms.monthSaleStoreMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MonthSaleStoreMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 월별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.12.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.12.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthSaleStoreMomsService {

    /** 월별매출(매장) - 조회 */
    List<DefaultMap<Object>> getMonthSaleStoreMomsList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 월별매출(매장) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getMonthSaleStoreMomsExcelList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO);

    /** 월별매출(매장) - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getMonthSaleStoreMomsExcelDivisionList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO);
}