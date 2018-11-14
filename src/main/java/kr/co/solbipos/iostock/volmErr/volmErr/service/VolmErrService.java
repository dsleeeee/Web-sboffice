package kr.co.solbipos.iostock.volmErr.volmErr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface VolmErrService {
    /** 물량오류관리 - 물량오류관리 리스트 조회 */
    List<DefaultMap<String>> getVolmErrList(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 상세 리스트 조회 */
    List<DefaultMap<String>> getVolmErrDtlList(VolmErrVO volmErrVO);

    /** 물량오류관리 - 물량오류관리 상세 저장 */
    int saveVolmErrDtl(VolmErrVO[] volmErrVOs, SessionInfoVO sessionInfoVO);




    /** 콤보조회 */
    List<DefaultMap<String>> selectCmmCodeList(VolmErrVO volmErrVO);

    /** 다이나믹 콤보조회 */
    List<DefaultMap<String>> selectDynamicCodeList(VolmErrVO volmErrVO);

}
