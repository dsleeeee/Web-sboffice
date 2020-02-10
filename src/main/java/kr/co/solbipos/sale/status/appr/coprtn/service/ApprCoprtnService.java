package kr.co.solbipos.sale.status.appr.coprtn.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface ApprCoprtnService {
    /** 신용카드 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprCoprtnList(ApprCoprtnVO apprCoprtnVO, SessionInfoVO sessionInfoVO);
}
