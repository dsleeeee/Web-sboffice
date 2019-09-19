package kr.co.solbipos.membr.anals.membrPossesn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrPossesn.service.MembrPossesnService;
import kr.co.solbipos.membr.anals.membrPossesn.service.MembrPossesnVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MembrPossesnServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 회원매출점유
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MembrPossesnService")
@Transactional
public class MembrPossesnServiceImpl implements MembrPossesnService {
    private final MembrPossesnMapper mapper;

    /** Constructor Injection */
    @Autowired
    public MembrPossesnServiceImpl(MembrPossesnMapper mapper) {
        this.mapper = mapper;
    }

    /** 회원매출점유 */
    @Override
    public List<DefaultMap<Object>> getMembrPossesnList(MembrPossesnVO membrPossesnVO,
                                                          SessionInfoVO sessionInfoVO) {

        membrPossesnVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrPossesnVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getMembrPossesnList(membrPossesnVO);
    }
}