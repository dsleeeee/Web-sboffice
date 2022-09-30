package kr.co.solbipos.sale.appr.gift.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.gift.service.GiftApprService;
import kr.co.solbipos.sale.appr.gift.service.GiftApprVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : GiftServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 상품권 승인 조회
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
@Service("giftApprService")
@Transactional
public class GiftApprApprServiceImpl implements GiftApprService {
    private final GiftApprMapper giftMapper;

    public GiftApprApprServiceImpl(GiftApprMapper giftMapper) {
        this.giftMapper = giftMapper;
    }

    /** 모바일쿠폰입금관리 - 매장 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getGiftList(GiftApprVO giftVO, SessionInfoVO sessionInfoVO) {

        giftVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            giftVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = giftVO.getStoreCds().split(",");
        giftVO.setStoreCdList(storeCds);


        return giftMapper.getGiftList(giftVO);
    }

}