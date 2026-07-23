package kr.co.solbipos.accounting.accountingDlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AccountingDlvrService.java
 * @Description : 벤슨 > 회계관리 > 배달비 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AccountingDlvrService {

    /** 배달비현황 - 일별 탭 조회 */
    List<DefaultMap<Object>> getAccountingDlvrDayList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO);

    /** 배달비현황 - 일별 탭 엑셀 조회 */
    List<DefaultMap<Object>> getAccountingDlvrDayExcelList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO);

    /** 배달비현황 - 월별 탭 조회 */
    List<DefaultMap<Object>> getAccountingDlvrMonthList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO);

    /** 배달비현황 - 월별 탭 엑셀 조회 */
    List<DefaultMap<Object>> getAccountingDlvrMonthExcelList(AccountingDlvrVO accountingDlvrVO, SessionInfoVO sessionInfoVO);
}
