package kr.co.solbipos.sale.prod.monthProdBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonVO;
import kr.co.solbipos.sale.prod.monthProdBenson.service.MonthProdBensonService;
import kr.co.solbipos.sale.prod.monthProdBenson.service.MonthProdBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdBensonServiceImpl.java
 * @Description : 벤슨 > 상품매출분석 > 월별상품매출현황
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
@Service("monthProdBensonService")
@Transactional
public class MonthProdBensonServiceImpl implements MonthProdBensonService {

    private final MonthProdBensonMapper monthProdBensonMapper;
    private final PopupMapper popupMapper;

    public MonthProdBensonServiceImpl(MonthProdBensonMapper monthProdBensonMapper, PopupMapper popupMapper) {
        this.monthProdBensonMapper = monthProdBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 월별상품매출현황 리스트 조회
     * @param monthProdBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdBensonList(MonthProdBensonVO monthProdBensonVO, SessionInfoVO sessionInfoVO) {

        monthProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdBensonVO.getStoreCds(), 3900));
            monthProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdBensonVO.getProdClassCd() != null && !"".equals(monthProdBensonVO.getProdClassCd())) {
            String[] prodCdList = monthProdBensonVO.getProdClassCd().split(",");
            monthProdBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(monthProdBensonVO.getProdCds(), 3900));
            monthProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdBensonVO.getStoreHqBrandCd() == "" || monthProdBensonVO.getStoreHqBrandCd() == null || monthProdBensonVO.getProdHqBrandCd() == "" || monthProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdBensonVO.getUserBrands().split(",");
                monthProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdBensonMapper.getMonthProdBensonList(monthProdBensonVO);
    }

    /**
     * 월별상품매출현황 엑셀 다운로드 조회
     * @param monthProdBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdBensonExcelList(MonthProdBensonVO monthProdBensonVO, SessionInfoVO sessionInfoVO) {

        monthProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            monthProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdBensonVO.getStoreCds(), 3900));
            monthProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdBensonVO.getProdClassCd() != null && !"".equals(monthProdBensonVO.getProdClassCd())) {
            String[] prodCdList = monthProdBensonVO.getProdClassCd().split(",");
            monthProdBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(monthProdBensonVO.getProdCds(), 3900));
            monthProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdBensonVO.getStoreHqBrandCd() == "" || monthProdBensonVO.getStoreHqBrandCd() == null || monthProdBensonVO.getProdHqBrandCd() == "" || monthProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdBensonVO.getUserBrands().split(",");
                monthProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdBensonMapper.getMonthProdBensonExcelList(monthProdBensonVO);
    }
}
