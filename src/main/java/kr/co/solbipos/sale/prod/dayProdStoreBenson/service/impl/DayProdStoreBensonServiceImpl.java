package kr.co.solbipos.sale.prod.dayProdStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.prod.dayProdStoreBenson.service.DayProdStoreBensonService;
import kr.co.solbipos.sale.prod.dayProdStoreBenson.service.DayProdStoreBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayProdStoreBensonServiceImpl.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황(매장별)
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
@Service("dayProdStoreBensonService")
@Transactional
public class DayProdStoreBensonServiceImpl implements DayProdStoreBensonService {

    private final DayProdStoreBensonMapper dayProdStoreBensonMapper;
    private final PopupMapper popupMapper;

    public DayProdStoreBensonServiceImpl(DayProdStoreBensonMapper dayProdStoreBensonMapper, PopupMapper popupMapper) {
        this.dayProdStoreBensonMapper = dayProdStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별상품매출현황(매장별) 리스트 조회
     * @param dayProdStoreBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreBensonList(DayProdStoreBensonVO dayProdStoreBensonVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreBensonVO.getStoreCds(), 3900));
            dayProdStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreBensonVO.getProdClassCd() != null && !"".equals(dayProdStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreBensonVO.getProdClassCd().split(",");
            dayProdStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdStoreBensonVO.getProdCds(), 3900));
            dayProdStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreBensonVO.getStoreHqBrandCd() == "" || dayProdStoreBensonVO.getStoreHqBrandCd() == null || dayProdStoreBensonVO.getProdHqBrandCd() == "" || dayProdStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreBensonVO.getUserBrands().split(",");
                dayProdStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreBensonMapper.getDayProdStoreBensonList(dayProdStoreBensonVO);
    }

    /**
     * 일별상품매출현황(매장별) 엑셀 다운로드 조회
     * @param dayProdStoreBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreBensonExcelList(DayProdStoreBensonVO dayProdStoreBensonVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreBensonVO.getStoreCds(), 3900));
            dayProdStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreBensonVO.getProdClassCd() != null && !"".equals(dayProdStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreBensonVO.getProdClassCd().split(",");
            dayProdStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdStoreBensonVO.getProdCds(), 3900));
            dayProdStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreBensonVO.getStoreHqBrandCd() == "" || dayProdStoreBensonVO.getStoreHqBrandCd() == null || dayProdStoreBensonVO.getProdHqBrandCd() == "" || dayProdStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreBensonVO.getUserBrands().split(",");
                dayProdStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreBensonMapper.getDayProdStoreBensonExcelList(dayProdStoreBensonVO);
    }
}
