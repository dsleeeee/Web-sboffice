package kr.co.solbipos.iostock.orderReturn.rtnDstmn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnDstmnService {
    /** 반품명세표 - 매장요청 미확정건, 출고자료 미생성건 조회 */
    DefaultMap<String> getReqNoConfirmCnt(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 리스트 조회 */
    List<DefaultMap<String>> getRtnDstmnList(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 전표상세 조회 */
    DefaultMap<String> getSlipNoInfo(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstmnDtlList(RtnDstmnVO rtnDstmnVO);

    /** 반품명세표 - 반품명세표 상세 리스트 저장 */
    int saveRtnDstmnDtl(RtnDstmnVO[] rtnDstmnVOs, SessionInfoVO sessionInfoVO);

    /** 반품명세표 - 반품매장출고 이후 저장 */
    int saveOutstockAfter(RtnDstmnVO rtnDstmnVO, SessionInfoVO sessionInfoVO);
}
