package kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.MonthProdStoreMrpizzaService;
import kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.MonthProdStoreMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdStoreMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("monthProdStoreMrpizzaService")
@Transactional
public class MonthProdStoreMrpizzaServiceImpl implements MonthProdStoreMrpizzaService {

    private final MonthProdStoreMrpizzaMapper monthProdStoreMrpizzaMapper;
    private final PopupMapper popupMapper;

    public MonthProdStoreMrpizzaServiceImpl(MonthProdStoreMrpizzaMapper monthProdStoreMrpizzaMapper, PopupMapper popupMapper) {
        this.monthProdStoreMrpizzaMapper = monthProdStoreMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 월별상품매출현황(매장별) 리스트 조회
     *
     * @param monthProdStoreMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreMrpizzaList(MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdStoreMrpizzaVO.getStoreCds(), 3900));
            monthProdStoreMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdStoreMrpizzaVO.getProdClassCd() != null && !"".equals(monthProdStoreMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = monthProdStoreMrpizzaVO.getProdClassCd().split(",");
            monthProdStoreMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (monthProdStoreMrpizzaVO.getProdCds() != null && !"".equals(monthProdStoreMrpizzaVO.getProdCds())) {
            String[] prodCdList = monthProdStoreMrpizzaVO.getProdCds().split(",");
            monthProdStoreMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (monthProdStoreMrpizzaVO.getProdHqBrandCd() == "" || monthProdStoreMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreMrpizzaVO.getUserBrands().split(",");
                monthProdStoreMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreMrpizzaMapper.getMonthProdStoreMrpizzaList(monthProdStoreMrpizzaVO);
    }

    /**
     * 월별상품매출현황(매장별) 엑셀다운로드 조회
     *
     * @param monthProdStoreMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreMrpizzaExcelList(MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdStoreMrpizzaVO.getStoreCds(), 3900));
            monthProdStoreMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdStoreMrpizzaVO.getProdClassCd() != null && !"".equals(monthProdStoreMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = monthProdStoreMrpizzaVO.getProdClassCd().split(",");
            monthProdStoreMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (monthProdStoreMrpizzaVO.getProdCds() != null && !"".equals(monthProdStoreMrpizzaVO.getProdCds())) {
            String[] prodCdList = monthProdStoreMrpizzaVO.getProdCds().split(",");
            monthProdStoreMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdStoreMrpizzaVO.getProdHqBrandCd() == "" || monthProdStoreMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreMrpizzaVO.getUserBrands().split(",");
                monthProdStoreMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreMrpizzaMapper.getMonthProdStoreMrpizzaExcelList(monthProdStoreMrpizzaVO);
    }
}
