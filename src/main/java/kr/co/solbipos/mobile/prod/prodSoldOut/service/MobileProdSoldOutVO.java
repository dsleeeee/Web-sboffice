package kr.co.solbipos.mobile.prod.prodSoldOut.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileProdSoldOutVO.java
 * @Description : 상품관리 > 품절관리(상품)
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileProdSoldOutVO extends PageVO {

    private static final long serialVersionUID = 2507060678407090632L;

    /** 브랜드명 */
    private String hqBrandNm;
    /** 매장코드 */
    private String storeCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 사용여부 */
    private String useYn;
    /** 바코드 */
    private String barCd;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    /** 전체기간 여부 */
    private boolean chkDt;
    /** 품절여부 */
    private String SoldOutYn;
    /** 사용자아이디 */
    private String userId;

    public String getHqBrandNm() {
        return hqBrandNm;
    }

    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
    }
    
    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }

    public String getSoldOutYn() {
        return SoldOutYn;
    }

    public void setSoldOutYn(String soldOutYn) {
        SoldOutYn = soldOutYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}