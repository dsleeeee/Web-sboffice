package kr.co.solbipos.sale.anals.saletrnsitn.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : SaleTrnsitnService.java
 * @Description : 매출관리 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.14  조현수       최초생성
 * @ 2020.
 *
 * @author NHN한국사이버결제 KCP 
 * @since 2020. 01.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleTrnsitnService {

	/**
	 * 매출추이분석 목록조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  조현수
	 * @since   2020. 01. 14
	*/
    List<DefaultMap<String>> 	getSaletrnsitnList		(SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO);

	/**
	 * 매출추이분석(엑셀) 목록조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  박지선
	 * @since   2020. 04. 22
	*/
    List<DefaultMap<String>> 	getSaletrnsitnExcelList		(SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO);


//	/**
//	 * 매출추이분석 14일 날짜조회 (매출관리 > 매출분석 > 매출추이분석)
//	 * @param   saleTrnsitnVO
//	 * @param   request
//	 * @return  kr.co.common.data.structure.Result
//	 * @author  조현수
//	 * @since   2020. 01. 16
//	*/    
//  //DefaultMap<String> 			getPreviouseDatesInfo	(SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO);
//    SaleTrnsitnDatesVO			getPreviouseDatesInfo	(SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO);

}
