package kr.co.solbipos.base.store.tblms.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : TblmsVO.java
* @Description : 기초관리 > 매장관리 > 테이블관리
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
public class TblmsVO extends PageVO {

    private static final long serialVersionUID = -1652554561773776441L;

    /** 매장코드 */
    private String storeCd;
    /** '테이블관리 임시패스워드' */
    private String tblmsTempPw;
    /** '테이블관리 접속구분(0: 대기, 1: 접속성공, 2:접속실패' */
    private String tblmsLoginFg;
    /** '테이블관리 임시패스워드 생성일시' */
    private String tblmsTempPwDt;
    /** '테이블관리 임시패스워드 생성자' */
    private String tblmsTempPwRegId;

    /**
     * @return the storeCd
     */
    public String getStoreCd() { return storeCd; }
    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getTblmsTempPw() { return tblmsTempPw; }

    public void setTblmsTempPw(String tblmsTempPw) { this.tblmsTempPw = tblmsTempPw; }

    public String getTblmsLoginFg() { return tblmsLoginFg; }

    public void setTblmsLoginFg(String tblmsLoginFg) { this.tblmsLoginFg = tblmsLoginFg; }

    public String getTblmsTempPwDt() { return tblmsTempPwDt; }

    public void setTblmsTempPwDt(String tblmsTempPwDt) { this.tblmsTempPwDt = tblmsTempPwDt; }

    public String getTblmsTempPwRegId() { return tblmsTempPwRegId; }

    public void setTblmsTempPwRegId(String tblmsTempPwRegId) { this.tblmsTempPwRegId = tblmsTempPwRegId; }
}
