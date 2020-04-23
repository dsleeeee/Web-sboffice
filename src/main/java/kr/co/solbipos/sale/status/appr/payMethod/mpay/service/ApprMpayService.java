package kr.co.solbipos.sale.status.appr.payMethod.mpay.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprMpayService {
    /** Mpay 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprMpayList(ApprMpayVO apprMpayVO, SessionInfoVO sessionInfoVO);
    /** Mpay 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprMpayExcelList(ApprMpayVO apprMpayVO, SessionInfoVO sessionInfoVO);
}
