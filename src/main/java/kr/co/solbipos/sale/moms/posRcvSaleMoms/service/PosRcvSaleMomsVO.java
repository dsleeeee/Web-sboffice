package kr.co.solbipos.sale.moms.posRcvSaleMoms.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PosRcvSaleMomsVO.java
 * @Description : 맘스터치 > 정산 > POS내역수신(매출)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.18  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class PosRcvSaleMomsVO extends PageVO {

    private static final long serialVersionUID = 939253596223608063L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 조회일자 */
    private String saleDate;
    /** 동적 컬럼 생성을 위한 쿼리 변수(내점/배달/포장 채널별 컬럼) */
    private String sQuery1;
    /** 동적 컬럼 생성을 위한 쿼리 변수(내점/배달/포장 채널별 CASE WHEN) */
    private String sQuery2;
    /** 주문채널 구분코드 목록(콤마구분) */
    private String dlvrInFgCol;
    /** 주문채널 구분코드 배열 */
    private String[] arrDlvrInFgCol;

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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getsQuery1() {
        return sQuery1;
    }

    public void setsQuery1(String sQuery1) {
        this.sQuery1 = sQuery1;
    }

    public String getsQuery2() {
        return sQuery2;
    }

    public void setsQuery2(String sQuery2) {
        this.sQuery2 = sQuery2;
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
}
