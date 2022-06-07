package kr.co.solbipos.sale.day.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import kr.co.solbipos.sale.day.month.service.MonthService;
import kr.co.solbipos.sale.day.month.service.MonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

import java.util.ArrayList;
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
    private final DayMapper dayMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthServiceImpl(MonthMapper monthMapper, DayMapper dayMapper) {
        this.monthMapper = monthMapper;
        this.dayMapper = dayMapper;
    }

    /** 월별종합탭 - 월별종합조회 */
    @Override
    public List<DefaultMap<Object>> getMonthTotalList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());

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
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());
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
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());
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
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());
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

        if(monthVO.getOptionFg().equals("time")){ // 시간대

            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(monthVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(monthVO.getEndTime());

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                sQuery1 += ", NVL(SUM(tssh.REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T"  + i +  "\n";
                sQuery1 += ", NVL(SUM(tssh.SALE_CNT_T" + i + "), 0) AS SALE_CNT_T"  + i +  "\n";
                sQuery1 += ", NVL(SUM(tssh.TOT_GUEST_CNT_T" + i + "), 0) AS TOT_GUEST_CNT_T"  + i +  "\n";

                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + i +  "\n";
                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT_T"  + i +  "\n";
                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN GUEST_CNT_1 ELSE 0 END) AS TOT_GUEST_CNT_T"  + i +  "\n";
            }
        } else if(monthVO.getOptionFg().equals("timeSlot")){
            DayVO dayVO = new DayVO();
            dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
            monthVO.setStoreCd(sessionInfoVO.getStoreCd());
            List<DefaultMap<String>> timeSlotColList = dayMapper.getTimeSlotList(dayVO);
            for(int i = 0; i < timeSlotColList.size(); i++) {
                sQuery1 += ", NVL(SUM(tssh.REAL_SALE_AMT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS REAL_SALE_AMT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
                sQuery1 += ", NVL(SUM(tssh.SALE_CNT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS SALE_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
                sQuery1 += ", NVL(SUM(tssh.TOT_GUEST_CNT_T" + timeSlotColList.get(i).getStr("value").replace("~", "") + "), 0) AS TOT_GUEST_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";

                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
                sQuery2 += ", SUM(CASE WHEN TIME_SLOT = " + timeSlotColList.get(i).getStr("value").replace("~", "") + " THEN GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T"  + timeSlotColList.get(i).getStr("value").replace("~", "") +  "\n";
            }
        }

        monthVO.setsQuery1(sQuery1);
        monthVO.setsQuery2(sQuery2);

        return monthMapper.getMonthTimeList(monthVO);
    }

    /** 상품분류별탭 - 상품분류별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdClassList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());
        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
//        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setLevel("Level" + monthVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = monthVO.getStoreCds().split(",");
            monthVO.setStoreCdList(storeCds);
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
            String[] storeCds = monthVO.getStoreCds().split(",");
            monthVO.setStoreCdList(storeCds);
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
            strAmt += (strAmt.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_REAL_SALE_AMT, 0)";
            strQty += (strQty.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_SALE_QTY, 0)";
            pivotProdClassCol1 += (pivotProdClassCol1.equals("") ? "" : ", ") + "tba.PAY" + (i+1) + "_REAL_SALE_AMT, tba.PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol2 += (pivotProdClassCol2.equals("") ? "" : ", ") + "NVL(SUM(PAY" + (i+1) + "_REAL_SALE_AMT), 0) AS PAY" + (i+1) + "_REAL_SALE_AMT, NVL(SUM(PAY" + (i+1) + "_SALE_QTY), 0) AS PAY" + (i+1) + "_SALE_QTY";
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

        monthVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setArrCornerCol(monthVO.getStoreCornerCd().split(",")); // 코너구분 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotCornerCol = "";
        String arrCornerCol[] = monthVO.getStoreCornerCd().split(",");
        for(int i=0; i < arrCornerCol.length; i++) {
            pivotCornerCol += (pivotCornerCol.equals("") ? "" : ",") + "'"+arrCornerCol[i]+"'"+" AS CORNR_"+arrCornerCol[i];
        }
        monthVO.setPivotCornerCol(pivotCornerCol);

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

        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        // 포스구분 array 값 세팅
//        monthVO.setArrPosCol(monthVO.getPosCol().split(","));
//        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
//        String pivotPosCol = "";
//        String arrPosCol[] = monthVO.getPosCol().split(",");
//        for(int i=0; i < arrPosCol.length; i++) {
//            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS_"+arrPosCol[i];
//        }
//        monthVO.setPivotPosCol(pivotPosCol);

        List<DefaultMap<Object>> result =  monthMapper.getMonthPosList(monthVO);

        List<DefaultMap<Object>> selectList = new ArrayList<DefaultMap<Object>>();

        DefaultMap<Object> map = new DefaultMap<>();

        for(int i = 0; i < result.size(); i++) {
            if(result.get(i).get("storePosNo").equals("TOTAL")){
                // 총매출정보
                map.put("TOT_SALE_AMT", result.get(i).get("saleAmt"));
                map.put("TOT_DC_AMT", result.get(i).get("dcAmt"));
                map.put("TOT_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("TOT_SALE_QTY", result.get(i).get("saleQty"));

                selectList.add(map);

                map = new DefaultMap<>();
            } else {
                // 공통정보 요일
                map.put("YEAR_MONTH", result.get(i).get("yearMonth"));
                // 매장별 정보
                map.put("POS_" + result.get(i).get("storePosNo") + "_SALE_AMT", result.get(i).get("saleAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_DC_AMT", result.get(i).get("dcAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_SALE_QTY", result.get(i).get("saleQty"));
            }
        }

        return selectList;
    }

    /** 사원카드별탭 - 사원카드별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getMonthEmpCardList(MonthVO monthVO, SessionInfoVO sessionInfoVO) {
        monthVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        monthVO.setEmpNo(sessionInfoVO.getEmpNo());
        monthVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthVO.getStoreCds().split(",");
        monthVO.setStoreCdList(storeCds);

        return monthMapper.getMonthEmpCardList(monthVO);
    }
}