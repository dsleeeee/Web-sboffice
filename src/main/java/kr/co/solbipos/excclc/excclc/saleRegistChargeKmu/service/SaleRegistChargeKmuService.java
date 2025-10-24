package kr.co.solbipos.excclc.excclc.saleRegistChargeKmu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SaleRegistChargeKmuService.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(수수료)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleRegistChargeKmuService {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistChargeKmuList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 저장*/
    int getNewRegistList(SaleRegistChargeKmuVO[] saleRegistChargeKmuVOs, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 전표 현금 금액 */
    List<DefaultMap<String>> getCashAmt(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 전표 매출 구분 */
    String getSaleFg(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    int getBillDel(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 회원선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistChargeKmuMemberList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출처선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistChargeKmuCustomerList(SaleRegistChargeKmuVO saleRegistChargeKmuVO, SessionInfoVO sessionInfoVO);
}