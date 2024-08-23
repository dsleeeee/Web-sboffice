package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

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
    List<DefaultMap<String>> getCouponClassList(PayMethodClassVO payMethodClassVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰분류 저장 */
    int saveCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰분류 매장 적용 */
    int applyCouponClassList(PayMethodClassVO[] payMethodClassVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 조회 */
    List<DefaultMap<String>> getCouponList(CouponVO couponVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 저장 */
    int saveCouponList(CouponVO[] couponVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 등록/미등록 상품 조회*/
    List<DefaultMap<String>> getProdList(CouponProdVO couponProdVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 적용 상품 등록 */
    int registCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 적용 상품 삭제 */
    int deleteCouponProd(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 등록/미등록 매장 조회*/
    List<DefaultMap<String>> getStoreList(CouponStoreVO couponStoreVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 적용 매장 등록 */
    int registCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 적용 매장 삭제 */
    int deleteCouponStore(CouponStoreVO[] couponStoreVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 순서저장 */
    int getCouponSeqChgSave(CouponVO[] couponVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰순서 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getCouponSeqChgStoreRegistList(CouponVO couponVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰순서 매장적용 팝업 - 저장 */
    int getCouponSeqChgStoreRegistSave(CouponVO[] couponVOs, SessionInfoVO sessionInfoVO);

    /** 제외상품 등록/미등록 상품 조회 */
    List<DefaultMap<String>> getExceptProdList(CouponProdVO couponProdVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 제외상품 등록 */
    int registCouponProdExcept(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰 제외상품 삭제 */
    int deleteCouponProdExcept(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 적용대상소분류 등록/미등록 조회 */
    List<DefaultMap<String>> getProdClsList(CouponProdVO couponProdVO, SessionInfoVO sessionInfoVO);

    /** 적용대상소분류 등록 */
    int registCouponProdCls(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 적용대상소분류 삭제 */
    int deleteCouponProdCls(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀 업로드 */
    int getExcelUploadSave(CouponProdVO[] couponProdVOs, SessionInfoVO sessionInfoVO);

    /** 상품 양식다운로드 */
    List<DefaultMap<String>> getExcelProdList(CouponProdVO couponProdVO, SessionInfoVO sessionInfoVO);
}
