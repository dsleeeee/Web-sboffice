package kr.co.solbipos.base.price.chgCostPriceHistory.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ChgCostPriceHistoryVO.java
 * @Description : 기초관리 - 가격관리 - 원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ChgCostPriceHistoryVO extends PageVO {

    private static final long serialVersionUID = -9123990893674186977L;

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

    /** 사용자 아이디 */
    private String userId;

    /** 원가 변경항목 */
    private String costUprcType;

    /** 수불년월 */
    private String iostockYm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 창고코드 */
    private String storageCd;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCostUprcType() {
        return costUprcType;
    }

    public void setCostUprcType(String costUprcType) {
        this.costUprcType = costUprcType;
    }

    public String getIostockYm() {
        return iostockYm;
    }

    public void setIostockYm(String iostockYm) {
        this.iostockYm = iostockYm;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
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

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }
}
