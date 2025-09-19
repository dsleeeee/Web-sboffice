package kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : WorkHistoryByWorkStudentVO.java
 * @Description : 국민대 > 근로학생관리 > 근로학생별 근무내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class WorkHistoryByWorkStudentVO extends PageVO {

    private static final long serialVersionUID = -8455324709330400164L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회일자 */
    private String startTime;
    private String endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
