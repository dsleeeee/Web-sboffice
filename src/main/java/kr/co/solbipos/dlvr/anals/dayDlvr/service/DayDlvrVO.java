package kr.co.solbipos.dlvr.anals.dayDlvr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DayDlvrVO extends PageVO {
  private String saleDate;
  private String hqOfficeCd;
  private String hqBrandCd;
  private String nonDlvrSaleDate;
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
  private int sumDlvrRealSaleAmt;
  private int cntDlvrBillNo;
  private int sumRealSaleAmt;
  private int cntBillNo;
  private String searchDate;

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

  public int getSumDlvrRealSaleAmt() {
    return sumDlvrRealSaleAmt;
  }

  public void setSumDlvrRealSaleAmt(int sumDlvrRealSaleAmt) {
    this.sumDlvrRealSaleAmt = sumDlvrRealSaleAmt;
  }

  public int getCntDlvrBillNo() {
    return cntDlvrBillNo;
  }

  public void setCntDlvrBillNo(int cntDlvrBillNo) {
    this.cntDlvrBillNo = cntDlvrBillNo;
  }

  public int getSumRealSaleAmt() {
    return sumRealSaleAmt;
  }

  public void setSumRealSaleAmt(int sumRealSaleAmt) {
    this.sumRealSaleAmt = sumRealSaleAmt;
  }

  public int getCntBillNo() {
    return cntBillNo;
  }

  public String getNonDlvrSaleDate() {
    return nonDlvrSaleDate;
  }

  public void setNonDlvrSaleDate(String nonDlvrSaleDate) {
    this.nonDlvrSaleDate = nonDlvrSaleDate;
  }

  public void setCntBillNo(int cntBillNo) {
    this.cntBillNo = cntBillNo;
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

  public String getSearchDate() {
    return searchDate;
  }

  public void setSearchDate(String searchDate) {
    this.searchDate = searchDate;
  }
}
