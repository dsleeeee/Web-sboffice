package kr.co.solbipos.base.prod.purchaser.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PurchaserVO.java
 * @Description : 국민대 > 매입처관리 > 매입처조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PurchaserVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 거래처코드 */
    private String vendrCd;

    /** 거래처명 */
    private String vendrNm;

    /** 팀코드 */
    private String teamCd;

    /** 팀명 */
    private String teamNm;

    /** 지점코드 */
    private String branchCd;

    /** 지점명 */
    private String branchNm;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getVendrCd() { return vendrCd; }

    public void setVendrCd(String vendrCd) { this.vendrCd = vendrCd; }

    public String getVendrNm() { return vendrNm; }

    public void setVendrNm(String vendrNm) { this.vendrNm = vendrNm; }

    public String getTeamCd() { return teamCd; }

    public void setTeamCd(String teamCd) { this.teamCd = teamCd; }

    public String getTeamNm() { return teamNm; }

    public void setTeamNm(String teamNm) { this.teamNm = teamNm; }

    public String getBranchCd() { return branchCd; }

    public void setBranchCd(String branchCd) { this.branchCd = branchCd; }

    public String getBranchNm() { return branchNm; }

    public void setBranchNm(String branchNm) { this.branchNm = branchNm; }
}