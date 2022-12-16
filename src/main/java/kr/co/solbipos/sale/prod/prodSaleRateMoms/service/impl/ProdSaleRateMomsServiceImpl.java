package kr.co.solbipos.sale.prod.prodSaleRateMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodSaleRateMoms.service.ProdSaleRateMomsService;
import kr.co.solbipos.sale.prod.prodSaleRateMoms.service.ProdSaleRateMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdSaleRateMomsServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.13   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2022.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleRateMomsService")
@Transactional
public class ProdSaleRateMomsServiceImpl implements ProdSaleRateMomsService {

    private final ProdSaleRateMomsMapper prodSaleRateMomsMapper;

    public ProdSaleRateMomsServiceImpl(ProdSaleRateMomsMapper prodSaleRateMomsMapper) {
        this.prodSaleRateMomsMapper = prodSaleRateMomsMapper;
    }

    /** 상품판매비율 조회 */
    @Override
    public List<DefaultMap<String>> getProdSaleRateList(ProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO){
        prodSaleRateMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodSaleRateMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRateMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodSaleRateMomsVO.getStoreCds().split(",");
        prodSaleRateMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (prodSaleRateMomsVO.getProdCds() != null && !"".equals(prodSaleRateMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleRateMomsVO.getProdCds().split(",");
            prodSaleRateMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (prodSaleRateMomsVO.getStoreHqBrandCd() == "" || prodSaleRateMomsVO.getStoreHqBrandCd() == null || prodSaleRateMomsVO.getProdHqBrandCd() == "" || prodSaleRateMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleRateMomsVO.getUserBrands().split(",");
                prodSaleRateMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = prodSaleRateMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                prodSaleRateMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = prodSaleRateMomsVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
        }

        // 내점, 배달, 포장의 주문채널별 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            for (int i = 0; i < list.length; i++) {
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";

                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
            }
        }

        prodSaleRateMomsVO.setsQuery1(sQuery1);
        prodSaleRateMomsVO.setsQuery2(sQuery2);

        return prodSaleRateMomsMapper.getProdSaleRateList(prodSaleRateMomsVO);
    }

    /** 상품판매비율 조회(엑셀용) */
    @Override
    public List<DefaultMap<String>> getProdSaleRateExcelList(ProdSaleRateMomsVO prodSaleRateMomsVO, SessionInfoVO sessionInfoVO){
        prodSaleRateMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        prodSaleRateMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRateMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodSaleRateMomsVO.getStoreCds().split(",");
        prodSaleRateMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (prodSaleRateMomsVO.getProdCds() != null && !"".equals(prodSaleRateMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleRateMomsVO.getProdCds().split(",");
            prodSaleRateMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (prodSaleRateMomsVO.getStoreHqBrandCd() == "" || prodSaleRateMomsVO.getStoreHqBrandCd() == null || prodSaleRateMomsVO.getProdHqBrandCd() == "" || prodSaleRateMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleRateMomsVO.getUserBrands().split(",");
                prodSaleRateMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = prodSaleRateMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                prodSaleRateMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = prodSaleRateMomsVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
        }

        // 내점, 배달, 포장의 주문채널별 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            for (int i = 0; i < list.length; i++) {
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery1 += ", " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";

                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", SUM(CASE WHEN DLVR_ORDER_FG = '" + j + "' AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
            }
        }

        prodSaleRateMomsVO.setsQuery1(sQuery1);
        prodSaleRateMomsVO.setsQuery2(sQuery2);

        return prodSaleRateMomsMapper.getProdSaleRateExcelList(prodSaleRateMomsVO);
    }
}
