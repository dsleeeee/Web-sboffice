package kr.co.solbipos.membr.anals.dayMembr.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DayMembrVO.java
 * @Description : 회원관리 > 회원분석 > 일자별회원 구매내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DayMembrVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 영업일자 */
    private String saleDate;

    /** 포스번호 */
    private String posNo;

    /** 영수증번호 */
    private String billNo;

    /** 회원번호 */
    private String membrNo;

    /** 판매구분 */
    private String saleFg;

    /** 회원카드번호 */
    private String membrCardNo;

    /** 결제수단컬럼 */
    private String payCol;
    /** 결제수단 array */
    private String arrPayCol[];
    /** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
    private String pivotPayCol;

    /**  구분 */
    private String gubun;

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    public String getSaleFg() {
        return saleFg;
    }

    public void setSaleFg(String saleFg) {
        this.saleFg = saleFg;
    }

    public String getMembrCardNo() {
        return membrCardNo;
    }

    public void setMembrCardNo(String membrCardNo) {
        this.membrCardNo = membrCardNo;
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

    public String getPivotPayCol() { return pivotPayCol; }

    public void setPivotPayCol(String pivotPayCol) { this.pivotPayCol = pivotPayCol; }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }
}