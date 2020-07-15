package kr.co.solbipos.membr.anals.incln.service;

import kr.co.solbipos.application.common.service.PageVO;

public class InclnVO extends PageVO {
  private String gendrFg;
  private String birthday;
  private String age;
  private String lv3Cd;
  private String lv3Nm;
  private String prod_Cd;
  private String prodNm;
  private String membrNo;
  private String membrNm;
  private String sumSaleQty;
  private String sumSaleAmt;
  private String startDate;
  private String endDate;

  public String getGendrFg() {
    return gendrFg;
  }

  public void setGendrFg(String gendrFg) {
    this.gendrFg = gendrFg;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getLv3Cd() {
    return lv3Cd;
  }

  public void setLv3Cd(String lv3Cd) {
    this.lv3Cd = lv3Cd;
  }

  public String getLv3Nm() {
    return lv3Nm;
  }

  public void setLv3Nm(String lv3Nm) {
    this.lv3Nm = lv3Nm;
  }

  public String getProd_Cd() {
    return prod_Cd;
  }

  public void setProd_Cd(String prod_Cd) {
    this.prod_Cd = prod_Cd;
  }

  public String getProdNm() {
    return prodNm;
  }

  public void setProdNm(String prodNm) {
    this.prodNm = prodNm;
  }

  public String getMembrNo() {
    return membrNo;
  }

  public void setMembrNo(String membrNo) {
    this.membrNo = membrNo;
  }

  public String getMembrNm() {
    return membrNm;
  }

  public void setMembrNm(String membrNm) {
    this.membrNm = membrNm;
  }

  public String getSumSaleQty() {
    return sumSaleQty;
  }

  public void setSumSaleQty(String sumSaleQty) {
    this.sumSaleQty = sumSaleQty;
  }

  public String getSumSaleAmt() {
    return sumSaleAmt;
  }

  public void setSumSaleAmt(String sumSaleAmt) {
    this.sumSaleAmt = sumSaleAmt;
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
}
