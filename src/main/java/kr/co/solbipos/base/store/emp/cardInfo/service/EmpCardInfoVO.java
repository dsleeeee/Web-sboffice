package kr.co.solbipos.base.store.emp.cardInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : EmpCardInfoVO.java
 * @Description : 기초관리 - 사원관리 - 사원카드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EmpCardInfoVO extends PageVO {

    private static final long serialVersionUID = -1246853862055481134L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사원소속코드 */
    private String employeeOrgnCd;
    /** 사원카드번호 */
    private String employeeCardNo;
    /** 사원번호 */
    private String employeeNo;
    /** 사원이름 */
    private String employeeNm;
    /** 소속명 */
    private String divNm;
    /** 부서명*/
    private String deptNm;
    /** 직위명 */
    private String positionNm;
    /** 카드사용구분 */
    private String useFg;

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

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getEmployeeOrgnCd() {
        return employeeOrgnCd;
    }

    public void setEmployeeOrgnCd(String employeeOrgnCd) {
        this.employeeOrgnCd = employeeOrgnCd;
    }

    public String getEmployeeCardNo() {
        return employeeCardNo;
    }

    public void setEmployeeCardNo(String employeeCardNo) {
        this.employeeCardNo = employeeCardNo;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeNm() {
        return employeeNm;
    }

    public void setEmployeeNm(String employeeNm) {
        this.employeeNm = employeeNm;
    }

    public String getDivNm() {
        return divNm;
    }

    public void setDivNm(String divNm) {
        this.divNm = divNm;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getPositionNm() {
        return positionNm;
    }

    public void setPositionNm(String positionNm) {
        this.positionNm = positionNm;
    }

    public String getUseFg() {
        return useFg;
    }

    public void setUseFg(String useFg) {
        this.useFg = useFg;
    }
}
