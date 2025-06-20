package kr.co.solbipos.sale.prod.monthProdMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.monthProdMrpizza.service.MonthProdMrpizzaService;
import kr.co.solbipos.sale.prod.monthProdMrpizza.service.MonthProdMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황
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
@Service("monthProdMrpizzaService")
@Transactional
public class MonthProdMrpizzaServiceImpl implements MonthProdMrpizzaService {
    private final MonthProdMrpizzaMapper monthProdMrpizzaMapper;
    private final PopupMapper popupMapper;

    public MonthProdMrpizzaServiceImpl(MonthProdMrpizzaMapper monthProdMrpizzaMapper, PopupMapper popupMapper) {
        this.monthProdMrpizzaMapper = monthProdMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 월별상품매출현황 리스트 조회
     * @param monthProdMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdMrpizzaList(MonthProdMrpizzaVO monthProdMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthProdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdMrpizzaVO.getStoreCds(), 3900));
            monthProdMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdMrpizzaVO.getProdClassCd() != null && !"".equals(monthProdMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = monthProdMrpizzaVO.getProdClassCd().split(",");
            monthProdMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (monthProdMrpizzaVO.getProdCds() != null && !"".equals(monthProdMrpizzaVO.getProdCds())) {
            String[] prodCdList = monthProdMrpizzaVO.getProdCds().split(",");
            monthProdMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (monthProdMrpizzaVO.getProdHqBrandCd() == "" || monthProdMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdMrpizzaVO.getUserBrands().split(",");
                monthProdMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdMrpizzaMapper.getMonthProdMrpizzaList(monthProdMrpizzaVO);
    }

    /**
     * 월별상품매출현황 엑셀다운로드 조회
     * @param monthProdMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getMonthProdMrpizzaExcelList(MonthProdMrpizzaVO monthProdMrpizzaVO, SessionInfoVO sessionInfoVO) {

        monthProdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdMrpizzaVO.getStoreCds(), 3900));
            monthProdMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdMrpizzaVO.getProdClassCd() != null && !"".equals(monthProdMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = monthProdMrpizzaVO.getProdClassCd().split(",");
            monthProdMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (monthProdMrpizzaVO.getProdCds() != null && !"".equals(monthProdMrpizzaVO.getProdCds())) {
            String[] prodCdList = monthProdMrpizzaVO.getProdCds().split(",");
            monthProdMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (monthProdMrpizzaVO.getProdHqBrandCd() == "" || monthProdMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdMrpizzaVO.getUserBrands().split(",");
                monthProdMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdMrpizzaMapper.getMonthProdMrpizzaExcelList(monthProdMrpizzaVO);
    }
}
