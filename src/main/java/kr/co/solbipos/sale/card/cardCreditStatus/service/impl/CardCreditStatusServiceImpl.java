package kr.co.solbipos.sale.card.cardCreditStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.card.cardCreditStatus.service.CardCreditStatusService;
import kr.co.solbipos.sale.card.cardCreditStatus.service.CardCreditStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : CardCreditStatusServiceImpl.java
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
@Service("cardCreditStatusService")
@Transactional
public class CardCreditStatusServiceImpl implements CardCreditStatusService {
    private final CardCreditStatusMapper cardCreditStatusMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardCreditStatusServiceImpl(CardCreditStatusMapper cardCreditStatusMapper) { this.cardCreditStatusMapper = cardCreditStatusMapper; }

    /** 신용카드입금현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getCardCreditStatusList(CardCreditStatusVO cardCreditStatusVO, SessionInfoVO sessionInfoVO) {

        cardCreditStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            cardCreditStatusVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return cardCreditStatusMapper.getCardCreditStatusList(cardCreditStatusVO);
    }
}