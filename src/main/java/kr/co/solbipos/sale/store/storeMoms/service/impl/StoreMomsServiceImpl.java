package kr.co.solbipos.sale.store.storeMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeMoms.service.StoreMomsService;
import kr.co.solbipos.sale.store.storeMoms.service.StoreMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreMomsServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeMomsService")
@Transactional
public class StoreMomsServiceImpl implements StoreMomsService {
    private final StoreMomsMapper storeMomsMapper;
    private final PopupMapper popupMapper;

    public StoreMomsServiceImpl(StoreMomsMapper storeMomsMapper, PopupMapper popupMapper) {
        this.storeMomsMapper = storeMomsMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreMomsList(StoreMomsVO storeMomsVO, SessionInfoVO sessionInfoVO) {

        storeMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMomsVO.getStoreCds(), 3900));
            storeMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeMomsMapper.getStoreMomsList(storeMomsVO);
    }
    
    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreMomsExcelList(StoreMomsVO storeMomsVO, SessionInfoVO sessionInfoVO) {

        storeMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeMomsVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeMomsVO.getStoreCds(), 3900));
            storeMomsVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeMomsMapper.getStoreMomsExcelList(storeMomsVO);
    }

}