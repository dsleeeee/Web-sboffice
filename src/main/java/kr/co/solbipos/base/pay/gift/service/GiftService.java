package kr.co.solbipos.base.pay.gift.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponStoreVO;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;

import java.util.List;

/**
 * @Class Name : CouponService.java
 * @Description : 기초관리 > 결제수단 > 상품권등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.18  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface GiftService {

    /** 상품권 분류 조회 */
    List<DefaultMap<String>> getGiftClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO);

    /** 상품권분류 저장 */
    int saveGiftClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO);

    /** 상품권 조회 */
    List<DefaultMap<String>> getGiftList(GiftVO giftVO, SessionInfoVO sessionInfoVO);

    /** 상품권 저장 */
    int saveGiftList(GiftVO[] giftVOs, SessionInfoVO sessionInfoVO);

}
