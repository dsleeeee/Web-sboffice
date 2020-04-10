package kr.co.solbipos.iostock.frnchs.slip.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface SlipService {
    /** 본사 매장간 입출고내역 - 전표별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getSlipList(SlipVO slipVO, SessionInfoVO sessionInfoVO);

    /** 본사 매장간 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getSlipDtlList(SlipVO slipVO, SessionInfoVO sessionInfoVO);

    /** 전표별 입출고내역 - 조회조건 전표구분 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchSlipFgList(SlipVO slipVO, SessionInfoVO sessionInfoVO);

    /** 전표별 입출고내역 - 조회조건 전표종류 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchSlipKindList(SlipVO slipVO, SessionInfoVO sessionInfoVO);

    /** 전표별 입출고내역 - 조회조건 진행상태 콤보 리스트 조회  */
    List<DefaultMap<String>> getSrchProcFgList(SlipVO slipVO, SessionInfoVO sessionInfoVO);

}
