package kr.co.solbipos.sale.moms.prodSalePmixStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.prodSalePmixStoreMoms.service.ProdSalePmixStoreMomsService;
import kr.co.solbipos.sale.moms.prodSalePmixStoreMoms.service.ProdSalePmixStoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Class Name : ProdSalePmixStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출(P.MIX 매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.21  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSalePmixStoreMomsService")
@Transactional
public class ProdSalePmixStoreMomsServiceImpl implements ProdSalePmixStoreMomsService {
    private final ProdSalePmixStoreMomsMapper prodSalePmixStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSalePmixStoreMomsServiceImpl(ProdSalePmixStoreMomsMapper prodSalePmixStoreMomsMapper, PopupMapper popupMapper) {
        this.prodSalePmixStoreMomsMapper = prodSalePmixStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출(P.MIX 매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSalePmixStoreMomsList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSalePmixStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSalePmixStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSalePmixStoreMomsVO.getStoreCds(), 3900));
            prodSalePmixStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSalePmixStoreMomsVO.getProdCds() != null && !"".equals(prodSalePmixStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSalePmixStoreMomsVO.getProdCds().split(",");
            prodSalePmixStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSalePmixStoreMomsVO.getStoreHqBrandCd() == "" || prodSalePmixStoreMomsVO.getStoreHqBrandCd() == null || prodSalePmixStoreMomsVO.getProdHqBrandCd() == "" || prodSalePmixStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSalePmixStoreMomsVO.getUserBrands().split(",");
                prodSalePmixStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSalePmixStoreMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSalePmixStoreMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSalePmixStoreMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", NVL(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )), 0) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", NVL(SUM(B.REAL_SALE_AMT1) / DECODE(SUM(B.P_REAL_SALE_AMT1), 0, NULL, SUM(B.P_REAL_SALE_AMT1)), 0) AS TOT_P_MIX_SALE1" + "\n";

        prodSalePmixStoreMomsVO.setsQuery1(sQuery1);

        return prodSalePmixStoreMomsMapper.getProdSalePmixStoreMomsList(prodSalePmixStoreMomsVO);
    }

    /** 상품매출(P.MIX 매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSalePmixStoreMomsExcelList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSalePmixStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSalePmixStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSalePmixStoreMomsVO.getStoreCds(), 3900));
            prodSalePmixStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSalePmixStoreMomsVO.getProdCds() != null && !"".equals(prodSalePmixStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSalePmixStoreMomsVO.getProdCds().split(",");
            prodSalePmixStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSalePmixStoreMomsVO.getStoreHqBrandCd() == "" || prodSalePmixStoreMomsVO.getStoreHqBrandCd() == null || prodSalePmixStoreMomsVO.getProdHqBrandCd() == "" || prodSalePmixStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSalePmixStoreMomsVO.getUserBrands().split(",");
                prodSalePmixStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSalePmixStoreMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSalePmixStoreMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSalePmixStoreMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", NVL(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )), 0) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", NVL(SUM(B.REAL_SALE_AMT1) / DECODE(SUM(B.P_REAL_SALE_AMT1), 0, NULL, SUM(B.P_REAL_SALE_AMT1)), 0) AS TOT_P_MIX_SALE1" + "\n";

        prodSalePmixStoreMomsVO.setsQuery1(sQuery1);

        return prodSalePmixStoreMomsMapper.getProdSalePmixStoreMomsExcelList(prodSalePmixStoreMomsVO);
    }

    /** 상품매출(P.MIX 매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSalePmixStoreMomsExcelDivisionList(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO, SessionInfoVO sessionInfoVO) {

        prodSalePmixStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSalePmixStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSalePmixStoreMomsVO.getStoreCds(), 3900));
            prodSalePmixStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (prodSalePmixStoreMomsVO.getProdCds() != null && !"".equals(prodSalePmixStoreMomsVO.getProdCds())) {
            String[] prodCdList = prodSalePmixStoreMomsVO.getProdCds().split(",");
            prodSalePmixStoreMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSalePmixStoreMomsVO.getStoreHqBrandCd() == "" || prodSalePmixStoreMomsVO.getStoreHqBrandCd() == null || prodSalePmixStoreMomsVO.getProdHqBrandCd() == "" || prodSalePmixStoreMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSalePmixStoreMomsVO.getUserBrands().split(",");
                prodSalePmixStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSalePmixStoreMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSalePmixStoreMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSalePmixStoreMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", NVL(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )), 0) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", NVL(SUM(B.REAL_SALE_AMT1) / DECODE(SUM(B.P_REAL_SALE_AMT1), 0, NULL, SUM(B.P_REAL_SALE_AMT1)), 0) AS TOT_P_MIX_SALE1" + "\n";

        prodSalePmixStoreMomsVO.setsQuery1(sQuery1);

        return prodSalePmixStoreMomsMapper.getProdSalePmixStoreMomsExcelDivisionList(prodSalePmixStoreMomsVO);
    }

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    @Override
    public List<HashMap<String, String>> getDateDiff(ProdSalePmixStoreMomsVO prodSalePmixStoreMomsVO) {

        // 시작기간 ~ 종료기간 사이의 날짜계산
        String datePattrn = "";
        if("day".equals(prodSalePmixStoreMomsVO.getDayGubun())){
            datePattrn = "yyyyMMdd";
        }else if("month".equals(prodSalePmixStoreMomsVO.getDayGubun())){
            datePattrn = "yyyyMM";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattrn);
        List<HashMap<String, String>> dateArr = new ArrayList<HashMap<String, String>>();
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(prodSalePmixStoreMomsVO.getStartDate());
            endDate = dateFormat.parse(prodSalePmixStoreMomsVO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            HashMap<String, String> m = new HashMap<>();
            if("day".equals(prodSalePmixStoreMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate));
//                m.put("eDate", dateFormat.format(currentDate));
                dateArr.add(m);
            }else if("month".equals(prodSalePmixStoreMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate) + "01");
//                m.put("eDate", dateFormat.format(currentDate) + "31");
                dateArr.add(m);
            }

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            if("day".equals(prodSalePmixStoreMomsVO.getDayGubun())){
                c.add(Calendar.DATE, 1);
            }else if("month".equals(prodSalePmixStoreMomsVO.getDayGubun())){
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