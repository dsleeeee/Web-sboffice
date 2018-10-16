package kr.co.solbipos.iostock.order.instockConfm.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface InstockConfmService {
    /** 입고확정 리스트 조회 */
    List<DefaultMap<String>> getInstockConfmList(InstockConfmVO instockConfmVO);

    /** 입고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(InstockConfmVO instockConfmVO);

    /** 입고확정 상세 리스트 조회 */
    List<DefaultMap<String>> getInstockConfmDtlList(InstockConfmVO instockConfmVO);

    /** 입고확정 - 입고확정 상세 리스트 저장 */
    int saveInstockConfmDtl(InstockConfmVO[] instockConfmVOs, SessionInfoVO sessionInfoVO);

}
