package kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : WorkScheduleStudentVO.java
 * @Description : 국민대 > 근로학생관리 > 근로학생 배치
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class WorkScheduleStudentVO extends PageVO {
    private static final long serialVersionUID = -8460830505834623746L;

    /** 조회년도 */
    private String srchDate;
    /** 본사코드 */
    private String hqOfficeCd;

    /** 년도 */
    private String termYear;
    /** 학기구분 */
    private String termFg;
    /** 근무배치여부 */
    private String regFg;
    /** 근무코드 */
    private String workSchCode;
    /** 매장코드 */
    private String storeCd;
    /** 시작시간 */
    private String startDay;
    /** 종료시간 */
    private String endDay;
    /** 학번 */
    private String studentNo;
    /** 업무 */
    private String workFg;
    /** 비고 */
    private String remark;

    /** 이름 */
    private String studentNm;
    /** 학과 */
    private String department;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getSrchDate() {
        return srchDate;
    }

    public void setSrchDate(String srchDate) {
        this.srchDate = srchDate;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
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

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getWorkSchCode() {
        return workSchCode;
    }

    public void setWorkSchCode(String workSchCode) {
        this.workSchCode = workSchCode;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getWorkFg() {
        return workFg;
    }

    public void setWorkFg(String workFg) {
        this.workFg = workFg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getStudentNm() {
        return studentNm;
    }

    public void setStudentNm(String studentNm) {
        this.studentNm = studentNm;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
