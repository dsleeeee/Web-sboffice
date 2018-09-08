package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
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
public class CouponProdVO extends CmmVO {

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
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
}
