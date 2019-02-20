package kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service.DayPayInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayPayInfoMapper {
    /** 일자별 매출공통팝업 - 신용카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayCardList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 현금 상세 리스트 조회 */
    List<DefaultMap<String>> getDayCashList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - PAYCO 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPaycoList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - VMEM 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVpointList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - VMEM 쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVcoupnList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - VMEM 전자상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayVchargeList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 모바일페이 상세 리스트 조회 */
    List<DefaultMap<String>> getDayMpayList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 모바일쿠폰 상세 리스트 조회 */
    List<DefaultMap<String>> getDayMcoupnList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 포인트 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPointList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 회원선불 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPrepaidList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 회원후불 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPostpaidList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 상품권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayGiftList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 식권 상세 리스트 조회 */
    List<DefaultMap<String>> getDayFstmpList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 제휴카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayPartnerList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 사원카드 상세 리스트 조회 */
    List<DefaultMap<String>> getDayEmpCardList(DayPayInfoVO dayPayInfoVO);

    /** 일자별 매출공통팝업 - 가승인 상세 리스트 조회 */
    List<DefaultMap<String>> getDayTemporaryList(DayPayInfoVO dayPayInfoVO);
}
