package kr.co.solbipos.base.pay.coupon.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.pay.coupon.service.enums.PayTypeFg;

/**
 * @Class Name : PayMethodClassVO.java
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
public class PayMethodClassVO extends CmmVO {

    /** 본사브랜드코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 권종분류코드 */
    private String payClassCd;
    /** 권종유형구분 */
    private PayTypeFg payTypeFg;
    /** 권종분류명 */
    private String payClassNm;
    /** 일련번호여부 */
    private UseYn serNoYn;
    /** 사용여부 */
    private UseYn useYn;

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
     * @return the payTypeFg
     */

    public PayTypeFg getPayTypeFg() {
        return payTypeFg;
    }

    /**
     * @param payTypeFg the payTypeFg to set
     */
    public void setPayTypeFg(PayTypeFg payTypeFg) {
        this.payTypeFg = payTypeFg;
    }

    /**
     * @return the payClassNm
     */

    public String getPayClassNm() {
        return payClassNm;
    }

    /**
     * @param payClassNm the payClassNm to set
     */
    public void setPayClassNm(String payClassNm) {
        this.payClassNm = payClassNm;
    }

    /**
     * @return the serNoYn
     */

    public UseYn getSerNoYn() {
        return serNoYn;
    }

    /**
     * @param serNoYn the serNoYn to set
     */
    public void setSerNoYn(UseYn serNoYn) {
        this.serNoYn = serNoYn;
    }

    /**채
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
}
