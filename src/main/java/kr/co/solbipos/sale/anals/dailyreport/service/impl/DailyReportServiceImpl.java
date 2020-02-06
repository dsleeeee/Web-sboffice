package kr.co.solbipos.sale.anals.dailyreport.service.impl;

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
import kr.co.solbipos.sale.anals.dailyreport.service.DailyReportService;
import kr.co.solbipos.sale.anals.dailyreport.service.DailyReportVO;
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
@Service("dailyReportService")
public class DailyReportServiceImpl implements DailyReportService {

    private final Logger 			LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService 	messageService;
    private final DailyReportMapper dailyReportMapper;
    private final CmmEnvUtil 		cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public DailyReportServiceImpl(DailyReportMapper dailyReportMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.dailyReportMapper 	= dailyReportMapper;
        this.cmmEnvUtil 		= cmmEnvUtil;
        this.messageService 	= messageService;
    }



    /**
	 * 영업일보 조회 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @Override
    public Map<String, Object> getReportList(@RequestBody DailyReportVO dailyReportVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        dailyReportVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        dailyReportVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        dailyReportVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드

        LOGGER.debug("### OrgnFg        : " + dailyReportVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + dailyReportVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + dailyReportVO.getStoreCd		());
        LOGGER.debug("### searchStoreCd : " + dailyReportVO.getSearchStoreCd());
        LOGGER.debug("### startDate     : " + dailyReportVO.getStartDate	());
    	LOGGER.debug("### endDate       : " + dailyReportVO.getEndDate		());

    	Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

    	rtnMap.put("sl",		dailyReportMapper.getSaleList   (dailyReportVO));   //매출종합
    	rtnMap.put("pay",       dailyReportMapper.getPayList	(dailyReportVO));   //결제수단
    	rtnMap.put("pos",       dailyReportMapper.getPosList	(dailyReportVO));   //비매출종합
    	rtnMap.put("nsl",       dailyReportMapper.getNsaleList  (dailyReportVO));   //비매출결제수단
    	rtnMap.put("npay",      dailyReportMapper.getNpayList	(dailyReportVO));   //포스정산
    	rtnMap.put("emp",       dailyReportMapper.getEmpList	(dailyReportVO));   //판매원별 매출
    	rtnMap.put("dc",        dailyReportMapper.getDcList		(dailyReportVO));   //할인내역
    	rtnMap.put("dcdtl",     dailyReportMapper.getDcdtlList	(dailyReportVO));   //할인상세내역
    	rtnMap.put("gift",    	dailyReportMapper.getGiftList	(dailyReportVO));   //상품권 판매 및 회수내역
      //rtnMap.put("order",		dailyReportMapper.getOrderList	(dailyReportVO));   //수발주내역
    	rtnMap.put("lv1",       dailyReportMapper.getLv1List	(dailyReportVO));   //대분류별 매출
    	rtnMap.put("lv2",       dailyReportMapper.getLv2List	(dailyReportVO));   //중분류별 매출
    	rtnMap.put("lv3",       dailyReportMapper.getLv3List	(dailyReportVO));   //소분류별 매출
    	rtnMap.put("prod",      dailyReportMapper.getProdList   (dailyReportVO));   //상품별 매출
      //rtnMap.put("compt",   	dailyReportMapper.getComptList	(dailyReportVO));   //경쟁사매출
    	rtnMap.put("appr",      dailyReportMapper.getApprList   (dailyReportVO));   //승인현황
        rtnMap.put("membr",     dailyReportMapper.getMembrList  (dailyReportVO));   //회원
    	rtnMap.put("work",      dailyReportMapper.getWorkList   (dailyReportVO));   //근태관리

    	rtnMap.put("payline",	dailyReportMapper.getPayLineList(dailyReportVO));   //결재라인
    	rtnMap.put("cfg",      	dailyReportMapper.getCfgList   	(dailyReportVO));   //영업일보 구성

        return rtnMap;
    }



    /**
	 * 영업일보 구성 조회 (결재라인 + 영업일보 구성) (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportVO
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.01.28
	*/
    @Override
    public Map<String, Object> getConfigList(@RequestBody DailyReportVO dailyReportVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        dailyReportVO.setOrgnFg		(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        dailyReportVO.setHqOfficeCd	(sessionInfoVO.getHqOfficeCd()		);	//본사코드
        dailyReportVO.setStoreCd	(sessionInfoVO.getStoreCd()			);	//매장코드

        LOGGER.debug("### OrgnFg        : " + dailyReportVO.getOrgnFg		());
        LOGGER.debug("### hqOfficeCd    : " + dailyReportVO.getHqOfficeCd	());
        LOGGER.debug("### storeCd       : " + dailyReportVO.getStoreCd		());
        LOGGER.debug("### searchStoreCd : " + dailyReportVO.getSearchStoreCd());

    	Map<String,Object> 	rtnMap 	= new HashMap<String, Object>();

    	rtnMap.put("payline",	dailyReportMapper.getPayLineList(dailyReportVO));   //결재라인
    	rtnMap.put("cfg",      	dailyReportMapper.getCfgList   	(dailyReportVO));   //영업일보 구성

        return rtnMap;
    }




    /**
	 * 영업일보 구성 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportVOs
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @Override
    public int saveConfigList(DailyReportVO[] dailyReportVOs, SessionInfoVO sessionInfoVO) {
        int 	result 		= 0;
        String 	currentDt 	= currentDateTimeString();

        for(DailyReportVO dailyReportVO : dailyReportVOs){
        	/*
        	dailyReportVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            	dailyReportVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            	dailyReportVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            */
            dailyReportVO.setRegDt(currentDt				);
            dailyReportVO.setRegId(sessionInfoVO.getUserId());
            dailyReportVO.setModDt(currentDt				);
            dailyReportVO.setModId(sessionInfoVO.getUserId());

            result += dailyReportMapper.mergeConfig(dailyReportVO);
        }

        if ( result == dailyReportVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));	//cmm.saveFail=저장 중 에러가 발생 하였습니다.
        }
    }



    /**
	 * 결재라인 저장 (매출관리 > 매출분석 > 영업일보)
	 * @param   dailyReportVOs
	 * @param   request
	 * @return  kr.co.common.data.structure.Result
	 * @author  조현수
	 * @since   2020.02.05
	*/
    @Override
    public int savePayLineList(DailyReportVO[] dailyReportVOs, SessionInfoVO sessionInfoVO) {
        int 	result 		= 0;
        String 	currentDt 	= currentDateTimeString();
        String 	cfgStatus 	= "";

        for(DailyReportVO dailyReportVO : dailyReportVOs){
        	/*
        	dailyReportVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            	dailyReportVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            	dailyReportVO.setStoreCd(sessionInfoVO.getOrgnCd());
            }
            */
            dailyReportVO.setRegDt(currentDt				);
            dailyReportVO.setRegId(sessionInfoVO.getUserId());
            dailyReportVO.setModDt(currentDt				);
            dailyReportVO.setModId(sessionInfoVO.getUserId());

            //result += dailyReportMapper.mergePayLineList(dailyReportVO);
            /*
            LOGGER.debug("### dailyReportVO: " + dailyReportVO);
            LOGGER.debug("### dailyReportVO: " + dailyReportVO.getStatus());

            if 		( dailyReportVO.getStatus() == GridDataFg.INSERT ) {	result += dailyReportMapper.insertPayLine(dailyReportVO);	}	//추가
            else if ( dailyReportVO.getStatus() == GridDataFg.UPDATE ) {	result += dailyReportMapper.updatePayLine(dailyReportVO);	}	//수정
            else if ( dailyReportVO.getStatus() == GridDataFg.DELETE ) {	result += dailyReportMapper.deletePayLine(dailyReportVO);	}	//삭제
            */
            cfgStatus = StringUtils.defaultIfEmpty(dailyReportVO.getCfgStatus(), "");
            LOGGER.debug("### dailyReportVO          : " + dailyReportVO);
            LOGGER.debug("### dailyReportVO cfgStatus: " + cfgStatus);

            if 		( cfgStatus.equals(GridDataFg.INSERT.getCode()) ) 	{	result += dailyReportMapper.insertPayLine(dailyReportVO);	}	//추가
            else if ( cfgStatus.equals(GridDataFg.DELETE.getCode()) ) 	{	result += dailyReportMapper.deletePayLine(dailyReportVO);	}	//삭제
            else														{	result += dailyReportMapper.updatePayLine(dailyReportVO);	}	//수정

        }

        if ( result == dailyReportVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));	//cmm.saveFail=저장 중 에러가 발생 하였습니다.
        }
    }



}
