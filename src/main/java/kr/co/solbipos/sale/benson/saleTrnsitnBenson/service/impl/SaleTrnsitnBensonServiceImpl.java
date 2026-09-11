package kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonDatesVO;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonService;
import kr.co.solbipos.sale.benson.saleTrnsitnBenson.service.SaleTrnsitnBensonVO;

/**
 * @Class Name : SaleTrnsitnBensonServiceImpl.java
 * @Description : 벤슨 > 매출분석 > 매출추이분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("saleTrnsitnBensonService")
public class SaleTrnsitnBensonServiceImpl implements SaleTrnsitnBensonService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final SaleTrnsitnBensonMapper saleTrnsitnBensonMapper;
    private final CmmEnvUtil cmmEnvUtil;
    private final PopupMapper popupMapper;

    /** Constructor Injection */
    @Autowired
    public SaleTrnsitnBensonServiceImpl(SaleTrnsitnBensonMapper saleTrnsitnBensonMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService, PopupMapper popupMapper) {
        this.saleTrnsitnBensonMapper = saleTrnsitnBensonMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
        this.popupMapper = popupMapper;
    }

    /**
     * 매출추이분석 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @param   sessionInfoVO
     * @return  java.util.List<DefaultMap<String>> - XML_String
     * @author  김유승
     * @since   2026.09.10
    */
    @Override
    public List<DefaultMap<String>> getSaletrnsitnBensonList(@RequestBody SaleTrnsitnBensonVO saleTrnsitnBensonVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        saleTrnsitnBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        saleTrnsitnBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());	//본사코드
        saleTrnsitnBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            // 매장 array 값 세팅
            if(!StringUtil.getOrBlank(saleTrnsitnBensonVO.getStoreCds()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleTrnsitnBensonVO.getStoreCds(), 3900));
                saleTrnsitnBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            saleTrnsitnBensonVO.setStoreCd(sessionInfoVO.getStoreCd());	//매장코드
        }

        LOGGER.debug("### saleTrnsitnBensonVO: " + saleTrnsitnBensonVO);

        saleTrnsitnBensonVO.setBaseDate( DateUtil.currentDateString() );	//DEFAULT_YMD_FORMAT = "yyyyMMdd";

        String endDate = StringUtils.defaultIfEmpty(saleTrnsitnBensonVO.getEndDate(), "");
        if(!"".contentEquals(endDate)) {
            saleTrnsitnBensonVO.setBaseDate( saleTrnsitnBensonVO.getEndDate() );
            String baseDate = StringUtils.defaultIfEmpty(saleTrnsitnBensonVO.getBaseDate(), "");

            if("".equals(baseDate) ){
                saleTrnsitnBensonVO.setBaseDate( DateUtil.currentDateString() );
            }
        }

        SaleTrnsitnBensonDatesVO datesVo = saleTrnsitnBensonMapper.getPreviouseDatesInfo(saleTrnsitnBensonVO);
        BeanUtils.copyProperties(datesVo, saleTrnsitnBensonVO);

        return saleTrnsitnBensonMapper.getSaletrnsitnBensonList(saleTrnsitnBensonVO);
    }

    /**
     * 매출추이분석(엑셀) 목록조회 (벤슨 > 매출분석 > 매출추이분석)
     * @param   saleTrnsitnBensonVO
     * @param   sessionInfoVO
     * @return  java.util.List<DefaultMap<String>> - XML_String
     * @author  김유승
     * @since   2026.09.10
    */
    @Override
    public List<DefaultMap<String>> getSaletrnsitnBensonExcelList(@RequestBody SaleTrnsitnBensonVO saleTrnsitnBensonVO, SessionInfoVO sessionInfoVO) {
        //소속구분 설정
        saleTrnsitnBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());	//소속구분(M:시스템, A:대리점, H:본사, S:매장,가맹점
        saleTrnsitnBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());	//본사코드
        saleTrnsitnBensonVO.setEmpNo(sessionInfoVO.getEmpNo());

        if ( sessionInfoVO.getOrgnFg() == OrgnFg.HQ ) {
            // 매장(멀티) 선택
            if(!StringUtil.getOrBlank(saleTrnsitnBensonVO.getStoreCds()).equals("")) {
                StoreVO storeVO = new StoreVO();
                storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleTrnsitnBensonVO.getStoreCds(), 3900));
                saleTrnsitnBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        } else if ( sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            saleTrnsitnBensonVO.setStoreCd(sessionInfoVO.getStoreCd());	//매장코드
        }

        LOGGER.debug("### saleTrnsitnBensonVO: " + saleTrnsitnBensonVO);

        saleTrnsitnBensonVO.setBaseDate( DateUtil.currentDateString() );	//DEFAULT_YMD_FORMAT = "yyyyMMdd";

        String endDate = StringUtils.defaultIfEmpty(saleTrnsitnBensonVO.getEndDate(), "");
        if(!"".contentEquals(endDate)) {
            saleTrnsitnBensonVO.setBaseDate( saleTrnsitnBensonVO.getEndDate() );
            String baseDate = StringUtils.defaultIfEmpty(saleTrnsitnBensonVO.getBaseDate(), "");

            if("".equals(baseDate) ){
                saleTrnsitnBensonVO.setBaseDate( DateUtil.currentDateString() );
            }
        }

        SaleTrnsitnBensonDatesVO datesVo = saleTrnsitnBensonMapper.getPreviouseDatesInfo(saleTrnsitnBensonVO);
        BeanUtils.copyProperties(datesVo, saleTrnsitnBensonVO);

        return saleTrnsitnBensonMapper.getSaletrnsitnBensonExcelList(saleTrnsitnBensonVO);
    }

}
