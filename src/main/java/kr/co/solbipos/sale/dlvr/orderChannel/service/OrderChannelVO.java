package kr.co.solbipos.sale.dlvr.orderChannel.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : OrderChannelVO.java
 * @Description : 매출관리 > 배달현황 > 주문채널별현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.01  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.09.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class OrderChannelVO extends PageVO {

    private static final long serialVersionUID = 7337427527224251926L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 매장코드 (조회용) */
    private String srchStoreCd;

    /** 다중매장코드 array */
    private String arrStoreCd[];

    /** 결제수단컬럼 */
    private String payCol;

    /** 결제수단 array */
    private String arrPayCol[];

    /** 할인컬럼 */
    private String dcCol;

    /** 할인구분 array */
    private String arrDcCol[];

    /** 객수컬럼 */
    private String guestCol;

    /** 객수 array */
    private String arrGuestCol[];

    /** 주문채널 구분자 컬럼 */
    private String dlvrInFgCol;

    /** 주문채널 구분자 array */
    private String arrDlvrInFgCol[];

    private String saleDate;

    private String dlvrInFg;

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

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getPayCol() {
        return payCol;
    }

    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }

    public String[] getArrPayCol() {
        return arrPayCol;
    }

    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
    }

    public String getDcCol() {
        return dcCol;
    }

    public void setDcCol(String dcCol) {
        this.dcCol = dcCol;
    }

    public String[] getArrDcCol() {
        return arrDcCol;
    }

    public void setArrDcCol(String[] arrDcCol) {
        this.arrDcCol = arrDcCol;
    }

    public String getGuestCol() {
        return guestCol;
    }

    public void setGuestCol(String guestCol) {
        this.guestCol = guestCol;
    }

    public String[] getArrGuestCol() {
        return arrGuestCol;
    }

    public void setArrGuestCol(String[] arrGuestCol) {
        this.arrGuestCol = arrGuestCol;
    }

    public String getDlvrInFgCol() {
        return dlvrInFgCol;
    }

    public void setDlvrInFgCol(String dlvrInFgCol) {
        this.dlvrInFgCol = dlvrInFgCol;
    }

    public String[] getArrDlvrInFgCol() {
        return arrDlvrInFgCol;
    }

    public void setArrDlvrInFgCol(String[] arrDlvrInFgCol) {
        this.arrDlvrInFgCol = arrDlvrInFgCol;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getDlvrInFg() {
        return dlvrInFg;
    }

    public void setDlvrInFg(String dlvrInFg) {
        this.dlvrInFg = dlvrInFg;
    }
}
