package kr.co.solbipos.sale.store.storeSoldOutHistory.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.store.storeSoldOutHistory.service.StoreSoldOutHistoryService;
import kr.co.solbipos.sale.store.storeSoldOutHistory.service.StoreSoldOutHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : StoreSoldOutHistoryServiceImpl.java
 * @Description : 맘스터치 > 매장관리 > 매장품절현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("StoreSoldOutHistoryService")
@Transactional
public class StoreSoldOutHistoryServiceImpl implements StoreSoldOutHistoryService {

    private final StoreSoldOutHistoryMapper storeSoldOutHistoryMapper;
    private final PopupMapper popupMapper;

    /**
     *  Constructor Injection
     */
    @Autowired
    public StoreSoldOutHistoryServiceImpl(StoreSoldOutHistoryMapper storeSoldOutHistoryMapper, PopupMapper popupMapper) {
        this.storeSoldOutHistoryMapper = storeSoldOutHistoryMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장품절현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchSoldOutHistory(StoreSoldOutHistoryVO storeSoldOutHistoryVO, SessionInfoVO sessionInfoVO) {

        storeSoldOutHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSoldOutHistoryVO.getStoreCds(), 3900));
            storeSoldOutHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (storeSoldOutHistoryVO.getProdClassCd() != null && !"".equals(storeSoldOutHistoryVO.getProdClassCd())) {
            String[] prodCdList = storeSoldOutHistoryVO.getProdClassCd().split(",");
            storeSoldOutHistoryVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(storeSoldOutHistoryVO.getProdCds(), 3900));
            storeSoldOutHistoryVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (storeSoldOutHistoryVO.getStoreHqBrandCd() == "" || storeSoldOutHistoryVO.getStoreHqBrandCd() == null || storeSoldOutHistoryVO.getProdHqBrandCd() == "" || storeSoldOutHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeSoldOutHistoryVO.getUserBrands().split(",");
                storeSoldOutHistoryVO.setUserBrandList(userBrandList);
            }
        }
        return storeSoldOutHistoryMapper.getSearchSoldOutHistory(storeSoldOutHistoryVO);
    }

    /** 매장품절현황 - 엑셀조회 */
    @Override
    public List<DefaultMap<Object>> getStoreSoldOutHistoryExcelList(StoreSoldOutHistoryVO storeSoldOutHistoryVO, SessionInfoVO sessionInfoVO) {
        storeSoldOutHistoryVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeSoldOutHistoryVO.getStoreCds(), 3900));
            storeSoldOutHistoryVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (storeSoldOutHistoryVO.getProdClassCd() != null && !"".equals(storeSoldOutHistoryVO.getProdClassCd())) {
            String[] prodCdList = storeSoldOutHistoryVO.getProdClassCd().split(",");
            storeSoldOutHistoryVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(storeSoldOutHistoryVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(storeSoldOutHistoryVO.getProdCds(), 3900));
            storeSoldOutHistoryVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (storeSoldOutHistoryVO.getStoreHqBrandCd() == "" || storeSoldOutHistoryVO.getStoreHqBrandCd() == null || storeSoldOutHistoryVO.getProdHqBrandCd() == "" || storeSoldOutHistoryVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeSoldOutHistoryVO.getUserBrands().split(",");
                storeSoldOutHistoryVO.setUserBrandList(userBrandList);
            }
        }
        return storeSoldOutHistoryMapper.getStoreSoldOutHistoryExcelList(storeSoldOutHistoryVO);
    }
}
