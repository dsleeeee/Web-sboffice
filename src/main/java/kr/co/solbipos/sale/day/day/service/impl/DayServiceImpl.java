package kr.co.solbipos.sale.day.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dayService")
public class DayServiceImpl implements DayService {
    private final DayMapper dayMapper;
    private final MessageService messageService;

    @Autowired
    public DayServiceImpl(DayMapper dayMapper, MessageService messageService) {
        this.dayMapper = dayMapper;
        this.messageService = messageService;
    }


    /** 일자별 - 결제수단 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPayColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getPayColList(dayVO);
    }
    /** 일자별 - 결제수단 컬럼 리스트 조회(현금영수증 포함) */
    @Override
    public List<DefaultMap<String>> getPayColAddList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getPayColAddList(dayVO);
    }


    /** 일자별 - 할인 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getDcColList(dayVO);
    }

    /** 코너별 탭 - 코너 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getCornerColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dayMapper.getCornerColList(dayVO);
    }

    /** 외식테이블 탭 - 외식테이블 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getTableColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dayMapper.getTableColList(dayVO);
    }

    /** 포스별 탭 - 포스 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getPosColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return dayMapper.getPosColList(dayVO);
    }




    /** 일자별(일별종합 탭) - 일별종합 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayTotalList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

//        // 결제수단 array 값 세팅
//        dayVO.setArrPayCol(dayVO.getPayCol().split(","));
//        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
//        String pivotPayCol = "";
//        String arrPayCol[] = dayVO.getPayCol().split(",");
//        for(int i=0; i < arrPayCol.length; i++) {
//            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
//        }
//        dayVO.setPivotPayCol(pivotPayCol);


        /** PAY_CD = 02 현금,현금영수증 분리 */
        // 결제수단 array 값 세팅
//        dayVO.setArrPayCol(dayVO.getPayCol().split(","));
        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외
            if(! (("02").equals(arrPayCol[i]) || ("021").equals(arrPayCol[i])) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayVO.setPivotPayCol(pivotPayCol);
        dayVO.setArrPayCol(payCol.split(","));

        return dayMapper.getDayTotalList(dayVO);
    }

    @Override
    public List<DefaultMap<String>> getDayCashTotalList(DayVO dayVO, SessionInfoVO sessionInfoVO) {        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        String payCol= "";
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            // 현금,현금영수증 제외 + payCd길이가 1인거 = 현금(.. 인애들 제외
            if(! (arrPayCol[i].length() == 1) ) {
                pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'" + arrPayCol[i] + "'" + " AS PAY" + arrPayCol[i];
                payCol += (payCol.equals("") ? "" : ",") + arrPayCol[i];
            }
        }
        dayVO.setPivotPayCol(pivotPayCol);
        dayVO.setArrPayCol(payCol.split(","));

        return dayMapper.getDayCashTotalList(dayVO);
    }


    /** 매장별 매출현황 팝업 - 매장별 매출현황 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayStoreDtlList(dayVO);
    }



    /** 매장별 매출현황 팝업 - 매장별 매출현황 조회 */
    @Override
    public List<DefaultMap<String>> getDayProdDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayProdDtlList(dayVO);
    }


    /** 매장별 매출현황 팝업 - 매장별 매출현황 조회 */
    @Override
    public List<DefaultMap<String>> getDayDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayDtlList(dayVO);
    }


    /** 매장별 매출현황 팝업 - 매장별 매출현황 조회 */
    @Override
    public List<DefaultMap<String>> getDaySumAvgList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDaySumAvgList(dayVO);
    }


    /** 매장별 할인내역 팝업 - 매장별 할인내역 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 할인구분 array 값 세팅
        dayVO.setArrDcCol(dayVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = dayVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        dayVO.setPivotDcCol(pivotDcCol);

        return dayMapper.getDayStoreDcList(dayVO);
    }


    /** 일자별(할인구분별 탭) - 할인구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayDcList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 할인구분 array 값 세팅
        dayVO.setArrDcCol(dayVO.getDcCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotDcCol = "";
        String arrDcCol[] = dayVO.getDcCol().split(",");
        for(int i=0; i < arrDcCol.length; i++) {
            pivotDcCol += (pivotDcCol.equals("") ? "" : ",") + "'"+arrDcCol[i]+"'"+" AS DC"+arrDcCol[i];
        }
        dayVO.setPivotDcCol(pivotDcCol);

        return dayMapper.getDayDcList(dayVO);
    }

    /** 일자별(과면세별 탭) - 과면세 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayTaxList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayTaxList(dayVO);
    }

    /** 일자별(시간대별 탭) - 시간대별 리스트 조회 */
    public List<DefaultMap<String>> getDayTimeList(DayVO dayVO, SessionInfoVO sessionInfoVO){
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        // 매출 시간대 설정
        int iSaleDateStart = Integer.parseInt(dayVO.getStartTime());
        int iSaleDateEnd = Integer.parseInt(dayVO.getEndTime());

        for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {
            sQuery1 += ", NVL(SUM(tssh.REAL_SALE_AMT_T" + i + "), 0) AS REAL_SALE_AMT_T"  + i +  "\n";
            sQuery1 += ", NVL(SUM(tssh.SALE_CNT_T" + i + "), 0) AS SALE_CNT_T"  + i +  "\n";
            sQuery1 += ", NVL(SUM(tssh.TOT_GUEST_CNT_T" + i + "), 0) AS TOT_GUEST_CNT_T"  + i +  "\n";

            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T"  + i +  "\n";
            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN SALE_CNT ELSE 0 END) AS SALE_CNT_T"  + i +  "\n";
            sQuery2 += ", SUM(CASE WHEN SALE_HOUR = " + i + " THEN GUEST_CNT_1 ELSE 0 END) AS TOT_GUEST_CNT_T"  + i +  "\n";

        }
        dayVO.setsQuery1(sQuery1);
        dayVO.setsQuery2(sQuery2);
        return dayMapper.getDayTimeList(dayVO);
    }

    /** 일자별(상품분류 탭) - 상품분류 MAX(depth) 값 가져오기 */
    @Override
    public int getDayProdClassMaxLevel(DayVO dayVO, SessionInfoVO sessionInfoVO){

        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        dayVO.setpProdClassCd("00000");

        return dayMapper.getDayProdClassMaxLevel(dayVO);
    }

    /** 일자별(상품분류 탭) - 분류레벨에 따른 상품분류 가져오기 */
    public List<DefaultMap<String>> getDayProdClassLevel(DayVO dayVO, SessionInfoVO sessionInfoVO){

        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        dayVO.setpProdClassCd("00000");

        return dayMapper.getDayProdClassLevel(dayVO);
    }

    /** 일자별(상품분류 탭) - 상품분류별 리스트 조회 */
    public List<DefaultMap<String>> getDayProdClassList(DayVO dayVO, SessionInfoVO sessionInfoVO){
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
//        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayVO.setpProdClassCd("00000");
        dayVO.setLevel("Level" + dayVO.getLevel());

        // storeCd 관련처리
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사면서 매장 검색조건이 있는 경우 배열변수에 넣는다.
            if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
                dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
            }
        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setArrStoreCd(sessionInfoVO.getStoreCd().split(","));
        }

        // 레벨에 따른 분류값 가져와서 배열변수에 넣음.
        dayVO.setArrProdClassCd(dayVO.getStrProdClassCd().split(","));

        // 쿼리조회를 위한 변수
        String pivotProdClassCol1 = "";
        String pivotProdClassCol2 = "";
        String pivotProdClassCol3 = "";
        String strAmt = "";
        String strQty = "";

        for(int i=0; i<  dayVO.getArrProdClassCd().length; i++) {
            strAmt += (strAmt.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_REAL_SALE_AMT, 0)";
            strQty += (strQty.equals("") ? "" : "+") +"NVL(tba.PAY" + (i+1) +"_SALE_QTY, 0)";
            pivotProdClassCol1 += (pivotProdClassCol1.equals("") ? "" : ",") + "tba.PAY" + (i+1) + "_REAL_SALE_AMT, tba.PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol2 += (pivotProdClassCol2.equals("") ? "" : ",") + "SUM(PAY" + (i+1) + "_REAL_SALE_AMT) AS PAY" + (i+1) + "_REAL_SALE_AMT, SUM(PAY" + (i+1) + "_SALE_QTY) AS PAY" + (i+1) + "_SALE_QTY";
            pivotProdClassCol3 += (pivotProdClassCol3.equals("") ? "" : ",") + "'" + dayVO.getArrProdClassCd()[i]  + "' AS PAY" + (i+1);
        }
        strAmt = "(" + strAmt + ") AS TOT_REAL_SALE_AMT,";
        strQty = "(" + strQty + ") AS TOT_SALE_QTY,";

        dayVO.setPivotProdClassCol1(strAmt + strQty + pivotProdClassCol1);
        dayVO.setPivotProdClassCol2(pivotProdClassCol2);
        dayVO.setPivotProdClassCol3(pivotProdClassCol3);

        return dayMapper.getDayProdClassList(dayVO);
    }

    /** 코너별 - 코너별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayCornerList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        dayVO.setArrCornerCol(dayVO.getStoreCornerCd().split(",")); // 코너구분 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotCornerCol = "";
        String arrCornerCol[] = dayVO.getStoreCornerCd().split(",");
        for(int i=0; i < arrCornerCol.length; i++) {
            pivotCornerCol += (pivotCornerCol.equals("") ? "" : ",") + "'"+arrCornerCol[i]+"'"+" AS CORNR_"+arrCornerCol[i];
        }
        dayVO.setPivotCornerCol(pivotCornerCol);

        return dayMapper.getDayCornerList(dayVO);
    }

    /** 외식테이블별 - 외식테이블별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayTableList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 외식테이블 콤보박스
        if(dayVO.getTableCd() == null)
        {
            // 외식테이블구분 array 값 세팅
            dayVO.setArrTableCol(dayVO.getTableCol().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "";
            String arrTableCol[] = dayVO.getTableCol().split(",");
            for(int i=0; i < arrTableCol.length; i++) {
                pivotTableCol += (pivotTableCol.equals("") ? "" : ",") + "'"+arrTableCol[i]+"'"+" AS TBL"+arrTableCol[i];
            }
            dayVO.setPivotTableCol(pivotTableCol);
        }
        else
        {
            // 외식테이블구분 array 값 세팅
            dayVO.setArrTableCol(dayVO.getTableCd().split(","));
            // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
            String pivotTableCol = "'"+dayVO.getTableCd()+"'"+" AS TBL"+dayVO.getTableCd();
            dayVO.setPivotTableCol(pivotTableCol);
        }

        return dayMapper.getDayTableList(dayVO);
    }

    /** 상품매출 상세내역 팝업 - 상품매출 상세내역 조회 */
    @Override
    public List<DefaultMap<Object>> getDayProdSaleDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        // 결제수단 array 값 세팅
        dayVO.setArrPayCol(dayVO.getPayCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPayCol = "";
        String arrPayCol[] = dayVO.getPayCol().split(",");
        for(int i=0; i < arrPayCol.length; i++) {
            pivotPayCol += (pivotPayCol.equals("") ? "" : ",") + "'"+arrPayCol[i]+"'"+" AS PAY"+arrPayCol[i];
        }
        dayVO.setPivotPayCol(pivotPayCol);

        return dayMapper.getDayProdSaleDtlList(dayVO);
    }

    /** 포스별 - 포스별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPosList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

//        // 포스구분 array 값 세팅
//        dayVO.setArrPosCol(dayVO.getPosCol().split(","));
//        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
//        String pivotPosCol = "";
//        String arrPosCol[] = dayVO.getPosCol().split(",");
//        for(int i=0; i < arrPosCol.length; i++) {
//            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS_"+arrPosCol[i];
//        }
//        dayVO.setPivotPosCol(pivotPosCol);

        List<DefaultMap<Object>> result =  dayMapper.getDayPosList(dayVO);

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
                // 공통정보 날짜,요일
                map.put("SALE_DATE", result.get(i).get("saleDate"));
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

    /** 사원카드별탭 - 사원카드별 매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayEmpCardList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        dayVO.setEmpNo(sessionInfoVO.getEmpNo());
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayVO.getStoreCds().split(",");
        dayVO.setStoreCdList(storeCds);

        return dayMapper.getDayEmpCardList(dayVO);
    }
}