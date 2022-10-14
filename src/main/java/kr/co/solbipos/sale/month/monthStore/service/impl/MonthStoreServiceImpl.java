package kr.co.solbipos.sale.month.monthStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthStore.service.MonthStoreService;
import kr.co.solbipos.sale.month.monthStore.service.MonthStoreVO;
import kr.co.solbipos.sale.month.monthStore.service.impl.MonthStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthStoreServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황(매장합산)
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
@Service("monthStoreService")
@Transactional
public class MonthStoreServiceImpl implements MonthStoreService {
    private final MonthStoreMapper monthStoreMapper;

    public MonthStoreServiceImpl(MonthStoreMapper monthStoreMapper) {
        this.monthStoreMapper = monthStoreMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthStoreList(MonthStoreVO monthStoreVO, SessionInfoVO sessionInfoVO) {

        monthStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthStoreVO.getStoreCds().split(",");
        monthStoreVO.setStoreCdList(storeCds);

        return monthStoreMapper.getMonthStoreList(monthStoreVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthStoreExcelList(MonthStoreVO monthStoreVO, SessionInfoVO sessionInfoVO) {

        monthStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthStoreVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthStoreVO.getStoreCds().split(",");
        monthStoreVO.setStoreCdList(storeCds);

        return monthStoreMapper.getMonthStoreExcelList(monthStoreVO);
    }
}