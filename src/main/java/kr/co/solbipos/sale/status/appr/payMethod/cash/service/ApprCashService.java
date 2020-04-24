package kr.co.solbipos.sale.status.appr.payMethod.cash.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprCashService {
    /** 현금 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprCashList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO);
    /** 현금 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprCashExcelList(ApprCashVO apprCashVO, SessionInfoVO sessionInfoVO);
}
