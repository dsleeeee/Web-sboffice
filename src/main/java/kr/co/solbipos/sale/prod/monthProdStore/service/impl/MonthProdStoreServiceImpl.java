package kr.co.solbipos.sale.prod.monthProdStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthProdStore.service.MonthProdStoreService;
import kr.co.solbipos.sale.prod.monthProdStore.service.MonthProdStoreVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdStoreServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 월별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthProdStoreService")
@Transactional
public class MonthProdStoreServiceImpl implements MonthProdStoreService {
    private final MonthProdStoreMapper monthProdStoreMapper;

    public MonthProdStoreServiceImpl(MonthProdStoreMapper monthProdStoreMapper) {
        this.monthProdStoreMapper = monthProdStoreMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreList(MonthProdStoreVO monthProdStoreVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdStoreVO.getStoreCds().split(",");
        monthProdStoreVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdStoreVO.getProdCds() != null && !"".equals(monthProdStoreVO.getProdCds())) {
            String[] prodCdList = monthProdStoreVO.getProdCds().split(",");
            monthProdStoreVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdStoreVO.getStoreHqBrandCd() == "" || monthProdStoreVO.getStoreHqBrandCd() == null || monthProdStoreVO.getProdHqBrandCd() == "" || monthProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreVO.getUserBrands().split(",");
                monthProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreMapper.getMonthProdStoreList(monthProdStoreVO);
    }

    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreExcelList(MonthProdStoreVO monthProdStoreVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthProdStoreVO.getStoreCds().split(",");
        monthProdStoreVO.setStoreCdList(storeCds);

        // 상품 array 값 세팅
        if (monthProdStoreVO.getProdCds() != null && !"".equals(monthProdStoreVO.getProdCds())) {
            String[] prodCdList = monthProdStoreVO.getProdCds().split(",");
            monthProdStoreVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdStoreVO.getStoreHqBrandCd() == "" || monthProdStoreVO.getStoreHqBrandCd() == null || monthProdStoreVO.getProdHqBrandCd() == "" || monthProdStoreVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreVO.getUserBrands().split(",");
                monthProdStoreVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreMapper.getMonthProdStoreExcelList(monthProdStoreVO);
    }
}