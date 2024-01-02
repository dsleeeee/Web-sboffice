package kr.co.solbipos.sale.moms.daySaleStoreMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.daySaleStoreMoms.service.DaySaleStoreMomsService;
import kr.co.solbipos.sale.moms.daySaleStoreMoms.service.DaySaleStoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : DaySaleStoreMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 일별매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.29  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("daySaleStoreMomsService")
@Transactional
public class DaySaleStoreMomsServiceImpl implements DaySaleStoreMomsService {
    private final DaySaleStoreMomsMapper daySaleStoreMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleStoreMomsServiceImpl(DaySaleStoreMomsMapper daySaleStoreMomsMapper, PopupMapper popupMapper) {
        this.daySaleStoreMomsMapper = daySaleStoreMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별매출(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleStoreMomsList(DaySaleStoreMomsVO daySaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        daySaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleStoreMomsVO.getStoreCds(), 3900));
            daySaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleStoreMomsVO.getStoreHqBrandCd() == "" || daySaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleStoreMomsVO.getUserBrands().split(",");
                daySaleStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleStoreMomsMapper.getDaySaleStoreMomsList(daySaleStoreMomsVO);
    }

    /** 일별매출(매장) - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleStoreMomsExcelList(DaySaleStoreMomsVO daySaleStoreMomsVO, SessionInfoVO sessionInfoVO) {

        daySaleStoreMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleStoreMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleStoreMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleStoreMomsVO.getStoreCds(), 3900));
            daySaleStoreMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleStoreMomsVO.getStoreHqBrandCd() == "" || daySaleStoreMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleStoreMomsVO.getUserBrands().split(",");
                daySaleStoreMomsVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleStoreMomsMapper.getDaySaleStoreMomsExcelList(daySaleStoreMomsVO);
    }
}