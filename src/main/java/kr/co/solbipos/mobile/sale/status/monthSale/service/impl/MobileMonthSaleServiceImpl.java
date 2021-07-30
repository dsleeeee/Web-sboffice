package kr.co.solbipos.mobile.sale.status.monthSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.monthSale.service.MobileMonthSaleService;
import kr.co.solbipos.mobile.sale.status.monthSale.service.MobileMonthSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileMonthSaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 월별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileMonthSaleService")
@Transactional
public class MobileMonthSaleServiceImpl implements  MobileMonthSaleService {
    private final  MobileMonthSaleMapper  mobileMonthSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMonthSaleServiceImpl(MobileMonthSaleMapper mobileMonthSaleMapper) {
        this.mobileMonthSaleMapper = mobileMonthSaleMapper;
    }

    /** 매출종합 - 조회 */
    @Override
    public DefaultMap<String> getMobileMonthSaleTotalList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSaleTotalList(mobileMonthSaleVO);
    }

    /** 결제수단 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMonthSalePayList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSalePayList(mobileMonthSaleVO);
    }

    /** 할인내역 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMonthSaleDcList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSaleDcList(mobileMonthSaleVO);
    }

    /** 내점현황 - 조회 */
    @Override
    public DefaultMap<String> getMobileMonthSaleShopList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSaleShopList(mobileMonthSaleVO);
    }

    /** 내점/배달/포장 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMonthSaleDlvrList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSaleDlvrList(mobileMonthSaleVO);
    }

    /** 내점/배달/포장 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMonthSaleDlvrChartList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        if(mobileMonthSaleVO.getGubun().equals("1")) {
            result = mobileMonthSaleMapper.getMobileMonthSaleDlvrChartList(mobileMonthSaleVO);

        } else {
            // 내점
            if(mobileMonthSaleVO.getGubun().equals("2")) {
                mobileMonthSaleVO.setGubun("1");
                // 배달
            } else if(mobileMonthSaleVO.getGubun().equals("3")) {
                mobileMonthSaleVO.setGubun("2");
                // 포장
            } else if(mobileMonthSaleVO.getGubun().equals("4")) {
                mobileMonthSaleVO.setGubun("3");
            }

            result = mobileMonthSaleMapper.getMobileMonthSaleDlvrChart2List(mobileMonthSaleVO);
        }

        return result;
    }

    /** 월자별 매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMonthSaleDtlList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMonthSaleVO.setArrStoreCd(mobileMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMonthSaleMapper.getMobileMonthSaleDtlList(mobileMonthSaleVO);
    }
}