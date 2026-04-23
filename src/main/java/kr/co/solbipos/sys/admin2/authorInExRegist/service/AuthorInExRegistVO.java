package kr.co.solbipos.sys.admin2.authorInExRegist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name  : AuthorInExRegistVO.java
 * @Description : 시스템관리 > 관리자기능2 > 메뉴권한임의등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.04.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class AuthorInExRegistVO extends PageVO {

    private static final long serialVersionUID = 381650614168958995L;
    
    /** 사용자ID */
    private String userId;

    /** 사용자명 */
    private String userNm;

    /** 권한처리구분 */
    private String authorProdcFg;

    /** 리소스코드 */
    private String resrceCd;

    /** 리소스명 */
    private String resrceNm;

    /** 포함제외구분 */
    private String incldExcldFg;

    /** 사용여부 */
    private String useYn;

    /** 로그인구분 */
    private String loginFg;

    /** 비고 */
    private String remark;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 권한그룹여부 */
    private String authGrpFg;

    /** 소속코드 */
    private String orgnCd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getAuthorProdcFg() {
        return authorProdcFg;
    }

    public void setAuthorProdcFg(String authorProdcFg) {
        this.authorProdcFg = authorProdcFg;
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

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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

    public String getAuthGrpFg() {
        return authGrpFg;
    }

    public void setAuthGrpFg(String authGrpFg) {
        this.authGrpFg = authGrpFg;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }
}
