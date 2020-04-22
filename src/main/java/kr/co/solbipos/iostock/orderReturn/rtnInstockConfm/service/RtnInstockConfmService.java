package kr.co.solbipos.iostock.orderReturn.rtnInstockConfm.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnInstockConfmService {
    /** 반품본사입고 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmList(RtnInstockConfmVO rtnInstockConfmVO);

    /** 반품본사입고 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnInstockConfmVO rtnInstockConfmVO);

    /** 반품본사입고 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnInstockConfmDtlList(RtnInstockConfmVO rtnInstockConfmVO, SessionInfoVO sessionInfoVO);

    /** 반품본사입고 - 반품본사입고 상세 리스트 저장 */
    int saveRtnInstockConfmDtl(RtnInstockConfmVO[] rtnInstockConfmVOs, SessionInfoVO sessionInfoVO);

}
