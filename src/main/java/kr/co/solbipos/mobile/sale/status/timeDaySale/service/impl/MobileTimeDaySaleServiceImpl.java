package kr.co.solbipos.mobile.sale.status.timeDaySale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import kr.co.solbipos.mobile.sale.status.prod.service.impl.MobileProdSaleMapper;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleService;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final MobileProdSaleMapper mobileProdSaleMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTimeDaySaleServiceImpl(MobileTimeDaySaleMapper mobileTimeDaySaleMapper, MobileProdSaleMapper mobileProdSaleMapper, PopupMapper popupMapper) {
        this.mobileTimeDaySaleMapper = mobileTimeDaySaleMapper;
        this.mobileProdSaleMapper = mobileProdSaleMapper;
        this.popupMapper = popupMapper;
    }

    /** 일자-시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleDateTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTimeDaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileTimeDaySaleVO.getSrchStoreCd(), 3900));
            mobileTimeDaySaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        if(mobileTimeDaySaleVO.getOptionFg().equals("time")) { // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(mobileTimeDaySaleVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(mobileTimeDaySaleVO.getEndTime());
            for (int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                sQuery1 += ", SUM(CASE WHEN tsdt.SALE_HOUR = " + i + " THEN tsdt.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T" + i + "\n";
                sQuery2 += ", NVL(SUM(REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T" + i + "\n";
            }
        } else if(mobileTimeDaySaleVO.getOptionFg().equals("timeSlot")) {
            MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();
            mobileProdSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            mobileProdSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
            mobileTimeDaySaleVO.setStoreCd(sessionInfoVO.getStoreCd());
            List<DefaultMap<String>> timeSlotColList = mobileProdSaleMapper.getTimeSlotList(mobileProdSaleVO);
            for (int i = 0; i < timeSlotColList.size(); i++) {
                sQuery1 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
                sQuery2 += ", NVL(SUM(REAL_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS REAL_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "\n";
            }
        }

        mobileTimeDaySaleVO.setsQuery1(sQuery1);
        mobileTimeDaySaleVO.setsQuery2(sQuery2);

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleDateTimeList(mobileTimeDaySaleVO);
    }

    /** 시간대별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTimeDaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileTimeDaySaleVO.getSrchStoreCd(), 3900));
            mobileTimeDaySaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleTimeList(mobileTimeDaySaleVO);
    }

    /** 시간대별 - 차트 조회 */
    @Override
    public List<DefaultMap<Object>> getMobileTimeDaySaleTimeChartList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO) {

        mobileTimeDaySaleVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        mobileTimeDaySaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        mobileTimeDaySaleVO.setEmpNo(sessionInfoVO.getEmpNo());
        if(!StringUtil.getOrBlank(mobileTimeDaySaleVO.getSrchStoreCd()).equals("")) {
            // 기존에 매장권한인 경우, AuthenticationInterceptor.java에서 session.storeCd와 request.storeCd를 비교하여 다르면 에러 처리함.
            // 모바일의 경우 매장권한으로 다중매장을 조회하는 경우가 있으므로, request.srchStoreCd(storeCd 사용 X)에 가져와서 ServiceImple에서 다시 담아 처리.
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(mobileTimeDaySaleVO.getSrchStoreCd(), 3900));
            mobileTimeDaySaleVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return mobileTimeDaySaleMapper.getMobileTimeDaySaleTimeChartList(mobileTimeDaySaleVO);
    }
}