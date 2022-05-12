package kr.co.solbipos.adi.sms.smsCharge.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsChargeVO.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SmsChargeVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 소속코드 */
    private String orgnCd;

    /** 충전일자 */
    private String chargeDate;

    /** 충전시각 */
    private String chargeTime;

    /** 결제수단 */
    private String pgresource;

    /** 충전금액 */
    private String chargeAmt;

    /** 결제금액(KCP 실제 거래 금액) */
    private String chargeTot;

    /** 결제성공여부 */
    private String successYn;

    /** KICC승인번호 */
    private String controlno;

    /** 승인번호 */
    private String approvalnum;

    /** 결과코드 */
    private String resultcode;

    /** 결과메세지 */
    private String resultmessage;

    /** SMS 기초수량 */
    private String smsBaseQty;

    /** SMS 충전수량 */
    private String smsChargeQty;

    /** 잔여수량 */
    private String smsQty;

    /** 소속코드 */
    private String selectOrgnCd;

    /** 결제취소일자 */
    private String rtnDate;

    /** 결제취소시각 */
    private String rtnTime;

    /** 사용자ID */
    private String userId;

    /** 알림톡비용차감 주체 소속코드 */
    private String alkChargeOrgnCd;

    /** 잔여금액 설정값 */
    private String rmSmsAmt;

    /** 전화번호 */
    private String telNo;

    /** 잔여금액 설정값 이하시 전송여부 */
    private String rmSmsAmtYn;

    /** 0이하시 SMS전송여부 */
    private String zeroSmsAmtYn;

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getChargeDate() { return chargeDate; }

    public void setChargeDate(String chargeDate) { this.chargeDate = chargeDate; }

    public String getChargeTime() { return chargeTime; }

    public void setChargeTime(String chargeTime) { this.chargeTime = chargeTime; }

    public String getPgresource() { return pgresource; }

    public void setPgresource(String pgresource) { this.pgresource = pgresource; }

    public String getChargeAmt() { return chargeAmt; }

    public void setChargeAmt(String chargeAmt) { this.chargeAmt = chargeAmt; }

    public String getChargeTot() { return chargeTot; }

    public void setChargeTot(String chargeTot) { this.chargeTot = chargeTot; }

    public String getSuccessYn() { return successYn; }

    public void setSuccessYn(String successYn) { this.successYn = successYn; }

    public String getControlno() { return controlno; }

    public void setControlno(String controlno) { this.controlno = controlno; }

    public String getApprovalnum() { return approvalnum; }

    public void setApprovalnum(String approvalnum) { this.approvalnum = approvalnum; }

    public String getResultcode() { return resultcode; }

    public void setResultcode(String resultcode) { this.resultcode = resultcode; }

    public String getResultmessage() { return resultmessage; }

    public void setResultmessage(String resultmessage) { this.resultmessage = resultmessage; }

    public String getSmsBaseQty() { return smsBaseQty; }

    public void setSmsBaseQty(String smsBaseQty) { this.smsBaseQty = smsBaseQty; }

    public String getSmsChargeQty() { return smsChargeQty; }

    public void setSmsChargeQty(String smsChargeQty) { this.smsChargeQty = smsChargeQty; }

    public String getSmsQty() { return smsQty; }

    public void setSmsQty(String smsQty) {  this.smsQty = smsQty; }

    public String getSelectOrgnCd() { return selectOrgnCd; }

    public void setSelectOrgnCd(String selectOrgnCd) { this.selectOrgnCd = selectOrgnCd; }

    public String getRtnDate() { return rtnDate; }

    public void setRtnDate(String rtnDate) { this.rtnDate = rtnDate; }

    public String getRtnTime() { return rtnTime; }

    public void setRtnTime(String rtnTime) { this.rtnTime = rtnTime; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getAlkChargeOrgnCd() { return alkChargeOrgnCd; }

    public void setAlkChargeOrgnCd(String alkChargeOrgnCd) { this.alkChargeOrgnCd = alkChargeOrgnCd; }

    public String getRmSmsAmt() { return rmSmsAmt; }

    public void setRmSmsAmt(String rmSmsAmt) { this.rmSmsAmt = rmSmsAmt; }

    public String getTelNo() { return telNo; }

    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getRmSmsAmtYn() { return rmSmsAmtYn; }

    public void setRmSmsAmtYn(String rmSmsAmtYn) { this.rmSmsAmtYn = rmSmsAmtYn; }

    public String getZeroSmsAmtYn() { return zeroSmsAmtYn; }

    public void setZeroSmsAmtYn(String zeroSmsAmtYn) { this.zeroSmsAmtYn = zeroSmsAmtYn; }
}