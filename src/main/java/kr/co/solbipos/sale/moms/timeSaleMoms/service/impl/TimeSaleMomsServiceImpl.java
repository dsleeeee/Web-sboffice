package kr.co.solbipos.sale.moms.timeSaleMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.timeSaleMoms.service.TimeSaleMomsService;
import kr.co.solbipos.sale.moms.timeSaleMoms.service.TimeSaleMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : TimeSaleMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.01.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("timeSaleMomsService")
@Transactional
public class TimeSaleMomsServiceImpl implements TimeSaleMomsService {
    private final TimeSaleMomsMapper timeSaleMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleMomsServiceImpl(TimeSaleMomsMapper timeSaleMomsMapper, PopupMapper popupMapper) {
        this.timeSaleMomsMapper = timeSaleMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 시간대매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleMomsList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleMomsVO.getStoreCds(), 3900));
            timeSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (timeSaleMomsVO.getProdCds() != null && !"".equals(timeSaleMomsVO.getProdCds())) {
            String[] prodCdList = timeSaleMomsVO.getProdCds().split(",");
            timeSaleMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleMomsVO.getStoreHqBrandCd() == "" || timeSaleMomsVO.getStoreHqBrandCd() == null || timeSaleMomsVO.getProdHqBrandCd() == "" || timeSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleMomsVO.getUserBrands().split(",");
                timeSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return timeSaleMomsMapper.getTimeSaleMomsList(timeSaleMomsVO);
    }

    /** 시간대매출 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleMomsExcelList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleMomsVO.getStoreCds(), 3900));
            timeSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (timeSaleMomsVO.getProdCds() != null && !"".equals(timeSaleMomsVO.getProdCds())) {
            String[] prodCdList = timeSaleMomsVO.getProdCds().split(",");
            timeSaleMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleMomsVO.getStoreHqBrandCd() == "" || timeSaleMomsVO.getStoreHqBrandCd() == null || timeSaleMomsVO.getProdHqBrandCd() == "" || timeSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleMomsVO.getUserBrands().split(",");
                timeSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return timeSaleMomsMapper.getTimeSaleMomsExcelList(timeSaleMomsVO);
    }

    /** 시간대매출 - 분할 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeSaleMomsExcelDivisionList(TimeSaleMomsVO timeSaleMomsVO, SessionInfoVO sessionInfoVO) {

        timeSaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            timeSaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(timeSaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(timeSaleMomsVO.getStoreCds(), 3900));
            timeSaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 상품 array 값 세팅
        if (timeSaleMomsVO.getProdCds() != null && !"".equals(timeSaleMomsVO.getProdCds())) {
            String[] prodCdList = timeSaleMomsVO.getProdCds().split(",");
            timeSaleMomsVO.setProdCdList(prodCdList);
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드, 상품브랜드가 '전체' 일때
            if (timeSaleMomsVO.getStoreHqBrandCd() == "" || timeSaleMomsVO.getStoreHqBrandCd() == null || timeSaleMomsVO.getProdHqBrandCd() == "" || timeSaleMomsVO.getProdHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = timeSaleMomsVO.getUserBrands().split(",");
                timeSaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return timeSaleMomsMapper.getTimeSaleMomsExcelDivisionList(timeSaleMomsVO);
    }
}