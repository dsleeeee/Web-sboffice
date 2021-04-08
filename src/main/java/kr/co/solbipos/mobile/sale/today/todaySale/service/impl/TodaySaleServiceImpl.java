package kr.co.solbipos.mobile.sale.today.todaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.today.todaySale.service.TodaySaleService;
import kr.co.solbipos.mobile.sale.today.todaySale.service.TodaySaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : TodaySaleServiceImpl.java
 * @Description : (모바일) 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("todaySaleService")
@Transactional
public class TodaySaleServiceImpl implements TodaySaleService {
    private final TodaySaleMapper todaySaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TodaySaleServiceImpl(TodaySaleMapper todaySaleMapper) {
        this.todaySaleMapper = todaySaleMapper;
    }

    /** 당일매출종합 - 조회 */
    @Override
    public DefaultMap<String> getTodaySaleList(TodaySaleVO todaySaleVO, SessionInfoVO sessionInfoVO) {

        todaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todaySaleVO.getStoreCd()).equals("")) {
            todaySaleVO.setArrStoreCd(todaySaleVO.getStoreCd().split(","));
        }

        DefaultMap<String> resultMap = new DefaultMap<String>();

        resultMap = todaySaleMapper.getTodaySaleList(todaySaleVO);

        // 데이터가 없으면 0 셋팅
        if(ObjectUtils.isEmpty(resultMap)) {
            resultMap = todaySaleMapper.getTodaySaleNullList(todaySaleVO);
        }

        return resultMap;
    }

    /** 결제수단 조회 */
    @Override
    public List<DefaultMap<Object>> getTodaySalePayList(TodaySaleVO todaySaleVO, SessionInfoVO sessionInfoVO) {

        todaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todaySaleVO.getStoreCd()).equals("")) {
            todaySaleVO.setArrStoreCd(todaySaleVO.getStoreCd().split(","));
        }

        return todaySaleMapper.getTodaySalePayList(todaySaleVO);
    }

    /** 할인내역 조회 */
    @Override
    public List<DefaultMap<Object>> getTodaySaleDcList(TodaySaleVO todaySaleVO, SessionInfoVO sessionInfoVO) {

        todaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todaySaleVO.getStoreCd()).equals("")) {
            todaySaleVO.setArrStoreCd(todaySaleVO.getStoreCd().split(","));
        }

        return todaySaleMapper.getTodaySaleDcList(todaySaleVO);
    }

    /** 매장/배달/포장 조회 */
    @Override
    public List<DefaultMap<Object>> getTodaySaleDlvrList(TodaySaleVO todaySaleVO, SessionInfoVO sessionInfoVO) {

        todaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todaySaleVO.getStoreCd()).equals("")) {
            todaySaleVO.setArrStoreCd(todaySaleVO.getStoreCd().split(","));
        }

        return todaySaleMapper.getTodaySaleDlvrList(todaySaleVO);
    }

    /** 시간대별 조회 */
    @Override
    public List<DefaultMap<Object>> getTodaySaleTimeList(TodaySaleVO todaySaleVO, SessionInfoVO sessionInfoVO) {

        todaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(todaySaleVO.getStoreCd()).equals("")) {
            todaySaleVO.setArrStoreCd(todaySaleVO.getStoreCd().split(","));
        }

        return todaySaleMapper.getTodaySaleTimeList(todaySaleVO);
    }
}