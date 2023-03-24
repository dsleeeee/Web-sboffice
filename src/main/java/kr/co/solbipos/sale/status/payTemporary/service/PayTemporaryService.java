package kr.co.solbipos.sale.status.payTemporary.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PayTemporaryService {

    /** 리스트 조회 */
    List<DefaultMap<String>> getPayTemporaryList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getPayTemporaryExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getPayTemporaryDtlList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
}
