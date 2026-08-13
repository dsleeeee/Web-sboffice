package kr.co.solbipos.sys.auth.smsMenuAuth.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsMenuAuthVO.java
 * @Description : 시스템관리 > 권한관리 > SMS화면관리(관리자)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SmsMenuAuthVO extends PageVO {

    private static final long serialVersionUID = 6387815364664801984L;

    /** 권한을 등록하거나 삭제할 사용자 ID */
    private String userId;

    /** 권한 대상 SMS 메뉴 코드 */
    private String resrceCd;

    /** SMS 메뉴명 조회조건 */
    private String resrceNm;

    /** 메뉴권한 처리 범위(H: 본사) */
    private String authorProdcFg;

    /** 메뉴권한 포함/제외 구분(I: 포함) */
    private String incldExcldFg;

    /** 메뉴권한 사용 여부 */
    private String useYn;

    /** 메뉴권한 접속 구분(W: 웹) */
    private String loginFg;

    /** 메뉴권한 등록 비고 */
    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public String getResrceNm() {
        return resrceNm;
    }

    public void setResrceNm(String resrceNm) {
        this.resrceNm = resrceNm;
    }

    public String getAuthorProdcFg() {
        return authorProdcFg;
    }

    public void setAuthorProdcFg(String authorProdcFg) {
        this.authorProdcFg = authorProdcFg;
    }

    public String getIncldExcldFg() {
        return incldExcldFg;
    }

    public void setIncldExcldFg(String incldExcldFg) {
        this.incldExcldFg = incldExcldFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getLoginFg() {
        return loginFg;
    }

    public void setLoginFg(String loginFg) {
        this.loginFg = loginFg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
