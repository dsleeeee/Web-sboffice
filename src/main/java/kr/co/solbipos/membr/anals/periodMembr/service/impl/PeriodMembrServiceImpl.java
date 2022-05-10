package kr.co.solbipos.membr.anals.periodMembr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.periodMembr.service.PeriodMembrService;
import kr.co.solbipos.membr.anals.periodMembr.service.PeriodMembrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PeriodMembrServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 기간회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("PeriodMembrService")
@Transactional
public class PeriodMembrServiceImpl implements PeriodMembrService {
    private final PeriodMembrMapper mapper;

    /** Constructor Injection */
    @Autowired
    public PeriodMembrServiceImpl(PeriodMembrMapper mapper) {
        this.mapper = mapper;
    }

    /** 기간회원 구매내역*/
    @Override
    public List<DefaultMap<Object>> getPeriodMembrList(PeriodMembrVO periodMembrVO, SessionInfoVO sessionInfoVO) {

        periodMembrVO.setMembrOrgnCd(sessionInfoVO.getOrgnGrpCd());
        periodMembrVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        periodMembrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        periodMembrVO.setEmpNo(sessionInfoVO.getEmpNo());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            periodMembrVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getPeriodMembrList(periodMembrVO);
    }
}