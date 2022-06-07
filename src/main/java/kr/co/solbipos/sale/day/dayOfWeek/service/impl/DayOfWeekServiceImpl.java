package kr.co.solbipos.sale.day.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.day.service.impl.DayMapper;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekService;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : DayOfWeekServiceImpl.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 요일별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.29  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("dayOfWeekService")
@Transactional
public class DayOfWeekServiceImpl implements DayOfWeekService {
    private final DayOfWeekMapper dayOfWeekMapper;
    private final DayMapper dayMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayOfWeekServiceImpl(DayOfWeekMapper dayOfWeekMapper, DayMapper dayMapper) {
        this.dayOfWeekMapper = dayOfWeekMapper;
        this.dayMapper = dayMapper;
    }

    /** 주간종합탭 - 주간종합조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTotalList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {
        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        // 결제수단 array 값 세팅
        dayOfWeekVO.setArrPayCol(dayOfWeekVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayOfWeekVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dayOfWeekVO.setPivotPayCol(pivotPayCol);

        return dayOfWeekMapper.getDayOfWeekTotalList(dayOfWeekVO);
    }

    /** 할인구별별탭 - 할인구분별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekDcList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {
        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        // 할인구분 array 값 세팅
        dayOfWeekVO.setArrDcCol(dayOfWeekVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = dayOfWeekVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        dayOfWeekVO.setPivotDcCol(pivotDcCol);

        return dayOfWeekMapper.getDayOfWeekDcList(dayOfWeekVO);
    }

    /** 과면세별탭 - 과면세별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTaxList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {
        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        return dayOfWeekMapper.getDayOfWeekTaxList(dayOfWeekVO);
    }

    /** 시간대별탭 - 시간대별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTimeList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {
        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        if(dayOfWeekVO.getOptionFg().equals("time")){ // 시간대
            // 매출 시간대 설정
            int iSaleDateStart = Integer.parseInt(dayOfWeekVO.getStartTime());
            int iSaleDateEnd = Integer.parseInt(dayOfWeekVO.getEndTime());

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
                sQuery1 += ", NVL(SUM(tssh.REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T"  + i +  "\n";
                sQuery1 += ", NVL(SUM(tssh.SALE_CNT_T" + i + "), 0) AS SALE_CNT_T"  + i +  "\n";
                sQuery1 += ", NVL(SUM(tssh.TOT_GUEST_CNT_T" + i + "), 0) AS TOT_GUEST_CNT_T"  + i +  "\n";

                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + i +  "\n";
                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT_T"  + i +  "\n";
                sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN GUEST_CNT_1 ELSE 0 END) AS TOT_GUEST_CNT_T"  + i +  "\n";

            }
        } else if(dayOfWeekVO.getOptionFg().equals("timeSlot")){
            DayVO dayVO = new DayVO();
            dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
            dayOfWeekVO.setStoreCd(sessionInfoVO.getStoreCd());
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

        dayOfWeekVO.setsQuery1(sQuery1);
        dayOfWeekVO.setsQuery2(sQuery2);

        return dayOfWeekMapper.getDayOfWeekTimeList(dayOfWeekVO);
    }

    /** 상품분류별탭 - 상품분류별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekProdClassList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {
        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
//        dayOfWeekVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayOfWeekVO.setLevel("Level" + dayOfWeekVO.getLevel());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
            dayOfWeekVO.setStoreCdList(storeCds);
        }
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
            String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
            dayOfWeekVO.setStoreCdList(storeCds);
        }

        // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
        dayOfWeekVO.setArrProdClassCd(dayOfWeekVO.getStrProdClassCd().split(","));

        // 쿼리조회를 위한 변수
        String pivotProdClassCol1 = "";
        String pivotProdClassCol2 = "";
        String pivotProdClassCol3 = "";
        String strAmt = "";
        String strQty = "";

        for(int i=0; i<  dayOfWeekVO.getArrProdClassCd().length; i++) {
            strAmt += (strAmt.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_REAL_SALE_AMT, 0)";
            strQty += (strQty.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_SALE_QTY, 0)";
            pivotProdClassCol1 += (pivotProdClassCol1.equals("") ? "" : ", ") + "tba.PAY" + (i+1) + "_REAL_SALE_AMT, tba.PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol2 += (pivotProdClassCol2.equals("") ? "" : ", ") + "NVL(SUM(PAY" + (i+1) + "_REAL_SALE_AMT), 0) AS PAY" + (i+1) + "_REAL_SALE_AMT, NVL(SUM(PAY" + (i+1) + "_SALE_QTY), 0) AS PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol3 += (pivotProdClassCol3.equals("") ? "" : ", ") + "'" + dayOfWeekVO.getArrProdClassCd()[i]  + "' AS PAY" + (i+1);
        }
        strAmt = "(" + strAmt + ") AS TOT_REAL_SALE_AMT, ";
        strQty = "(" + strQty + ") AS TOT_SALE_QTY, ";

        dayOfWeekVO.setPivotProdClassCol1(strAmt + strQty + pivotProdClassCol1);
        dayOfWeekVO.setPivotProdClassCol2(pivotProdClassCol2);
        dayOfWeekVO.setPivotProdClassCol3(pivotProdClassCol3);

        return dayOfWeekMapper.getDayOfWeekProdClassList(dayOfWeekVO);
    }

    /** 코너별탭 - 코너별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekCornerList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        dayOfWeekVO.setArrCornerCol(dayOfWeekVO.getStoreCornerCd().split(",")); // 코너구분 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotCornerCol = "";
        String arrCornerCol[] = dayOfWeekVO.getStoreCornerCd().split(",");
        for(int i=0; i < arrCornerCol.length; i++) {
            pivotCornerCol += (pivotCornerCol.equals("") ? "" : ",") + "'"+arrCornerCol[i]+"'"+" AS CORNR_"+arrCornerCol[i];
        }
        dayOfWeekVO.setPivotCornerCol(pivotCornerCol);

        return dayOfWeekMapper.getDayOfWeekCornerList(dayOfWeekVO);
    }

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTableList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 외식테이블 콤보박스
        if(dayOfWeekVO.getTableCd() == null)
        {
            // 외식테이블구분 array 값 세팅
            dayOfWeekVO.setArrTableCol(dayOfWeekVO.getTableCol().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "";
            String arrTableCol[] = dayOfWeekVO.getTableCol().split(",");
            for(int i=0; i < arrTableCol.length; i++) {
                pivotTableCol += (pivotTableCol.equals("") ? "" : ",") + "'"+arrTableCol[i]+"'"+" AS TBL"+arrTableCol[i];
            }
            dayOfWeekVO.setPivotTableCol(pivotTableCol);
        }
        else
        {
            // 외식테이블구분 array 값 세팅
            dayOfWeekVO.setArrTableCol(dayOfWeekVO.getTableCd().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "'"+dayOfWeekVO.getTableCd()+"'"+" AS TBL"+dayOfWeekVO.getTableCd();
            dayOfWeekVO.setPivotTableCol(pivotTableCol);
        }

        return dayOfWeekMapper.getDayOfWeekTableList(dayOfWeekVO);
    }

    /** 포스별탭 - 포스별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekPosList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

        dayOfWeekVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        // 포스구분 array 값 세팅
//        dayOfWeekVO.setArrPosCol(dayOfWeekVO.getPosCol().split(","));
//        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
//        String pivotPosCol = "";
//        String arrPosCol[] = dayOfWeekVO.getPosCol().split(",");
//        for(int i=0; i < arrPosCol.length; i++) {
//            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS_"+arrPosCol[i];
//        }
//        dayOfWeekVO.setPivotPosCol(pivotPosCol);

        List<DefaultMap<Object>> result =  dayOfWeekMapper.getDayOfWeekPosList(dayOfWeekVO);

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
                map.put("YOIL_NUM", result.get(i).get("yoilNum"));
                map.put("YOIL", result.get(i).get("yoil"));
                // 매장별 정보
                map.put("POS_" + result.get(i).get("storePosNo") + "_SALE_AMT", result.get(i).get("saleAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_DC_AMT", result.get(i).get("dcAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_REAL_SALE_AMT", result.get(i).get("realSaleAmt"));
                map.put("POS_" + result.get(i).get("storePosNo") + "_SALE_QTY", result.get(i).get("saleQty"));
            }
        }

        return selectList;
    }
}