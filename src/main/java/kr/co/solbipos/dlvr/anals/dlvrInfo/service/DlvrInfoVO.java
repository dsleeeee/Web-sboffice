package kr.co.solbipos.dlvr.anals.dlvrInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DlvrInfoVO.java
 * @Description : 배달관리 > 배달분석 > 배달내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.09  Joshua      최초생성
 *
 * @author
 * @since 2020.07.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DlvrInfoVO extends PageVO {
  private String saleDate;
  private String posNo;
  private String billNo;
  private int realSaleAmt;
  private String dlvrAddr;
  private String dlvrTelNo;
  private String membrNm;
  private String billDt;
  private String empNm;
  private String startDate;
  private String endDate;

  private String hqOfficeCd;
  private String hqBrandCd;
  private String storeCd;
  private String dlvrPayEmpNm;

  /**
   * 소속구분
   * M : 시스템
   * A : 대리점
   * H : 본사
   * S : 매장, 가맹점
   */
  private String orgnFg;
  /** 결제수단컬럼 */
  private String payCol;
  /** 결제수단 array */
  private String arrPayCol[];
  /** 할인컬럼 */
  private String dcCol;
  /** 할인구분 array */
  private String arrDcCol[];
  /** 쿼리문의 PIVOT IN에 사용할 할인구분 컬럼 문자열 */
  private String pivotDcCol;
  /** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
  private String pivotPayCol;


  public String getOrgnFg() { return orgnFg; }

  public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

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

  public int getRealSaleAmt() {
    return realSaleAmt;
  }

  public void setRealSaleAmt(int realSaleAmt) {
    this.realSaleAmt = realSaleAmt;
  }

  public String getDlvrAddr() {
    return dlvrAddr;
  }

  public void setDlvrAddr(String dlvrAddr) {
    this.dlvrAddr = dlvrAddr;
  }

  public String getDlvrTelNo() {
    return dlvrTelNo;
  }

  public void setDlvrTelNo(String dlvrTelNo) {
    this.dlvrTelNo = dlvrTelNo;
  }

  public String getMembrNm() {
    return membrNm;
  }

  public void setMembrNm(String membrNm) {
    this.membrNm = membrNm;
  }

  public String getBillDt() {
    return billDt;
  }

  public void setBillDt(String billDt) {
    this.billDt = billDt;
  }

  public String getEmpNm() {
    return empNm;
  }

  public void setEmpNm(String empNm) {
    this.empNm = empNm;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getHqOfficeCd() {
    return hqOfficeCd;
  }

  public void setHqOfficeCd(String hqOfficeCd) {
    this.hqOfficeCd = hqOfficeCd;
  }

  public String getHqBrandCd() {
    return hqBrandCd;
  }

  public void setHqBrandCd(String hqBrandCd) {
    this.hqBrandCd = hqBrandCd;
  }

  public String getStoreCd() {
    return storeCd;
  }

  public void setStoreCd(String storeCd) {
    this.storeCd = storeCd;
  }

  public String getDlvrPayEmpNm() {
    return dlvrPayEmpNm;
  }

  public void setDlvrPayEmpNm(String dlvrPayEmpNm) {
    this.dlvrPayEmpNm = dlvrPayEmpNm;
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

  public String getPivotDcCol() {
    return pivotDcCol;
  }

  public void setPivotDcCol(String pivotDcCol) {
    this.pivotDcCol = pivotDcCol;
  }

  public String getPivotPayCol() {
    return pivotPayCol;
  }

  public void setPivotPayCol(String pivotPayCol) {
    this.pivotPayCol = pivotPayCol;
  }
}
