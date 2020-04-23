package kr.co.solbipos.sale.anals.dailyreportnew.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewService;
import kr.co.solbipos.sale.anals.dailyreportnew.service.DailyReportNewVO;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DailyReportServiceImpl.java
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
@Service("dailyReportNewService")
public class DailyReportNewServiceImpl implements DailyReportNewService {

    private final Logger 				LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService 		messageService;
    private final DailyReportNewMapper	dailyReportNewMapper;
    private final CmmEnvUtil 			cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DailyReportNewServiceImpl(DailyReportNewMapper dailyReportNewMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.dailyReportNewMapper 	= dailyReportNewMapper;
        this.cmmEnvUtil 			= cmmEnvUtil;
        this.messageService 		= messageService;
    }



    /**
	 * 영업일보 조회 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @Override
    public Map<String, Object> getReportList(@RequestBody DailyReportNewVO dailyReportNewVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        dailyReportNewVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        dailyReportNewVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        dailyReportNewVO.setStoreCd		(sessionInfoVO.getStoreCd()			);	//매장코드

        LOGGER.debug("### OrgnFg        : " + dailyReportNewVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + dailyReportNewVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + dailyReportNewVO.getStoreCd		());
        LOGGER.debug("### searchStoreCd : " + dailyReportNewVO.getSearchStoreCd	());
        LOGGER.debug("### startDate     : " + dailyReportNewVO.getStartDate		());
    	LOGGER.debug("### endDate       : " + dailyReportNewVO.getEndDate		());

    	Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

    	rtnMap.put("sl",		dailyReportNewMapper.getSaleList   	(dailyReportNewVO));   //매출종합
    	rtnMap.put("pay",       dailyReportNewMapper.getPayList		(dailyReportNewVO));   //결제수단
    	rtnMap.put("pos",       dailyReportNewMapper.getPosList		(dailyReportNewVO));   //비매출종합
    	rtnMap.put("nsl",       dailyReportNewMapper.getNsaleList  	(dailyReportNewVO));   //비매출결제수단
    	rtnMap.put("npay",      dailyReportNewMapper.getNpayList	(dailyReportNewVO));   //포스정산
    	rtnMap.put("emp",       dailyReportNewMapper.getEmpList		(dailyReportNewVO));   //판매원별 매출
    	rtnMap.put("dc",        dailyReportNewMapper.getDcList		(dailyReportNewVO));   //할인내역
    	rtnMap.put("dcdtl",     dailyReportNewMapper.getDcdtlList	(dailyReportNewVO));   //할인상세내역
    	rtnMap.put("gift",    	dailyReportNewMapper.getGiftList	(dailyReportNewVO));   //상품권 판매 및 회수내역
      //rtnMap.put("order",		dailyReportNewMapper.getOrderList	(dailyReportNewVO));   //수발주내역
    	rtnMap.put("lv1",       dailyReportNewMapper.getLv1List		(dailyReportNewVO));   //대분류별 매출
    	rtnMap.put("lv2",       dailyReportNewMapper.getLv2List		(dailyReportNewVO));   //중분류별 매출
    	rtnMap.put("lv3",       dailyReportNewMapper.getLv3List		(dailyReportNewVO));   //소분류별 매출
    	rtnMap.put("prod",      dailyReportNewMapper.getProdList   	(dailyReportNewVO));   //상품별 매출
      //rtnMap.put("compt",   	dailyReportNewMapper.getComptList	(dailyReportNewVO));   //경쟁사매출
    	rtnMap.put("appr",      dailyReportNewMapper.getApprList   	(dailyReportNewVO));   //승인현황
        rtnMap.put("membr",     dailyReportNewMapper.getMembrList  	(dailyReportNewVO));   //회원
    	rtnMap.put("work",      dailyReportNewMapper.getWorkList   	(dailyReportNewVO));   //근태관리

    	rtnMap.put("payline",	dailyReportNewMapper.getPayLineList	(dailyReportNewVO));   //결재라인
    	rtnMap.put("cfg",      	dailyReportNewMapper.getCfgList   	(dailyReportNewVO));   //영업일보 구성

        return rtnMap;
    }



    /**
	 * 영업일보 구성 조회 (결재라인 + 영업일보 구성) (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @Override
    public Map<String, Object> getConfigList(@RequestBody DailyReportNewVO dailyReportNewVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        dailyReportNewVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        dailyReportNewVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        dailyReportNewVO.setStoreCd		(sessionInfoVO.getStoreCd()			);	//매장코드

        LOGGER.debug("### OrgnFg        : " + dailyReportNewVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + dailyReportNewVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + dailyReportNewVO.getStoreCd		());
        LOGGER.debug("### searchStoreCd : " + dailyReportNewVO.getSearchStoreCd	());

    	Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

    	rtnMap.put("payline",	dailyReportNewMapper.getPayLineList	(dailyReportNewVO));   //결재라인
    	rtnMap.put("cfg",      	dailyReportNewMapper.getCfgList   	(dailyReportNewVO));   //영업일보 구성

        return rtnMap;
    }




    /**
	 * 영업일보 구성 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVOs
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @Override
    public int saveConfigList(DailyReportNewVO[] dailyReportNewVOs, SessionInfoVO sessionInfoVO) {
        int 	result 		= 0;
        String 	currentDt 	= currentDateTimeString();

        for(DailyReportNewVO dailyReportNewVO : dailyReportNewVOs){
        	/*
        	dailyReportNewVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            	dailyReportNewVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            	dailyReportNewVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            */
        	dailyReportNewVO.setOrgnFg	(sessionInfoVO.getOrgnFg().getCode());	//소속구분 (M:시스템, A:대리점, H:본사, S:매장,가맹점)
            dailyReportNewVO.setRegDt	(currentDt							);
            dailyReportNewVO.setRegId	(sessionInfoVO.getUserId()			);
            dailyReportNewVO.setModDt	(currentDt							);
            dailyReportNewVO.setModId	(sessionInfoVO.getUserId()			);
            LOGGER.debug("### dailyReportNewVO orgnFg   : " + dailyReportNewVO.getOrgnFg());

            result += dailyReportNewMapper.mergeConfig(dailyReportNewVO);
        }

        if ( result == dailyReportNewVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));	//cmm.saveFail=저장 중 에러가 발생 하였습니다.
        }
    }



    /**
	 * 결재라인 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportNewVOs
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @Override
    public int savePayLineList(DailyReportNewVO[] dailyReportNewVOs, SessionInfoVO sessionInfoVO) {
        int 	result 		= 0;
        String 	currentDt 	= currentDateTimeString();
        String 	cfgStatus 	= "";

        for(DailyReportNewVO dailyReportNewVO : dailyReportNewVOs){
        	/*
        	dailyReportNewVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            	dailyReportNewVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            	dailyReportNewVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            */
        	dailyReportNewVO.setOrgnFg	(sessionInfoVO.getOrgnFg().getCode());	//소속구분 (M:시스템, A:대리점, H:본사, S:매장,가맹점)
            dailyReportNewVO.setRegDt	(currentDt							);
            dailyReportNewVO.setRegId	(sessionInfoVO.getUserId()			);
            dailyReportNewVO.setModDt	(currentDt							);
            dailyReportNewVO.setModId	(sessionInfoVO.getUserId()			);

            //result += dailyReportNewMapper.mergePayLineList(dailyReportNewVO);
            /*
            LOGGER.debug("### dailyReportNewVO: " + dailyReportNewVO);
            LOGGER.debug("### dailyReportNewVO: " + dailyReportNewVO.getStatus());

            if 		( dailyReportNewVO.getStatus() == GridDataFg.INSERT ) {	result += dailyReportNewMapper.insertPayLine(dailyReportNewVO);	}	//추가
            else if ( dailyReportNewVO.getStatus() == GridDataFg.UPDATE ) {	result += dailyReportNewMapper.updatePayLine(dailyReportNewVO);	}	//수정
            else if ( dailyReportNewVO.getStatus() == GridDataFg.DELETE ) {	result += dailyReportNewMapper.deletePayLine(dailyReportNewVO);	}	//삭제
            */
            cfgStatus = StringUtils.defaultIfEmpty(dailyReportNewVO.getCfgStatus(), "");
            LOGGER.debug("### dailyReportNewVO          : " + dailyReportNewVO);
            LOGGER.debug("### dailyReportNewVO cfgStatus: " + cfgStatus);            
            LOGGER.debug("### dailyReportNewVO orgnFg   : " + dailyReportNewVO.getOrgnFg());

            if 		( cfgStatus.equals(GridDataFg.INSERT.getCode()) ) 	{	result += dailyReportNewMapper.insertPayLine(dailyReportNewVO);	}	//추가
            else if ( cfgStatus.equals(GridDataFg.DELETE.getCode()) ) 	{	result += dailyReportNewMapper.deletePayLine(dailyReportNewVO);	}	//삭제
            else														{	result += dailyReportNewMapper.updatePayLine(dailyReportNewVO);	}	//수정

        }

        if ( result == dailyReportNewVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));	//cmm.saveFail=저장 중 에러가 발생 하였습니다.
        }
    }



}
