package kr.co.solbipos.sale.day.dayTest.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.day.dayTest.service.DayTestService;
import kr.co.solbipos.sale.day.dayTest.service.DayTestVO;
import kr.co.solbipos.sale.day.dayTest.service.impl.DayTestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DayTestServiceImpl.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(테스트)
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
@Service("dayTestService")
@Transactional
public class DayTestServiceImpl implements DayTestService {
    private final DayTestMapper dayTestMapper;

    public DayTestServiceImpl(DayTestMapper dayTestMapper) {
        this.dayTestMapper = dayTestMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getDayTestList(DayTestVO dayTestVO, SessionInfoVO sessionInfoVO) {

        dayTestVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayTestVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayTestVO.getStoreCds().split(",");
        dayTestVO.setStoreCdList(storeCds);

        return dayTestMapper.getDayTestList(dayTestVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getDayTestExcelList(DayTestVO dayTestVO, SessionInfoVO sessionInfoVO) {

        dayTestVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            dayTestVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = dayTestVO.getStoreCds().split(",");
        dayTestVO.setStoreCdList(storeCds);

        return dayTestMapper.getDayTestExcelList(dayTestVO);
    }
}