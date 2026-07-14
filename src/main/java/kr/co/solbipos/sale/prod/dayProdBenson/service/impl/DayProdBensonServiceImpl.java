package kr.co.solbipos.sale.prod.dayProdBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonService;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayProdBensonServiceImpl.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황
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
@Service("dayProdBensonService")
@Transactional
public class DayProdBensonServiceImpl implements DayProdBensonService {

    private final DayProdBensonMapper dayProdBensonMapper;
    private final PopupMapper popupMapper;

    public DayProdBensonServiceImpl(DayProdBensonMapper dayProdBensonMapper, PopupMapper popupMapper) {
        this.dayProdBensonMapper = dayProdBensonMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별상품매출현황 리스트 조회
     * @param dayProdBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdBensonList(DayProdBensonVO dayProdBensonVO, SessionInfoVO sessionInfoVO) {

        dayProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdBensonVO.getStoreCds(), 3900));
            dayProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdBensonVO.getProdClassCd() != null && !"".equals(dayProdBensonVO.getProdClassCd())) {
            String[] prodCdList = dayProdBensonVO.getProdClassCd().split(",");
            dayProdBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdBensonVO.getProdCds(), 3900));
            dayProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdBensonVO.getStoreHqBrandCd() == "" || dayProdBensonVO.getStoreHqBrandCd() == null || dayProdBensonVO.getProdHqBrandCd() == "" || dayProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdBensonVO.getUserBrands().split(",");
                dayProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdBensonMapper.getDayProdBensonList(dayProdBensonVO);
    }

    /**
     * 일별상품매출현황 엑셀 다운로드 조회
     * @param dayProdBensonVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdBensonExcelList(DayProdBensonVO dayProdBensonVO, SessionInfoVO sessionInfoVO) {

        dayProdBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdBensonVO.getStoreCds(), 3900));
            dayProdBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdBensonVO.getProdClassCd() != null && !"".equals(dayProdBensonVO.getProdClassCd())) {
            String[] prodCdList = dayProdBensonVO.getProdClassCd().split(",");
            dayProdBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdBensonVO.getProdCds(), 3900));
            dayProdBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdBensonVO.getStoreHqBrandCd() == "" || dayProdBensonVO.getStoreHqBrandCd() == null || dayProdBensonVO.getProdHqBrandCd() == "" || dayProdBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdBensonVO.getUserBrands().split(",");
                dayProdBensonVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdBensonMapper.getDayProdBensonExcelList(dayProdBensonVO);
    }
}
