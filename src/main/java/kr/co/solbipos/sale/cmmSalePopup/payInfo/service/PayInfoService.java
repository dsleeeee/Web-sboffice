package kr.co.solbipos.sale.cmmSalePopup.payInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PayInfoService {
    /** 매출공통팝업 - 신용카드 상세 리스트 조회 */
    List<DefaultMap<String>> getCardList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 현금 상세 리스트 조회 */
    List<DefaultMap<String>> getCashList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - PAYCO 상세 리스트 조회 */
    List<DefaultMap<String>> getPaycoList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - VMEM 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getVpointList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getVcoupnList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getVchargeList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 모바일페이 상세 리스트 조회 */
    List<DefaultMap<String>> getMpayList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 모바일쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getMcoupnList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getPointList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 회원선불 상세 리스트 조회 */
    List<DefaultMap<String>> getPrepaidList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 회원후불 상세 리스트 조회 */
    List<DefaultMap<String>> getPostpaidList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getGiftList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 식권 상세 리스트 조회 */
    List<DefaultMap<String>> getFstmpList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 제휴카드 상세 리스트 조회 */
    List<DefaultMap<String>> getPartnerList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 사원카드 상세 리스트 조회 */
    List<DefaultMap<String>> getEmpCardList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 가승인 상세 리스트 조회 */
    List<DefaultMap<String>> getTemporaryList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 스마트오더 상세 리스트 조회 */
    List<DefaultMap<String>> getSmartOrderList(PayInfoVO payInfoVO, SessionInfoVO sessionInfoVO);


}
