package kr.co.solbipos.membr.info.dlvr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DlvrVO extends PageVO {
  private String membrOrgnCd;
  private String membrNo;
  private String membrNm;
  private String membrClassCd;
  private int dlvrAddrSeq;
  private String dlvrStoreCd;
  private String dlvrLzoneCd;
  private String dlvrMzoneCd;
  private String addr;
  private String addrDtl;
  private String lastDlvrDate;
  private int totDlvrCnt;
  private String dlvrUseYn;
  private int dlvrTelSeq;
  private String telNo;
  private String shortNo;
  private String dlvrTeluseYn;

  public String getMembrOrgnCd() {
    return membrOrgnCd;
  }

  public String getMembrClassCd() {
    return membrClassCd;
  }

  public void setMembrClassCd(String membrClassCd) {
    this.membrClassCd = membrClassCd;
  }

  public String getMembrNm() {
    return membrNm;
  }

  public void setMembrNm(String membrNm) {
    this.membrNm = membrNm;
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

  public int getDlvrAddrSeq() {
    return dlvrAddrSeq;
  }

  public void setDlvrAddrSeq(int dlvrAddrSeq) {
    this.dlvrAddrSeq = dlvrAddrSeq;
  }

  public String getDlvrStoreCd() {
    return dlvrStoreCd;
  }

  public void setDlvrStoreCd(String dlvrStoreCd) {
    this.dlvrStoreCd = dlvrStoreCd;
  }

  public String getDlvrLzoneCd() {
    return dlvrLzoneCd;
  }

  public void setDlvrLzoneCd(String dlvrLzoneCd) {
    this.dlvrLzoneCd = dlvrLzoneCd;
  }

  public String getDlvrMzoneCd() {
    return dlvrMzoneCd;
  }

  public void setDlvrMzoneCd(String dlvrMzoneCd) {
    this.dlvrMzoneCd = dlvrMzoneCd;
  }

  public String getAddr() {
    return addr;
  }

  public void setAddr(String addr) {
    this.addr = addr;
  }

  public String getAddrDtl() {
    return addrDtl;
  }

  public void setAddrDtl(String addrDtl) {
    this.addrDtl = addrDtl;
  }

  public String getLastDlvrDate() {
    return lastDlvrDate;
  }

  public void setLastDlvrDate(String lastDlvrDate) {
    this.lastDlvrDate = lastDlvrDate;
  }

  public int getTotDlvrCnt() {
    return totDlvrCnt;
  }

  public void setTotDlvrCnt(int totDlvrCnt) {
    this.totDlvrCnt = totDlvrCnt;
  }

  public String getDlvrUseYn() {
    return dlvrUseYn;
  }

  public void setDlvrUseYn(String dlvrUseYn) {
    this.dlvrUseYn = dlvrUseYn;
  }

  public int getDlvrTelSeq() {
    return dlvrTelSeq;
  }

  public void setDlvrTelSeq(int dlvrTelSeq) {
    this.dlvrTelSeq = dlvrTelSeq;
  }

  public String getTelNo() {
    return telNo;
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  public String getShortNo() {
    return shortNo;
  }

  public void setShortNo(String shortNo) {
    this.shortNo = shortNo;
  }

  public String getDlvrTeluseYn() {
    return dlvrTeluseYn;
  }

  public void setDlvrTeluseYn(String dlvrTeluseYn) {
    this.dlvrTeluseYn = dlvrTeluseYn;
  }
}
