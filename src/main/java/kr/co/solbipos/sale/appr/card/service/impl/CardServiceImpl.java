package kr.co.solbipos.sale.appr.card.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.card.service.CardService;
import kr.co.solbipos.sale.appr.card.service.CardVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : CardServiceImpl.java
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
@Service("cardService")
@Transactional
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;

    public CardServiceImpl(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    /** 신용카드입금관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getCardList(CardVO cardVO, SessionInfoVO sessionInfoVO) {

        cardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            cardVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = cardVO.getStoreCds().split(",");
        cardVO.setStoreCdList(storeCds);


        return cardMapper.getCardList(cardVO);
    }

    /** 신용카드입금관리 - 엑셀 조회 */
    @Override
    public List<DefaultMap<Object>> getCardExcelList(CardVO cardVO, SessionInfoVO sessionInfoVO) {

        cardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            cardVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = cardVO.getStoreCds().split(",");
        cardVO.setStoreCdList(storeCds);


        return cardMapper.getCardExcelList(cardVO);
    }

}