package kr.co.solbipos.excclc.excclc.dayClose.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DayCloseVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 Array */
    private String arrStoreCd[];
    /** 매장코드 */
    private String storeCd;
    /** 개점일자 */
    private String openDate;
    /** 마감일자 */
    private String closeDate;
    /** 마감구분 */
    private String closeFg;

    private long interestAmt; // 결산이자
    private long inAmt; // 입금액
    private long outAmt; // 출금액
    private long groupAmt; // 단체
    private long hockeyAmt; // 하키
    private long etcAmt; // 기타
    private long inDayAmt; // 1일 일계입금
    private long inSum; // 입금계
    private long inMonthSum; // 월입금계
    private long inBMonthSum; // 전월누계
    private long inTotalSum; // 총누계
    private long outSum; // 출금계
    private long outMonthSum; // 월출금계
    private long outBMonthSum; // 전월누계
    private long outTotalSum; // 총누계

    private String remark1;
    private String remark2;
    private String remark3;
    private String remark4;
    private String remark5;
    private String remark6;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getCloseFg() {
        return closeFg;
    }

    public void setCloseFg(String closeFg) {
        this.closeFg = closeFg;
    }

    public long getInterestAmt() {
        return interestAmt;
    }

    public void setInterestAmt(long interestAmt) {
        this.interestAmt = interestAmt;
    }

    public long getInAmt() {
        return inAmt;
    }

    public void setInAmt(long inAmt) {
        this.inAmt = inAmt;
    }

    public long getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(long outAmt) {
        this.outAmt = outAmt;
    }

    public long getGroupAmt() {
        return groupAmt;
    }

    public void setGroupAmt(long groupAmt) {
        this.groupAmt = groupAmt;
    }

    public long getHockeyAmt() {
        return hockeyAmt;
    }

    public void setHockeyAmt(long hockeyAmt) {
        this.hockeyAmt = hockeyAmt;
    }

    public long getEtcAmt() {
        return etcAmt;
    }

    public void setEtcAmt(long etcAmt) {
        this.etcAmt = etcAmt;
    }

    public long getInDayAmt() {
        return inDayAmt;
    }

    public void setInDayAmt(long inDayAmt) {
        this.inDayAmt = inDayAmt;
    }

    public long getInSum() {
        return inSum;
    }

    public void setInSum(long inSum) {
        this.inSum = inSum;
    }

    public long getInMonthSum() {
        return inMonthSum;
    }

    public void setInMonthSum(long inMonthSum) {
        this.inMonthSum = inMonthSum;
    }

    public long getInBMonthSum() {
        return inBMonthSum;
    }

    public void setInBMonthSum(long inBMonthSum) {
        this.inBMonthSum = inBMonthSum;
    }

    public long getInTotalSum() {
        return inTotalSum;
    }

    public void setInTotalSum(long inTotalSum) {
        this.inTotalSum = inTotalSum;
    }

    public long getOutSum() {
        return outSum;
    }

    public void setOutSum(long outSum) {
        this.outSum = outSum;
    }

    public long getOutMonthSum() {
        return outMonthSum;
    }

    public void setOutMonthSum(long outMonthSum) {
        this.outMonthSum = outMonthSum;
    }

    public long getOutBMonthSum() {
        return outBMonthSum;
    }

    public void setOutBMonthSum(long outBMonthSum) {
        this.outBMonthSum = outBMonthSum;
    }

    public long getOutTotalSum() {
        return outTotalSum;
    }

    public void setOutTotalSum(long outTotalSum) {
        this.outTotalSum = outTotalSum;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }

    public String getRemark6() {
        return remark6;
    }

    public void setRemark6(String remark6) {
        this.remark6 = remark6;
    }
}
