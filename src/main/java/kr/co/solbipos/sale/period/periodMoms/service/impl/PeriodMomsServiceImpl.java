package kr.co.solbipos.sale.period.periodMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.period.periodMoms.service.PeriodMomsService;
import kr.co.solbipos.sale.period.periodMoms.service.PeriodMomsVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PeriodMomsServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출
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
@Service("periodMomsService")
@Transactional
public class PeriodMomsServiceImpl implements PeriodMomsService {
    private final PeriodMomsMapper periodMomsMapper;

    public PeriodMomsServiceImpl(PeriodMomsMapper periodMomsMapper) {
        this.periodMomsMapper = periodMomsMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodMomsList(PeriodMomsVO periodMomsVO, SessionInfoVO sessionInfoVO) {

        periodMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = periodMomsVO.getStoreCds().split(",");
        periodMomsVO.setStoreCdList(storeCds);

        return periodMomsMapper.getPeriodMomsList(periodMomsVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodMomsExcelList(PeriodMomsVO periodMomsVO, SessionInfoVO sessionInfoVO) {

        periodMomsVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodMomsVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = periodMomsVO.getStoreCds().split(",");
        periodMomsVO.setStoreCdList(storeCds);

        return periodMomsMapper.getPeriodMomsExcelList(periodMomsVO);
    }
}