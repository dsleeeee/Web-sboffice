package kr.co.solbipos.sys.stats.webLogin.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : WebLoginVO.java
 * @Description : 시스템관리 > 통계 > 웹로그인 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.06.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class WebLoginVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 사용자ID */
    private String userId;

    /** 사용자명 */
    private String userNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 로그인 결과 */
    private String statCd;

    /** 로그인 방법 */
    private String loginOrgn;

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getUserNm() { return userNm; }

    public void setUserNm(String userNm) { this.userNm = userNm; }

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

    public String getStatCd() {
        return statCd;
    }

    public void setStatCd(String statCd) {
        this.statCd = statCd;
    }

    public String getLoginOrgn() {
        return loginOrgn;
    }

    public void setLoginOrgn(String loginOrgn) {
        this.loginOrgn = loginOrgn;
    }
}