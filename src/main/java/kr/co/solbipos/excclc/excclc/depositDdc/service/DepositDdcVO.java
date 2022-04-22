package kr.co.solbipos.excclc.excclc.depositDdc.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DepositDdcVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 다중매장코드 array */
    private String arrStoreCd[];
    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
    /** 입금/기타공제 등록일자 */
    private String moneyDate;
    /** 계정구분 */
    private String moneyFg;
    /** 금액 */
    private String moneyAmt;
    /** 비고 */
    private String remark;
    /** 일련번호 */
    private String seqNo;


    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(String moneyDate) {
        this.moneyDate = moneyDate;
    }

    public String getMoneyFg() {
        return moneyFg;
    }

    public void setMoneyFg(String moneyFg) {
        this.moneyFg = moneyFg;
    }

    public String getMoneyAmt() {
        return moneyAmt;
    }

    public void setMoneyAmt(String moneyAmt) {
        this.moneyAmt = moneyAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
}
