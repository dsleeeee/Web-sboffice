package kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.CardCoSaleMrpizzaService;
import kr.co.solbipos.sale.mrpizza.cardCoSaleMrpizza.service.CardCoSaleMrpizzaVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : CardCoSaleMrpizzaServiceImpl.java
 * @Description : 미스터피자 > 마케팅조회 > 카드사별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("cardCoSaleMrpizzaService")
@Transactional
public class CardCoSaleMrpizzaServiceImpl implements CardCoSaleMrpizzaService {

    private final CardCoSaleMrpizzaMapper cardCoSaleMrpizzaMapper;
    private final PopupMapper popupMapper;

    public CardCoSaleMrpizzaServiceImpl(CardCoSaleMrpizzaMapper cardCoSaleMrpizzaMapper, PopupMapper popupMapper) {
        this.cardCoSaleMrpizzaMapper = cardCoSaleMrpizzaMapper;
        this.popupMapper = popupMapper;
    }

    /** 카드사별매출 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getCardCoSaleMrpizzaList(CardCoSaleMrpizzaVO cardCoSaleMrpizzaVO, SessionInfoVO sessionInfoVO) {

        cardCoSaleMrpizzaVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        // 매장 array 값 세팅
        if (!StringUtil.getOrBlank(cardCoSaleMrpizzaVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(cardCoSaleMrpizzaVO.getStoreCds(), 3900));
            cardCoSaleMrpizzaVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }

        return cardCoSaleMrpizzaMapper.getCardCoSaleMrpizzaList(cardCoSaleMrpizzaVO);
    }
}
