package kr.co.solbipos.sale.benson.billSaleBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.benson.billSaleBenson.service.BillSaleBensonService;
import kr.co.solbipos.sale.benson.billSaleBenson.service.BillSaleBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : BillSaleBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 영수건별매출
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
@Service("billSaleBensonService")
@Transactional
public class BillSaleBensonServiceImpl implements BillSaleBensonService {
    private final BillSaleBensonMapper billSaleBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public BillSaleBensonServiceImpl(BillSaleBensonMapper billSaleBensonMapper, PopupMapper popupMapper) {
        this.billSaleBensonMapper = billSaleBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 영수건별매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleBensonList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO) {

        billSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleBensonVO.getStoreCds(), 3900));
            billSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleBensonVO.getProdCds(), 3900));
            billSaleBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleBensonVO.getStoreHqBrandCd() == "" || billSaleBensonVO.getStoreHqBrandCd() == null || billSaleBensonVO.getProdHqBrandCd() == "" || billSaleBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleBensonVO.getUserBrands().split(",");
                billSaleBensonVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleBensonMapper.getBillSaleBensonList(billSaleBensonVO);
    }

    /** 영수건별매출 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleBensonExcelList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO) {

        billSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleBensonVO.getStoreCds(), 3900));
            billSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleBensonVO.getProdCds(), 3900));
            billSaleBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleBensonVO.getStoreHqBrandCd() == "" || billSaleBensonVO.getStoreHqBrandCd() == null || billSaleBensonVO.getProdHqBrandCd() == "" || billSaleBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleBensonVO.getUserBrands().split(",");
                billSaleBensonVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleBensonMapper.getBillSaleBensonExcelList(billSaleBensonVO);
    }

    /** 영수건별매출 - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleBensonExcelDivisionList(BillSaleBensonVO billSaleBensonVO, SessionInfoVO sessionInfoVO) {

        billSaleBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleBensonVO.getStoreCds(), 3900));
            billSaleBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleBensonVO.getProdCds(), 3900));
            billSaleBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleBensonVO.getStoreHqBrandCd() == "" || billSaleBensonVO.getStoreHqBrandCd() == null || billSaleBensonVO.getProdHqBrandCd() == "" || billSaleBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleBensonVO.getUserBrands().split(",");
                billSaleBensonVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleBensonMapper.getBillSaleBensonExcelDivisionList(billSaleBensonVO);
    }
}
