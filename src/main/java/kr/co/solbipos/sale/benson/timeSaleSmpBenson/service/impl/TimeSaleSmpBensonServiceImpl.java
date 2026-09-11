package kr.co.solbipos.sale.benson.timeSaleSmpBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.timeSaleSmpBenson.service.TimeSaleSmpBensonService;
import kr.co.solbipos.sale.benson.timeSaleSmpBenson.service.TimeSaleSmpBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : TimeSaleSmpBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleSmpBensonService")
@Transactional
public class TimeSaleSmpBensonServiceImpl implements TimeSaleSmpBensonService {
    private final TimeSaleSmpBensonMapper timeSaleSmpBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleSmpBensonServiceImpl(TimeSaleSmpBensonMapper timeSaleSmpBensonMapper, PopupMapper popupMapper) {
        this.timeSaleSmpBensonMapper = timeSaleSmpBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleSmpBensonList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleSmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleSmpBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleSmpBensonVO.getStoreCds(), 3900));
            timeSaleSmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeSaleSmpBensonVO.getProdCds(), 3900));
            timeSaleSmpBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleSmpBensonVO.getStoreHqBrandCd() == "" || timeSaleSmpBensonVO.getStoreHqBrandCd() == null || timeSaleSmpBensonVO.getProdHqBrandCd() == "" || timeSaleSmpBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleSmpBensonVO.getUserBrands().split(",");
                timeSaleSmpBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleSmpBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleSmpBensonVO.getDlvrOrderFg())) {
            timeSaleSmpBensonVO.setDlvrOrderFgList(timeSaleSmpBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleSmpBensonMapper.getTimeSaleSmpBensonList(timeSaleSmpBensonVO);
    }

    /** 시간대매출 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleSmpBensonExcelList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleSmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleSmpBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleSmpBensonVO.getStoreCds(), 3900));
            timeSaleSmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeSaleSmpBensonVO.getProdCds(), 3900));
            timeSaleSmpBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleSmpBensonVO.getStoreHqBrandCd() == "" || timeSaleSmpBensonVO.getStoreHqBrandCd() == null || timeSaleSmpBensonVO.getProdHqBrandCd() == "" || timeSaleSmpBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleSmpBensonVO.getUserBrands().split(",");
                timeSaleSmpBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleSmpBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleSmpBensonVO.getDlvrOrderFg())) {
            timeSaleSmpBensonVO.setDlvrOrderFgList(timeSaleSmpBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleSmpBensonMapper.getTimeSaleSmpBensonExcelList(timeSaleSmpBensonVO);
    }

    /** 시간대매출 - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleSmpBensonExcelDivisionList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, SessionInfoVO sessionInfoVO) {

        timeSaleSmpBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleSmpBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleSmpBensonVO.getStoreCds(), 3900));
            timeSaleSmpBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleSmpBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeSaleSmpBensonVO.getProdCds(), 3900));
            timeSaleSmpBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleSmpBensonVO.getStoreHqBrandCd() == "" || timeSaleSmpBensonVO.getStoreHqBrandCd() == null || timeSaleSmpBensonVO.getProdHqBrandCd() == "" || timeSaleSmpBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleSmpBensonVO.getUserBrands().split(",");
                timeSaleSmpBensonVO.setUserBrandList(userBrandList);
            }
        }

        // 조회옵션 array 값 세팅
        if (timeSaleSmpBensonVO.getDlvrOrderFg() != null && !"".equals(timeSaleSmpBensonVO.getDlvrOrderFg())) {
            timeSaleSmpBensonVO.setDlvrOrderFgList(timeSaleSmpBensonVO.getDlvrOrderFg().split(","));
        }

        return timeSaleSmpBensonMapper.getTimeSaleSmpBensonExcelDivisionList(timeSaleSmpBensonVO);
    }
}
