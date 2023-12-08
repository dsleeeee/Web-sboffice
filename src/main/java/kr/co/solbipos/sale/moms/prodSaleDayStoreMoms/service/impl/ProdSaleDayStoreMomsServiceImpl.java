package kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsService;
import kr.co.solbipos.sale.moms.prodSaleDayStoreMoms.service.ProdSaleDayStoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : ProdSaleDayStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleDayStoreMomsService")
@Transactional
public class ProdSaleDayStoreMomsServiceImpl implements ProdSaleDayStoreMomsService {
    private final ProdSaleDayStoreMomsMapper prodSaleDayStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayStoreMomsServiceImpl(ProdSaleDayStoreMomsMapper prodSaleDayStoreMomsMapper, PopupMapper popupMapper) {
        this.prodSaleDayStoreMomsMapper = prodSaleDayStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreMomsList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreMomsVO.getStoreCds(), 3900));
            prodSaleDayStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayStoreMomsVO.getProdCds() != null && !"".equals(prodSaleDayStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayStoreMomsVO.getProdCds().split(",");
            prodSaleDayStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleDayStoreMomsVO.getStoreHqBrandCd() == null || prodSaleDayStoreMomsVO.getProdHqBrandCd() == "" || prodSaleDayStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreMomsVO.getUserBrands().split(",");
                prodSaleDayStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreMomsMapper.getProdSaleDayStoreMomsList(prodSaleDayStoreMomsVO);
    }

    /** 상품매출일별(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayStoreMomsExcelList(ProdSaleDayStoreMomsVO prodSaleDayStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayStoreMomsVO.getStoreCds(), 3900));
            prodSaleDayStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayStoreMomsVO.getProdCds() != null && !"".equals(prodSaleDayStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayStoreMomsVO.getProdCds().split(",");
            prodSaleDayStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayStoreMomsVO.getStoreHqBrandCd() == "" || prodSaleDayStoreMomsVO.getStoreHqBrandCd() == null || prodSaleDayStoreMomsVO.getProdHqBrandCd() == "" || prodSaleDayStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayStoreMomsVO.getUserBrands().split(",");
                prodSaleDayStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return prodSaleDayStoreMomsMapper.getProdSaleDayStoreMomsExcelList(prodSaleDayStoreMomsVO);
    }
}