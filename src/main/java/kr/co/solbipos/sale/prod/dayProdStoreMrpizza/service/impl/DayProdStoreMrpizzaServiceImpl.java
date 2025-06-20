package kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service.DayProdStoreMrpizzaService;
import kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service.DayProdStoreMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayProdStoreMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황(매장별)
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
@Service("dayProdStoreMrpizzaService")
@Transactional
public class DayProdStoreMrpizzaServiceImpl implements DayProdStoreMrpizzaService {

    private final DayProdStoreMrpizzaMapper dayProdStoremrpizzaMapper;
    private final PopupMapper popupMapper;

    public DayProdStoreMrpizzaServiceImpl(DayProdStoreMrpizzaMapper dayProdStoremrpizzaMapper, PopupMapper popupMapper) {
        this.dayProdStoremrpizzaMapper = dayProdStoremrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /**
     * 일별상품매출현황(매장별) 리스트 조회
     * @param dayProdStoreMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreMrpizzaList(DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreMrpizzaVO.getStoreCds(), 3900));
            dayProdStoreMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreMrpizzaVO.getProdClassCd() != null && !"".equals(dayProdStoreMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreMrpizzaVO.getProdClassCd().split(",");
            dayProdStoreMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (dayProdStoreMrpizzaVO.getProdCds() != null && !"".equals(dayProdStoreMrpizzaVO.getProdCds())) {
            String[] prodCdList = dayProdStoreMrpizzaVO.getProdCds().split(",");
            dayProdStoreMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (dayProdStoreMrpizzaVO.getProdHqBrandCd() == "" || dayProdStoreMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreMrpizzaVO.getUserBrands().split(",");
                dayProdStoreMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoremrpizzaMapper.getDayProdStoreMrpizzaList(dayProdStoreMrpizzaVO);
    }

    /**
     * 일별상품매출현황(매장별) 엑셀다운로드 조회
     * @param dayProdStoreMrpizzaVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<Object>> getDayProdStoreMrpizzaExcelList(DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO, SessionInfoVO sessionInfoVO) {

        dayProdStoreMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            dayProdStoreMrpizzaVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayProdStoreMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayProdStoreMrpizzaVO.getStoreCds(), 3900));
            dayProdStoreMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 분류 array 값 세팅
        if (dayProdStoreMrpizzaVO.getProdClassCd() != null && !"".equals(dayProdStoreMrpizzaVO.getProdClassCd())) {
            String[] prodCdList = dayProdStoreMrpizzaVO.getProdClassCd().split(",");
            dayProdStoreMrpizzaVO.setArrProdClassCd(prodCdList);
        }

        // 상품 array 값 세팅
        if (dayProdStoreMrpizzaVO.getProdCds() != null && !"".equals(dayProdStoreMrpizzaVO.getProdCds())) {
            String[] prodCdList = dayProdStoreMrpizzaVO.getProdCds().split(",");
            dayProdStoreMrpizzaVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 상품브랜드가 '전체' 일때
            if (dayProdStoreMrpizzaVO.getProdHqBrandCd() == "" || dayProdStoreMrpizzaVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = dayProdStoreMrpizzaVO.getUserBrands().split(",");
                dayProdStoreMrpizzaVO.setUserBrandList(userBrandList);
            }
        }

        return dayProdStoremrpizzaMapper.getDayProdStoreMrpizzaExcelList(dayProdStoreMrpizzaVO);
    }
}
