package kr.co.solbipos.membr.reportKwu.monthlyMembr.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MonthlyMembrVO.java
 * @Description : 광운대 > 리포트 > 월별회원등록현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class MonthlyMembrVO extends PageVO {

    private static final long serialVersionUID = -8333861160812084527L;

    /** 강습구분 */
    private String classFg;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 조회월 */
    private String startMonth;

    /** 코드타입 : 콤보박스용인지 컬럼용인지에 따라 다름, [1: comboBox용, 2: column용] */
    private String codeType;


    public String getClassFg() {
        return classFg;
    }

    public void setClassFg(String classFg) {
        this.classFg = classFg;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
