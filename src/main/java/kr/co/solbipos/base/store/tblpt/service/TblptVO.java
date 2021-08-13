package kr.co.solbipos.base.store.tblpt.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : TblptVO.java
* @Description : 기초관리 > 매장관리 > 테이블속성
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @
*
* @author
* @since
* @version
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class TblptVO extends PageVO {

    private static final long serialVersionUID = -1652554561773776441L;

    /** 매장코드 */
    private String storeCd;
    /** '테이블속성 임시패스워드' */
    private String tblptTempPw;
    /** '테이블속성 접속구분(0: 대기, 1: 접속성공, 2:접속실패' */
    private String tblptLoginFg;
    /** '테이블속성 임시패스워드 생성일시' */
    private String tblptTempPwDt;
    /** '테이블속성 임시패스워드 생성자' */
    private String tblptTempPwRegId;

    /**
     * @return the storeCd
     */
    public String getStoreCd() { return storeCd; }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getTblptTempPw() { return tblptTempPw; }

    public void setTblptTempPw(String tblptTempPw) { this.tblptTempPw = tblptTempPw; }

    public String getTblptLoginFg() { return tblptLoginFg; }

    public void setTblptLoginFg(String tblptLoginFg) { this.tblptLoginFg = tblptLoginFg; }

    public String getTblptTempPwDt() { return tblptTempPwDt; }

    public void setTblptTempPwDt(String tblptTempPwDt) { this.tblptTempPwDt = tblptTempPwDt; }

    public String getTblptTempPwRegId() { return tblptTempPwRegId; }

    public void setTblptTempPwRegId(String tblptTempPwRegId) { this.tblptTempPwRegId = tblptTempPwRegId; }
}
