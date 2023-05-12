package kr.co.solbipos.store.manage.pwdManageStore.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.store.manage.pwdmanage.enums.EmpOrgnFg;

/**
 * @Class Name : PwdManageStoreVO.java
 * @Description : 기초관리 > 비밀번호 임의변경 > 비밀번호 임의변경(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PwdManageStoreVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 사용자ID */
    private String userId;

    /** 사용자명 */
    private String userNm;

    /** 조회 사원 구분 */
    private EmpOrgnFg empOrgnFg;

    /** 조회용 매장코드 */
    private String srchStoreCd;

    /** 조회용 사용자ID */
    private String srchUserId;

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

    public EmpOrgnFg getEmpOrgnFg() {
        return empOrgnFg;
    }

    public void setEmpOrgnFg(EmpOrgnFg empOrgnFg) {
        this.empOrgnFg = empOrgnFg;
    }

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String getSrchUserId() {
        return srchUserId;
    }

    public void setSrchUserId(String srchUserId) {
        this.srchUserId = srchUserId;
    }
}
