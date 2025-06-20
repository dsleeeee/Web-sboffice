package kr.co.solbipos.sale.prod.dayProdMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.dayProdMrpizza.service.DayProdMrpizzaService;
import kr.co.solbipos.sale.prod.dayProdMrpizza.service.DayProdMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayProdMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황
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
@Service("dayProdMrpizzaService")
@Transactional
public class DayProdMrpizzaServiceImpl implements DayProdMrpizzaService {
    private final DayProdMrpizzaMapper dayProdMrpizzaMapper;
    private final PopupMapper popupMapper;

    public DayProdMrpizzaServiceImpl(DayProdMrpizzaMapper dayProdMrpizzaMapper, PopupMapper popupMapper) {
        this.dayProdMrpizzaMapper = dayProdMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별상품매출현황 리스트 조회
     *
     * @param dayProdMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdMrpizzaList(DayProdMrpizzaVO dayProdMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayProdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdMrpizzaVO.getStoreCds(), 3900));
            dayProdMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdMrpizzaVO.getProdClassCd() != null && !"".equals(dayProdMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = dayProdMrpizzaVO.getProdClassCd().split(",");
            dayProdMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (dayProdMrpizzaVO.getProdCds() != null && !"".equals(dayProdMrpizzaVO.getProdCds())) {
            String[] prodCdList = dayProdMrpizzaVO.getProdCds().split(",");
            dayProdMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (dayProdMrpizzaVO.getProdHqBrandCd() == "" || dayProdMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdMrpizzaVO.getUserBrands().split(",");
                dayProdMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdMrpizzaMapper.getDayProdMrpizzaList(dayProdMrpizzaVO);
    }

    @Override
    public List<DefaultMap<Object>> getDayProdMrpizzaExcelList(DayProdMrpizzaVO dayProdMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayProdMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdMrpizzaVO.getStoreCds(), 3900));
            dayProdMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdMrpizzaVO.getProdClassCd() != null && !"".equals(dayProdMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = dayProdMrpizzaVO.getProdClassCd().split(",");
            dayProdMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (dayProdMrpizzaVO.getProdCds() != null && !"".equals(dayProdMrpizzaVO.getProdCds())) {
            String[] prodCdList = dayProdMrpizzaVO.getProdCds().split(",");
            dayProdMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (dayProdMrpizzaVO.getProdHqBrandCd() == "" || dayProdMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdMrpizzaVO.getUserBrands().split(",");
                dayProdMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdMrpizzaMapper.getDayProdMrpizzaExcelList(dayProdMrpizzaVO);
    }
}
