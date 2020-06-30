package kr.co.solbipos.membr.anals.membrRecomendr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface MembrRecomendrService {
    List<DefaultMap<Object>> getMembrRecomendrList(MembrRecomendrVO membrRecomendrVO, SessionInfoVO sessionInfoVO);
}
