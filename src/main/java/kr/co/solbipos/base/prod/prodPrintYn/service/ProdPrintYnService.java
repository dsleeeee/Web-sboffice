package kr.co.solbipos.base.prod.prodPrintYn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdPrintYnService.java
 * @Description : 기초관리 > 상품관리2 > 출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdPrintYnService {

    /** 옵션관리 탭 - 조회 */
    List<DefaultMap<Object>> getProdOptionPrintYnList(ProdPrintYnVO prodPrintYnVO, SessionInfoVO sessionInfoVO);

    /** 옵션관리 탭 - 저장 */
    int getProdOptionPrintYnSave(ProdPrintYnVO[] prodPrintYnVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴관리 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdPrintYnList(ProdPrintYnVO prodPrintYnVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴관리 탭 - 저장 */
    int getSideMenuProdPrintYnSave(ProdPrintYnVO[] prodPrintYnVOs, SessionInfoVO sessionInfoVO);
}