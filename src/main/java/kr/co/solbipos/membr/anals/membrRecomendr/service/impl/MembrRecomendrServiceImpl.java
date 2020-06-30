package kr.co.solbipos.membr.anals.membrRecomendr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointService;
import kr.co.solbipos.membr.anals.membrPoint.service.MembrPointVO;
import kr.co.solbipos.membr.anals.membrPoint.service.impl.MembrPointMapper;
import kr.co.solbipos.membr.anals.membrRecomendr.service.MembrRecomendrService;
import kr.co.solbipos.membr.anals.membrRecomendr.service.MembrRecomendrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MembrRecomendrService")
@Transactional
public class MembrRecomendrServiceImpl implements MembrRecomendrService {

    private final MembrRecomendrMapper membrRecomendrMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MembrRecomendrServiceImpl(MembrRecomendrMapper membrRecomendrMapper) {
        this.membrRecomendrMapper = membrRecomendrMapper;
    }

    /**
     * 회원 유치사원
     */
    @Override
    public List<DefaultMap<Object>> getMembrRecomendrList(MembrRecomendrVO membrRecomendrVO, SessionInfoVO sessionInfoVO) {
        return membrRecomendrMapper.getMembrPointList(membrRecomendrVO);
    }
}
