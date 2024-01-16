package kr.co.solbipos.sale.moms.prodSaleDayMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSaleDayMoms.service.ProdSaleDayMomsService;
import kr.co.solbipos.sale.moms.prodSaleDayMoms.service.ProdSaleDayMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdSaleDayMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleDayMomsService")
@Transactional
public class ProdSaleDayMomsServiceImpl implements ProdSaleDayMomsService {
    private final ProdSaleDayMomsMapper prodSaleDayMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayMomsServiceImpl(ProdSaleDayMomsMapper prodSaleDayMomsMapper, PopupMapper popupMapper) {
        this.prodSaleDayMomsMapper = prodSaleDayMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayMomsList(ProdSaleDayMomsVO prodSaleDayMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayMomsVO.getStoreCds(), 3900));
            prodSaleDayMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayMomsVO.getProdCds() != null && !"".equals(prodSaleDayMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayMomsVO.getProdCds().split(",");
            prodSaleDayMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayMomsVO.getStoreHqBrandCd() == "" || prodSaleDayMomsVO.getStoreHqBrandCd() == null || prodSaleDayMomsVO.getProdHqBrandCd() == "" || prodSaleDayMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayMomsVO.getUserBrands().split(",");
                prodSaleDayMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayMomsMapper.getProdSaleDayMomsList(prodSaleDayMomsVO);
    }

    /** 상품매출일별 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayMomsExcelList(ProdSaleDayMomsVO prodSaleDayMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayMomsVO.getStoreCds(), 3900));
            prodSaleDayMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayMomsVO.getProdCds() != null && !"".equals(prodSaleDayMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayMomsVO.getProdCds().split(",");
            prodSaleDayMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayMomsVO.getStoreHqBrandCd() == "" || prodSaleDayMomsVO.getStoreHqBrandCd() == null || prodSaleDayMomsVO.getProdHqBrandCd() == "" || prodSaleDayMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayMomsVO.getUserBrands().split(",");
                prodSaleDayMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayMomsMapper.getProdSaleDayMomsExcelList(prodSaleDayMomsVO);
    }
}