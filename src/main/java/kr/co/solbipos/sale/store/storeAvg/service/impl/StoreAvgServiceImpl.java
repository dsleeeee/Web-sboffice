package kr.co.solbipos.sale.store.storeAvg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.store.storeAvg.service.StoreAvgService;
import kr.co.solbipos.sale.store.storeAvg.service.StoreAvgVO;
import kr.co.solbipos.sale.store.storeAvg.service.impl.StoreAvgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StoreAvgServiceImpl.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 평균 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("storeAvgService")
@Transactional
public class StoreAvgServiceImpl implements StoreAvgService {
    private final StoreAvgMapper storeAvgMapper;
    private final PopupMapper popupMapper;

    public StoreAvgServiceImpl(StoreAvgMapper storeAvgMapper, PopupMapper popupMapper) {
        this.storeAvgMapper = storeAvgMapper;
        this.popupMapper = popupMapper;
    }


    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreAvgList(StoreAvgVO storeAvgVO, SessionInfoVO sessionInfoVO) {

        storeAvgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeAvgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeAvgVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeAvgVO.getStoreCds(), 3900));
            storeAvgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeAvgMapper.getStoreAvgList(storeAvgVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreAvgExcelList(StoreAvgVO storeAvgVO, SessionInfoVO sessionInfoVO) {

        storeAvgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            storeAvgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(storeAvgVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(storeAvgVO.getStoreCds(), 3900));
            storeAvgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return storeAvgMapper.getStoreAvgExcelList(storeAvgVO);
    }

}