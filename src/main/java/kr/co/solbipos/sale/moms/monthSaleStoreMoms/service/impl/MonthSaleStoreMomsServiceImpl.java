package kr.co.solbipos.sale.moms.monthSaleStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.monthSaleStoreMoms.service.MonthSaleStoreMomsService;
import kr.co.solbipos.sale.moms.monthSaleStoreMoms.service.MonthSaleStoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : MonthSaleStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 월별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.12.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.12.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthSaleStoreMomsService")
@Transactional
public class MonthSaleStoreMomsServiceImpl implements MonthSaleStoreMomsService {
    private final MonthSaleStoreMomsMapper monthSaleStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthSaleStoreMomsServiceImpl(MonthSaleStoreMomsMapper monthSaleStoreMomsMapper, PopupMapper popupMapper) {
        this.monthSaleStoreMomsMapper = monthSaleStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 월별매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreMomsList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreMomsVO.getStoreCds(), 3900));
            monthSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreMomsVO.getStoreHqBrandCd() == "" || monthSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreMomsVO.getUserBrands().split(",");
                monthSaleStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreMomsMapper.getMonthSaleStoreMomsList(monthSaleStoreMomsVO);
    }

    /** 월별매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreMomsExcelList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreMomsVO.getStoreCds(), 3900));
            monthSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreMomsVO.getStoreHqBrandCd() == "" || monthSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreMomsVO.getUserBrands().split(",");
                monthSaleStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreMomsMapper.getMonthSaleStoreMomsExcelList(monthSaleStoreMomsVO);
    }

    /** 월별매출(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreMomsExcelDivisionList(MonthSaleStoreMomsVO monthSaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreMomsVO.getStoreCds(), 3900));
            monthSaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreMomsVO.getStoreHqBrandCd() == "" || monthSaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreMomsVO.getUserBrands().split(",");
                monthSaleStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreMomsMapper.getMonthSaleStoreMomsExcelDivisionList(monthSaleStoreMomsVO);
    }
}