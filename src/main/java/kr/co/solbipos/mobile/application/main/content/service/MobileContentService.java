package kr.co.solbipos.mobile.application.main.content.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.enums.MainSrchFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : MobileContentService.java
 * @Description : 어플리케이션 > 메인 > 내용
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileContentService {

    /**
     * 본사의 메인 컨텐츠 조회
     * @param sessionInfoVO
     * @return
     */
//    Map<String, Object> getHqMain(SessionInfoVO sessionInfoVO);

    /** 매출현황 날짜 select box */
    List<Map<String,String>> getDateSelList(MainSrchFg type);

    /** 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getStoreCntList(SessionInfoVO sessionInfoVO);

    /** 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getPosCntList(SessionInfoVO sessionInfoVO);

    /** 주간매출(매장수/포스수) */
    List<DefaultMap<String>> getWeekSaleList(SessionInfoVO sessionInfoVO);

    /** 공지사항 */
    List<DefaultMap<String>> getNoticeList(SessionInfoVO sessionInfoVO);

    /** 주간 POS 설치현황(신규설치/재설치) */
    List<DefaultMap<String>> getWeekPosInstList(SessionInfoVO sessionInfoVO);

    /** 주간 POS 설치 상위 대리점 */
    List<DefaultMap<String>> getWeekPosInstTopList(SessionInfoVO sessionInfoVO);

    /** 주간 매출 상위 가맹점 */
    List<DefaultMap<String>> getWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO);

    /** 매출현황 일별(1주) */
    List<DefaultMap<String>> getSaleWeekList(SessionInfoVO sessionInfoVO);

    /** 매출현황 요일별(1개월) */
    List<DefaultMap<String>> getSaleMonthList(SessionInfoVO sessionInfoVO);

    /** 매출현황 월별(1년) */
    List<DefaultMap<String>> getSaleYearList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 상품 오늘 */
    List<DefaultMap<String>> getSaleProdDayList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 상품 1주일 */
    List<DefaultMap<String>> getSaleProdWeekList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 상품 1개월 */
    List<DefaultMap<String>> getSaleProdMonthList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 가맹점 오늘 */
    List<DefaultMap<String>> getSaleStoreDayList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 가맹점 1주일 */
    List<DefaultMap<String>> getSaleStoreWeekList(SessionInfoVO sessionInfoVO);

    /** 매출 상위 가맹점 1개월 */
    List<DefaultMap<String>> getSaleStoreMonthList(SessionInfoVO sessionInfoVO);

    /** 오늘의 매출건수 */
    List<DefaultMap<String>> getDaySaleCntList(SessionInfoVO sessionInfoVO);

    /** 오늘의 매출금액 */
    List<DefaultMap<String>> getDaySaleAmtList(SessionInfoVO sessionInfoVO);
}
