package kr.co.solbipos.mobile.sale.status.timeMonthSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.timeMonthSale.service.MobileTimeMonthSaleService;
import kr.co.solbipos.mobile.sale.status.timeMonthSale.service.MobileTimeMonthSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileTimeMonthSaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 시간대별(월별)매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileTimeMonthSaleService")
@Transactional
public class MobileTimeMonthSaleServiceImpl implements MobileTimeMonthSaleService {
    private final MobileTimeMonthSaleMapper mobileTimeMonthSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTimeMonthSaleServiceImpl(MobileTimeMonthSaleMapper mobileTimeMonthSaleMapper) {
        this.mobileTimeMonthSaleMapper = mobileTimeMonthSaleMapper;
    }

    /** 일자-시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeMonthSaleDateTimeList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeMonthSaleVO.setArrStoreCd(mobileTimeMonthSaleVO.getSrchStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";
        // 매출 시간대 설정
        int iSaleDateStart = Integer.parseInt(mobileTimeMonthSaleVO.getStartTime());
        int iSaleDateEnd = Integer.parseInt(mobileTimeMonthSaleVO.getEndTime());
        for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
            sQuery1 += ", SUM(CASE WHEN tsmt.SALE_HOUR = " + i + " THEN tsmt.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + i +  "\n";
            sQuery2 += ", NVL(SUM(REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T"  + i +  "\n";
        }
        mobileTimeMonthSaleVO.setsQuery1(sQuery1);
        mobileTimeMonthSaleVO.setsQuery2(sQuery2);

        return mobileTimeMonthSaleMapper.getMobileTimeMonthSaleDateTimeList(mobileTimeMonthSaleVO);
    }

    /** 시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeMonthSaleTimeList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeMonthSaleVO.setArrStoreCd(mobileTimeMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileTimeMonthSaleMapper.getMobileTimeMonthSaleTimeList(mobileTimeMonthSaleVO);
    }

    /** 시간대별 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeMonthSaleTimeChartList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeMonthSaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeMonthSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeMonthSaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeMonthSaleVO.setArrStoreCd(mobileTimeMonthSaleVO.getSrchStoreCd().split(","));
        }

        return mobileTimeMonthSaleMapper.getMobileTimeMonthSaleTimeChartList(mobileTimeMonthSaleVO);
    }
}