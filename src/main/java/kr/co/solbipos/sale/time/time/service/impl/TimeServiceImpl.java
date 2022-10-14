package kr.co.solbipos.sale.time.time.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.time.time.service.TimeService;
import kr.co.solbipos.sale.time.time.service.TimeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : TimeServiceImpl.java
 * @Description : 맘스터치 > 시간대별매출 > 시간대별 일 매출
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
@Service("timeService")
@Transactional
public class TimeServiceImpl implements TimeService {
    private final TimeMapper timeMapper;

    public TimeServiceImpl(TimeMapper timeMapper) {
        this.timeMapper = timeMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeList(TimeVO timeVO, SessionInfoVO sessionInfoVO) {

        timeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeVO.getStoreCds().split(",");
        timeVO.setStoreCdList(storeCds);

        return timeMapper.getTimeList(timeVO);
    }
    
    /** 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getTimeExcelList(TimeVO timeVO, SessionInfoVO sessionInfoVO) {

        timeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            timeVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = timeVO.getStoreCds().split(",");
        timeVO.setStoreCdList(storeCds);

        return timeMapper.getTimeExcelList(timeVO);
    }
}