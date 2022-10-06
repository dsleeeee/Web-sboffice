package kr.co.solbipos.sale.appr.card.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CardService.java
 * @Description : 맘스터치 > 승인관리2 > 신용카드 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CardService {

    /** 신용카드 승인 조회 */
    List<DefaultMap<Object>> getCardList(CardVO cardVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getCardExcelList(CardVO cardVO, SessionInfoVO sessionInfoVO);

}