package kr.co.solbipos.sale.benson.prodSaleDayBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.prodSaleDayBenson.service.ProdSaleDayBensonService;
import kr.co.solbipos.sale.benson.prodSaleDayBenson.service.ProdSaleDayBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdSaleDayBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 상품매출일별
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
@Service("prodSaleDayBensonService")
@Transactional
public class ProdSaleDayBensonServiceImpl implements ProdSaleDayBensonService {
    private final ProdSaleDayBensonMapper prodSaleDayBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayBensonServiceImpl(ProdSaleDayBensonMapper prodSaleDayBensonMapper, PopupMapper popupMapper) {
        this.prodSaleDayBensonMapper = prodSaleDayBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별 - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayBensonList(ProdSaleDayBensonVO prodSaleDayBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayBensonVO.getStoreCds(), 3900));
            prodSaleDayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleDayBensonVO.getProdClassCd() != null && !"".equals(prodSaleDayBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleDayBensonVO.getProdClassCd().split(",");
            prodSaleDayBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleDayBensonVO.getProdCds(), 3900));
            prodSaleDayBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayBensonVO.getStoreHqBrandCd() == "" || prodSaleDayBensonVO.getStoreHqBrandCd() == null || prodSaleDayBensonVO.getProdHqBrandCd() == "" || prodSaleDayBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayBensonVO.getUserBrands().split(",");
                prodSaleDayBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayBensonMapper.getProdSaleDayBensonList(prodSaleDayBensonVO);
    }

    /** 상품매출일별 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayBensonExcelList(ProdSaleDayBensonVO prodSaleDayBensonVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayBensonVO.getStoreCds(), 3900));
            prodSaleDayBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (prodSaleDayBensonVO.getProdClassCd() != null && !"".equals(prodSaleDayBensonVO.getProdClassCd())) {
            String[] prodCdList = prodSaleDayBensonVO.getProdClassCd().split(",");
            prodSaleDayBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSaleDayBensonVO.getProdCds(), 3900));
            prodSaleDayBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayBensonVO.getStoreHqBrandCd() == "" || prodSaleDayBensonVO.getStoreHqBrandCd() == null || prodSaleDayBensonVO.getProdHqBrandCd() == "" || prodSaleDayBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayBensonVO.getUserBrands().split(",");
                prodSaleDayBensonVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayBensonMapper.getProdSaleDayBensonExcelList(prodSaleDayBensonVO);
    }
}
