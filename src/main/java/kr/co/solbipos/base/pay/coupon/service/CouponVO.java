package kr.co.solbipos.base.pay.coupon.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnDcFg;
/**
 * @Class Name : CouponVO.java
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
public class CouponVO extends CmmVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 쿠폰코드 */
    private String coupnCd;
    /** 쿠폰명 */
    private String coupnNm;
    /** 권종분류코드 */
    private String payClassCd;
    /** 쿠폰할인구분 */
    private CoupnDcFg coupnDcFg;
    /** 쿠폰할인율 */
    private double coupnDcRate;
    /** 쿠폰할인금액 */
    private double coupnDcAmt;
    /** 쿠폰적용구분 */
    private String coupnApplyFg;
    /** 사용여부 */
    private boolean useYn;

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */

    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the coupnCd
     */

    public String getCoupnCd() {
        return coupnCd;
    }

    /**
     * @param coupnCd the coupnCd to set
     */
    public void setCoupnCd(String coupnCd) {
        this.coupnCd = coupnCd;
    }

    /**
     * @return the coupnNm
     */

    public String getCoupnNm() {
        return coupnNm;
    }

    /**
     * @param coupnNm the coupnNm to set
     */
    public void setCoupnNm(String coupnNm) {
        this.coupnNm = coupnNm;
    }

    /**
     * @return the payClassCd
     */

    public String getPayClassCd() {
        return payClassCd;
    }

    /**
     * @param payClassCd the payClassCd to set
     */
    public void setPayClassCd(String payClassCd) {
        this.payClassCd = payClassCd;
    }



}
