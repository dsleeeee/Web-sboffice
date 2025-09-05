package kr.co.solbipos.kookmin.workStudent.workStudent.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : WorkStudentKookminVO.java
 * @Description : 국민대 > 근로학생관리 > 근로학생관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.31  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class WorkStudentKookminVO extends PageVO {
    private static final long serialVersionUID = 3194595903317570763L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회조건 */
    private String srchCondition;

    /** 학번 */
    private String studentNo;
    /** 이름 */
    private String studentNm;
    /** 학과 */
    private String department;
    /** 연락처 */
    private String mpNo;
    /** 은행 */
    private String bankNm;
    /** 계좌번호 */
    private String accountNo;
    /** 조합원 */
    private String coopYn;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSrchCondition() {
        return srchCondition;
    }

    public void setSrchCondition(String srchCondition) {
        this.srchCondition = srchCondition;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCoopYn() {
        return coopYn;
    }

    public void setCoopYn(String coopYn) {
        this.coopYn = coopYn;
    }
}
