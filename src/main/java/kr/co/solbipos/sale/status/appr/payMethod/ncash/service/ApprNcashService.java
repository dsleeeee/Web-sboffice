package kr.co.solbipos.sale.status.appr.payMethod.ncash.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprNcashService {
    /** 신용카드 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprNcashList(ApprNcashVO apprpaycoVO, SessionInfoVO sessionInfoVO);
}
