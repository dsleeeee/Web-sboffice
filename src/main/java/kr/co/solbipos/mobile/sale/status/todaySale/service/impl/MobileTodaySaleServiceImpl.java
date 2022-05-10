package kr.co.solbipos.mobile.sale.status.todaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.todaySale.service.MobileTodaySaleService;
import kr.co.solbipos.mobile.sale.status.todaySale.service.MobileTodaySaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileTodaySaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileTodaySaleService")
@Transactional
public class MobileTodaySaleServiceImpl implements  MobileTodaySaleService {
    private final  MobileTodaySaleMapper  mobileTodaySaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTodaySaleServiceImpl(MobileTodaySaleMapper mobileTodaySaleMapper) {
        this.mobileTodaySaleMapper = mobileTodaySaleMapper;
    }

    /** 당일매출종합 - 조회 */
    @Override
    public DefaultMap<String> getMobileTodaySaleTotalList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTodaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTodaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTodaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTodaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTodaySaleVO.setArrStoreCd(mobileTodaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTodaySaleMapper.getMobileTodaySaleTotalList(mobileTodaySaleVO);
    }

    /** 결제수단 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTodaySalePayList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTodaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTodaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTodaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTodaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTodaySaleVO.setArrStoreCd(mobileTodaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTodaySaleMapper.getMobileTodaySalePayList(mobileTodaySaleVO);
    }

    /** 할인내역 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTodaySaleDcList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTodaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTodaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTodaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTodaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTodaySaleVO.setArrStoreCd(mobileTodaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTodaySaleMapper.getMobileTodaySaleDcList(mobileTodaySaleVO);
    }

    /** 내점/배달/포장 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTodaySaleDlvrList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTodaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTodaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTodaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTodaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTodaySaleVO.setArrStoreCd(mobileTodaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTodaySaleMapper.getMobileTodaySaleDlvrList(mobileTodaySaleVO);
    }

    /** 시간대별 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTodaySaleTimeList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTodaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTodaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTodaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTodaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTodaySaleVO.setArrStoreCd(mobileTodaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTodaySaleMapper.getMobileTodaySaleTimeList(mobileTodaySaleVO);
    }
}