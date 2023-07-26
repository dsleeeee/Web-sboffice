package kr.co.solbipos.base.prod.storeProdPrintYn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreProdPrintYnService.java
 * @Description : 기초관리 > 상품관리2 > 매장별출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreProdPrintYnService {

    /** 옵션관리 탭 - 조회 */
    List<DefaultMap<Object>> getStoreProdOptionPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, SessionInfoVO sessionInfoVO);

    /** 옵션관리 탭 - 저장 */
    int getStoreProdOptionPrintYnSave(StoreProdPrintYnVO[] storeProdPrintYnVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴관리 탭 - 조회 */
    List<DefaultMap<Object>> getStoreSideMenuProdPrintYnList(StoreProdPrintYnVO storeProdPrintYnVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴관리 탭 - 저장 */
    int getStoreSideMenuProdPrintYnSave(StoreProdPrintYnVO[] storeProdPrintYnVOs, SessionInfoVO sessionInfoVO);
}