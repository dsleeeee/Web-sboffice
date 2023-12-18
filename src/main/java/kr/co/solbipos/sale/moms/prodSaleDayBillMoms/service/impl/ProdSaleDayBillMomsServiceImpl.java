package kr.co.solbipos.sale.moms.prodSaleDayBillMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSaleDayBillMoms.service.ProdSaleDayBillMomsService;
import kr.co.solbipos.sale.moms.prodSaleDayBillMoms.service.ProdSaleDayBillMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : ProdSaleDayBillMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출일별(영수)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleDayBillMomsService")
@Transactional
public class ProdSaleDayBillMomsServiceImpl implements ProdSaleDayBillMomsService {
    private final ProdSaleDayBillMomsMapper prodSaleDayBillMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleDayBillMomsServiceImpl(ProdSaleDayBillMomsMapper prodSaleDayBillMomsMapper, PopupMapper popupMapper) {
        this.prodSaleDayBillMomsMapper = prodSaleDayBillMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출일별(영수) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayBillMomsList(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayBillMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayBillMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBillMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayBillMomsVO.getStoreCds(), 3900));
            prodSaleDayBillMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayBillMomsVO.getProdCds() != null && !"".equals(prodSaleDayBillMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayBillMomsVO.getProdCds().split(",");
            prodSaleDayBillMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayBillMomsVO.getStoreHqBrandCd() == "" || prodSaleDayBillMomsVO.getStoreHqBrandCd() == null || prodSaleDayBillMomsVO.getProdHqBrandCd() == "" || prodSaleDayBillMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayBillMomsVO.getUserBrands().split(",");
                prodSaleDayBillMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSaleDayBillMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSaleDayBillMomsVO.setStartDate(dateArr.get(i).get("sDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSaleDayBillMomsVO.setEndDate(dateArr.get(i).get("eDate"));
            }

            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.BILL_CNT ELSE 0 END) AS BILL_CNT_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.SALE_QTY ELSE 0 END) AS SALE_QTY_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.SALE_AMT ELSE 0 END) AS SALE_AMT_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.BILL_CNT ELSE 0 END) AS TOT_BILL_CNT" + "\n";
        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY" + "\n";
        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.SALE_AMT ELSE 0 END) AS TOT_SALE_AMT" + "\n";

        // 영수건수 계산을 위한 쿼리문 생성
        sQuery2 = ", SUM(";
        for(int j = 1; j <= 20; j++) {
            sQuery2 += "A.PAY_CNT_"  + (j < 10 ? "0" + j : j);
            sQuery2 += (j < 20 ? " + ":"");
        }
        sQuery2 += ") AS BILL_CNT";

        prodSaleDayBillMomsVO.setsQuery1(sQuery1);
        prodSaleDayBillMomsVO.setsQuery2(sQuery2);

        return prodSaleDayBillMomsMapper.getProdSaleDayBillMomsList(prodSaleDayBillMomsVO);
    }

    /** 상품매출일별(영수) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleDayBillMomsExcelList(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO, SessionInfoVO sessionInfoVO) {

        prodSaleDayBillMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSaleDayBillMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSaleDayBillMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSaleDayBillMomsVO.getStoreCds(), 3900));
            prodSaleDayBillMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSaleDayBillMomsVO.getProdCds() != null && !"".equals(prodSaleDayBillMomsVO.getProdCds())) {
            String[] prodCdList = prodSaleDayBillMomsVO.getProdCds().split(",");
            prodSaleDayBillMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSaleDayBillMomsVO.getStoreHqBrandCd() == "" || prodSaleDayBillMomsVO.getStoreHqBrandCd() == null || prodSaleDayBillMomsVO.getProdHqBrandCd() == "" || prodSaleDayBillMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSaleDayBillMomsVO.getUserBrands().split(",");
                prodSaleDayBillMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";
        String sQuery2 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSaleDayBillMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSaleDayBillMomsVO.setStartDate(dateArr.get(i).get("sDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSaleDayBillMomsVO.setEndDate(dateArr.get(i).get("eDate"));
            }

            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.BILL_CNT ELSE 0 END) AS BILL_CNT_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.SALE_QTY ELSE 0 END) AS SALE_QTY_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(i).get("sDate") + "' AND '" + dateArr.get(i).get("eDate") + "' THEN tsdp.SALE_AMT ELSE 0 END) AS SALE_AMT_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.BILL_CNT ELSE 0 END) AS TOT_BILL_CNT" + "\n";
        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.SALE_QTY ELSE 0 END) AS TOT_SALE_QTY" + "\n";
        sQuery1 += ", SUM(CASE WHEN tsdp.SALE_DATE BETWEEN '" + dateArr.get(0).get("sDate") + "' AND '" + dateArr.get(dateArr.size() - 1).get("eDate") + "' THEN tsdp.SALE_AMT ELSE 0 END) AS TOT_SALE_AMT" + "\n";

        // 영수건수 계산을 위한 쿼리문 생성
        sQuery2 = ", SUM(";
        for(int j = 1; j <= 20; j++) {
            sQuery2 += "A.PAY_CNT_"  + (j < 10 ? "0" + j : j);
            sQuery2 += (j < 20 ? " + ":"");
        }
        sQuery2 += ") AS BILL_CNT";

        prodSaleDayBillMomsVO.setsQuery1(sQuery1);
        prodSaleDayBillMomsVO.setsQuery2(sQuery2);

        return prodSaleDayBillMomsMapper.getProdSaleDayBillMomsExcelList(prodSaleDayBillMomsVO);
    }

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    @Override
    public List<HashMap<String, String>> getDateDiff(ProdSaleDayBillMomsVO prodSaleDayBillMomsVO) {

        // 시작기간 ~ 종료기간 사이의 날짜계산
        String datePattrn = "";
        if("day".equals(prodSaleDayBillMomsVO.getDayGubun())){
            datePattrn = "yyyyMMdd";
        }else if("month".equals(prodSaleDayBillMomsVO.getDayGubun())){
            datePattrn = "yyyyMM";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattrn);
        List<HashMap<String, String>> dateArr = new ArrayList<HashMap<String, String>>();
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(prodSaleDayBillMomsVO.getStartDate());
            endDate = dateFormat.parse(prodSaleDayBillMomsVO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            HashMap<String, String> m = new HashMap<>();
            if("day".equals(prodSaleDayBillMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
                m.put("sDate", dateFormat.format(currentDate));
                m.put("eDate", dateFormat.format(currentDate));
                dateArr.add(m);
            }else if("month".equals(prodSaleDayBillMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
                m.put("sDate", dateFormat.format(currentDate));
                m.put("eDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate) + "01");
//                m.put("eDate", dateFormat.format(currentDate) + "31");
                dateArr.add(m);
            }

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            if("day".equals(prodSaleDayBillMomsVO.getDayGubun())){
                c.add(Calendar.DATE, 1);
            }else if("month".equals(prodSaleDayBillMomsVO.getDayGubun())){
                c.add(Calendar.MONTH, 1);
            }

            currentDate = c.getTime();
        }

        for (HashMap<String, String> date : dateArr) {
            System.out.println(date.get("sDate")  + "/" + date.get("eDate"));
        }

        return dateArr;
    }
}