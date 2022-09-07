package kr.co.solbipos.excclc.excclc.saleRegistKw.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SaleRegistKwKwService.java
 * @Description : 광운대 > 후방매출등록 > 후방매출등록	
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.31  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.08.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface SaleRegistKwService {

    /** 매출수기등록 리스트 조회 */
    List<DefaultMap<String>> getSaleRegistKwList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 상품 조회 */
    List<DefaultMap<String>> getSelectProdList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    /** 매출처 팝업 조회 */
    List<DefaultMap<String>> getMembrKwList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 저장*/
    int getNewRegistKwList(SaleRegistKwVO[] saleRegistKwVOs, SessionInfoVO sessionInfoVO);

    /** 매출수기등록 특정 전표 조회 */
    List<DefaultMap<String>> getBillDtlList(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    /** 전표 현금 금액 */
    List<DefaultMap<String>> getCashAmt(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getHdrInfo(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    /** 전표 매출 구분 */
    String getSaleFg(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);

    int getBillDel(SaleRegistKwVO saleRegistKwVO, SessionInfoVO sessionInfoVO);
}
