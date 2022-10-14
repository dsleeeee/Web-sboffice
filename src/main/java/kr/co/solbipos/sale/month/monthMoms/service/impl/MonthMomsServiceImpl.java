package kr.co.solbipos.sale.month.monthMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsService;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsVO;
import kr.co.solbipos.sale.month.monthMoms.service.impl.MonthMomsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MonthMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황
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
@Service("monthMomsService")
@Transactional
public class MonthMomsServiceImpl implements MonthMomsService {
    private final MonthMomsMapper monthMomsMapper;

    public MonthMomsServiceImpl(MonthMomsMapper monthMomsMapper) {
        this.monthMomsMapper = monthMomsMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthMomsList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO) {

        monthMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthMomsVO.getStoreCds().split(",");
        monthMomsVO.setStoreCdList(storeCds);

        return monthMomsMapper.getMonthMomsList(monthMomsVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getMonthMomsExcelList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO) {

        monthMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            monthMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = monthMomsVO.getStoreCds().split(",");
        monthMomsVO.setStoreCdList(storeCds);

        return monthMomsMapper.getMonthMomsExcelList(monthMomsVO);
    }
}