package kr.co.solbipos.excclc.excclc.saleRegistKmu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SaleRegistKmuService.java
 * @Description : 국민대 > 매출관리 > 매출전표등록(일반)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleRegistKmuService {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistKmuList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 저장*/
    int getNewRegistList(SaleRegistKmuVO[] saleRegistKmuVOs, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 전표 현금 금액 */
    List<DefaultMap<String>> getCashAmt(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 전표 매출 구분 */
    String getSaleFg(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    int getBillDel(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 회원선택 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleRegistKmuMemberList(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);

    /** 매출전표등록(일반) - 삭제 */
    int getNewRegistDel(SaleRegistKmuVO saleRegistKmuVO, SessionInfoVO sessionInfoVO);
}
