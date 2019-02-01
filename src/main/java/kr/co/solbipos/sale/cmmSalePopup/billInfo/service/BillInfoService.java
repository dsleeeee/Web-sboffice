package kr.co.solbipos.sale.cmmSalePopup.billInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface BillInfoService {
    /** 매출공통팝업 - 영수증상세 종합내역 조회 */
    DefaultMap<String> getBillInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 영수증상세 종합내역 조회 */
    DefaultMap<String> getBillPayInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 영수증상세 방문인원 조회 */
    DefaultMap<String> getBillGuestInfo(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO);

    /** 매출공통팝업 - 영수증상세 상품 리스트 조회 */
    List<DefaultMap<String>> getBillProdList(BillInfoVO billInfoVO, SessionInfoVO sessionInfoVO);

}
