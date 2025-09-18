package kr.co.solbipos.kookmin.workStudent.workHistory.service;

import kr.co.solbipos.application.common.service.PageVO;

public class WorkHistoryVO extends PageVO {

    private static final long serialVersionUID = 3225168169293873270L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 근무일자 */
    private String workDate;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 학번 */
    private String studentNo;
    /** 이름 */
    private String studentNm;

    /** 년도 */
    private String termYear;
    /** 학기구분 */
    private String termFg;
    /** 근무코드 */
    private String workSchCode;
    /** 시작기준 */
    private String baseStartTime;
    /** 종료기준 */
    private String baseEndTime;
    /** 근로시작 */
    private String realStartTime;
    /** 근로종료 */
    private String realEndTime;
    /** 수정사유 */
    private String modReason;
    /** 등록구분(P:POS, W:WEB) */
    private String regInFg;
    /** 근로시간 */
    private String workTime;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentNm() {
        return studentNm;
    }

    public void setStudentNm(String studentNm) {
        this.studentNm = studentNm;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getTermFg() {
        return termFg;
    }

    public void setTermFg(String termFg) {
        this.termFg = termFg;
    }

    public String getWorkSchCode() {
        return workSchCode;
    }

    public void setWorkSchCode(String workSchCode) {
        this.workSchCode = workSchCode;
    }

    public String getBaseStartTime() {
        return baseStartTime;
    }

    public void setBaseStartTime(String baseStartTime) {
        this.baseStartTime = baseStartTime;
    }

    public String getBaseEndTime() {
        return baseEndTime;
    }

    public void setBaseEndTime(String baseEndTime) {
        this.baseEndTime = baseEndTime;
    }

    public String getRealStartTime() {
        return realStartTime;
    }

    public void setRealStartTime(String realStartTime) {
        this.realStartTime = realStartTime;
    }

    public String getRealEndTime() {
        return realEndTime;
    }

    public void setRealEndTime(String realEndTime) {
        this.realEndTime = realEndTime;
    }

    public String getModReason() {
        return modReason;
    }

    public void setModReason(String modReason) {
        this.modReason = modReason;
    }

    public String getRegInFg() {
        return regInFg;
    }

    public void setRegInFg(String regInFg) {
        this.regInFg = regInFg;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
}
