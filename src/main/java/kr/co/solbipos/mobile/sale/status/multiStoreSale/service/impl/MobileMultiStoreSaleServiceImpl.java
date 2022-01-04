package kr.co.solbipos.mobile.sale.status.multiStoreSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.multiStoreSale.service.MobileMultiStoreSaleService;
import kr.co.solbipos.mobile.sale.status.multiStoreSale.service.MobileMultiStoreSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileMultiStoreSaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 다중매장매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileMultiStoreSaleService")
@Transactional
public class MobileMultiStoreSaleServiceImpl implements  MobileMultiStoreSaleService {
    private final MobileMultiStoreSaleMapper mobileMultiStoreSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMultiStoreSaleServiceImpl(MobileMultiStoreSaleMapper mobileMultiStoreSaleMapper) {
        this.mobileMultiStoreSaleMapper = mobileMultiStoreSaleMapper;
    }

    /** 다중매장매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMultiStoreSaleList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMultiStoreSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMultiStoreSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMultiStoreSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMultiStoreSaleVO.setArrStoreCd(mobileMultiStoreSaleVO.getSrchStoreCd().split(","));
        }

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        if(mobileMultiStoreSaleVO.getGubun().equals("1")) {
            result = mobileMultiStoreSaleMapper.getMobileMultiStoreSaleList(mobileMultiStoreSaleVO);

        } else if(mobileMultiStoreSaleVO.getGubun().equals("2")) {
            result = mobileMultiStoreSaleMapper.getMobileMultiStoreSale2List(mobileMultiStoreSaleVO);
        }

        return result;
    }

    /** 다중매장매출현황 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMultiStoreSaleChartList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMultiStoreSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMultiStoreSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMultiStoreSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMultiStoreSaleVO.setArrStoreCd(mobileMultiStoreSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMultiStoreSaleMapper.getMobileMultiStoreSaleChartList(mobileMultiStoreSaleVO);
    }

    /** 일자-매장별 매출현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileMultiStoreSaleDayStoreList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO) {

        mobileMultiStoreSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileMultiStoreSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileMultiStoreSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileMultiStoreSaleVO.setArrStoreCd(mobileMultiStoreSaleVO.getSrchStoreCd().split(","));
        }

        return mobileMultiStoreSaleMapper.getMobileMultiStoreSaleDayStoreList(mobileMultiStoreSaleVO);
    }
}