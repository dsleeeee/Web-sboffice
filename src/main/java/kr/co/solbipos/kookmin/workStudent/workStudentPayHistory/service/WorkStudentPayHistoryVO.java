package kr.co.solbipos.kookmin.workStudent.workStudentPayHistory.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : WorkStudentPayHistoryVO.java
 * @Description : 국민대 > 근로학생관리 > 근로장학금 지급내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.18
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class WorkStudentPayHistoryVO extends PageVO {
    private static final long serialVersionUID = 5447298864341222252L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회년월 */
    private String srchYm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 학번 */
    private String studentNo;
    /** 이름 */
    private String studentNm;

    /** 근무코드 */
    private String workSchCode;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSrchYm() {
        return srchYm;
    }

    public void setSrchYm(String srchYm) {
        this.srchYm = srchYm;
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

    public String getWorkSchCode() {
        return workSchCode;
    }

    public void setWorkSchCode(String workSchCode) {
        this.workSchCode = workSchCode;
    }
}
