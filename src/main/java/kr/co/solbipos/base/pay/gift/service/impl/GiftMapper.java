package kr.co.solbipos.base.pay.gift.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.pay.coupon.service.CouponProdVO;
import kr.co.solbipos.base.pay.coupon.service.CouponStoreVO;
import kr.co.solbipos.base.pay.coupon.service.CouponVO;
import kr.co.solbipos.base.pay.coupon.service.PayMethodClassVO;
import kr.co.solbipos.base.pay.gift.service.GiftVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : GiftMapper.java
 * @Description : 기초관리 > 결제수단 > 상품권등록
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
public interface GiftMapper {

    /** 본사 상품권 분류 조회 */
    List<DefaultMap<String>> getHqGiftClassList(PayMethodClassVO payMethodClassVO);

    /** 매장 상품권 분류 조회 */
    List<DefaultMap<String>> getStoreGiftClassList(PayMethodClassVO payMethodClassVO);

    /** 상품권 분류코드 조회 */
    String getPayMethodClassCd(PayMethodClassVO payMethodClassVO);

    /** 본사 상품권 분류 등록 */
    int insertHqGiftClass(PayMethodClassVO payMethodClassVO);

    /** 본사 상품권 분류 수정 */
    int updateHqGiftClass(PayMethodClassVO payMethodClassVO);

    /** 본사 상품권 분류 삭제 */
    int deleteHqGiftClass(PayMethodClassVO payMethodClassVO);

    /** 매장 상품권 분류 등록 */
    int insertStoreGiftClass(PayMethodClassVO payMethodClassVO);

    /** 매장 상품권 분류 수정 */
    int updateStoreGiftClass(PayMethodClassVO payMethodClassVO);

    /** 매장 상품권 분류 삭제 */
    int deleteStoreGiftClass(PayMethodClassVO payMethodClassVO);

    /** 본사 상품권 조회 */
    List<DefaultMap<String>> getHqGiftList(GiftVO GiftVO);

    /** 매장 상품권 조회*/
    List<DefaultMap<String>> getStoreGiftList(GiftVO GiftVO);

    /** 상품권 코드 조회 */
    String getGiftCd(GiftVO GiftVO);

    /** 본사 상품권 등록 */
    int insertHqGift(GiftVO GiftVO);

    /** 본사 상품권 수정 */
    int updateHqGift(GiftVO GiftVO);

    /** 본사 상품권 삭제 */
    int deleteHqGift(GiftVO GiftVO);

    /** 매장 상품권 등록 */
    int insertStoreGift(GiftVO GiftVO);

    /** 매장 상품권 수정*/
    int updateStoreGift(GiftVO GiftVO);

    /** 매장 상품권 삭제 */
    int deleteStoreGift(GiftVO GiftVO);

}
