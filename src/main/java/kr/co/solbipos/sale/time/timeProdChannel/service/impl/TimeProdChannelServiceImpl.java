package kr.co.solbipos.sale.time.timeProdChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.time.timeProdChannel.service.TimeProdChannelService;
import kr.co.solbipos.sale.time.timeProdChannel.service.TimeProdChannelVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeProdChannelServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품별시간대매출(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.20   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeProdChannelService")
@Transactional
public class TimeProdChannelServiceImpl implements TimeProdChannelService {

    private final TimeProdChannelMapper timeProdChannelMapper;
    private final PopupMapper popupMapper;

    public TimeProdChannelServiceImpl(TimeProdChannelMapper timeProdChannelMapper, PopupMapper popupMapper) {
        this.timeProdChannelMapper = timeProdChannelMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품별시간대매출(채널별) 조회 */
    @Override
    public List<DefaultMap<String>> getTimeProdChannelList(TimeProdChannelVO timeProdChannelVO, SessionInfoVO sessionInfoVO){
        timeProdChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        timeProdChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdChannelVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdChannelVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdChannelVO.getStoreCds(), 3900));
            timeProdChannelVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdChannelVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeProdChannelVO.getProdCds(), 3900));
            timeProdChannelVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (timeProdChannelVO.getStoreHqBrandCd() == "" || timeProdChannelVO.getStoreHqBrandCd() == null || timeProdChannelVO.getProdHqBrandCd() == "" || timeProdChannelVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdChannelVO.getUserBrands().split(",");
                timeProdChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = timeProdChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                timeProdChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = timeProdChannelVO.getArrDlvrInFgCol(); // 주문채널


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

        timeProdChannelVO.setsQuery1(sQuery1);
        timeProdChannelVO.setsQuery2(sQuery2);

        return timeProdChannelMapper.getTimeProdChannelList(timeProdChannelVO);
    }

    /** 상품별시간대매출(채널별) 엑셀다운로드 */
    @Override
    public List<DefaultMap<String>> getTimeProdChannelExcelList(TimeProdChannelVO timeProdChannelVO, SessionInfoVO sessionInfoVO){
        timeProdChannelVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        timeProdChannelVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeProdChannelVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdChannelVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdChannelVO.getStoreCds(), 3900));
            timeProdChannelVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdChannelVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(timeProdChannelVO.getProdCds(), 3900));
            timeProdChannelVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (timeProdChannelVO.getStoreHqBrandCd() == "" || timeProdChannelVO.getStoreHqBrandCd() == null || timeProdChannelVO.getProdHqBrandCd() == "" || timeProdChannelVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdChannelVO.getUserBrands().split(",");
                timeProdChannelVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        String[] arrDlvrInFgCol = timeProdChannelVO.getDlvrInFgCol().split(",");

        if(arrDlvrInFgCol.length > 0){
            if(arrDlvrInFgCol[0] != null && !"".equals(arrDlvrInFgCol[0])){
                timeProdChannelVO.setArrDlvrInFgCol(arrDlvrInFgCol);
            }
        }

        String[] orderFg = {"STIN","DLVR","PACK"}; // 내점, 배달, 포장
        String[] list = timeProdChannelVO.getArrDlvrInFgCol(); // 주문채널


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

        timeProdChannelVO.setsQuery1(sQuery1);
        timeProdChannelVO.setsQuery2(sQuery2);

        return timeProdChannelMapper.getTimeProdChannelExcelList(timeProdChannelVO);
    }
}
