package kr.co.solbipos.sale.anals.saletrnsitn.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnDatesVO;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnVO;

/**
 * @Class Name : SaleTrnsitnMapper.java
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
@Mapper
@Repository
public interface SaleTrnsitnMapper {

	/**
	 * 매출추이분석 목록조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  조현수
	 * @since   2020. 01. 14
	*/
    List<DefaultMap<String>> 	getSaletrnsitnList(SaleTrnsitnVO saletrnsitnVO);


    
	/**
	 * 특정일 기준 이전일자들 조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @return 	SaleTrnsitnDateVO
	 * @author  조현수
	 * @since   2020. 01. 15
	*/
  //DefaultMap<String>			getPreviouseDatesInfo(SaleTrnsitnVO saletrnsitnVO);
    SaleTrnsitnDatesVO			getPreviouseDatesInfo(SaleTrnsitnVO saletrnsitnVO);
    
    
    
    
//	/**
//	 * 특정일 기준 이전일자들 조회 (매출관리 > 매출분석 > 매출추이분석)
//     * @param 	saleTrnsitnVO
//     * @return 	SaleTrnsitnDateVO
//	 * @author  조현수
//	 * @since   2020. 01. 15
//	*/
//    SaleTrnsitnDatesVO			getPreviouseDatesInfo(SaleTrnsitnVO saletrnsitnVO);
    
}
