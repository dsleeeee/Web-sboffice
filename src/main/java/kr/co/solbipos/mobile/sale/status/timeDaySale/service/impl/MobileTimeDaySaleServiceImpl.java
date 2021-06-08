package kr.co.solbipos.mobile.sale.status.timeDaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleService;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileTimeDaySaleServiceImpl.java
 * @Description : (모바일) 매출현황 > 시간대별(일자별)매출현황
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
@Service("mobileTimeDaySaleService")
@Transactional
public class MobileTimeDaySaleServiceImpl implements MobileTimeDaySaleService {
    private final MobileTimeDaySaleMapper mobileTimeDaySaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTimeDaySaleServiceImpl(MobileTimeDaySaleMapper mobileTimeDaySaleMapper) {
        this.mobileTimeDaySaleMapper = mobileTimeDaySaleMapper;
    }

    /** 일자-시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleDateTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeDaySaleVO.setArrStoreCd(mobileTimeDaySaleVO.getSrchStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";
        // 매출 시간대 설정
        int iSaleDateStart = Integer.parseInt(mobileTimeDaySaleVO.getStartTime());
        int iSaleDateEnd = Integer.parseInt(mobileTimeDaySaleVO.getEndTime());
        for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
            sQuery1 += ", SUM(CASE WHEN tsdt.SALE_HOUR = " + i + " THEN tsdt.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + i +  "\n";
            sQuery2 += ", NVL(SUM(REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T"  + i +  "\n";
        }
        mobileTimeDaySaleVO.setsQuery1(sQuery1);
        mobileTimeDaySaleVO.setsQuery2(sQuery2);

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleDateTimeList(mobileTimeDaySaleVO);
    }

    /** 시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeDaySaleVO.setArrStoreCd(mobileTimeDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleTimeList(mobileTimeDaySaleVO);
    }

    /** 시간대별 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleTimeChartList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            mobileTimeDaySaleVO.setArrStoreCd(mobileTimeDaySaleVO.getSrchStoreCd().split(","));
        }

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleTimeChartList(mobileTimeDaySaleVO);
    }
}