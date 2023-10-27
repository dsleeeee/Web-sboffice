package kr.co.solbipos.sale.prod.prodSaleRate2.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodSaleRate2.service.ProdSaleRate2Service;
import kr.co.solbipos.sale.prod.prodSaleRate2.service.ProdSaleRate2VO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdSaleRate2ServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품 판매 비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleRate2Service")
@Transactional
public class ProdSaleRate2ServiceImpl implements ProdSaleRate2Service {
    private final ProdSaleRate2Mapper prodSaleRate2Mapper;
    private final PopupMapper popupMapper;

    public ProdSaleRate2ServiceImpl(ProdSaleRate2Mapper prodSaleRate2Mapper, PopupMapper popupMapper) {
        this.prodSaleRate2Mapper = prodSaleRate2Mapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleRate2List(ProdSaleRate2VO prodSaleRate2VO, SessionInfoVO sessionInfoVO) {

        prodSaleRate2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRate2VO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleRate2VO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleRate2VO.getStoreCds(), 3900));
            prodSaleRate2VO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";

        String[] arrDlvrInFgCol = prodSaleRate2VO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                prodSaleRate2VO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] list = prodSaleRate2VO.getArrDlvrInFgCol();

        for (int i = 0; i < list.length; i++) {
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '1' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS STIN_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '1' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS STIN_AMT" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '2' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS DLVR_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '2' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS DLVR_AMT" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '3' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS PACK_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '3' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS PACK_AMT" + list[i] + "\n";
        }

        prodSaleRate2VO.setsQuery1(sQuery1);

        return prodSaleRate2Mapper.getProdSaleRate2List(prodSaleRate2VO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleRate2ExcelList(ProdSaleRate2VO prodSaleRate2VO, SessionInfoVO sessionInfoVO) {

        prodSaleRate2VO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRate2VO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleRate2VO.getStoreCds()).equals("")) {
        StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleRate2VO.getStoreCds(), 3900));
            prodSaleRate2VO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";

        String[] arrDlvrInFgCol = prodSaleRate2VO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                prodSaleRate2VO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] list = prodSaleRate2VO.getArrDlvrInFgCol();

        for (int i = 0; i < list.length; i++) {
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '1' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS STIN_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '1' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS STIN_AMT" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '2' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS DLVR_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '2' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS DLVR_AMT" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '3' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END)	AS PACK_QTY" + list[i] + "\n";
            sQuery1 += ", SUM(CASE WHEN DLVR_ORDER_FG = '3' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END)	AS PACK_AMT" + list[i] + "\n";
        }

        prodSaleRate2VO.setsQuery1(sQuery1);
        
        return prodSaleRate2Mapper.getProdSaleRate2ExcelList(prodSaleRate2VO);
    }
}