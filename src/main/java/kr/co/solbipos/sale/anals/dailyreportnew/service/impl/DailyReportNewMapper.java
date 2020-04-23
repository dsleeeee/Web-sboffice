package kr.co.solbipos.sale.anals.dailyreportnew.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewVO;

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
public interface DailyReportNewMapper {

	/**
	 * 매출종합 목록조회 (매출관리 > 매출분석 > 영업일보)
     * @param 	dailyReportNewVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  조현수
	 * @since   2020.01.28
	*/
    List<DefaultMap<String>> 	getSaleList			(DailyReportNewVO dailyReportNewVO);  //매출종합
    List<DefaultMap<String>> 	getPayList			(DailyReportNewVO dailyReportNewVO);  //결제수단
    List<DefaultMap<String>> 	getPosList			(DailyReportNewVO dailyReportNewVO);  //비매출종합
    List<DefaultMap<String>> 	getNsaleList		(DailyReportNewVO dailyReportNewVO);  //비매출결제수단
    List<DefaultMap<String>> 	getNpayList			(DailyReportNewVO dailyReportNewVO);  //포스정산
    List<DefaultMap<String>> 	getEmpList			(DailyReportNewVO dailyReportNewVO);  //판매원별 매출
    List<DefaultMap<String>> 	getDcList			(DailyReportNewVO dailyReportNewVO);  //할인내역
    List<DefaultMap<String>> 	getDcdtlList		(DailyReportNewVO dailyReportNewVO);  //할인상세내역
    List<DefaultMap<String>> 	getGiftList			(DailyReportNewVO dailyReportNewVO);  //상품권 판매 및 회수내역
//    List<DefaultMap<String>> 	getOrderList		(DailyReportNewVO dailyReportNewVO);  //수발주내역
    List<DefaultMap<String>> 	getLv1List			(DailyReportNewVO dailyReportNewVO);  //대분류별 매출
    List<DefaultMap<String>> 	getLv2List			(DailyReportNewVO dailyReportNewVO);  //중분류별 매출
    List<DefaultMap<String>> 	getLv3List			(DailyReportNewVO dailyReportNewVO);  //소분류별 매출
    List<DefaultMap<String>> 	getProdList			(DailyReportNewVO dailyReportNewVO);  //상품별 매출
 //   List<DefaultMap<String>> 	getComptList		(DailyReportNewVO dailyReportNewVO);  //경쟁사매출
    List<DefaultMap<String>> 	getApprList			(DailyReportNewVO dailyReportNewVO);  //승인현황
    List<DefaultMap<String>> 	getMembrList		(DailyReportNewVO dailyReportNewVO);  //회원
    List<DefaultMap<String>> 	getWorkList			(DailyReportNewVO dailyReportNewVO);  //근태관리

    List<DefaultMap<String>> 	getPayLineList		(DailyReportNewVO dailyReportNewVO);  //결재라인
    List<DefaultMap<String>> 	getCfgList			(DailyReportNewVO dailyReportNewVO);  //영업일보 구성


    int 						mergeConfig			(DailyReportNewVO dailyReportNewVO);	//영업일보 구성 저장


    int 						mergePayLine		(DailyReportNewVO dailyReportNewVO);	//결재라인 저장	-> 아래 3개를 사용하는 것으로 변경

    int 						insertPayLine		(DailyReportNewVO dailyReportNewVO);	//결재라인 생성
    int 						updatePayLine		(DailyReportNewVO dailyReportNewVO);	//결재라인 수정
    int 						deletePayLine		(DailyReportNewVO dailyReportNewVO);	//결재라인 삭제

}
