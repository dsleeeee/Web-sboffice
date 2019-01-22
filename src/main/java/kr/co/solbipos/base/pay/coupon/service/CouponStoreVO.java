package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.pay.coupon.service.enums.CoupnEnvFg;

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
public class CouponStoreVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 쿠폰분류코드 */
    private String payClassCd;
    /** 쿠폰코드 */
    private String coupnCd;
    /** 로그인한 본사의 코드 */
    private String orgnCd;
    /** 사용여부 */
    private UseYn useYn;
    /** 매장적용여부 */
    private UseYn storeRegFg;
    /** 기능키 관련 프로시져 실행 결과 */
    private String result;

    /** /////////////////////////////조회조건/////////////////// */
     /** 매장코드 */
    private String srchStoreCd;
    /** 매장명 */
    private String srchStoreNm;
    /** /////////////////////////////조회조건/////////////////// */

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
     * @return the orgnCd
     */

    public String getOrgnCd() {
        return orgnCd;
    }

    /**
     * @param orgnCd the orgnCd to set
     */
    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    /**
     * @return the srchStoreCd
     */

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    /**
     * @param srchStoreCd the srchStoreCd to set
     */
    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    /**
     * @return the srchStoreNm
     */

    public String getSrchStoreNm() {
        return srchStoreNm;
    }

    /**
     * @param srchStoreNm the srchStoreNm to set
     */
    public void setSrchStoreNm(String srchStoreNm) {
        this.srchStoreNm = srchStoreNm;
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
     * @return the storeRegFg
     */

    public UseYn getStoreRegFg() {
        return storeRegFg;
    }

    /**
     * @param storeRegFg the storeRegFg to set
     */
    public void setStoreRegFg(UseYn storeRegFg) {
        this.storeRegFg = storeRegFg;
    }

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
