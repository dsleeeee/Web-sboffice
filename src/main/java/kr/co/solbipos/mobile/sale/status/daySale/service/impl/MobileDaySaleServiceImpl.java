package kr.co.solbipos.mobile.sale.status.daySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleService;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileDaySaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 일별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileDaySaleService")
@Transactional
public class MobileDaySaleServiceImpl implements  MobileDaySaleService {
    private final  MobileDaySaleMapper  mobileDaySaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileDaySaleServiceImpl(MobileDaySaleMapper mobileDaySaleMapper) {
        this.mobileDaySaleMapper = mobileDaySaleMapper;
    }

    /** 매출종합 - 조회 */
    @Override
    public DefaultMap<String> getMobileDaySaleTotalList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySaleTotalList(mobileDaySaleVO);
    }

    /** 결제수단 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileDaySalePayList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySalePayList(mobileDaySaleVO);
    }

    /** 할인내역 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileDaySaleDcList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySaleDcList(mobileDaySaleVO);
    }

    /** 내점현황 - 조회 */
    @Override
    public DefaultMap<String> getMobileDaySaleShopList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySaleShopList(mobileDaySaleVO);
    }

    /** 내점/배달/포장 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileDaySaleDlvrList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySaleDlvrList(mobileDaySaleVO);
    }

    /** 내점/배달/포장 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileDaySaleDlvrChartList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        if(mobileDaySaleVO.getGubun().equals("1")) {
            result = mobileDaySaleMapper.getMobileDaySaleDlvrChartList(mobileDaySaleVO);

        } else {
            // 내점
            if(mobileDaySaleVO.getGubun().equals("2")) {
                mobileDaySaleVO.setGubun("1");
            // 배달
            } else if(mobileDaySaleVO.getGubun().equals("3")) {
                    mobileDaySaleVO.setGubun("2");
            // 포장
            } else if(mobileDaySaleVO.getGubun().equals("4")) {
                mobileDaySaleVO.setGubun("3");
            }

            result = mobileDaySaleMapper.getMobileDaySaleDlvrChart2List(mobileDaySaleVO);
        }

        return result;
    }

    /** 일자별 매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileDaySaleDtlList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileDaySaleVO.setArrStoreCd(mobileDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileDaySaleMapper.getMobileDaySaleDtlList(mobileDaySaleVO);
    }
}