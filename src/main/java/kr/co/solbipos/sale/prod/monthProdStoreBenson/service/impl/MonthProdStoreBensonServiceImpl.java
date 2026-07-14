package kr.co.solbipos.sale.prod.monthProdStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.sale.prod.monthProdStoreBenson.service.MonthProdStoreBensonService;
import kr.co.solbipos.sale.prod.monthProdStoreBenson.service.MonthProdStoreBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthProdStoreBensonServiceImpl.java
 * @Description : 벤슨 > 상품매출분석 > 월별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("monthProdStoreBensonService")
@Transactional
public class MonthProdStoreBensonServiceImpl implements MonthProdStoreBensonService {

    private final MonthProdStoreBensonMapper monthProdStoreBensonMapper;
    private final PopupMapper popupMapper;

    public MonthProdStoreBensonServiceImpl(MonthProdStoreBensonMapper monthProdStoreBensonMapper, PopupMapper popupMapper) {
        this.monthProdStoreBensonMapper = monthProdStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 월별상품매출현황(매장별) 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreBensonList(MonthProdStoreBensonVO monthProdStoreBensonVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdStoreBensonVO.getStoreCds(), 3900));
            monthProdStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdStoreBensonVO.getProdClassCd() != null && !"".equals(monthProdStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = monthProdStoreBensonVO.getProdClassCd().split(",");
            monthProdStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(monthProdStoreBensonVO.getProdCds(), 3900));
            monthProdStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdStoreBensonVO.getStoreHqBrandCd() == "" || monthProdStoreBensonVO.getStoreHqBrandCd() == null || monthProdStoreBensonVO.getProdHqBrandCd() == "" || monthProdStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreBensonVO.getUserBrands().split(",");
                monthProdStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreBensonMapper.getMonthProdStoreBensonList(monthProdStoreBensonVO);
    }

    /** 월별상품매출현황(매장별) 엑셀 다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthProdStoreBensonExcelList(MonthProdStoreBensonVO monthProdStoreBensonVO, SessionInfoVO sessionInfoVO) {

        monthProdStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthProdStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(monthProdStoreBensonVO.getStoreCds(), 3900));
            monthProdStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (monthProdStoreBensonVO.getProdClassCd() != null && !"".equals(monthProdStoreBensonVO.getProdClassCd())) {
            String[] prodCdList = monthProdStoreBensonVO.getProdClassCd().split(",");
            monthProdStoreBensonVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if(!StringUtil.getOrBlank(monthProdStoreBensonVO.getProdCds()).equals("")) {
            ProdVO prodVO = new ProdVO();
            prodVO.setArrSplitProdCd(CmmUtil.splitText(monthProdStoreBensonVO.getProdCds(), 3900));
            monthProdStoreBensonVO.setProdCdQuery(popupMapper.getSearchMultiProdRtn(prodVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (monthProdStoreBensonVO.getStoreHqBrandCd() == "" || monthProdStoreBensonVO.getStoreHqBrandCd() == null || monthProdStoreBensonVO.getProdHqBrandCd() == "" || monthProdStoreBensonVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = monthProdStoreBensonVO.getUserBrands().split(",");
                monthProdStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return monthProdStoreBensonMapper.getMonthProdStoreBensonExcelList(monthProdStoreBensonVO);
    }
}
