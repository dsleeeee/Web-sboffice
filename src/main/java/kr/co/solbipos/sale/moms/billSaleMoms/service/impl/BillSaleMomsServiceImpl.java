package kr.co.solbipos.sale.moms.billSaleMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.moms.billSaleMoms.service.BillSaleMomsService;
import kr.co.solbipos.sale.moms.billSaleMoms.service.BillSaleMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : BillSaleMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 영수건별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("billSaleMomsService")
@Transactional
public class BillSaleMomsServiceImpl implements BillSaleMomsService {
    private final BillSaleMomsMapper billSaleMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public BillSaleMomsServiceImpl(BillSaleMomsMapper billSaleMomsMapper, PopupMapper popupMapper) {
        this.billSaleMomsMapper = billSaleMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 영수건별매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleMomsList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO) {

        billSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleMomsVO.getStoreCds(), 3900));
            billSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleMomsVO.getProdCds(), 3900));
            billSaleMomsVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleMomsVO.getStoreHqBrandCd() == "" || billSaleMomsVO.getStoreHqBrandCd() == null || billSaleMomsVO.getProdHqBrandCd() == "" || billSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleMomsVO.getUserBrands().split(",");
                billSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleMomsMapper.getBillSaleMomsList(billSaleMomsVO);
    }

    /** 영수건별매출 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleMomsExcelList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO) {

        billSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleMomsVO.getStoreCds(), 3900));
            billSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleMomsVO.getProdCds(), 3900));
            billSaleMomsVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleMomsVO.getStoreHqBrandCd() == "" || billSaleMomsVO.getStoreHqBrandCd() == null || billSaleMomsVO.getProdHqBrandCd() == "" || billSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleMomsVO.getUserBrands().split(",");
                billSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleMomsMapper.getBillSaleMomsExcelList(billSaleMomsVO);
    }

    /** 영수건별매출 - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getBillSaleMomsExcelDivisionList(BillSaleMomsVO billSaleMomsVO, SessionInfoVO sessionInfoVO) {

        billSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            billSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(billSaleMomsVO.getStoreCds(), 3900));
            billSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(billSaleMomsVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(billSaleMomsVO.getProdCds(), 3900));
            billSaleMomsVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (billSaleMomsVO.getStoreHqBrandCd() == "" || billSaleMomsVO.getStoreHqBrandCd() == null || billSaleMomsVO.getProdHqBrandCd() == "" || billSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = billSaleMomsVO.getUserBrands().split(",");
                billSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return billSaleMomsMapper.getBillSaleMomsExcelDivisionList(billSaleMomsVO);
    }
}