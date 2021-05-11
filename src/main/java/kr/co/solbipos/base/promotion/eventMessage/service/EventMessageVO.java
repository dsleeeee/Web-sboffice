package kr.co.solbipos.base.promotion.eventMessage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : EventMessageVO.java
 * @Description : 기초관리 - 프로모션관리 - 이벤트문구출력관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .05. 03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class EventMessageVO extends PageVO {

    private static final long serialVersionUID = 6338378010379513907L;

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
    /** 매장명 */
    private String storeNm;
    /** 매장코드 */
    private String storeCd;
    /** 이벤트출력물코드 */
    private String msgCd;
    /** 출력적용 시작일 */
    private String startYmd;
    /** 출력적용 종료일 */
    private String endYmd;
    /** 응모권출력여부 */
    private String ticketPrintYn;
    /** 재출력여부 */
    private String rePrintYn;
    /** 출력조건 */
    private String printCondiFg;
    /** 혜택출력문구 */
    private String printMessage1;
    /** 관련 프로모션 코드 */
    private String promotionCd;
    /** 사용여부 */
    private String useYn;
    /** 상품명 */
    private String prodNm;
    /** 상품코드 */
    private String prodCd;
    /** 대상수량 */
    private String saleQty;
    /** 이벤트 문구출력 조회 날짜  */
    private String eventMessageDate;
    /** 매장상태구분 */
    private String sysStatFg;
    /** 이벤트문구 등록구분 - H:본사 S;매장 */
    private String regFg;
    /** 매장등록구분 */
    private String storeSelectExceptFg;


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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getMsgCd() {
        return msgCd;
    }

    public void setMsgCd(String msgCd) {
        this.msgCd = msgCd;
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

    public String getTicketPrintYn() {
        return ticketPrintYn;
    }

    public void setTicketPrintYn(String ticketPrintYn) {
        this.ticketPrintYn = ticketPrintYn;
    }

    public String getRePrintYn() {
        return rePrintYn;
    }

    public void setRePrintYn(String rePrintYn) {
        this.rePrintYn = rePrintYn;
    }

    public String getPrintCondiFg() {
        return printCondiFg;
    }

    public void setPrintCondiFg(String printCondiFg) {
        this.printCondiFg = printCondiFg;
    }

    public String getPrintMessage1() {
        return printMessage1;
    }

    public void setPrintMessage1(String printMessage1) {
        this.printMessage1 = printMessage1;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(String saleQty) {
        this.saleQty = saleQty;
    }

    public String getEventMessageDate() {
        return eventMessageDate;
    }

    public void setEventMessageDate(String eventMessageDate) {
        this.eventMessageDate = eventMessageDate;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getStoreSelectExceptFg() {
        return storeSelectExceptFg;
    }

    public void setStoreSelectExceptFg(String storeSelectExceptFg) {
        this.storeSelectExceptFg = storeSelectExceptFg;
    }
}
