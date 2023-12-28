package kr.co.solbipos.sale.moms.daySaleMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.moms.daySaleMoms.service.DaySaleMomsService;
import kr.co.solbipos.sale.moms.daySaleMoms.service.DaySaleMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Class Name : DaySaleMomsServiceImpl.java
 * @Description : 맘스터치 > 간소화화면 > 일별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.12.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.12.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("daySaleMomsService")
@Transactional
public class DaySaleMomsServiceImpl implements DaySaleMomsService {
    private final DaySaleMomsMapper daySaleMomsMapper;
    private final PopupMapper popupMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleMomsServiceImpl(DaySaleMomsMapper daySaleMomsMapper, PopupMapper popupMapper) {
        this.daySaleMomsMapper = daySaleMomsMapper;
        this.popupMapper = popupMapper;
    }

    /** 일별매출 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleMomsList(DaySaleMomsVO daySaleMomsVO, SessionInfoVO sessionInfoVO) {

        daySaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleMomsVO.getStoreCds(), 3900));
            daySaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleMomsVO.getStoreHqBrandCd() == "" || daySaleMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleMomsVO.getUserBrands().split(",");
                daySaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleMomsMapper.getDaySaleMomsList(daySaleMomsVO);
    }

    /** 일별매출 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySaleMomsExcelList(DaySaleMomsVO daySaleMomsVO, SessionInfoVO sessionInfoVO) {

        daySaleMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            daySaleMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(daySaleMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(daySaleMomsVO.getStoreCds(), 3900));
            daySaleMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            // 매장브랜드가 '전체' 일때
            if (daySaleMomsVO.getStoreHqBrandCd() == "" || daySaleMomsVO.getStoreHqBrandCd() == null) {
                // 사용자별 브랜드 array 값 세팅
                String[] userBrandList = daySaleMomsVO.getUserBrands().split(",");
                daySaleMomsVO.setUserBrandList(userBrandList);
            }
        }

        return daySaleMomsMapper.getDaySaleMomsExcelList(daySaleMomsVO);
    }
}