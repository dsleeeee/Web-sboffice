package kr.co.solbipos.base.promotion.artiseePromotion.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ArtiseePromotionVO.java
 * @Description : 기초관리 - 프로모션관리 - 아티제전용프로모션
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.06.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ArtiseePromotionVO extends PageVO {

    private static final long serialVersionUID = -4894661482349856507L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 프로모션코드 */
    private String promotionCd;
    /** 프로모션명 */
    private String promotionNm;
    /** 메모 */
    private String memo;
    /** 프로모션적용업체구분 */
    private String promoCompFg;
    /** 사용여부 */
    private String useYn;
    /** 프로모션타입구분 */
    private String promoTypeFg;
    /** 할인율, 할인금액 */
    private String dcSet;
    /** 적용기간 시작일 */
    private String startYmd;
    /** 적용기간 종료일 */
    private String endYmd;
    /** 적용대상 */
    private String prodTypeFg;
    /** 일정 */
    private String promotionDate;
    /** 조건수량 */
    private String giftQty;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류 */
    private String prodClassCd;
    /** 상품분류명 */
    private String prodClassNm;
    /** 매장상태 */
    private String sysStatFg;
    /** 적용대상 변경여부 */
    private String prodTypeFgChgYn;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getPromotionNm() {
        return promotionNm;
    }

    public void setPromotionNm(String promotionNm) {
        this.promotionNm = promotionNm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPromoCompFg() {
        return promoCompFg;
    }

    public void setPromoCompFg(String promoCompFg) {
        this.promoCompFg = promoCompFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getPromoTypeFg() {
        return promoTypeFg;
    }

    public void setPromoTypeFg(String promoTypeFg) {
        this.promoTypeFg = promoTypeFg;
    }

    public String getDcSet() {
        return dcSet;
    }

    public void setDcSet(String dcSet) {
        this.dcSet = dcSet;
    }

    public String getStartYmd() {
        return startYmd;
    }

    public void setStartYmd(String startYmd) {
        this.startYmd = startYmd;
    }

    public String getEndYmd() {
        return endYmd;
    }

    public void setEndYmd(String endYmd) {
        this.endYmd = endYmd;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(String promotionDate) {
        this.promotionDate = promotionDate;
    }

    public String getGiftQty() {
        return giftQty;
    }

    public void setGiftQty(String giftQty) {
        this.giftQty = giftQty;
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

    public String getProdClassNm() {
        return prodClassNm;
    }

    public void setProdClassNm(String prodClassNm) {
        this.prodClassNm = prodClassNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getProdTypeFgChgYn() {
        return prodTypeFgChgYn;
    }

    public void setProdTypeFgChgYn(String prodTypeFgChgYn) {
        this.prodTypeFgChgYn = prodTypeFgChgYn;
    }
}
