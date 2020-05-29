package kr.co.solbipos.sys.stats.userBase.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : UserBaseVO.java
 * @Description : 시스템관리 > 통계 > 사용자기준 사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class UserBaseVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 메뉴명 */
    private String resrceNm;

    /** 사용환경 */
    private String useEnv;

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

    /** 리소스코드 */
    private String resrceCd;

    /** 대,중,소메뉴 */
    private String level;

    public String getResrceNm() { return resrceNm; }

    public void setResrceNm(String resrceNm) { this.resrceNm = resrceNm; }

    public String getUseEnv() { return useEnv; }

    public void setUseEnv(String useEnv) { this.useEnv = useEnv; }

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

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}