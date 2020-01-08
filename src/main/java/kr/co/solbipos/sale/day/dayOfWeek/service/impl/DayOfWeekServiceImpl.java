package kr.co.solbipos.sale.day.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekService;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

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

    /**
     * Constructor Injection
     */
    @Autowired
    public DayOfWeekServiceImpl(DayOfWeekMapper dayOfWeekMapper) {
        this.dayOfWeekMapper = dayOfWeekMapper;
    }

    /** 주간종합탭 - 주간종합조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTotalList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

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

    /** 할인구별별탭 - 할인구분별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekDcList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

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

    /** 과면세별탭 - 과면세별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTaxList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        return dayOfWeekMapper.getDayOfWeekTaxList(dayOfWeekVO);
    }

    /** 시간대별 - 시간대별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekTimeList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

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

        //매출 발생 시간
        String sSaleDate ="";

        // 매출 시간대 설정
        int iSaleDateStart = 0;
        int iSaleDateEnd = 23;

        // 시간대 '전체' 선택 시
        if(dayOfWeekVO.getSaleTime() == null) {

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
            if(dayOfWeekVO.getSaleTime() == SaleTimeFg.NIGHT) {
                iSaleDateStart = 0;
                iSaleDateEnd = 6;
            } else if(dayOfWeekVO.getSaleTime() == SaleTimeFg.MORNING) {
                iSaleDateStart = 7;
                iSaleDateEnd = 10;
            } else if(dayOfWeekVO.getSaleTime() == SaleTimeFg.LUNCH) {
                iSaleDateStart = 11;
                iSaleDateEnd = 15;
            } else if(dayOfWeekVO.getSaleTime() == SaleTimeFg.EVENING) {
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

        dayOfWeekVO.setsQuery1(sQuery1);
        dayOfWeekVO.setsQuery2(sQuery2);

        return dayOfWeekMapper.getDayOfWeekTimeList(dayOfWeekVO);
    }

    /** 외식테이블별 - 외식테이블별매출조회 */
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

    /** 포스별 - 포스별매출조회 */
    @Override
    public List<DefaultMap<Object>> getDayOfWeekPosList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO) {

        dayOfWeekVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayOfWeekVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayOfWeekVO.getStoreCds().split(",");
        dayOfWeekVO.setStoreCdList(storeCds);

        // 포스구분 array 값 세팅
        dayOfWeekVO.setArrPosCol(dayOfWeekVO.getPosCol().split(","));
        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotPosCol = "";
        String arrPosCol[] = dayOfWeekVO.getPosCol().split(",");
        for(int i=0; i < arrPosCol.length; i++) {
            pivotPosCol += (pivotPosCol.equals("") ? "" : ",") + "'"+arrPosCol[i]+"'"+" AS POS"+arrPosCol[i];
        }
        dayOfWeekVO.setPivotPosCol(pivotPosCol);

        return dayOfWeekMapper.getDayOfWeekPosList(dayOfWeekVO);
    }
}