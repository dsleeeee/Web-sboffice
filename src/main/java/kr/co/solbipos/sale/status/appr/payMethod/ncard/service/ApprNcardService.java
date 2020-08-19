package kr.co.solbipos.sale.status.appr.payMethod.ncard.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprNcardService {
    /** 비매출카드 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprNcardList(ApprNcardVO apprpaycoVO, SessionInfoVO sessionInfoVO);
    /** 비매출카드 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprNcardExcelList(ApprNcardVO apprpaycoVO, SessionInfoVO sessionInfoVO);
}
