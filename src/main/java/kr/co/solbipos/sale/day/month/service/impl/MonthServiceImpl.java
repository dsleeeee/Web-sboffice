package kr.co.solbipos.sale.day.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.month.service.MonthService;
import kr.co.solbipos.sale.day.month.service.MonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

import java.util.List;

/**
 * @Class Name : MonthServiceImpl.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 월별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("monthService")
@Transactional
public class MonthServiceImpl implements MonthService {
    private final MonthMapper monthMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthServiceImpl(MonthMapper monthMapper) {
        this.monthMapper = monthMapper;
    }

    /** 월별종합탭 - 월별종합조회 */
    @Override
    public List<DefaultMap<Object>> getMonthTotalList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        monthVO.setArrPayCol(monthVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = monthVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        monthVO.setPivotPayCol(pivotPayCol);

        return monthMapper.getMonthTotalList(monthVO);
    }

    /** 할인구별별탭 - 할인구분별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthDcList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        // 할인구분 array 값 세팅
        monthVO.setArrDcCol(monthVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = monthVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        monthVO.setPivotDcCol(pivotDcCol);

        return monthMapper.getMonthDcList(monthVO);
    }

    /** 과면세별탭 - 과면세별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthTaxList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        return monthMapper.getMonthTaxList(monthVO);
    }

    /** 시간대별탭 - 시간대별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthTimeList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        //매출 발생 시간
        String sSaleDate ="";

        // 매출 시간대 설정
        int iSaleDateStart = 0;
        int iSaleDateEnd = 23;

        // 시간대 '전체' 선택 시
        if(monthVO.getSaleTime() == null) {

            for(int i = 0; i <= 3; i++) {
                sQuery1 += ", SUM(tssh.REAL_SALE_AMT_T" + i + ") AS REAL_SALE_AMT_T" + i + "\n";
                sQuery1 += ", SUM(tssh.SALE_CNT_T" + i + ") AS SALE_CNT_T" + i + "\n";
                sQuery1 += ", SUM(tssh.TOT_GUEST_CNT_T" + i + ") AS TOT_GUEST_CNT_T" + i + "\n";
            }

            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T0" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T0" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T0" + "\n";

            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T1" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T1" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T1" + "\n";

            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T2" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T2" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T2" + "\n";

            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T3" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T3" + "\n";
            sQuery2 +=", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T3" + "\n";
        }
        else
        {
            // 매출 시간대 설정(심야,아침,점심,저녁)
            if(monthVO.getSaleTime() == SaleTimeFg.NIGHT) {
                iSaleDateStart = 0;
                iSaleDateEnd = 6;
            } else if(monthVO.getSaleTime() == SaleTimeFg.MORNING) {
                iSaleDateStart = 7;
                iSaleDateEnd = 10;
            } else if(monthVO.getSaleTime() == SaleTimeFg.LUNCH) {
                iSaleDateStart = 11;
                iSaleDateEnd = 15;
            } else if(monthVO.getSaleTime() == SaleTimeFg.EVENING) {
                iSaleDateStart = 16;
                iSaleDateEnd = 23;
            }

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {

                // 10보다 작은건 01~09
                sSaleDate = i < 10 ? "0" + String.valueOf(i) : String.valueOf(i);

                sQuery1 += ", SUM(tssh.REAL_SALE_AMT_T" + sSaleDate + ") AS REAL_SALE_AMT_T" + sSaleDate + "\n";
                sQuery1 += ", SUM(tssh.SALE_CNT_T" + sSaleDate + ") AS SALE_CNT_T" + sSaleDate + "\n";
                sQuery1 += ", SUM(tssh.TOT_GUEST_CNT_T" + sSaleDate + ") AS TOT_GUEST_CNT_T" + sSaleDate + "\n";

                sQuery2 += ", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('" + sSaleDate + "') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T" + sSaleDate + "\n";
                sQuery2 += ", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('" + sSaleDate + "') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T" + sSaleDate + "\n";
                sQuery2 += ", (CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('" + sSaleDate + "') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T" + sSaleDate + "\n";
            }
        }

        monthVO.setsQuery1(sQuery1);
        monthVO.setsQuery2(sQuery2);

        return monthMapper.getMonthTimeList(monthVO);
    }

    /** 상품분류별탭 - 상품분류별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdClassList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setLevel("Level" + monthVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = monthVO.getStoreCds().split(",");
            monthVO.setStoreCdList(storeCds);
        }
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
        monthVO.setArrProdClassCd(monthVO.getStrProdClassCd().split(","));

        // 쿼리조회를 위한 변수
        String pivotProdClassCol1 = "";
        String pivotProdClassCol2 = "";
        String pivotProdClassCol3 = "";
        String strAmt = "";
        String strQty = "";

        for(int i=0; i<  monthVO.getArrProdClassCd().length; i++) {
            strAmt += (strAmt.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_SALE_AMT, 0)";
            strQty += (strAmt.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_SALE_QTY, 0)";
            pivotProdClassCol1 += (pivotProdClassCol1.equals("") ? "" : ", ") + "tba.PAY" + (i+1) + "_SALE_AMT, tba.PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol2 += (pivotProdClassCol2.equals("") ? "" : ", ") + "NVL(SUM(PAY" + (i+1) + "_SALE_AMT), 0) AS PAY" + (i+1) + "_SALE_AMT, NVL(SUM(PAY" + (i+1) + "_SALE_QTY), 0) AS PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol3 += (pivotProdClassCol3.equals("") ? "" : ", ") + "'" + monthVO.getArrProdClassCd()[i]  + "' AS PAY" + (i+1);
        }
        strAmt = "(" + strAmt + ") AS TOT_REAL_SALE_AMT, ";
        strQty = "(" + strQty + ") AS TOT_SALE_QTY, ";

        monthVO.setPivotProdClassCol1(strAmt + strQty + pivotProdClassCol1);
        monthVO.setPivotProdClassCol2(pivotProdClassCol2);
        monthVO.setPivotProdClassCol3(pivotProdClassCol3);

        return monthMapper.getMonthProdClassList(monthVO);
    }

    /** 코너별탭 - 코너별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthCornerList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 코너구분
        // 매장일때
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE)
        {
            // 코너구분 array 값 세팅
            monthVO.setArrCornerCol(monthVO.getCornerCol().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotCornerCol = "";
            String arrCornerCol[] = monthVO.getCornerCol().split(",");
            for(int i=0; i < arrCornerCol.length; i++) {
                pivotCornerCol += (pivotCornerCol.equals("") ? "" : ",") + "'"+arrCornerCol[i]+"'"+" AS CORNR_"+arrCornerCol[i];
            }
            monthVO.setPivotCornerCol(pivotCornerCol);
        }
        // 본사일때 전체매장선택시
        else if(monthVO.getStoreCd() == null)
        {
            // 코너구분 array 값 세팅
            monthVO.setArrCornerCol(monthVO.getCornerCol().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotCornerCol = "";
            String arrCornerCol[] = monthVO.getCornerCol().split(",");
            for(int i=0; i < arrCornerCol.length; i++) {
                pivotCornerCol += (pivotCornerCol.equals("") ? "" : ",") + "'"+arrCornerCol[i]+"'"+" AS CORNR_"+arrCornerCol[i];
            }
            monthVO.setPivotCornerCol(pivotCornerCol);
        }
        // 본사일때 특정매장선택시
        else
        {
            if(monthVO.getStoreCornerCd() != null) {
                // 코너구분 array 값 세팅
                monthVO.setArrCornerCol(monthVO.getStoreCornerCd().split(","));
                // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
                String pivotCornerCol = "'" + monthVO.getStoreCornerCd() + "'" + " AS CORNR_" + monthVO.getStoreCornerCd();
                monthVO.setPivotCornerCol(pivotCornerCol);
            }
        }

        return monthMapper.getMonthCornerList(monthVO);
    }

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthTableList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 외식테이블 콤보박스
        if(monthVO.getTableCd() == null)
        {
            // 외식테이블구분 array 값 세팅
            monthVO.setArrTableCol(monthVO.getTableCol().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "";
            String arrTableCol[] = monthVO.getTableCol().split(",");
            for(int i=0; i < arrTableCol.length; i++) {
                pivotTableCol += (pivotTableCol.equals("") ? "" : ",") + "'"+arrTableCol[i]+"'"+" AS TBL"+arrTableCol[i];
            }
            monthVO.setPivotTableCol(pivotTableCol);
        }
        else
        {
            // 외식테이블구분 array 값 세팅
            monthVO.setArrTableCol(monthVO.getTableCd().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "'"+monthVO.getTableCd()+"'"+" AS TBL"+monthVO.getTableCd();
            monthVO.setPivotTableCol(pivotTableCol);
        }

        return monthMapper.getMonthTableList(monthVO);
    }

    /** 포스별탭 - 포스별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthPosList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        // 포스구분 array 값 세팅
        monthVO.setArrPosCol(monthVO.getPosCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPosCol = "";
        String arrPosCol[] = monthVO.getPosCol().split(",");
        for(int i=0; i < arrPosCol.length; i++) {
            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS"+arrPosCol[i];
        }
        monthVO.setPivotPosCol(pivotPosCol);

        return monthMapper.getMonthPosList(monthVO);
    }
}