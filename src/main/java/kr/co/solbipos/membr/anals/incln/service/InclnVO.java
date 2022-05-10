package kr.co.solbipos.membr.anals.incln.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.hibernate.validator.constraints.NotBlank;

public class InclnVO extends PageVO {
  private String gendrFg;
  private String birthday;
  private String membrOrgnCd;
  private String storeCd;
  private String orgnFg;
  private String age;
  private String lv3Cd;
  private String lv3Nm;
  private String prod_Cd;
  private String prodNm;
  private String membrNo;
  private String membrNm;
  private String sumSale;
  private String sumSaleQty;
  private String sumSaleAmt;
  private String startDate;
  private String endDate;
  private String sumGendrF;
  private String sumGendrG;
  private String sumAgeteens;
  private String sumAgetwenties;
  private String sumAgethirties;
  private String sumAgeforties;
  private String sumAgefifties;
  private String option;
  private String hqOfficeCd;
  private String empNo;

  public String getHqOfficeCd() {
    return hqOfficeCd;
  }

  public void setHqOfficeCd(String hqOfficeCd) {
    this.hqOfficeCd = hqOfficeCd;
  }

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

  public String getMembrOrgnCd() {
    return membrOrgnCd;
  }

  public void setMembrOrgnCd(String membrOrgnCd) {
    this.membrOrgnCd = membrOrgnCd;
  }

  public String getStoreCd() {
    return storeCd;
  }

  public void setStoreCd(String storeCd) {
    this.storeCd = storeCd;
  }

  public String getOrgnFg() {
    return orgnFg;
  }

  public void setOrgnFg(String orgnFg) {
    this.orgnFg = orgnFg;
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

  public String getSumGendrF() {
    return sumGendrF;
  }

  public void setSumGendrF(String sumGendrF) {
    this.sumGendrF = sumGendrF;
  }

  public String getSumGendrG() {
    return sumGendrG;
  }

  public void setSumGendrG(String sumGendrG) {
    this.sumGendrG = sumGendrG;
  }

  public String getSumAgeteens() {
    return sumAgeteens;
  }

  public void setSumAgeteens(String sumAgeteens) {
    this.sumAgeteens = sumAgeteens;
  }

  public String getSumAgetwenties() {
    return sumAgetwenties;
  }

  public void setSumAgetwenties(String sumAgetwenties) {
    this.sumAgetwenties = sumAgetwenties;
  }

  public String getSumAgethirties() {
    return sumAgethirties;
  }

  public void setSumAgethirties(String sumAgethirties) {
    this.sumAgethirties = sumAgethirties;
  }

  public String getSumAgeforties() {
    return sumAgeforties;
  }

  public void setSumAgeforties(String sumAgeforties) {
    this.sumAgeforties = sumAgeforties;
  }

  public String getSumAgefifties() {
    return sumAgefifties;
  }

  public void setSumAgefifties(String sumAgefifties) {
    this.sumAgefifties = sumAgefifties;
  }

  public String getOption() {
    return option;
  }

  public void setOption(String option) {
    this.option = option;
  }

  public String getSumSale() {
    return sumSale;
  }

  public void setSumSale(String sumSale) {
    this.sumSale = sumSale;
  }

  public String getEmpNo() {
    return empNo;
  }

  public void setEmpNo(String empNo) {
    this.empNo = empNo;
  }
}
