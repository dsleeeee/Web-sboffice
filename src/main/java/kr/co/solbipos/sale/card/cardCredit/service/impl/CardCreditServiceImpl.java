package kr.co.solbipos.sale.card.cardCredit.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.card.cardCredit.service.CardCreditService;
import kr.co.solbipos.sale.card.cardCredit.service.CardCreditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : CardCreditServiceImpl.java
 * @Description : 광운대 > 신용카드입금관리 > 신용카드입금관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.09.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("cardCreditService")
@Transactional
public class CardCreditServiceImpl implements CardCreditService {
    private final CardCreditMapper cardCreditMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public CardCreditServiceImpl(CardCreditMapper cardCreditMapper) { this.cardCreditMapper = cardCreditMapper; }

    /** 신용카드입금관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getCardCreditList(CardCreditVO cardCreditVO, SessionInfoVO sessionInfoVO) {

        cardCreditVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            cardCreditVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return cardCreditMapper.getCardCreditList(cardCreditVO);
    }

    /** 신용카드입금관리 - 저장 */
    @Override
    public int getCardCreditSave(CardCreditVO[] cardCreditVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(CardCreditVO cardCreditVO : cardCreditVOs) {

            cardCreditVO.setModDt(currentDt);
            cardCreditVO.setModId(sessionInfoVO.getUserId());
            cardCreditVO.setRegDt(currentDt);
            cardCreditVO.setRegId(sessionInfoVO.getUserId());

            procCnt = cardCreditMapper.getCardCreditSaveMerge(cardCreditVO);
        }

        return procCnt;
    }
}