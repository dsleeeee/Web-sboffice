package kr.co.solbipos.sale.day.dayStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayStore.service.DayStoreService;
import kr.co.solbipos.sale.day.dayStore.service.DayStoreVO;
import kr.co.solbipos.sale.day.dayStore.service.impl.DayStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayStoreServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(매장합산)
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
@Service("dayStoreService")
@Transactional
public class DayStoreServiceImpl implements DayStoreService {
    private final DayStoreMapper dayStoreMapper;

    public DayStoreServiceImpl(DayStoreMapper dayStoreMapper) {
        this.dayStoreMapper = dayStoreMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayStoreList(DayStoreVO dayStoreVO, SessionInfoVO sessionInfoVO) {

        dayStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayStoreVO.getStoreCds().split(",");
        dayStoreVO.setStoreCdList(storeCds);

        return dayStoreMapper.getDayStoreList(dayStoreVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayStoreExcelList(DayStoreVO dayStoreVO, SessionInfoVO sessionInfoVO) {

        dayStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayStoreVO.getStoreCds().split(",");
        dayStoreVO.setStoreCdList(storeCds);

        return dayStoreMapper.getDayStoreExcelList(dayStoreVO);
    }

    @Override
    public List<DefaultMap<String>> getBranchMomsComboList(DayStoreVO dayStoreVO, SessionInfoVO sessionInfoVO) {
        dayStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return dayStoreMapper.getBranchMomsComboList(dayStoreVO);
    }

}