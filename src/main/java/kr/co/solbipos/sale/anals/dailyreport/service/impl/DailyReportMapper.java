package kr.co.solbipos.sale.anals.dailyreport.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.dailyreport.service.DailyReportVO;

/**
 * @Class Name : DailyReportMapper.java
 * @Description : 매출관리 > 매출분석 > 영업일보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.28  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP
 * @since 2020. 01.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface DailyReportMapper {

	/**
	 * 매출종합 목록조회 (매출관리 > 매출분석 > 영업일보)
     * @param 	dailyReportVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  조현수
	 * @since   2020.01.28
	*/
    List<DefaultMap<String>> 	getSaleList			(DailyReportVO dailyReportVO);  //매출종합
    List<DefaultMap<String>> 	getPayList			(DailyReportVO dailyReportVO);  //결제수단
    List<DefaultMap<String>> 	getPosList			(DailyReportVO dailyReportVO);  //비매출종합
    List<DefaultMap<String>> 	getNsaleList		(DailyReportVO dailyReportVO);  //비매출결제수단
    List<DefaultMap<String>> 	getNpayList			(DailyReportVO dailyReportVO);  //포스정산
    List<DefaultMap<String>> 	getEmpList			(DailyReportVO dailyReportVO);  //판매원별 매출
    List<DefaultMap<String>> 	getDcList			(DailyReportVO dailyReportVO);  //할인내역
    List<DefaultMap<String>> 	getDcdtlList		(DailyReportVO dailyReportVO);  //할인상세내역
    List<DefaultMap<String>> 	getGiftList			(DailyReportVO dailyReportVO);  //상품권 판매 및 회수내역
//    List<DefaultMap<String>> 	getOrderList		(DailyReportVO dailyReportVO);  //수발주내역
    List<DefaultMap<String>> 	getLv1List			(DailyReportVO dailyReportVO);  //대분류별 매출
    List<DefaultMap<String>> 	getLv2List			(DailyReportVO dailyReportVO);  //중분류별 매출
    List<DefaultMap<String>> 	getLv3List			(DailyReportVO dailyReportVO);  //소분류별 매출
    List<DefaultMap<String>> 	getProdList			(DailyReportVO dailyReportVO);  //상품별 매출
 //   List<DefaultMap<String>> 	getComptList		(DailyReportVO dailyReportVO);  //경쟁사매출
    List<DefaultMap<String>> 	getApprList			(DailyReportVO dailyReportVO);  //승인현황
    List<DefaultMap<String>> 	getMembrList		(DailyReportVO dailyReportVO);  //회원
    List<DefaultMap<String>> 	getWorkList			(DailyReportVO dailyReportVO);  //근태관리

    List<DefaultMap<String>> 	getPayLineList		(DailyReportVO dailyReportVO);  //결재라인
    List<DefaultMap<String>> 	getCfgList			(DailyReportVO dailyReportVO);  //영업일보 구성


    int 						mergeConfig			(DailyReportVO dailyReportVO);	//영업일보 구성 저장


    int 						mergePayLine		(DailyReportVO dailyReportVO);	//결재라인 저장	-> 아래 3개를 사용하는 것으로 변경

    int 						insertPayLine		(DailyReportVO dailyReportVO);	//결재라인 생성
    int 						updatePayLine		(DailyReportVO dailyReportVO);	//결재라인 수정
    int 						deletePayLine		(DailyReportVO dailyReportVO);	//결재라인 삭제

}
