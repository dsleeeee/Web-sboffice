package kr.co.solbipos.sale.day.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /** 일자별 - 할인 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDcColList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        return dayMapper.getDcColList(dayVO);
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

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return dayMapper.getPosColList(dayVO);
    }

    /** 일자별(일별종합 탭) - 일별종합 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayTotalList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
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

        return dayMapper.getDayTotalList(dayVO);
    }


    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayStoreDtlList(dayVO);
    }


    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO, SessionInfoVO sessionInfoVO) {
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

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
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        return dayMapper.getDayTaxList(dayVO);
    }

    /** 일자별(시간대별 탭) - 시간대별 리스트 조회 */
    public List<DefaultMap<String>> getDayTimeList(DayVO dayVO, SessionInfoVO sessionInfoVO){
        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";
        String sQuery4 = "";
        String sIn = "";

        //매출 발생 시간
        String sSaleDate ="";

        // 매출 시간대 설정
        int iSaleDateStart = 0;
        int iSaleDateEnd = 23;

        // 시간대 '전체' 선택 시
        if(dayVO.getSaleTime() == null){

            for(int i = 0; i <= 3; i++) {
                sQuery1 +=", tssh.REAL_SALE_AMT_T"  + i +  "\n";
                sQuery1 +=", tssh.SALE_CNT_T"  + i +  "\n";
                sQuery1 +=", tssh.TOT_GUEST_CNT_T"  + i +  "\n";

                sQuery2 += ", SUM(tssh.REAL_SALE_AMT_T" + i + ") AS REAL_SALE_AMT_T" + i + "\n";
                sQuery2 += ", SUM(tssh.SALE_CNT_T" + i + ") AS SALE_CNT_T" + i + "\n";
                sQuery2 += ", SUM(tssh.TOT_GUEST_CNT_T" + i + ") AS TOT_GUEST_CNT_T" + i + "\n";

            }

            sQuery3 += ", SUM(REAL_SALE_AMT) AS REAL_SALE_AMT \n";
            sQuery3 += ", SUM(SALE_FG) AS SALE_CNT \n";
            sQuery3 += ", SUM(TOT_GUEST_CNT) AS TOT_GUEST_CNT \n";

            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T0" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T0" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('00','01','02','03','04','05','06') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T0" + "\n";

            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T1" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T1" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('07','08','09','10') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T1" + "\n";

            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T2" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T2" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('11','12','13','14','15') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T2" + "\n";

            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_T3" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN SALE_FG ELSE 0 END) AS SALE_CNT_T3" + "\n";
            sQuery4 +=", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN ('16','17','18','19','20','21','22','23') THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_T3" + "\n";

        }else{

            // 매출 시간대 설정(심야,아침,점심,저녁)
            if(dayVO.getSaleTime() == SaleTimeFg.NIGHT){
                iSaleDateStart = 0;
                iSaleDateEnd = 6;
            }else if(dayVO.getSaleTime() == SaleTimeFg.MORNING){
                iSaleDateStart = 7;
                iSaleDateEnd = 10;
            }else if(dayVO.getSaleTime() == SaleTimeFg.LUNCH){
                iSaleDateStart = 11;
                iSaleDateEnd = 15;
            }else if(dayVO.getSaleTime() == SaleTimeFg.EVENING){
                iSaleDateStart = 16;
                iSaleDateEnd = 23;
            }

            for(int i = iSaleDateStart; i <= iSaleDateEnd; i++) {

                sSaleDate = i < 10 ? "0" + String.valueOf(i) : String.valueOf(i);

                sIn += ",'" + sSaleDate + "'";

                sQuery1 += ", tssh.REAL_SALE_AMT_" + sSaleDate + "\n";
                sQuery1 += ", tssh.SALE_CNT_" + sSaleDate + "\n";
                sQuery1 += ", tssh.TOT_GUEST_CNT_" + sSaleDate + "\n";

                sQuery2 += ", SUM(tssh.REAL_SALE_AMT_" + sSaleDate + ") AS REAL_SALE_AMT_" + sSaleDate + "\n";
                sQuery2 += ", SUM(tssh.SALE_CNT_" + sSaleDate + ") AS SALE_CNT_" + sSaleDate + "\n";
                sQuery2 += ", SUM(tssh.TOT_GUEST_CNT_" + sSaleDate + ") AS TOT_GUEST_CNT_" + sSaleDate + "\n";

                sQuery4 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) = '" + sSaleDate + "' THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_" + sSaleDate + "\n";
                sQuery4 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) = '" + sSaleDate + "' THEN SALE_FG ELSE 0 END) AS SALE_CNT_" + sSaleDate + "\n";
                sQuery4 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) = '" + sSaleDate + "' THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT_" + sSaleDate + "\n";
            }

            sQuery3 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN (" + sIn.substring(1, sIn.length()) + ") THEN REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT" + "\n";
            sQuery3 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN (" + sIn.substring(1, sIn.length()) + ") THEN SALE_FG ELSE 0 END) AS SALE_CNT" + "\n";
            sQuery3 += ", SUM(CASE WHEN SUBSTR(REG_DT, 9, 2) IN (" + sIn.substring(1, sIn.length()) + ") THEN TOT_GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT" + "\n";

        }

        dayVO.setsQuery1(sQuery1);
        dayVO.setsQuery2(sQuery2);
        dayVO.setsQuery3(sQuery3);
        dayVO.setsQuery4(sQuery4);

        return dayMapper.getDayTimeList(dayVO);
    }

    /** 포스별 - 포스별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayPosList(DayVO dayVO, SessionInfoVO sessionInfoVO) {

        dayVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if(!StringUtil.getOrBlank(dayVO.getStoreCd()).equals("")) {
            dayVO.setArrStoreCd(dayVO.getStoreCd().split(","));
        }

        // 포스구분 array 값 세팅
        dayVO.setArrPosCol(dayVO.getPosCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPosCol = "";
        String arrPosCol[] = dayVO.getPosCol().split(",");
        for(int i=0; i < arrPosCol.length; i++) {
            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS"+arrPosCol[i];
        }
        dayVO.setPivotPosCol(pivotPosCol);

        return dayMapper.getDayPosList(dayVO);
    }
}