package kr.co.solbipos.sale.prod.dayProdStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreService;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : DayProdStoreServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayProdStoreService")
@Transactional
public class DayProdStoreServiceImpl implements DayProdStoreService {
    private final DayProdStoreMapper dayProdStoreMapper;
    private final PopupMapper popupMapper;

    public DayProdStoreServiceImpl(DayProdStoreMapper dayProdStoreMapper, PopupMapper popupMapper) {
        this.dayProdStoreMapper = dayProdStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreVO.getStoreCds(), 3900));
            dayProdStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreVO.getProdClassCd() != null && !"".equals(dayProdStoreVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreVO.getProdClassCd().split(",");
            dayProdStoreVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdStoreVO.getProdCds(), 3900));
            dayProdStoreVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreVO.getStoreHqBrandCd() == "" || dayProdStoreVO.getStoreHqBrandCd() == null || dayProdStoreVO.getProdHqBrandCd() == "" || dayProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreVO.getUserBrands().split(",");
                dayProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreMapper.getDayProdStoreList(dayProdStoreVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreExcelList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreVO.getStoreCds(), 3900));
            dayProdStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreVO.getProdClassCd() != null && !"".equals(dayProdStoreVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreVO.getProdClassCd().split(",");
            dayProdStoreVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(dayProdStoreVO.getProdCds(), 3900));
            dayProdStoreVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (dayProdStoreVO.getStoreHqBrandCd() == "" || dayProdStoreVO.getStoreHqBrandCd() == null || dayProdStoreVO.getProdHqBrandCd() == "" || dayProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreVO.getUserBrands().split(",");
                dayProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoreMapper.getDayProdStoreExcelList(dayProdStoreVO);
    }
}