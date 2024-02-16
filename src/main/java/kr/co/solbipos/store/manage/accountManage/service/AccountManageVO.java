package kr.co.solbipos.store.manage.accountManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : AccountManageVO.java
 * @Description : 기초관리 > 매장정보관리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.14  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AccountManageVO extends PageVO {

    private static final long serialVersionUID = -2333193338580375959L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 사용자 아이디 */
    private String userId;
    /** 사용자 명 */
    private String userNm;
    /** 접속기간 */
    private String loginPeriod;
    /** 계정사용여부 */
    private String webUseYn;
    /** 계정 상태 변경 구분 */
    private String statChgFg;
    /** 사용자상태구분*/
    private String userStatFg;
    /** 사용여부  */
    private String useYn;
    /** 소속코드 */
    private String orgnCd;
    /** 사원번호 */
    private String empNo;
    /** 소속구분 */
    private String orgnFg;
    /** 사용자구분 */
    private String orgnFg2;
    /** 사용자구분(배열) */
    private String[] orgnFg2List;

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

    public String getLoginPeriod() {
        return loginPeriod;
    }

    public void setLoginPeriod(String loginPeriod) {
        this.loginPeriod = loginPeriod;
    }

    public String getWebUseYn() {
        return webUseYn;
    }

    public void setWebUseYn(String webUseYn) {
        this.webUseYn = webUseYn;
    }

    public String getStatChgFg() {
        return statChgFg;
    }

    public void setStatChgFg(String statChgFg) {
        this.statChgFg = statChgFg;
    }

    public String getUserStatFg() {
        return userStatFg;
    }

    public void setUserStatFg(String userStatFg) {
        this.userStatFg = userStatFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getOrgnFg2() {
        return orgnFg2;
    }

    public void setOrgnFg2(String orgnFg2) {
        this.orgnFg2 = orgnFg2;
    }

    public String[] getOrgnFg2List() {
        return orgnFg2List;
    }

    public void setOrgnFg2List(String[] orgnFg2List) {
        this.orgnFg2List = orgnFg2List;
    }
}
