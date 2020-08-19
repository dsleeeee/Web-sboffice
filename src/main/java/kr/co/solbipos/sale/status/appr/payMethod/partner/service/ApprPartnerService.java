package kr.co.solbipos.sale.status.appr.payMethod.partner.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprPartnerService {
    /** 제휴카드 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprPartnerList(ApprPartnerVO apprPartnerVO, SessionInfoVO sessionInfoVO);
    /** 제휴카드 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprPartnerExcelList(ApprPartnerVO apprPartnerVO, SessionInfoVO sessionInfoVO);
}
