package kr.co.solbipos.membr.reportKwu.instructorMembr.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : InstructorMembrVO.java
 * @Description : 광운대 > 리포트 > 강사별회원관리내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.19  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.19
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class InstructorMembrVO extends PageVO {

    private static final long serialVersionUID = 5108431173978132184L;

    /** 강사명 */
    private String teacherCd;

    /** 강습구분 */
    private String classFg;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 구분 */
    private String membrUseYn;


    public String getTeacherCd() {
        return teacherCd;
    }

    public void setTeacherCd(String teacherCd) {
        this.teacherCd = teacherCd;
    }

    public String getClassFg() {
        return classFg;
    }

    public void setClassFg(String classFg) {
        this.classFg = classFg;
    }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
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

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getMembrUseYn() {
        return membrUseYn;
    }

    public void setMembrUseYn(String membrUseYn) {
        this.membrUseYn = membrUseYn;
    }
}
