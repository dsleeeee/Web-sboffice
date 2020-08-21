package kr.co.solbipos.dlvr.anals.dlvrReceipt.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrReceiptService {
    List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO);
}
