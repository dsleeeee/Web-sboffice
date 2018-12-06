package kr.co.solbipos.iostock.vendr.prodStockInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdStockInfoService {
    /** 거래처 상품별 입출고내역 - 상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getProdStockInfoList(ProdStockInfoVO prodStockInfoVO, SessionInfoVO sessionInfoVO);

}
