package kr.co.solbipos.sale.card.cardCreditStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CardCreditStatusService.java
 * @Description : 광운대 > 신용카드입금관리 > 신용카드입금현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CardCreditStatusService {

    /** 신용카드입금현황 - 조회 */
    List<DefaultMap<Object>> getCardCreditStatusList(CardCreditStatusVO cardCreditStatusVO, SessionInfoVO sessionInfoVO);
}