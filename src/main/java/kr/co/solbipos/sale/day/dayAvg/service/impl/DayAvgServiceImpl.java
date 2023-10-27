package kr.co.solbipos.sale.day.dayAvg.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayAvg.service.DayAvgService;
import kr.co.solbipos.sale.day.dayAvg.service.DayAvgVO;
import kr.co.solbipos.sale.day.dayAvg.service.impl.DayAvgMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayAvgServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(합산평균)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("dayAvgService")
@Transactional
public class DayAvgServiceImpl implements DayAvgService {
    private final DayAvgMapper dayAvgMapper;
    private final PopupMapper popupMapper;

    public DayAvgServiceImpl(DayAvgMapper dayAvgMapper, PopupMapper popupMapper) {
        this.dayAvgMapper = dayAvgMapper;
        this.popupMapper = popupMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayAvgList(DayAvgVO dayAvgVO, SessionInfoVO sessionInfoVO) {

        dayAvgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayAvgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayAvgVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayAvgVO.getStoreCds(), 3900));
            dayAvgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayAvgMapper.getDayAvgList(dayAvgVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayAvgExcelList(DayAvgVO dayAvgVO, SessionInfoVO sessionInfoVO) {

        dayAvgVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayAvgVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(dayAvgVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(dayAvgVO.getStoreCds(), 3900));
            dayAvgVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return dayAvgMapper.getDayAvgExcelList(dayAvgVO);
    }
}