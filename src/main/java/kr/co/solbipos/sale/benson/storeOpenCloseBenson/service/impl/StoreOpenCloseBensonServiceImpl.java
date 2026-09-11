package kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.StoreOpenCloseBensonService;
import kr.co.solbipos.sale.benson.storeOpenCloseBenson.service.StoreOpenCloseBensonVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseBensonServiceImpl.java
 * @Description : (벤슨) 매장분석 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeOpenCloseBensonService")
@Transactional
public class StoreOpenCloseBensonServiceImpl implements StoreOpenCloseBensonService {

    private final StoreOpenCloseBensonMapper storeOpenCloseBensonMapper;
    private final PopupMapper popupMapper;

    public StoreOpenCloseBensonServiceImpl(StoreOpenCloseBensonMapper storeOpenCloseBensonMapper, PopupMapper popupMapper) {
        this.storeOpenCloseBensonMapper = storeOpenCloseBensonMapper;
        this.popupMapper = popupMapper;
    }

    /** 매장 오픈/마감 현황 - 일별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseBensonDayList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseBensonVO.getStoreCds(), 3900));
            storeOpenCloseBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseBensonVO.getStoreHqBrandCd() == "" || storeOpenCloseBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseBensonVO.getUserBrands().split(",");
                storeOpenCloseBensonVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseBensonMapper.getStoreOpenCloseBensonDayList(storeOpenCloseBensonVO);
    }

    /** 매장 오픈/마감 현황 - 일별 상세 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseBensonDayDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseBensonVO.getStoreCds(), 3900));
            storeOpenCloseBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseBensonVO.getStoreHqBrandCd() == "" || storeOpenCloseBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseBensonVO.getUserBrands().split(",");
                storeOpenCloseBensonVO.setUserBrandList(userBrandList);
            }
        }

        if(storeOpenCloseBensonVO.getGubun().equals("none")) {    // 미개점
            return storeOpenCloseBensonMapper.getStoreOpenCloseBensonDayDtlNoneList(storeOpenCloseBensonVO);
        } else {    // 개점/마감
            return storeOpenCloseBensonMapper.getStoreOpenCloseBensonDayDtlList(storeOpenCloseBensonVO);
        }
    }

    /** 매장 오픈/마감 현황 - 월별 탭 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseBensonMonthList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        /*String[] storeCds = storeOpenCloseBensonVO.getStoreCds().split(",");
        storeOpenCloseBensonVO.setStoreCdList(storeCds);*/

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseBensonVO.getStoreHqBrandCd() == "" || storeOpenCloseBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseBensonVO.getUserBrands().split(",");
                storeOpenCloseBensonVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseBensonMapper.getStoreOpenCloseBensonMonthList(storeOpenCloseBensonVO);
    }

    /** 매장 오픈/마감 현황 - 월별 상세 조회 */
    @Override
    public List<DefaultMap<String>> getStoreOpenCloseBensonMonthDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO) {

        storeOpenCloseBensonVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        storeOpenCloseBensonVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeOpenCloseBensonVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeOpenCloseBensonVO.getStoreCds(), 3900));
            storeOpenCloseBensonVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        // 매장브랜드 '전체' 일때
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            if (storeOpenCloseBensonVO.getStoreHqBrandCd() == "" || storeOpenCloseBensonVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = storeOpenCloseBensonVO.getUserBrands().split(",");
                storeOpenCloseBensonVO.setUserBrandList(userBrandList);
            }
        }

        return storeOpenCloseBensonMapper.getStoreOpenCloseBensonMonthDtlList(storeOpenCloseBensonVO);
    }
}
