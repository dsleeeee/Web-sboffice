package kr.co.solbipos.membr.info.bbqMemberExcelUpload.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidFg;
import kr.co.solbipos.membr.anals.postpaid.enums.PostpaidPayFg;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidFg;
import kr.co.solbipos.membr.anals.prepaid.enums.PrepaidPayFg;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;

public class BbqMemberExcelUploadVO extends PageVO {

    private static final long serialVersionUID = -6053994910135817728L;

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
      private String memberTelNo;
      private String memberShortNo;
      private String memberEmail;
      private String memberPostNo;
      private String memberAddr;
      private String memberAddrDtl;
      private String avablPoint;
      private String totAdjPoint;

      /** 본사코드 */
      private String hqOfficeCd;
      /** 선불충전금액 */
      private String prepaidAmt;
      /** 선불사용금액 */
      private String prepaidUseAmt;
      /** 후불발생금액 */
      private String postpaidAmt;
      /** 후불입금금액 */
      private String depositAmt;
      /** 외상 입금 날짜 */
      private String saleDate;
      /** 외상 입금 일시 */
      private String prepaidDt;
      /** 외상 입금 구분 */
      private PrepaidFg prepaidFg;
      /** 외상 입금 구분 */
      private PrepaidPayFg prepaidPayFg;
      /** 후불 입금 일시 */
      private String postpaidDt;
      /** 후불 입금 구분 */
      private PostpaidFg postpaidFg;
      /** 후불 입금 구분 */
      private PostpaidPayFg postpaidPayFg;
      /** 비매출 승인번호 */
      private String nonsaleTypeApprNo;
      /** 원거래비매출승인번호 */
      private String orgNonsaleTypeApprNo;
      /** 비매출 영수증 번호 */
      private String nonsaleBillNo;
      /** 금액 */
      private String amt;
      /** 최종주문일자 */
      private String lastSaleDate;

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

    public String getMemberTelNo() {
        return memberTelNo;
    }

    public void setMemberTelNo(String memberTelNo) {
        this.memberTelNo = memberTelNo;
    }

    public String getMemberShortNo() {
        return memberShortNo;
    }

    public void setMemberShortNo(String memberShortNo) {
        this.memberShortNo = memberShortNo;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPostNo() {
        return memberPostNo;
    }

    public void setMemberPostNo(String memberPostNo) {
        this.memberPostNo = memberPostNo;
    }

    public String getMemberAddr() {
        return memberAddr;
    }

    public void setMemberAddr(String memberAddr) {
        this.memberAddr = memberAddr;
    }

    public String getMemberAddrDtl() {
        return memberAddrDtl;
    }

    public void setMemberAddrDtl(String memberAddrDtl) {
        this.memberAddrDtl = memberAddrDtl;
    }

    public String getAvablPoint() {
        return avablPoint;
    }

    public void setAvablPoint(String avablPoint) {
        this.avablPoint = avablPoint;
    }

    public String getTotAdjPoint() {
        return totAdjPoint;
    }

    public void setTotAdjPoint(String totAdjPoint) {
        this.totAdjPoint = totAdjPoint;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getPrepaidAmt() {
        return prepaidAmt;
    }

    public void setPrepaidAmt(String prepaidAmt) {
        this.prepaidAmt = prepaidAmt;
    }

    public String getPrepaidUseAmt() {
        return prepaidUseAmt;
    }

    public void setPrepaidUseAmt(String prepaidUseAmt) {
        this.prepaidUseAmt = prepaidUseAmt;
    }

    public String getPostpaidAmt() {
        return postpaidAmt;
    }

    public void setPostpaidAmt(String postpaidAmt) {
        this.postpaidAmt = postpaidAmt;
    }

    public String getDepositAmt() {
        return depositAmt;
    }

    public void setDepositAmt(String depositAmt) {
        this.depositAmt = depositAmt;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPrepaidDt() {
        return prepaidDt;
    }

    public void setPrepaidDt(String prepaidDt) {
        this.prepaidDt = prepaidDt;
    }

    public PrepaidFg getPrepaidFg() {
        return prepaidFg;
    }

    public void setPrepaidFg(PrepaidFg prepaidFg) {
        this.prepaidFg = prepaidFg;
    }

    public PrepaidPayFg getPrepaidPayFg() {
        return prepaidPayFg;
    }

    public void setPrepaidPayFg(PrepaidPayFg prepaidPayFg) {
        this.prepaidPayFg = prepaidPayFg;
    }

    public String getPostpaidDt() {
        return postpaidDt;
    }

    public void setPostpaidDt(String postpaidDt) {
        this.postpaidDt = postpaidDt;
    }

    public PostpaidFg getPostpaidFg() {
        return postpaidFg;
    }

    public void setPostpaidFg(PostpaidFg postpaidFg) {
        this.postpaidFg = postpaidFg;
    }

    public PostpaidPayFg getPostpaidPayFg() {
        return postpaidPayFg;
    }

    public void setPostpaidPayFg(PostpaidPayFg postpaidPayFg) {
        this.postpaidPayFg = postpaidPayFg;
    }

    public String getNonsaleTypeApprNo() {
        return nonsaleTypeApprNo;
    }

    public void setNonsaleTypeApprNo(String nonsaleTypeApprNo) {
        this.nonsaleTypeApprNo = nonsaleTypeApprNo;
    }

    public String getOrgNonsaleTypeApprNo() {
        return orgNonsaleTypeApprNo;
    }

    public void setOrgNonsaleTypeApprNo(String orgNonsaleTypeApprNo) {
        this.orgNonsaleTypeApprNo = orgNonsaleTypeApprNo;
    }

    public String getNonsaleBillNo() {
        return nonsaleBillNo;
    }

    public void setNonsaleBillNo(String nonsaleBillNo) {
        this.nonsaleBillNo = nonsaleBillNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getLastSaleDate() {
        return lastSaleDate;
    }

    public void setLastSaleDate(String lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }
}
