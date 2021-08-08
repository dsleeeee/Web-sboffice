package kr.co.solbipos.dlvr.anals.dlvrReceipt.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrReceiptService.java
 * @Description : 배달관리 > 배달분석 > 배달지별 내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrReceiptService {
    /** 배달지별 -조회*/
    List<DefaultMap<String>> getDlvrReceiptList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO);

    /** 배달지별 - 상세조회*/
    List<DefaultMap<String>> getDlvrReceiptDetailList(DlvrReceiptVO dlvrReceiptVO, SessionInfoVO sessionInfoVO);
}
