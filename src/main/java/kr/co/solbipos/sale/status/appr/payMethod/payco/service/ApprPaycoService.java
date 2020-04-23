package kr.co.solbipos.sale.status.appr.payMethod.payco.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprPaycoService {
    /** 승인현황 승인현황 페이코 - 리스트 조회 */
    List<DefaultMap<String>> getApprPaycoList(ApprPaycoVO apprpaycoVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 승인현황 페이코 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprPaycoExcelList(ApprPaycoVO apprpaycoVO, SessionInfoVO sessionInfoVO);
}
