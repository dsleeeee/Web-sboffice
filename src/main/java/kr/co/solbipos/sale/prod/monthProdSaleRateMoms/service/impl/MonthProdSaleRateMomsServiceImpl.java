package kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.MonthProdSaleRateMomsService;
import kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.MonthProdSaleRateMomsVO;
import kr.co.solbipos.sale.prod.monthProdSaleRateMoms.service.impl.MonthProdSaleRateMomsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdSaleRateMomsServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품판매비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.03   권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.07.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthProdSaleRateMomsService")
@Transactional
public class MonthProdSaleRateMomsServiceImpl implements MonthProdSaleRateMomsService {

    private final MonthProdSaleRateMomsMapper monthProdSaleRateMomsMapper;

    public MonthProdSaleRateMomsServiceImpl(MonthProdSaleRateMomsMapper monthProdSaleRateMomsMapper) {
        this.monthProdSaleRateMomsMapper = monthProdSaleRateMomsMapper;
    }

    /** 상품판매비율 조회 */
    @Override
    public List<DefaultMap<String>> getProdSaleRateList(MonthProdSaleRateMomsVO monthProdSaleRateMomsVO, SessionInfoVO sessionInfoVO){
        monthProdSaleRateMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthProdSaleRateMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdSaleRateMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdSaleRateMomsVO.getStoreCds().split(",");
        monthProdSaleRateMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdSaleRateMomsVO.getProdCds() != null && !"".equals(monthProdSaleRateMomsVO.getProdCds())) {
            String[] prodCdList = monthProdSaleRateMomsVO.getProdCds().split(",");
            monthProdSaleRateMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (monthProdSaleRateMomsVO.getStoreHqBrandCd() == "" || monthProdSaleRateMomsVO.getStoreHqBrandCd() == null || monthProdSaleRateMomsVO.getProdHqBrandCd() == "" || monthProdSaleRateMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdSaleRateMomsVO.getUserBrands().split(",");
                monthProdSaleRateMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = monthProdSaleRateMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                monthProdSaleRateMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = monthProdSaleRateMomsVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
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

                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
            }
        }

        monthProdSaleRateMomsVO.setsQuery1(sQuery1);
        monthProdSaleRateMomsVO.setsQuery2(sQuery2);

        return monthProdSaleRateMomsMapper.getProdSaleRateList(monthProdSaleRateMomsVO);
    }

    /** 상품판매비율 조회(엑셀용) */
    @Override
    public List<DefaultMap<String>> getProdSaleRateExcelList(MonthProdSaleRateMomsVO monthProdSaleRateMomsVO, SessionInfoVO sessionInfoVO){
        monthProdSaleRateMomsVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthProdSaleRateMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdSaleRateMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdSaleRateMomsVO.getStoreCds().split(",");
        monthProdSaleRateMomsVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdSaleRateMomsVO.getProdCds() != null && !"".equals(monthProdSaleRateMomsVO.getProdCds())) {
            String[] prodCdList = monthProdSaleRateMomsVO.getProdCds().split(",");
            monthProdSaleRateMomsVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (monthProdSaleRateMomsVO.getStoreHqBrandCd() == "" || monthProdSaleRateMomsVO.getStoreHqBrandCd() == null || monthProdSaleRateMomsVO.getProdHqBrandCd() == "" || monthProdSaleRateMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdSaleRateMomsVO.getUserBrands().split(",");
                monthProdSaleRateMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = monthProdSaleRateMomsVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                monthProdSaleRateMomsVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = monthProdSaleRateMomsVO.getArrDlvrInFgCol(); // 주문채널


        // 내점, 배달, 포장의 수량과 실매출액
        for(int j = 1; j <= orderFg.length; j++) {
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery1 += ", " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";

            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY1" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY2" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_SALE_QTY3" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT1" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT2" + "\n";
            sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_REAL_SALE_AMT3" + "\n";
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

                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY1" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY2" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN TOT_SALE_QTY ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_SALE_QTY3" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'P') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT1" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' AND (SEL_TYPE_FG = 'N' OR SEL_TYPE_FG = 'S') THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT2" + "\n";
                sQuery2 += ", SUM(CASE WHEN (DLVR_ORDER_FG = '" + (j == 3 ? (j + "' OR DLVR_ORDER_FG = '4") : j) + "') AND DLVR_IN_FG = '" + list[i] + "' THEN REAL_SALE_AMT ELSE 0 END) AS " + orderFg[j-1] + "_DIFG" + list[i] + "_REAL_SALE_AMT3" + "\n";
            }
        }

        monthProdSaleRateMomsVO.setsQuery1(sQuery1);
        monthProdSaleRateMomsVO.setsQuery2(sQuery2);

        return monthProdSaleRateMomsMapper.getProdSaleRateExcelList(monthProdSaleRateMomsVO);
    }
}
