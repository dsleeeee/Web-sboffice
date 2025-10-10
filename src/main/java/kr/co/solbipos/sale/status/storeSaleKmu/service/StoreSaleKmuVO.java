package kr.co.solbipos.sale.status.storeSaleKmu.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreSaleKmuVO.java
 * @Description : 국민대 > 매출관리 > 점소별매출일보 (국민대)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreSaleKmuVO extends PageVO {

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

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 매장 array */
    private String arrStoreCol[];

    /** 쿼리문의 PIVOT IN에 사용할 매장 컬럼 문자열 */
    private String pivotStoreCol;

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

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String[] getArrStoreCol() {
        return arrStoreCol;
    }

    public void setArrStoreCol(String[] arrStoreCol) {
        this.arrStoreCol = arrStoreCol;
    }

    public String getPivotStoreCol() { return pivotStoreCol; }

    public void setPivotStoreCol(String pivotStoreCol) { this.pivotStoreCol = pivotStoreCol; }

    public String getTeamCd() { return teamCd; }

    public void setTeamCd(String teamCd) { this.teamCd = teamCd; }

    public String getTeamNm() { return teamNm; }

    public void setTeamNm(String teamNm) { this.teamNm = teamNm; }

    public String getBranchCd() { return branchCd; }

    public void setBranchCd(String branchCd) { this.branchCd = branchCd; }

    public String getBranchNm() { return branchNm; }

    public void setBranchNm(String branchNm) { this.branchNm = branchNm; }
}