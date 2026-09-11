package kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service.StoreSoldOutHistoryBensonService;
import kr.co.solbipos.sale.benson.storeSoldOutHistoryBenson.service.StoreSoldOutHistoryBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : StoreSoldOutHistoryBensonServiceImpl.java
 * @Description : 벤슨 > 매장분석 > 매장품절현황
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
@Service("StoreSoldOutHistoryBensonService")
@Transactional
public class StoreSoldOutHistoryBensonServiceImpl implements StoreSoldOutHistoryBensonService {

    private final StoreSoldOutHistoryBensonMapper storeSoldOutHistoryBensonMapper;
    private final PopupMapper popupMapper;

    /**
     *  Constructor Injection
     */
    @Autowired
    public StoreSoldOutHistoryBensonServiceImpl(StoreSoldOutHistoryBensonMapper storeSoldOutHistoryBensonMapper, PopupMapper popupMapper) {
        this.storeSoldOutHistoryBensonMapper = storeSoldOutHistoryBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장품절현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchSoldOutHistory(StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO, SessionInfoVO sessionInfoVO) {

        storeSoldOutHistoryBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSoldOutHistoryBensonVO.getStoreCds(), 3900));
            storeSoldOutHistoryBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (storeSoldOutHistoryBensonVO.getProdClassCd() != null && !"".equals(storeSoldOutHistoryBensonVO.getProdClassCd())) {
            String[] prodCdList = storeSoldOutHistoryBensonVO.getProdClassCd().split(",");
            storeSoldOutHistoryBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(storeSoldOutHistoryBensonVO.getProdCds(), 3900));
            storeSoldOutHistoryBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (storeSoldOutHistoryBensonVO.getStoreHqBrandCd() == "" || storeSoldOutHistoryBensonVO.getStoreHqBrandCd() == null || storeSoldOutHistoryBensonVO.getProdHqBrandCd() == "" || storeSoldOutHistoryBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeSoldOutHistoryBensonVO.getUserBrands().split(",");
                storeSoldOutHistoryBensonVO.setUserBrandList(userBrandList);
            }
        }
        return storeSoldOutHistoryBensonMapper.getSearchSoldOutHistory(storeSoldOutHistoryBensonVO);
    }

    /** 매장품절현황 - 엑셀조회 */
    @Override
    public List<DefaultMap<Object>> getStoreSoldOutHistoryBensonExcelList(StoreSoldOutHistoryBensonVO storeSoldOutHistoryBensonVO, SessionInfoVO sessionInfoVO) {
        storeSoldOutHistoryBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSoldOutHistoryBensonVO.getStoreCds(), 3900));
            storeSoldOutHistoryBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (storeSoldOutHistoryBensonVO.getProdClassCd() != null && !"".equals(storeSoldOutHistoryBensonVO.getProdClassCd())) {
            String[] prodCdList = storeSoldOutHistoryBensonVO.getProdClassCd().split(",");
            storeSoldOutHistoryBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(storeSoldOutHistoryBensonVO.getProdCds(), 3900));
            storeSoldOutHistoryBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (storeSoldOutHistoryBensonVO.getStoreHqBrandCd() == "" || storeSoldOutHistoryBensonVO.getStoreHqBrandCd() == null || storeSoldOutHistoryBensonVO.getProdHqBrandCd() == "" || storeSoldOutHistoryBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeSoldOutHistoryBensonVO.getUserBrands().split(",");
                storeSoldOutHistoryBensonVO.setUserBrandList(userBrandList);
            }
        }
        return storeSoldOutHistoryBensonMapper.getStoreSoldOutHistoryBensonExcelList(storeSoldOutHistoryBensonVO);
    }
}
