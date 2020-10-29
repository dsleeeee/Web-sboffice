package kr.co.solbipos.membr.anals.membrPoint.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointService;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @version 1.0
 * <p>
 * Copyright (C) by SOLBIPOS CORP. All right reserved.
 * @Class Name : MembrPointServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 회원 포인트실적
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.11  김설아      최초생성
 * @since 2019.11.11
 */
@Service("MembrPointService")
@Transactional
public class MembrPointServiceImpl implements MembrPointService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final MembrPointMapper membrPointMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MembrPointServiceImpl(MembrPointMapper membrPointMapper) {
        this.membrPointMapper = membrPointMapper;
    }

    /**
     * 회원 포인트실적
     */
    @Override
    public List<DefaultMap<Object>> getMembrPointList(MembrPointVO membrPointVO, SessionInfoVO sessionInfoVO) {

        LOGGER.debug("sessionInfoVO.getOrgnGrpCd(): {}", sessionInfoVO.getOrgnGrpCd());

        membrPointVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            membrPointVO.setStoreCd(sessionInfoVO.getStoreCd());
        } else {
            String[] storeCds = membrPointVO.getStoreCds().split(",");
            membrPointVO.setStoreCdList(storeCds);
        }


        return membrPointMapper.getMembrPointList(membrPointVO);
    }
}
