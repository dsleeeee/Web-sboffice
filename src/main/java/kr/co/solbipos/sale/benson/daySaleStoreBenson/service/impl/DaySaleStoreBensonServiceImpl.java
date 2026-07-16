package kr.co.solbipos.sale.benson.daySaleStoreBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.daySaleStoreBenson.service.DaySaleStoreBensonService;
import kr.co.solbipos.sale.benson.daySaleStoreBenson.service.DaySaleStoreBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : DaySaleStoreBensonServiceImpl.java
 * @Description : 벤슨 > 간소화화면 > 일별매출(매장)
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
@Service("daySaleStoreBensonService")
@Transactional
public class DaySaleStoreBensonServiceImpl implements DaySaleStoreBensonService {
    private final DaySaleStoreBensonMapper daySaleStoreBensonMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleStoreBensonServiceImpl(DaySaleStoreBensonMapper daySaleStoreBensonMapper, PopupMapper popupMapper) {
        this.daySaleStoreBensonMapper = daySaleStoreBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleStoreBensonList(DaySaleStoreBensonVO daySaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        daySaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleStoreBensonVO.getStoreCds(), 3900));
            daySaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleStoreBensonVO.getStoreHqBrandCd() == "" || daySaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleStoreBensonVO.getUserBrands().split(",");
                daySaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleStoreBensonMapper.getDaySaleStoreBensonList(daySaleStoreBensonVO);
    }

    /** 일별매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleStoreBensonExcelList(DaySaleStoreBensonVO daySaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        daySaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleStoreBensonVO.getStoreCds(), 3900));
            daySaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleStoreBensonVO.getStoreHqBrandCd() == "" || daySaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleStoreBensonVO.getUserBrands().split(",");
                daySaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleStoreBensonMapper.getDaySaleStoreBensonExcelList(daySaleStoreBensonVO);
    }

    /** 일별매출(매장) - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleStoreBensonExcelDivisionList(DaySaleStoreBensonVO daySaleStoreBensonVO, SessionInfoVO sessionInfoVO) {

        daySaleStoreBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleStoreBensonVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleStoreBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleStoreBensonVO.getStoreCds(), 3900));
            daySaleStoreBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleStoreBensonVO.getStoreHqBrandCd() == "" || daySaleStoreBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleStoreBensonVO.getUserBrands().split(",");
                daySaleStoreBensonVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleStoreBensonMapper.getDaySaleStoreBensonExcelDivisionList(daySaleStoreBensonVO);
    }
}
