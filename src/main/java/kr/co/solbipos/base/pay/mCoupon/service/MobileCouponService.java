package kr.co.solbipos.base.pay.mCoupon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;

import java.util.List;

/**
 * @Class Name : MobileCouponService.java
 * @Description : 기초관리 > 결제수단 >  모바일쿠폰등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileCouponService {

    /** 쿠폰분류 조회 */
    List<DefaultMap<String>> getMobileCouponClassList(PayMethodClassVO payMethodClassVO,
        SessionInfoVO sessionInfoVO);

    /** 쿠폰분류 저장 */
    int saveMobileCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO);

}
