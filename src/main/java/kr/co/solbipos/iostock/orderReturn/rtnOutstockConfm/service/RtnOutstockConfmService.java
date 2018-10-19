package kr.co.solbipos.iostock.orderReturn.rtnOutstockConfm.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnOutstockConfmService {
    /** 반품매장출고 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockConfmList(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 */
    int saveOutstockConfirm(RtnOutstockConfmVO[] rtnOutstockConfmVOs, SessionInfoVO sessionInfoVO);

    /** 반품매장출고 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnOutstockConfmDtlList(RtnOutstockConfmVO rtnOutstockConfmVO);

    /** 반품매장출고 - 반품매장출고 상세 리스트 저장 */
    int saveRtnOutstockConfmDtl(RtnOutstockConfmVO[] rtnOutstockConfmVOs, SessionInfoVO sessionInfoVO);

    /** 반품매장출고 - 반품매장출고 이후 저장 */
    int saveOutstockAfter(RtnOutstockConfmVO rtnOutstockConfmVO, SessionInfoVO sessionInfoVO);
}
