package kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DayPayInfoService {
    /** 일자별 매출공통팝업 - 신용카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayCardList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 현금 상세 리스트 조회 */
    List<DefaultMap<String>> getDayCashList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - PAYCO 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPaycoList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - VMEM 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVpointList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVcoupnList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVchargeList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 모바일페이 상세 리스트 조회 */
    List<DefaultMap<String>> getDayMpayList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 모바일쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getDayMcoupnList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPointList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 회원선불 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPrepaidList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 회원후불 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPostpaidList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayGiftList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 식권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayFstmpList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 제휴카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPartnerList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 사원카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayEmpCardList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 가승인 상세 리스트 조회 */
    List<DefaultMap<String>> getDayTemporaryList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출공통팝업 - 스마트오더 상세 리스트 조회 */
    List<DefaultMap<String>> getDaySmartorderList(DayPayInfoVO dayPayInfoVO, SessionInfoVO sessionInfoVO);
}
