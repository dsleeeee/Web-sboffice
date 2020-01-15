package kr.co.solbipos.sale.cmmSalePopup.saleInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface SaleInfoService {

    /** 매장정보,매출종합내역,결제내역,회원정보 조회 */
    DefaultMap<String> getSaleDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO);

    /** 신용카드 결재내역 조회 */
    List<DefaultMap<String>> getSaleCardDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO);

    /** 현금영수증 결재내역 조회 */
    List<DefaultMap<String>> getSaleCashDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO);

    /** 상품내역 조회 */
    List<DefaultMap<String>> getSaleProdDtlList(SaleInfoVO saleInfoVO, SessionInfoVO sessionInfoVO);
}
