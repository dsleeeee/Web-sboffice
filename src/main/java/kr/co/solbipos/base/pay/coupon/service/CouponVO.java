package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;
import org.springframework.data.domain.Page;

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
public class CouponVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 쿠폰등록구분 */
    private String coupnRegFg;
    /** 권종분류코드 */
    private String payClassCd;
    /** 쿠폰코드 */
    private String coupnCd;
    /** 쿠폰명 */
    private String coupnNm;
    /** 쿠폰할인구분 */
    private String coupnDcFg;
    /** 쿠폰할인율 */
    private double coupnDcRate;
    /** 쿠폰할인금액 */
    private double coupnDcAmt;
    /** 쿠폰적용구분 */
    private String coupnApplyFg;
    /** 쿠폰대상구분 */
    private String coupnTargetFg;
    /** 사용여부 */
    private UseYn useYn;
    /** 쿠폰등록 본사 통제여부 */
    private CoupnEnvFg coupnEnvstVal;
    /** 쿠폰 관련 프로시져 실행 결과 */
    private String result;

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
     * @return the coupnRegFg
     */

    public String getCoupnRegFg() {
        return coupnRegFg;
    }

    /**
     * @param coupnRegFg the coupnRegFg to set
     */
    public void setCoupnRegFg(String coupnRegFg) {
        this.coupnRegFg = coupnRegFg;
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

    /**
     * @return the coupnDcFg
     */

    public String getCoupnDcFg() {
        return coupnDcFg;
    }

    /**
     * @param coupnDcFg the coupnDcFg to set
     */
    public void setCoupnDcFg(String coupnDcFg) {
        this.coupnDcFg = coupnDcFg;
    }

    /**
     * @return the coupnDcRate
     */

    public double getCoupnDcRate() {
        return coupnDcRate;
    }

    /**
     * @param coupnDcRate the coupnDcRate to set
     */
    public void setCoupnDcRate(double coupnDcRate) {
        this.coupnDcRate = coupnDcRate;
    }

    /**
     * @return the coupnDcAmt
     */

    public double getCoupnDcAmt() {
        return coupnDcAmt;
    }

    /**
     * @param coupnDcAmt the coupnDcAmt to set
     */
    public void setCoupnDcAmt(double coupnDcAmt) {
        this.coupnDcAmt = coupnDcAmt;
    }

    /**
     * @return the coupnApplyFg
     */

    public String getCoupnApplyFg() {
        return coupnApplyFg;
    }

    /**
     * @param coupnApplyFg the coupnApplyFg to set
     */
    public void setCoupnApplyFg(String coupnApplyFg) {
        this.coupnApplyFg = coupnApplyFg;
    }

    public String getCoupnTargetFg() {
        return coupnTargetFg;
    }

    public void setCoupnTargetFg(String coupnTargetFg) {
        this.coupnTargetFg = coupnTargetFg;
    }

    /**
     * @return the useYn
     */

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the coupnEnvstVal
     */

    public CoupnEnvFg getCoupnEnvstVal() { return coupnEnvstVal; }

    /**
     * @param coupnEnvstVal the coupnEnvstVal to set
     */
    public void setCoupnEnvstVal(CoupnEnvFg coupnEnvstVal) { this.coupnEnvstVal = coupnEnvstVal; }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
}
