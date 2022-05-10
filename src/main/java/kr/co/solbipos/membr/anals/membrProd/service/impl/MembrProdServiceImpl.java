package kr.co.solbipos.membr.anals.membrProd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrProd.service.MembrProdService;
import kr.co.solbipos.membr.anals.membrProd.service.MembrProdVO;
import kr.co.solbipos.membr.info.regist.service.RegistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : MembrProdServiceImpl.java
 * @Description : 회원관리 > 회원분석 > 회원 상품 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.09.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.09.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("MembrProdService")
@Transactional
public class MembrProdServiceImpl implements MembrProdService {
    private final MembrProdMapper mapper;

    /** Constructor Injection */
    @Autowired
    public MembrProdServiceImpl(MembrProdMapper mapper) {
        this.mapper = mapper;
    }

    /** 회원 상품 구매내역*/
    @Override
    public List<DefaultMap<Object>> getMembrProdList(MembrProdVO membrProdVO, SessionInfoVO sessionInfoVO) {

        membrProdVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        membrProdVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        membrProdVO.setEmpNo(sessionInfoVO.getEmpNo());
        membrProdVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            membrProdVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return mapper.getMembrProdList(membrProdVO);
    }
}