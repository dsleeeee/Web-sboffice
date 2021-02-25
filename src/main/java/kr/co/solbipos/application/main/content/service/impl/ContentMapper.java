package kr.co.solbipos.application.main.content.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Class Name : ContentMapper.java
* @Description : 어플리케이션 > 메인 > 내용
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
@Mapper
@Repository
public interface ContentMapper {

    /** 관리자 - 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getStoreCntList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getStoreCntAgencyList(SessionInfoVO sessionInfoVO);

    /** 본사 - 총 매장수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getStoreCntHqList(SessionInfoVO sessionInfoVO);


    /** 관리자 - 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getPosCntList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getPosCntAgencyList(SessionInfoVO sessionInfoVO);

    /** 본사 - 총 포스수(전체, 오픈, 폐점, 중지, 데모) */
    List<DefaultMap<String>> getPosCntHqList(SessionInfoVO sessionInfoVO);


    /** 관리자 - 주간매출(매장수/포스수) */
    List<DefaultMap<String>> getWeekSaleList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 주간매출(매장수/포스수) */
    List<DefaultMap<String>> getWeekSaleAgencyList(SessionInfoVO sessionInfoVO);


    /** 관리자 - 공지사항 */
    List<DefaultMap<String>> getNoticeList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 공지사항 */
    List<DefaultMap<String>> getNoticeAgencyList(SessionInfoVO sessionInfoVO);

    /** 본사 - 공지사항 */
    List<DefaultMap<String>> getNoticeHqList(SessionInfoVO sessionInfoVO);

    /** 매장 - 공지사항 */
    List<DefaultMap<String>> getNoticeStoreList(SessionInfoVO sessionInfoVO);


    /** 관리자 - 주간 POS 설치현황(신규설치/재설치) */
    List<DefaultMap<String>> getWeekPosInstList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 주간 POS 설치현황(신규설치/재설치) */
    List<DefaultMap<String>> getWeekPosInstAgencyList(SessionInfoVO sessionInfoVO);


    /** 관리자 - 주간 POS 설치 상위 대리점 */
    List<DefaultMap<String>> getWeekPosInstTopList(SessionInfoVO sessionInfoVO);

    /** 총판/대리점 - 주간 매출 상위 가맹점 */
    List<DefaultMap<String>> getWeekSaleAgencyTopList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출현황 일별(1주) */
    List<DefaultMap<String>> getSaleWeekList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출현황 일별(1주) */
    List<DefaultMap<String>> getSaleWeekStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출현황 요일별(1개월) */
    List<DefaultMap<String>> getSaleMonthList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출현황 요일별(1개월) */
    List<DefaultMap<String>> getSaleMonthStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출현황 월별(1년) */
    List<DefaultMap<String>> getSaleYearList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출현황 월별(1년) */
    List<DefaultMap<String>> getSaleYearStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출 상위 상품 오늘 */
    List<DefaultMap<String>> getSaleProdDayList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출 상위 상품 오늘 */
    List<DefaultMap<String>> getSaleProdDayStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출 상위 상품 1주일 */
    List<DefaultMap<String>> getSaleProdWeekList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출 상위 상품 1주일 */
    List<DefaultMap<String>> getSaleProdWeekStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출 상위 상품 1개월 */
    List<DefaultMap<String>> getSaleProdMonthList(SessionInfoVO sessionInfoVO);

    /** 매장 - 매출 상위 상품 1개월 */
    List<DefaultMap<String>> getSaleProdMonthStoreList(SessionInfoVO sessionInfoVO);


    /** 본사 - 매출 상위 가맹점 오늘 */
    List<DefaultMap<String>> getSaleStoreDayList(SessionInfoVO sessionInfoVO);

    /** 본사 - 매출 상위 가맹점 1주일 */
    List<DefaultMap<String>> getSaleStoreWeekList(SessionInfoVO sessionInfoVO);

    /** 본사 - 매출 상위 가맹점 1개월 */
    List<DefaultMap<String>> getSaleStoreMonthList(SessionInfoVO sessionInfoVO);


    /** 매장 - 오늘의 매출건수 */
    List<DefaultMap<String>> getDaySaleCntList(SessionInfoVO sessionInfoVO);

    /** 매장 - 오늘의 매출금액 */
    List<DefaultMap<String>> getDaySaleAmtList(SessionInfoVO sessionInfoVO);
}