package kr.co.solbipos.sys.admin.funcKey.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : FuncKeyVO.java
 * @Description : 시스템관리 > 관리자기능 > 기능키적용버전등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.09  김유승       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김유승
 * @since 2024.01.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class FuncKeyVO extends PageVO{
    
    private static final long serialVersionUID = -7184818440208564250L;

    /** 기능구분명*/
    private String nmcodeNm;
    /** 기능키번호*/
    private String fnkeyNo;
    /** 기능키명*/
    private String fnkeyNm;
    /** 기능구분*/
    private String fnkeyFg;
    /** 적용버전*/
    private String fnkeyNoVersion;

    public String getFnkeyNo() { return fnkeyNo; }

    public void setFnkeyNo(String fnkeyNo) { this.fnkeyNo = fnkeyNo; }

    public String getFnkeyNm() { return fnkeyNm; }

    public void setFnkeyNm(String fnkeyNm) { this.fnkeyNm = fnkeyNm; }

    public String getFnkeyFg() { return fnkeyFg; }

    public void setFnkeyFg(String fnkeyFg) { this.fnkeyFg = fnkeyFg; }

    public String getFnkeyNoVersion() { return fnkeyNoVersion; }

    public void setFnkeyNoVersion(String fnkeyNoVersion) { this.fnkeyNoVersion = fnkeyNoVersion; }

    public String getNmcodeNm() { return nmcodeNm; }

    public void setNmcodeNm(String nmcodeNm) { this.nmcodeNm = nmcodeNm; }
}
