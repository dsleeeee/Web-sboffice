package kr.co.solbipos.sale.anals.saletrnsitn.service.impl;

import java.util.List;

import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnDatesVO;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnService;
import kr.co.solbipos.sale.anals.saletrnsitn.service.SaleTrnsitnVO;

/**
 * @Class Name : SaleTrnsitnServiceImpl.java
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
@Service("saleTrnsitnService")
public class SaleTrnsitnServiceImpl implements SaleTrnsitnService {

    private final Logger 			LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService 	messageService;
    private final SaleTrnsitnMapper saletrnsitnMapper;
    private final CmmEnvUtil 		cmmEnvUtil;
	private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public SaleTrnsitnServiceImpl(SaleTrnsitnMapper saletrnsitnMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, PopupMapper popupMapper) {
        this.saletrnsitnMapper 	= saletrnsitnMapper;
        this.cmmEnvUtil 		= cmmEnvUtil;
        this.messageService 	= messageService;
		this.popupMapper = popupMapper;
	}


    
    /**
	 * 매출추이분석 목록조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  조현수
	 * @since   2020. 01. 14
	*/
    @Override
    public List<DefaultMap<String>> getSaletrnsitnList(@RequestBody SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        saleTrnsitnVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        saleTrnsitnVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
		saleTrnsitnVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
			// 매장 array 값 세팅
			if(!StringUtil.getOrBlank(saleTrnsitnVO.getStoreCds()).equals("")) {
				StoreVO storeVO = new StoreVO();
				storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleTrnsitnVO.getStoreCds(), 3900));
				saleTrnsitnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
			}
		} else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
			saleTrnsitnVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드
		}

    	LOGGER.debug("### saleTrnsitnVO: " + saleTrnsitnVO);
    	LOGGER.debug("### baseDate     : " + saleTrnsitnVO.getBaseDate());
    	LOGGER.debug("### prodCd       : " + saleTrnsitnVO.getProdCd());
    	LOGGER.debug("### prodNm       : " + saleTrnsitnVO.getProdNm());
    	
		/* DateUtil에 날짜 기능이 있으나 Calendar 대신 Date를 사용하고 있음. -> 'new Date()'를 사용하기에  Calendar로의 변경 고려.
		SimpleDateFormat 	formatter 	= new SimpleDateFormat ("yyyyMMdd");
		Calendar 			cal			= Calendar.getInstance();
    	String 				today		= formatter.format(cal.getTime());
		*/    	
		saleTrnsitnVO.setBaseDate( DateUtil.currentDateString() );	//DEFAULT_YMD_FORMAT = "yyyyMMdd";

/* TODO Test용 - START */
		String	endDate = StringUtils.defaultIfEmpty(saleTrnsitnVO.getEndDate(), "");
		if(!"".contentEquals(endDate)) {
	    	saleTrnsitnVO.setBaseDate( saleTrnsitnVO.getEndDate() );
	    	String baseDate = StringUtils.defaultIfEmpty(saleTrnsitnVO.getBaseDate(), "");
	
	    	LOGGER.debug("### startDate    : " + saleTrnsitnVO.getStartDate());
	    	LOGGER.debug("### baseDate     : " + baseDate);
	    	if("".equals(baseDate) ){
	    		saleTrnsitnVO.setBaseDate( DateUtil.currentDateString() );
	    	}
		}
/* TODO Test용 - END */

    	SaleTrnsitnDatesVO datesVo = saletrnsitnMapper.getPreviouseDatesInfo(saleTrnsitnVO);
    	BeanUtils.copyProperties(datesVo, saleTrnsitnVO);

        return saletrnsitnMapper.getSaletrnsitnList(saleTrnsitnVO);
    }

    
    /**
	 * 매출추이분석(엑셀) 목록조회 (매출관리 > 매출분석 > 매출추이분석)
     * @param 	saleTrnsitnVO
     * @param 	sessionInfoVO
     * @return 	java.util.List<DefaultMap<String>> - XML_String
	 * @author  박지선
	 * @since   2020. 04. 22
	*/
    @Override
    public List<DefaultMap<String>> getSaletrnsitnExcelList(@RequestBody SaleTrnsitnVO saleTrnsitnVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        saleTrnsitnVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        saleTrnsitnVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
		saleTrnsitnVO.setEmpNo(sessionInfoVO.getEmpNo());

		if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
			// 매장(멀티) 선택
			if(!StringUtil.getOrBlank(saleTrnsitnVO.getStoreCds()).equals("")) {
				StoreVO storeVO = new StoreVO();
				storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleTrnsitnVO.getStoreCds(), 3900));
				saleTrnsitnVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
			}
		} else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
			saleTrnsitnVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드
		}

		System.out.println(saleTrnsitnVO.getDiffDay()+"데이");
    	LOGGER.debug("### saleTrnsitnVO: " + saleTrnsitnVO);
    	LOGGER.debug("### baseDate     : " + saleTrnsitnVO.getBaseDate());
    	LOGGER.debug("### prodCd       : " + saleTrnsitnVO.getProdCd());
    	LOGGER.debug("### prodNm       : " + saleTrnsitnVO.getProdNm());
    	
		/* DateUtil에 날짜 기능이 있으나 Calendar 대신 Date를 사용하고 있음. -> 'new Date()'를 사용하기에  Calendar로의 변경 고려.
		SimpleDateFormat 	formatter 	= new SimpleDateFormat ("yyyyMMdd");
		Calendar 			cal			= Calendar.getInstance();
    	String 				today		= formatter.format(cal.getTime());
		*/    	
		saleTrnsitnVO.setBaseDate( DateUtil.currentDateString() );	//DEFAULT_YMD_FORMAT = "yyyyMMdd";

/* TODO Test용 - START */
		String	endDate = StringUtils.defaultIfEmpty(saleTrnsitnVO.getEndDate(), "");
		if(!"".contentEquals(endDate)) {
	    	saleTrnsitnVO.setBaseDate( saleTrnsitnVO.getEndDate() );
	    	String baseDate = StringUtils.defaultIfEmpty(saleTrnsitnVO.getBaseDate(), "");
	
	    	LOGGER.debug("### startDate    : " + saleTrnsitnVO.getStartDate());
	    	LOGGER.debug("### baseDate     : " + baseDate);
	    	if("".equals(baseDate) ){
	    		saleTrnsitnVO.setBaseDate( DateUtil.currentDateString() );
	    	}
		}
/* TODO Test용 - END */

    	SaleTrnsitnDatesVO datesVo = saletrnsitnMapper.getPreviouseDatesInfo(saleTrnsitnVO);
    	BeanUtils.copyProperties(datesVo, saleTrnsitnVO);
    	
        return saletrnsitnMapper.getSaletrnsitnExcelList(saleTrnsitnVO);
    }
    
}
