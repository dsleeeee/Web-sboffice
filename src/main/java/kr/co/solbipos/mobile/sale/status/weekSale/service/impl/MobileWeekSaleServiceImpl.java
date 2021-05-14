package kr.co.solbipos.mobile.sale.status.weekSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleService;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileWeekSaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 주간매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileWeekSaleService")
@Transactional
public class MobileWeekSaleServiceImpl implements  MobileWeekSaleService {
    private final  MobileWeekSaleMapper  mobileWeekSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileWeekSaleServiceImpl(MobileWeekSaleMapper mobileWeekSaleMapper) {
        this.mobileWeekSaleMapper = mobileWeekSaleMapper;
    }

    /** 매출종합 - 조회 */
    @Override
    public DefaultMap<String> getMobileWeekSaleTotalList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSaleTotalList(mobileWeekSaleVO);
    }

    /** 결제수단 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileWeekSalePayList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSalePayList(mobileWeekSaleVO);
    }

    /** 할인내역 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileWeekSaleDcList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSaleDcList(mobileWeekSaleVO);
    }

    /** 내점현황 - 조회 */
    @Override
    public DefaultMap<String> getMobileWeekSaleShopList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSaleShopList(mobileWeekSaleVO);
    }

    /** 내점/배달/포장 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileWeekSaleDlvrList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSaleDlvrList(mobileWeekSaleVO);
    }

    /** 내점/배달/포장 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileWeekSaleDlvrChartList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        if(mobileWeekSaleVO.getGubun().equals("1")) {
            result = mobileWeekSaleMapper.getMobileWeekSaleDlvrChartList(mobileWeekSaleVO);

        } else {
            // 내점
            if(mobileWeekSaleVO.getGubun().equals("2")) {
                mobileWeekSaleVO.setGubun("1");
                // 배달
            } else if(mobileWeekSaleVO.getGubun().equals("3")) {
                mobileWeekSaleVO.setGubun("2");
                // 포장
            } else if(mobileWeekSaleVO.getGubun().equals("4")) {
                mobileWeekSaleVO.setGubun("3");
            }

            result = mobileWeekSaleMapper.getMobileWeekSaleDlvrChart2List(mobileWeekSaleVO);
        }

        return result;
    }

    /** 일자별 매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileWeekSaleDtlList(MobileWeekSaleVO mobileWeekSaleVO, SessionInfoVO sessionInfoVO) {

        mobileWeekSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileWeekSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileWeekSaleVO.setArrStoreCd(mobileWeekSaleVO.getSrchStoreCd().split(","));
        }

        return mobileWeekSaleMapper.getMobileWeekSaleDtlList(mobileWeekSaleVO);
    }
}