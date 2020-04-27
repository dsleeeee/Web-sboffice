package kr.co.solbipos.sale.status.appr.acquire.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ApprAcquireService {
    /** 승인현황 카드매입사별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 모바일쿠폰 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMcouponList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 모바일페이 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMpayList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 비매출카드 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireNcardList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);

    /** 승인현황 카드매입사별 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 엑셀 모바일쿠폰 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMcouponExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 엑셀 모바일페이 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMpayExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
    /** 승인현황 카드매입사별 탭 - 엑셀 비매출카드 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireNcardExcelList(ApprAcquireVO apprAcquireVO, SessionInfoVO sessionInfoVO);
}
