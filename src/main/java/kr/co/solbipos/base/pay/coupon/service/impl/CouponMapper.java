package kr.co.solbipos.base.pay.coupon.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponStoreVO;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : CouponMapper.java
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
@Mapper
@Repository
public interface CouponMapper {

    /** 본사 쿠폰 분류 조회 */
    List<DefaultMap<String>> getHqCouponClassList(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 조회 */
    List<DefaultMap<String>> getStoreCouponClassList(PayMethodClassVO payMethodClassVO);

    /** 쿠폰 분류코드 조회 */
    String getPayMethodClassCd(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 등록 */
    int insertHqCouponClass(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 수정 */
    int updateHqCouponClass(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 삭제 */
    int deleteHqCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 등록 */
    int insertStoreCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 수정 */
    int updateStoreCouponClass(PayMethodClassVO payMethodClassVO);

    /** 매장 쿠폰 분류 삭제 */
    int deleteStoreCouponClass(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 등록 프로시져 */
    String insertHqCouponClassToStore(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 수정 프로시져 */
    String updateHqCouponClassToStore(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 분류 삭제 프로시져 */
    String deleteHqCouponClassToStore(PayMethodClassVO payMethodClassVO);

    /** 본사 쿠폰 조회 */
    List<DefaultMap<String>> getHqCouponList(CouponVO couponVO);

    /** 매장 쿠폰 조회*/
    List<DefaultMap<String>> getStoreCouponList(CouponVO couponVO);

    /** 쿠폰 코드 조회 */
    String getCouponCd(CouponVO couponVO);

    /** 본사 쿠폰 등록 */
    int insertHqCoupon(CouponVO couponVO);

    /** 본사 쿠폰 수정 */
    int updateHqCoupon(CouponVO couponVO);

    /** 본사 쿠폰 삭제 */
    int deleteHqCoupon(CouponVO couponVO);

    /** 본사 쿠폰 등록 프로시져
     * @param couponVO*/
    String insertHqCouponToStore(CouponStoreVO couponVO);

    /** 본사 쿠폰 등록 프로시져 */
    String updateHqCouponToStore(CouponVO couponVO);

    /** 본사 쿠폰 등록 프로시져 */
    String deleteHqCouponToStore(CouponVO couponVO);

    /** 매장 쿠폰 등록 */
    int insertStoreCoupon(CouponVO couponVO);

    /** 매장 쿠폰 수정*/
    int updateStoreCoupon(CouponVO couponVO);

    /** 매장 쿠폰 삭제 */
    int deleteStoreCoupon(CouponVO couponVO);

    /** 쿠폰 적용/미적용 본사 상품 조회 */
    List<DefaultMap<String>> getHqProdList(CouponProdVO couponProdVO);

    /** 쿠폰 적용/미저기용 매장 상품 조회 */
    List<DefaultMap<String>> getStoreProdList(CouponProdVO couponProdVO);

    /** 본사 쿠폰 적용 상품 등록 */
    int insertHqCouponProd(CouponProdVO couponProdVO);

    /** 매장 쿠폰 적용 상품 등록 */
    int insertStoreCouponProd(CouponProdVO couponProdVO);

    /** 본사 쿠폰 적용 상품 삭제 */
    int deleteHqCouponProd(CouponProdVO couponProdVO);

    /** 매장 쿠폰 적용 상품 삭제 */
    int deleteStoreCouponProd(CouponProdVO couponProdVO);

    /** 본사 쿠폰 적용 상품 등록 프로시져 */
    String insertHqCouponProdToStore(CouponProdVO couponProdVO);

    /** 본사 쿠폰 적용 상품 삭제 프로시져 */
    String deleteHqCouponProdToStore(CouponProdVO couponProdVO);

    /** 쿠폰 적용/미적용 매장 조회 */
    List<DefaultMap<String>> getStoreList(CouponStoreVO couponStoreVO);

    /** 쿠폰 적용 매장 등록 */
    int insertCouponStore(CouponStoreVO couponStoreVO);

    /** 쿠폰 적용 매장 삭제 */
    int deleteCouponStore(CouponStoreVO couponStoreVO);

}
