package kr.co.solbipos.sale.period.periodStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.period.periodStore.service.PeriodStoreService;
import kr.co.solbipos.sale.period.periodStore.service.PeriodStoreVO;
import kr.co.solbipos.sale.period.periodStore.service.impl.PeriodStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PeriodStoreServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출(매장합산)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("periodStoreService")
@Transactional
public class PeriodStoreServiceImpl implements PeriodStoreService {
    private final PeriodStoreMapper periodStoreMapper;
    private final PopupMapper popupMapper;

    public PeriodStoreServiceImpl(PeriodStoreMapper periodStoreMapper, PopupMapper popupMapper) {
        this.periodStoreMapper = periodStoreMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodStoreList(PeriodStoreVO periodStoreVO, SessionInfoVO sessionInfoVO) {

        periodStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(periodStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(periodStoreVO.getStoreCds(), 3900));
            periodStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return periodStoreMapper.getPeriodStoreList(periodStoreVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodStoreExcelList(PeriodStoreVO periodStoreVO, SessionInfoVO sessionInfoVO) {

        periodStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(periodStoreVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(periodStoreVO.getStoreCds(), 3900));
            periodStoreVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return periodStoreMapper.getPeriodStoreExcelList(periodStoreVO);
    }
}