package kr.co.solbipos.membr.anals.membrPoint.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointService;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MembrPointServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 회원 포인트실적
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MembrPointService")
@Transactional
public class MembrPointServiceImpl implements MembrPointService {
    private final MembrPointMapper membrPointMapper;

    /** Constructor Injection */
    @Autowired
    public MembrPointServiceImpl(MembrPointMapper membrPointMapper) {
        this.membrPointMapper = membrPointMapper;
    }

    /** 회원 포인트실적 */
    @Override
    public List<DefaultMap<Object>> getMembrPointList(MembrPointVO membrPointVO, SessionInfoVO sessionInfoVO) {

        membrPointVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrPointVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        String[] storeCds = membrPointVO.getStoreCds().split(",");
        membrPointVO.setStoreCdList(storeCds);

        return membrPointMapper.getMembrPointList(membrPointVO);
    }
}
