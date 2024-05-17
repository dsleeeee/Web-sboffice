package kr.co.solbipos.base.price.chgCostPrice.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ChgCostPriceVO.java
 * @Description : 기초관리 - 가격관리 - 원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.29  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ChgCostPriceVO extends PageVO {

    private static final long serialVersionUID = 9176921246941902222L;

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

    /** 상품 원가(변경전 원래 원가) */
    private String bCostUprc;

    /** 상품 원가 */
    private String costUprc;

    /** 창고코드 */
    private String storageCd;

    /** 진행상태 */
    private String procFg;

    /** 세션ID */
    private String sessionId;

    /** 결과검증내용 */
    private String result;

    /** 순번 */
    private int seq;

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

    public String getbCostUprc() {
        return bCostUprc;
    }

    public void setbCostUprc(String bCostUprc) {
        this.bCostUprc = bCostUprc;
    }

    public String getCostUprc() {
        return costUprc;
    }

    public void setCostUprc(String costUprc) {
        this.costUprc = costUprc;
    }

    public String getStorageCd() {
        return storageCd;
    }

    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
