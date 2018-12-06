package kr.co.solbipos.iostock.vendr.slipStockInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface SlipStockInfoService {
    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getSlipStockInfoList(SlipStockInfoVO slipStockInfoVO, SessionInfoVO sessionInfoVO);

    /** 거래처 전표별 입출고내역 - 전표별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getSlipStockInfoDtlList(SlipStockInfoVO slipStockInfoVO, SessionInfoVO sessionInfoVO);

}
