package kr.co.solbipos.base.pay.mCoupon.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : MobileCouponMapper.java
 * @Description : 기초관리 > 결제수단 > 모바일쿠폰등록
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
@Mapper
@Repository
public interface MobileCouponMapper {

    /** 본사 쿠폰 분류 조회 */
    List<DefaultMap<String>> getHqMobileCouponClassList(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 조회 */
    List<DefaultMap<String>> getStoreMobileCouponClassList(PayMethodClassVO payMethodClassVO);

    /** 쿠폰 분류코드 조회 */
    String getPayMethodClassCd(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 등록 */
    int insertHqMobileCouponClass(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 수정 */
    int updateHqMobileCouponClass(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 삭제 */
    int deleteHqMobileCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 등록 */
    int insertStoreMobileCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 수정 */
    int updateStoreMobileCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 삭제 */
    int deleteStoreMobileCouponClass(PayMethodClassVO payMethodClassVO);
}
