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
public class CouponProdVO extends PageVO {

    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 쿠폰분류코드 */
    private String payClassCd;
    /** 쿠폰코드 */
    private String coupnCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 사용여부 */
    private UseYn useYn;
    /** 쿠폰등록여부 */
    private UseYn prodRegFg;
    /** 쿠폰등록 본사 통제여부 */
    private CoupnEnvFg coupnEnvstVal;
    /** 기능키 관련 프로시져 실행 결과 */
    private String result;

    /** 사용자 아이디 */
    private String userId;
    /** 소분류 코드 */
    private String sClassCd;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드 */
    private String barCd;

    /** 사용여부(검색조건용) */
    private String strUseYn;

    /** 상품유형구분 */
    private String prodTypeFg;


    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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
     * @return the prodCd
     */

    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }


    /**
     * @return the prodNm
     */

    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
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
     * @return the prodRegFg
     */

    public UseYn getProdRegFg() {
        return prodRegFg;
    }

    /**
     * @param prodRegFg the prodRegFg to set
     */
    public void setProdRegFg(UseYn prodRegFg) {
        this.prodRegFg = prodRegFg;
    }

    /**
     * @return the coupnEnvstVal
     */

    public CoupnEnvFg getCoupnEnvstVal() {
        return coupnEnvstVal;
    }

    /**
     * @param coupnEnvstVal the coupnEnvstVal to set
     */
    public void setCoupnEnvstVal(CoupnEnvFg coupnEnvstVal) {
        this.coupnEnvstVal = coupnEnvstVal;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getsClassCd() {
        return sClassCd;
    }

    public void setsClassCd(String sClassCd) {
        this.sClassCd = sClassCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getStrUseYn() {
        return strUseYn;
    }

    public void setStrUseYn(String strUseYn) {
        this.strUseYn = strUseYn;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }
}
