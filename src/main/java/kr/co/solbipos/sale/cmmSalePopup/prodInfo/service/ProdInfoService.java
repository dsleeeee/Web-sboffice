package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdInfoService {

    /** 매출공통팝업 - 상품매출 상세내역 조회 */
    List<DefaultMap<Object>> getProdSaleDtlList(ProdInfoVO prodInfoVO, SessionInfoVO sessionInfoVO);
}
