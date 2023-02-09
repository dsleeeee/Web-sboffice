package kr.co.solbipos.sale.anals.saleAnalsMomsBst.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.anals.saleAnalsMomsBst.service.SaleAnalsMomsBstService;
import kr.co.solbipos.sale.anals.saleAnalsMomsBst.service.SaleAnalsMomsBstVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Class Name : SaleAnalsMomsBstServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 매출분석(사업전략팀)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.27   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("saleAnalsMomsBstService")
@Transactional
public class SaleAnalsMomsBstServiceImpl implements SaleAnalsMomsBstService {

    private final SaleAnalsMomsBstMapper saleAnalsMomsBstMapper;

    public SaleAnalsMomsBstServiceImpl(SaleAnalsMomsBstMapper saleAnalsMomsBstMapper) {
        this.saleAnalsMomsBstMapper = saleAnalsMomsBstMapper;
    }

    /** 매출분석(사업전략팀) 조회 */
    @Override
    public List<DefaultMap<String>> getSaleAnalsMomsBstList(SaleAnalsMomsBstVO saleAnalsMomsBstVO, SessionInfoVO sessionInfoVO){
        saleAnalsMomsBstVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        saleAnalsMomsBstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            saleAnalsMomsBstVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = saleAnalsMomsBstVO.getStoreCds().split(",");
        saleAnalsMomsBstVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (saleAnalsMomsBstVO.getProdCds() != null && !"".equals(saleAnalsMomsBstVO.getProdCds())) {
            String[] prodCdList = saleAnalsMomsBstVO.getProdCds().split(",");
            saleAnalsMomsBstVO.setProdCdList(prodCdList);
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (saleAnalsMomsBstVO.getStoreHqBrandCd() == "" || saleAnalsMomsBstVO.getStoreHqBrandCd() == null || saleAnalsMomsBstVO.getProdHqBrandCd() == "" || saleAnalsMomsBstVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = saleAnalsMomsBstVO.getUserBrands().split(",");
                saleAnalsMomsBstVO.setUserBrandList(userBrandList);
            }
        }

        // 조회옵션1 array 값 세팅
        if (saleAnalsMomsBstVO.getDlvrInFg() != null && !"".equals(saleAnalsMomsBstVO.getDlvrInFg())) {
            saleAnalsMomsBstVO.setDlvrInFgList(saleAnalsMomsBstVO.getDlvrInFg().split(","));
        }

        // 조회옵션2 array 값 세팅
        if (saleAnalsMomsBstVO.getDlvrOrderFg() != null && !"".equals(saleAnalsMomsBstVO.getDlvrOrderFg())) {
            saleAnalsMomsBstVO.setDlvrOrderFgList(saleAnalsMomsBstVO.getDlvrOrderFg().split(","));
        }


        // 동적 컬럼 생성을 위한 쿼리 변수;
        String sQuery1 = "";
        String sQuery2 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(saleAnalsMomsBstVO);

        // 화면출력이 매장일때
        if("store".equals(saleAnalsMomsBstVO.getViewType())) {

            // 집계쿼리 생성
            for (int i = 0; i < dateArr.size(); i++) {

                // 검색기간의 첫날짜와 끝날짜 다시 셋팅
                if (i == 0) {
                    saleAnalsMomsBstVO.setStartDate(dateArr.get(i).get("sDate"));
                }
                if (i == dateArr.size() - 1) {
                    saleAnalsMomsBstVO.setEndDate(dateArr.get(i).get("eDate"));
                }

                if ("1".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.GUEST_CNT ELSE 0 END) AS GUEST_CNT_1_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_1_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_1_" + dateArr.get(i).get("sOrgDate");
                } else if ("2".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.GUEST_CNT ELSE 0 END) AS GUEST_CNT_2_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_2_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_2_" + dateArr.get(i).get("sOrgDate");
                } else if ("3".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.GUEST_CNT ELSE 0 END) AS GUEST_CNT_3_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_3_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_3_" + dateArr.get(i).get("sOrgDate");
                }
            }
            if ("1".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
            } else if ("2".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
            } else if ("3".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.GUEST_CNT ELSE 0 END) AS TOT_GUEST_CNT";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
            }

        }
        // 화면출력이 상품일떄
        else if("prod".equals(saleAnalsMomsBstVO.getViewType())) {

            int totAmt = 0;

            // 집계쿼리 생성
            for (int i = 0; i < dateArr.size(); i++) {

                // 검색기간의 첫날짜와 끝날짜 다시 셋팅
                if (i == 0) {
                    saleAnalsMomsBstVO.setStartDate(dateArr.get(i).get("sDate"));
                }
                if (i == dateArr.size() - 1) {
                    saleAnalsMomsBstVO.setEndDate(dateArr.get(i).get("eDate"));
                }

                // 날짜별 총매출액 구하기
                saleAnalsMomsBstVO.setSaleStartDate(dateArr.get(i).get("sDate"));
                saleAnalsMomsBstVO.setSaleEndDate(dateArr.get(i).get("sDate"));
                totAmt = saleAnalsMomsBstMapper.getRealSaleAmt(saleAnalsMomsBstVO);

                if ("1".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_1_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_1_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS P_MIX_1_" + dateArr.get(i).get("sOrgDate");
                } else if ("2".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_2_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_2_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS P_MIX_2_" + dateArr.get(i).get("sOrgDate");
                } else if ("3".equals(saleAnalsMomsBstVO.getProdOption())) {
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.SALE_QTY ELSE 0 END) AS SALE_QTY_3_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) AS REAL_SALE_AMT_3_" + dateArr.get(i).get("sOrgDate");
                    sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS P_MIX_3_" + dateArr.get(i).get("sOrgDate");
                }
            }

            // 날짜기간 총매출액 구하기
            saleAnalsMomsBstVO.setSaleStartDate(dateArr.get(0).get("sDate"));
            saleAnalsMomsBstVO.setSaleEndDate(dateArr.get(dateArr.size() - 1).get("eDate"));
            totAmt = saleAnalsMomsBstMapper.getRealSaleAmt(saleAnalsMomsBstVO);

            if ("1".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
                sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'P') THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS TOT_P_MIX";
            } else if ("2".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
                sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' AND (A.SEL_TYPE_FG = 'N' OR A.SEL_TYPE_FG = 'S') THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS TOT_P_MIX";
            } else if ("3".equals(saleAnalsMomsBstVO.getProdOption())) {
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY";
                sQuery1 += ", SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) AS TOT_REAL_SALE_AMT";
                sQuery1 += ", NVL(SUM(CASE WHEN A.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN A.REAL_SALE_AMT ELSE 0 END) / DECODE(" + totAmt + ", 0, NULL, " + totAmt + "), 0) AS TOT_P_MIX";

            }
        }

        // 고객수 계산을 위한 쿼리문 생성
        sQuery2 = ", (";
        for(int j = 1; j <= 20; j++) {
            sQuery2 += "tsdpsd.PAY_CNT_"  + (j < 10 ? "0" + j : j);
            sQuery2 += (j < 20 ? " + ":"");
        }
        sQuery2 += ") AS GUEST_CNT";

        saleAnalsMomsBstVO.setsQuery1(sQuery1);
        saleAnalsMomsBstVO.setsQuery2(sQuery2);

        return saleAnalsMomsBstMapper.getSaleAnalsMomsBstList(saleAnalsMomsBstVO);
    }


    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    @Override
    public List<HashMap<String, String>> getDateDiff(SaleAnalsMomsBstVO saleAnalsMomsBstVO){

        // 시작기간 ~ 종료기간 사이의 날짜계산
        String datePattrn = "";
        if("day".equals(saleAnalsMomsBstVO.getDayGubun())){
            datePattrn = "yyyyMMdd";
        }else if("month".equals(saleAnalsMomsBstVO.getDayGubun())){
            datePattrn = "yyyyMM";
        }else if("year".equals(saleAnalsMomsBstVO.getDayGubun())){
            datePattrn = "yyyy";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattrn);
        List<HashMap<String, String>> dateArr = new ArrayList<HashMap<String, String>>();
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(saleAnalsMomsBstVO.getStartDate());
            endDate = dateFormat.parse(saleAnalsMomsBstVO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            HashMap<String, String> m = new HashMap<>();
            if("day".equals(saleAnalsMomsBstVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
                m.put("sDate", dateFormat.format(currentDate));
                m.put("eDate", dateFormat.format(currentDate));
                dateArr.add(m);
            }else if("month".equals(saleAnalsMomsBstVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
                m.put("sDate", dateFormat.format(currentDate) + "01");
                m.put("eDate", dateFormat.format(currentDate) + "31");
                dateArr.add(m);
            }else if("year".equals(saleAnalsMomsBstVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
                m.put("sDate", dateFormat.format(currentDate) + "0101");
                m.put("eDate", dateFormat.format(currentDate) + "1231");
                dateArr.add(m);
            }

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            if("day".equals(saleAnalsMomsBstVO.getDayGubun())){
                c.add(Calendar.DATE, 1);
            }else if("month".equals(saleAnalsMomsBstVO.getDayGubun())){
                c.add(Calendar.MONTH, 1);
            }else if("year".equals(saleAnalsMomsBstVO.getDayGubun())){
                c.add(Calendar.YEAR, 1);
            }

            currentDate = c.getTime();
        }

        for (HashMap<String, String> date : dateArr) {
            System.out.println(date.get("sDate")  + "/" + date.get("eDate"));
        }

        return dateArr;
    }
}
