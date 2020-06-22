package kr.co.solbipos.membr.info.point.service;

import kr.co.solbipos.application.common.service.PageVO;

public class MemberPointVO extends PageVO {
  private String membrOrgnCd;
  private String membrNo;
  //  private Integer totSaleCnt;
//  private Integer totSaleAmt;
//  private Integer totDcAmt;
//  private Integer totSavePoint;
//  private Integer totUsePoint;
  private Integer avablPoint;
  private Integer totAdjPoint;
  //  private String firstSaleDate;
//  private String lastSaleDate;
  private String chgDate;
  private Integer chgSeq;
  private String pointChgFg;
  private Integer chgPoint;
  private String adjustPartRemark;
  private String orgnApprNo;

  public String getMembrOrgnCd() {
    return membrOrgnCd;
  }

  public void setMembrOrgnCd(String membrOrgnCd) {
    this.membrOrgnCd = membrOrgnCd;
  }

  public String getMembrNo() {
    return membrNo;
  }

  public void setMembrNo(String membrNo) {
    this.membrNo = membrNo;
  }

  public Integer getAvablPoint() {
    return avablPoint;
  }

  public void setAvablPoint(Integer avablPoint) {
    this.avablPoint = avablPoint;
  }

  public Integer getTotAdjPoint() {
    return totAdjPoint;
  }

  public void setTotAdjPoint(Integer totAdjPoint) {
    this.totAdjPoint = totAdjPoint;
  }

  public String getChgDate() {
    return chgDate;
  }

  public void setChgDate(String chgDate) {
    this.chgDate = chgDate;
  }

  public Integer getChgSeq() {
    return chgSeq;
  }

  public void setChgSeq(Integer chgSeq) {
    this.chgSeq = chgSeq;
  }

  public String getPointChgFg() {
    return pointChgFg;
  }

  public void setPointChgFg(String pointChgFg) {
    this.pointChgFg = pointChgFg;
  }

  public Integer getChgPoint() {
    return chgPoint;
  }

  public void setChgPoint(Integer chgPoint) {
    this.chgPoint = chgPoint;
  }

  public String getAdjustPartRemark() {
    return adjustPartRemark;
  }

  public void setAdjustPartRemark(String adjustPartRemark) {
    this.adjustPartRemark = adjustPartRemark;
  }

  public String getOrgnApprNo() {
    return orgnApprNo;
  }

  public void setOrgnApprNo(String orgnApprNo) {
    this.orgnApprNo = orgnApprNo;
  }
}