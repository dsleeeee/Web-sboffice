package kr.co.solbipos.iostock.order.outstockConfm.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface OutstockConfmService {
    /** 출고확정 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(OutstockConfmVO outstockConfmVO);

    /** 출고확정 리스트 조회 */
    List<DefaultMap<String>> getOutstockConfmList(OutstockConfmVO outstockConfmVO);

    /** 출고확정 - 출고확정 */
    int saveOutstockConfirm(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO);

    /** 출고확정 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(OutstockConfmVO outstockConfmVO);

    /** 출고확정 상세 리스트 조회 */
    List<DefaultMap<String>> getOutstockConfmDtlList(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO);

    /** 출고확정 - 출고확정 상세 리스트 저장 */
    int saveOutstockConfmDtl(OutstockConfmVO[] outstockConfmVOs, SessionInfoVO sessionInfoVO);

    /** 출고확정 - 출고확정 이후 저장 */
    int saveOutstockAfter(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO);

    /** 출고창고 콤보조회(권한에 상관없이 본사창고 또는 매장창고 조회) */
    List<DefaultMap<String>> getOutStorageCombo2(OutstockConfmVO outstockConfmVO, SessionInfoVO sessionInfoVO);

}
