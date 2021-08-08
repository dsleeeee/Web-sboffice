package kr.co.solbipos.membr.anals.membrRecomendr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface MembrRecomendrService {
    /** 회원 유치사원 */
    List<DefaultMap<Object>> getMembrRecomendrList(MembrRecomendrVO membrRecomendrVO, SessionInfoVO sessionInfoVO);
}
