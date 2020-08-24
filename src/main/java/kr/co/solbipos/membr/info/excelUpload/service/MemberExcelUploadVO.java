package kr.co.solbipos.membr.info.excelUpload.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.hibernate.validator.constraints.NotBlank;

public class MemberExcelUploadVO extends PageVO {
  private String membrOrgnCd;
  private String membrNo;
  private String membrNm;
  private String membrNicknm;
  private String membrClassCd;
  private String membrCardNo;
  private String regStoreCd;
  private String postNo;
  private String addr;
  private String addrDtl;
  private String birthday;
  private String lunarYn;
  private String gendrFg;
  private String emailAddr;
  private String shortNo;
  private String telNo;
  private WeddingYn weddingYn;
  private String weddingday;
  private String emailRecvYn;
  private String smsRecvYn;
  private String useYn;
  private String remark;
  private String memberEngNm;
  private String cstCardUseFg;
  private Integer cstCardIssCnt;
  private String orgCstCardNo;

//  private String dlvrAddrSeq;
//  private String dlvrStoreCd;
//  private String dlvrLzoneCd;
//  private String dlvrMzoneCd;
//  private String lastSlvrSate;
//  private String totSlvrCnt;

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

  public String getMembrNm() {
    return membrNm;
  }

  public void setMembrNm(String membrNm) {
    this.membrNm = membrNm;
  }

  public String getMembrNicknm() {
    return membrNicknm;
  }

  public void setMembrNicknm(String membrNicknm) {
    this.membrNicknm = membrNicknm;
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

  public String getRegStoreCd() {
    return regStoreCd;
  }

  public void setRegStoreCd(String regStoreCd) {
    this.regStoreCd = regStoreCd;
  }

  public String getPostNo() {
    return postNo;
  }

  public void setPostNo(String postNo) {
    this.postNo = postNo;
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

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getLunarYn() {
    return lunarYn;
  }

  public void setLunarYn(String lunarYn) {
    this.lunarYn = lunarYn;
  }

  public String getGendrFg() {
    return gendrFg;
  }

  public void setGendrFg(String gendrFg) {
    this.gendrFg = gendrFg;
  }

  public String getEmailAddr() {
    return emailAddr;
  }

  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }

  public String getShortNo() {
    return shortNo;
  }

  public void setShortNo(String shortNo) {
    this.shortNo = shortNo;
  }

  public String getTelNo() {
    return telNo;
  }

  public void setTelNo(String telNo) {
    this.telNo = telNo;
  }

  public WeddingYn getWeddingYn() {
    return weddingYn;
  }

  public void setWeddingYn(WeddingYn weddingYn) {
    this.weddingYn = weddingYn;
  }

  public String getWeddingday() {
    return weddingday;
  }

  public void setWeddingday(String weddingday) {
    this.weddingday = weddingday;
  }

  public String getEmailRecvYn() {
    return emailRecvYn;
  }

  public void setEmailRecvYn(String emailRecvYn) {
    this.emailRecvYn = emailRecvYn;
  }

  public String getSmsRecvYn() {
    return smsRecvYn;
  }

  public void setSmsRecvYn(String smsRecvYn) {
    this.smsRecvYn = smsRecvYn;
  }

  public String getUseYn() {
    return useYn;
  }

  public void setUseYn(String useYn) {
    this.useYn = useYn;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getMemberEngNm() {
    return memberEngNm;
  }

  public void setMemberEngNm(String memberEngNm) {
    this.memberEngNm = memberEngNm;
  }

  public String getCstCardUseFg() {
    return cstCardUseFg;
  }

  public void setCstCardUseFg(String cstCardUseFg) {
    this.cstCardUseFg = cstCardUseFg;
  }

  public Integer getCstCardIssCnt() {
    return cstCardIssCnt;
  }

  public void setCstCardIssCnt(Integer cstCardIssCnt) {
    this.cstCardIssCnt = cstCardIssCnt;
  }

  public String getOrgCstCardNo() {
    return orgCstCardNo;
  }

  public void setOrgCstCardNo(String orgCstCardNo) {
    this.orgCstCardNo = orgCstCardNo;
  }
}
