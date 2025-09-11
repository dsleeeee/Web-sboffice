package kr.co.solbipos.kookmin.base.termInfo.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : TermInfoVO.java
 * @Description : 국민대 > 기초관리 > 학기정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.05  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class TermInfoVO extends PageVO {
    private static final long serialVersionUID = 4634604712700159756L;

    /** 조회년도 */
    private String srchDate;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 년도 */
    private String termYear;
    /** 학기구분 */
    private String termFg;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;

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
}
