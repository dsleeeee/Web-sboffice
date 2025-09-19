package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

public class WorkStudentScheduleStatusVO extends PageVO {
    private static final long serialVersionUID = -5901911024339484788L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회년도 */
    private String srchYear;
    /** 학기구분 */
    private String termFg;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 학번 */
    private String studentNo;
    /** 이름 */
    private String studentNm;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSrchYear() {
        return srchYear;
    }

    public void setSrchYear(String srchYear) {
        this.srchYear = srchYear;
    }

    public String getTermFg() {
        return termFg;
    }

    public void setTermFg(String termFg) {
        this.termFg = termFg;
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
}
