package kr.co.solbipos.sale.prod.monthProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthProd.service.MonthProdService;
import kr.co.solbipos.sale.prod.monthProd.service.MonthProdVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : MonthProdServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 월별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthProdService")
@Transactional
public class MonthProdServiceImpl implements MonthProdService {
    private final MonthProdMapper monthProdMapper;

    public MonthProdServiceImpl(MonthProdMapper monthProdMapper) {
        this.monthProdMapper = monthProdMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdList(MonthProdVO monthProdVO, SessionInfoVO sessionInfoVO) {

        monthProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdVO.getStoreCds().split(",");
        monthProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdVO.getProdCds() != null && !"".equals(monthProdVO.getProdCds())) {
            String[] prodCdList = monthProdVO.getProdCds().split(",");
            monthProdVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdVO.getStoreHqBrandCd() == "" || monthProdVO.getStoreHqBrandCd() == null || monthProdVO.getProdHqBrandCd() == "" || monthProdVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdVO.getUserBrands().split(",");
                monthProdVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdMapper.getMonthProdList(monthProdVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdExcelList(MonthProdVO monthProdVO, SessionInfoVO sessionInfoVO) {

        monthProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdVO.getStoreCds().split(",");
        monthProdVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdVO.getProdCds() != null && !"".equals(monthProdVO.getProdCds())) {
            String[] prodCdList = monthProdVO.getProdCds().split(",");
            monthProdVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdVO.getStoreHqBrandCd() == "" || monthProdVO.getStoreHqBrandCd() == null || monthProdVO.getProdHqBrandCd() == "" || monthProdVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdVO.getUserBrands().split(",");
                monthProdVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdMapper.getMonthProdExcelList(monthProdVO);
    }
}