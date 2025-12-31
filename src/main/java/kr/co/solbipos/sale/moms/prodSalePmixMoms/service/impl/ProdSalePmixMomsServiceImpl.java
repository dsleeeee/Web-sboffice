package kr.co.solbipos.sale.moms.prodSalePmixMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.moms.prodSalePmixMoms.service.ProdSalePmixMomsService;
import kr.co.solbipos.sale.moms.prodSalePmixMoms.service.ProdSalePmixMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Class Name : ProdSalePmixMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 상품매출(P.MIX)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSalePmixMomsService")
@Transactional
public class ProdSalePmixMomsServiceImpl implements ProdSalePmixMomsService {
    private final ProdSalePmixMomsMapper prodSalePmixMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSalePmixMomsServiceImpl(ProdSalePmixMomsMapper prodSalePmixMomsMapper, PopupMapper popupMapper) {
        this.prodSalePmixMomsMapper = prodSalePmixMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품매출(P.MIX) - 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSalePmixMomsList(ProdSalePmixMomsVO prodSalePmixMomsVO, SessionInfoVO sessionInfoVO) {

        prodSalePmixMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSalePmixMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSalePmixMomsVO.getStoreCds(), 3900));
            prodSalePmixMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixMomsVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSalePmixMomsVO.getProdCds(), 3900));
            prodSalePmixMomsVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSalePmixMomsVO.getStoreHqBrandCd() == "" || prodSalePmixMomsVO.getStoreHqBrandCd() == null || prodSalePmixMomsVO.getProdHqBrandCd() == "" || prodSalePmixMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSalePmixMomsVO.getUserBrands().split(",");
                prodSalePmixMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSalePmixMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSalePmixMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSalePmixMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", NVL(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )), 0) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", NVL(SUM(B.REAL_SALE_AMT1) / DECODE(SUM(B.P_REAL_SALE_AMT1), 0, NULL, SUM(B.P_REAL_SALE_AMT1)), 0) AS TOT_P_MIX_SALE1" + "\n";

        prodSalePmixMomsVO.setsQuery1(sQuery1);

        return prodSalePmixMomsMapper.getProdSalePmixMomsList(prodSalePmixMomsVO);
    }

    /** 상품매출(P.MIX) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSalePmixMomsExcelList(ProdSalePmixMomsVO prodSalePmixMomsVO, SessionInfoVO sessionInfoVO) {

        prodSalePmixMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            prodSalePmixMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(prodSalePmixMomsVO.getStoreCds(), 3900));
            prodSalePmixMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(prodSalePmixMomsVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(prodSalePmixMomsVO.getProdCds(), 3900));
            prodSalePmixMomsVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (prodSalePmixMomsVO.getStoreHqBrandCd() == "" || prodSalePmixMomsVO.getStoreHqBrandCd() == null || prodSalePmixMomsVO.getProdHqBrandCd() == "" || prodSalePmixMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = prodSalePmixMomsVO.getUserBrands().split(",");
                prodSalePmixMomsVO.setUserBrandList(userBrandList);
            }
        }

        // 동적 컬럼 생성을 위한 쿼리 변수
        String sQuery1 = "";

        // 기간선택 두 날짜 사이 모든날짜 구하기
        List<HashMap<String, String>> dateArr = getDateDiff(prodSalePmixMomsVO);

        // 집계쿼리 생성
        for (int i = 0; i < dateArr.size(); i++) {

            // 검색기간의 첫날짜와 끝날짜 다시 셋팅
            if (i == 0) {
                prodSalePmixMomsVO.setStartDate(dateArr.get(i).get("sOrgDate"));
            }
            if (i == dateArr.size() - 1) {
                prodSalePmixMomsVO.setEndDate(dateArr.get(i).get("sOrgDate"));
            }

            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.SALE_QTY1, 0)) AS SALE_QTY1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) AS REAL_SALE_AMT1_" + dateArr.get(i).get("sOrgDate") + "\n";
            sQuery1 += ", NVL(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.REAL_SALE_AMT1, 0)) / DECODE(SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "' , B.P_REAL_SALE_AMT1, 0)), 0, NULL, SUM(DECODE(B.SALE_DATE, '" + dateArr.get(i).get("sOrgDate") + "', B.P_REAL_SALE_AMT1, 0) )), 0) AS P_MIX_SALE1_" + dateArr.get(i).get("sOrgDate") + "\n";
        }

        sQuery1 += ", SUM(B.SALE_QTY1) AS TOT_SALE_QTY1" + "\n";
        sQuery1 += ", SUM(B.REAL_SALE_AMT1) AS TOT_REAL_SALE_AMT1" + "\n";
        sQuery1 += ", NVL(SUM(B.REAL_SALE_AMT1) / DECODE(SUM(B.P_REAL_SALE_AMT1), 0, NULL, SUM(B.P_REAL_SALE_AMT1)), 0) AS TOT_P_MIX_SALE1" + "\n";

        prodSalePmixMomsVO.setsQuery1(sQuery1);

        return prodSalePmixMomsMapper.getProdSalePmixMomsExcelList(prodSalePmixMomsVO);
    }

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    @Override
    public List<HashMap<String, String>> getDateDiff(ProdSalePmixMomsVO prodSalePmixMomsVO) {

        // 시작기간 ~ 종료기간 사이의 날짜계산
        String datePattrn = "";
        if("day".equals(prodSalePmixMomsVO.getDayGubun())){
            datePattrn = "yyyyMMdd";
        }else if("month".equals(prodSalePmixMomsVO.getDayGubun())){
            datePattrn = "yyyyMM";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattrn);
        List<HashMap<String, String>> dateArr = new ArrayList<HashMap<String, String>>();
        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = dateFormat.parse(prodSalePmixMomsVO.getStartDate());
            endDate = dateFormat.parse(prodSalePmixMomsVO.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date currentDate = startDate;

        while (currentDate.compareTo(endDate) <= 0) {
            HashMap<String, String> m = new HashMap<>();
            if("day".equals(prodSalePmixMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate));
//                m.put("eDate", dateFormat.format(currentDate));
                dateArr.add(m);
            }else if("month".equals(prodSalePmixMomsVO.getDayGubun())){
                m.put("sOrgDate", dateFormat.format(currentDate));
//                m.put("sDate", dateFormat.format(currentDate) + "01");
//                m.put("eDate", dateFormat.format(currentDate) + "31");
                dateArr.add(m);
            }

            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);

            if("day".equals(prodSalePmixMomsVO.getDayGubun())){
                c.add(Calendar.DATE, 1);
            }else if("month".equals(prodSalePmixMomsVO.getDayGubun())){
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