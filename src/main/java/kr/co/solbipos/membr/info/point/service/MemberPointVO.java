package kr.co.solbipos.membr.info.point.service;

import kr.co.solbipos.application.common.service.PageVO;

public class MemberPointVO extends PageVO {

  /**
   * 소속구분
   * M : 시스템
   * A : 대리점
   * H : 본사
   * S : 매장, 가맹점
   */
  private String orgnFg;

  private String orgnCd;

  /** 본사코드 */
  private String hqOfficeCd;

  /** 매장코드 */
  private String storeCd;

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
  private String memberResult;
  private String membrClassCd;
  private String membrCardNo;
  private String membrNm;
  private String remark;
  private String tmpTotAdjPoint;

  public String getOrgnFg() { return orgnFg; }

  public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

  public String getOrgnCd() { return orgnCd; }

  public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

  public String getHqOfficeCd() { return hqOfficeCd; }

  public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

  public String getStoreCd() { return storeCd; }

  public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

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

  public String getMemberResult() {
    return memberResult;
  }

  public void setMemberResult(String memberResult) {
    this.memberResult = memberResult;
  }

  public String getMembrClassCd() {
    return membrClassCd;
  }

  public void setMembrClassCd(String membrClassCd) {
    this.membrClassCd = membrClassCd;
  }

  public String getMembrCardNo() {
    return membrCardNo;
  }

  public void setMembrCardNo(String membrCardNo) {
    this.membrCardNo = membrCardNo;
  }

  public String getMembrNm() {
    return membrNm;
  }

  public void setMembrNm(String membrNm) {
    this.membrNm = membrNm;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getTmpTotAdjPoint() {
    return tmpTotAdjPoint;
  }

  public void setTmpTotAdjPoint(String tmpTotAdjPoint) {
    this.tmpTotAdjPoint = tmpTotAdjPoint;
  }
}