package kr.co.solbipos.membr.anals.membrNonBilClct.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrNonBilClct.service.MembrNonBilClctService;
import kr.co.solbipos.membr.anals.membrNonBilClct.service.MembrNonBilClctVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MembrNonBilClctServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 회원 미수금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MembrNonBilClctService")
@Transactional
public class MembrNonBilClctServiceImpl implements MembrNonBilClctService {
    private final MembrNonBilClctMapper membrNonBilClctMapper;

    /** Constructor Injection */
    @Autowired
    public MembrNonBilClctServiceImpl(MembrNonBilClctMapper membrNonBilClctMapper) {
        this.membrNonBilClctMapper = membrNonBilClctMapper;
    }

    /** 회원 미수금현황 */
    @Override
    public List<DefaultMap<Object>> getMembrNonBilClctList(MembrNonBilClctVO membrNonBilClctVO, SessionInfoVO sessionInfoVO) {

        membrNonBilClctVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrNonBilClctVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return membrNonBilClctMapper.getMembrNonBilClctList(membrNonBilClctVO);
    }

    /** 회원 미수금현황 상세조회 */
    @Override
    public List<DefaultMap<Object>> getMembrNonBilClctDetailList(MembrNonBilClctVO membrNonBilClctVO, SessionInfoVO sessionInfoVO) {

        membrNonBilClctVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrNonBilClctVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return membrNonBilClctMapper.getMembrNonBilClctDetailList(membrNonBilClctVO);
    }
}