package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CouponService.java
 * @Description : 기초관리 > 결제수단 > 쿠폰등록
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
public interface CouponService {

    /** 쿠폰분류 조회 */
    List<DefaultMap<String>> getCouponClassList(PayMethodClassVO payMethodAClassVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰분류 저장 */
    int saveCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 조회 */
    List<DefaultMap<String>> getCouponList(CouponVO couponVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 저장 */
    int saveCouponList(CouponVO[] couponVOs, SessionInfoVO sessionInfoVO);
}
