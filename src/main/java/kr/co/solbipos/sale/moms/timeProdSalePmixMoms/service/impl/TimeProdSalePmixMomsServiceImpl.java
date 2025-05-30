package kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service.TimeProdSalePmixMomsService;
import kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service.TimeProdSalePmixMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Class Name : TimeProdSalePmixMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 시간대별 상품매출(P.MIX)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeProdSalePmixMomsService")
@Transactional
public class TimeProdSalePmixMomsServiceImpl implements TimeProdSalePmixMomsService {
    private final TimeProdSalePmixMomsMapper timeProdSalePmixMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeProdSalePmixMomsServiceImpl(TimeProdSalePmixMomsMapper timeProdSalePmixMomsMapper, PopupMapper popupMapper) {
        this.timeProdSalePmixMomsMapper = timeProdSalePmixMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대별 상품매출(P.MIX) - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdSalePmixMomsList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO, SessionInfoVO sessionInfoVO) {

        timeProdSalePmixMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeProdSalePmixMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdSalePmixMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdSalePmixMomsVO.getStoreCds(), 3900));
            timeProdSalePmixMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (timeProdSalePmixMomsVO.getProdCds() != null && !"".equals(timeProdSalePmixMomsVO.getProdCds())) {
            String[] prodCdList = timeProdSalePmixMomsVO.getProdCds().split(",");
            timeProdSalePmixMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdSalePmixMomsVO.getStoreHqBrandCd() == "" || timeProdSalePmixMomsVO.getStoreHqBrandCd() == null || timeProdSalePmixMomsVO.getProdHqBrandCd() == "" || timeProdSalePmixMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdSalePmixMomsVO.getUserBrands().split(",");
                timeProdSalePmixMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(timeProdSalePmixMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                timeProdSalePmixMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                timeProdSalePmixMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";

            sQuery2 += ", T1.SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery2 += ", T1.REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery2 += ", T1.P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) / SUM(B.P_REAL_SALE_AMT1) AS TOT_P_MIX_SALE1" + "\n";

        sQuery2 += ", T1.TOT_SALE_QTY1" + "\n";
        sQuery2 += ", T1.TOT_REAL_SALE_AMT1" + "\n";
        sQuery2 += ", T1.TOT_P_MIX_SALE1" + "\n";

        // 시간대
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += (i < 10 ? "0" + i : i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");

        for (int i = 0; i < arrTimeCol.length; i++) {
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_SALE_QTY1, 0)) AS TIME_SALE_QTY1_" + arrTimeCol[i] + "\n";
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_REAL_SALE_AMT1, 0)) AS TIME_REAL_SALE_AMT1_" + arrTimeCol[i] + "\n";
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "', B.TIME_P_REAL_SALE_AMT1, 0) )) AS TIME_P_MIX_SALE1_" + arrTimeCol[i] + "\n";

            sQuery2 += ", T2.TIME_SALE_QTY1_" + arrTimeCol[i] + "\n";
            sQuery2 += ", T2.TIME_REAL_SALE_AMT1_" + arrTimeCol[i] + "\n";
            sQuery2 += ", T2.TIME_P_MIX_SALE1_" + arrTimeCol[i] + "\n";
        }

        timeProdSalePmixMomsVO.setsQuery1(sQuery1);
        timeProdSalePmixMomsVO.setsQuery2(sQuery2);
        timeProdSalePmixMomsVO.setsQuery3(sQuery3);

        return timeProdSalePmixMomsMapper.getTimeProdSalePmixMomsList(timeProdSalePmixMomsVO);
    }

    /** 시간대별 상품매출(P.MIX) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeProdSalePmixMomsExcelList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO, SessionInfoVO sessionInfoVO) {

        timeProdSalePmixMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeProdSalePmixMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeProdSalePmixMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeProdSalePmixMomsVO.getStoreCds(), 3900));
            timeProdSalePmixMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (timeProdSalePmixMomsVO.getProdCds() != null && !"".equals(timeProdSalePmixMomsVO.getProdCds())) {
            String[] prodCdList = timeProdSalePmixMomsVO.getProdCds().split(",");
            timeProdSalePmixMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeProdSalePmixMomsVO.getStoreHqBrandCd() == "" || timeProdSalePmixMomsVO.getStoreHqBrandCd() == null || timeProdSalePmixMomsVO.getProdHqBrandCd() == "" || timeProdSalePmixMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeProdSalePmixMomsVO.getUserBrands().split(",");
                timeProdSalePmixMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";
        String sQuery3 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(timeProdSalePmixMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                timeProdSalePmixMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                timeProdSalePmixMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";

            sQuery2 += ", T1.SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery2 += ", T1.REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery2 += ", T1.P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) / SUM(B.P_REAL_SALE_AMT1) AS TOT_P_MIX_SALE1" + "\n";

        sQuery2 += ", T1.TOT_SALE_QTY1" + "\n";
        sQuery2 += ", T1.TOT_REAL_SALE_AMT1" + "\n";
        sQuery2 += ", T1.TOT_P_MIX_SALE1" + "\n";

        // 시간대
        String timeCol = "";
        for(int i = 0; i <= 23; i++) {
            timeCol += (i < 10 ? "0" + i : i);
            if(i != 23){
                timeCol += ",";
            }
        }
        String[] arrTimeCol = timeCol.split(",");

        for (int i = 0; i < arrTimeCol.length; i++) {
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_SALE_QTY1, 0)) AS TIME_SALE_QTY1_" + arrTimeCol[i] + "\n";
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_REAL_SALE_AMT1, 0)) AS TIME_REAL_SALE_AMT1_" + arrTimeCol[i] + "\n";
            sQuery3 += ", SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "' , B.TIME_P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_HOUR, '" + arrTimeCol[i] + "', B.TIME_P_REAL_SALE_AMT1, 0) )) AS TIME_P_MIX_SALE1_" + arrTimeCol[i] + "\n";

            sQuery2 += ", T2.TIME_SALE_QTY1_" + arrTimeCol[i] + "\n";
            sQuery2 += ", T2.TIME_REAL_SALE_AMT1_" + arrTimeCol[i] + "\n";
            sQuery2 += ", T2.TIME_P_MIX_SALE1_" + arrTimeCol[i] + "\n";
        }

        timeProdSalePmixMomsVO.setsQuery1(sQuery1);
        timeProdSalePmixMomsVO.setsQuery2(sQuery2);
        timeProdSalePmixMomsVO.setsQuery3(sQuery3);

        return timeProdSalePmixMomsMapper.getTimeProdSalePmixMomsExcelList(timeProdSalePmixMomsVO);
    }

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    @Override
    public List<HashMap<String, String>> getDateDiff(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO) {

        // 시작기간 ~ 종료기간 사이의 날짜계산
        String datePattrn = "";
        if("day".equals(timeProdSalePmixMomsVO.getDayGubun())){
            datePattrn = "yyyyMMdd";
        }else if("month".equals(timeProdSalePmixMomsVO.getDayGubun())){
            datePattrn = "yyyyMM";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattrn);
        List<HashMap<String, String>> dateArr = new ArrayList<HashMap<String, String>>();
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(timeProdSalePmixMomsVO.getStartDate());
            endDate = dateFormat.parse(timeProdSalePmixMomsVO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            HashMap<String, String> m = new HashMap<>();
            if("day".equals(timeProdSalePmixMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate));
//                m.put("eDate", dateFormat.format(currentDate));
                dateArr.add(m);
            }else if("month".equals(timeProdSalePmixMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate) + "01");
//                m.put("eDate", dateFormat.format(currentDate) + "31");
                dateArr.add(m);
            }

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            if("day".equals(timeProdSalePmixMomsVO.getDayGubun())){
                c.add(Calendar.DATE, 1);
            }else if("month".equals(timeProdSalePmixMomsVO.getDayGubun())){
                c.add(Calendar.MONTH, 1);
            }

            currentDate = c.getTime();
        }

//        for (HashMap<String, String> date : dateArr) {
//            System.out.println(date.get("sDate")  + "/" + date.get("eDate"));
//        }

        return dateArr;
    }
}