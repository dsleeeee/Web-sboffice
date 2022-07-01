package kr.co.solbipos.excclc.excclc.saleRegist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.depositDdc.service.DepositDdcVO;

import java.util.List;

/**
 * @Class Name : SaleRegistService.java
 * @Description : 정산관리 > 정산관리 > 매출수기등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.24  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.06.24
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface SaleRegistService {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 저장*/
    int getNewRegistList(SaleRegistVO[] saleRegistVOs, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);

    /** 전표 현금 금액 */
    List<DefaultMap<String>> getCashAmt(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);

    /** 전표 매출 구분 */
    String getSaleFg(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);

    int getBillDel(SaleRegistVO saleRegistVO, SessionInfoVO sessionInfoVO);
}
