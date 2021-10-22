package kr.co.solbipos.dlvr.info.dlvrEmp.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DlvrEmpVO extends PageVO {

    private static final long serialVersionUID = 12072813307997487L;

    /** 매장코드 */
    private String storeCd;
    /** 전체기간체크 */
    private boolean chkDt;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 배달사원번호*/
    private String dlvrEmpNo;
    /** 배달사원명*/
    private String dlvrEmpNm;
    /** 휴대폰번호 */
    private String hpNo;
    /** SMS 수신여부 */
    private String smsRecvYn;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getDlvrEmpNo() {
        return dlvrEmpNo;
    }

    public void setDlvrEmpNo(String dlvrEmpNo) {
        this.dlvrEmpNo = dlvrEmpNo;
    }

    public String getDlvrEmpNm() {
        return dlvrEmpNm;
    }

    public void setDlvrEmpNm(String dlvrEmpNm) {
        this.dlvrEmpNm = dlvrEmpNm;
    }

    public String getHpNo() {
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
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
}
