package kr.co.solbipos.sale.benson.monthSaleStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.monthSaleStoreBenson.service.MonthSaleStoreBensonService;
import kr.co.solbipos.sale.benson.monthSaleStoreBenson.service.MonthSaleStoreBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : MonthSaleStoreBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 월별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("monthSaleStoreBensonService")
@Transactional
public class MonthSaleStoreBensonServiceImpl implements MonthSaleStoreBensonService {
    private final MonthSaleStoreBensonMapper monthSaleStoreBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthSaleStoreBensonServiceImpl(MonthSaleStoreBensonMapper monthSaleStoreBensonMapper, PopupMapper popupMapper) {
        this.monthSaleStoreBensonMapper = monthSaleStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 월별매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreBensonList(MonthSaleStoreBensonVO monthSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreBensonVO.getStoreCds(), 3900));
            monthSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreBensonVO.getStoreHqBrandCd() == "" || monthSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreBensonVO.getUserBrands().split(",");
                monthSaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreBensonMapper.getMonthSaleStoreBensonList(monthSaleStoreBensonVO);
    }

    /** 월별매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreBensonExcelList(MonthSaleStoreBensonVO monthSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreBensonVO.getStoreCds(), 3900));
            monthSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreBensonVO.getStoreHqBrandCd() == "" || monthSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreBensonVO.getUserBrands().split(",");
                monthSaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreBensonMapper.getMonthSaleStoreBensonExcelList(monthSaleStoreBensonVO);
    }

    /** 월별매출(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthSaleStoreBensonExcelDivisionList(MonthSaleStoreBensonVO monthSaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        monthSaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthSaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthSaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthSaleStoreBensonVO.getStoreCds(), 3900));
            monthSaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (monthSaleStoreBensonVO.getStoreHqBrandCd() == "" || monthSaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthSaleStoreBensonVO.getUserBrands().split(",");
                monthSaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthSaleStoreBensonMapper.getMonthSaleStoreBensonExcelDivisionList(monthSaleStoreBensonVO);
    }
}
