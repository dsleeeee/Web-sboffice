package kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprMcouponService {
    /** 모바일쿠폰 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprMcouponList(ApprMcouponVO apprpaycoVO, SessionInfoVO sessionInfoVO);
    /** 모바일쿠폰 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprMcouponExcelList(ApprMcouponVO apprpaycoVO, SessionInfoVO sessionInfoVO);
}
