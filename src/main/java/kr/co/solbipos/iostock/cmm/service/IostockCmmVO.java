package kr.co.solbipos.iostock.cmm.service;

import kr.co.solbipos.application.common.service.PageVO;

public class IostockCmmVO extends PageVO {

    private static final long serialVersionUID = -6387982658080046166L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 공통코드 */
    private String nmcodeGrpCd;
    /** 공통코드 아이템1 */
    private String nmcodeItem1;
    /** 테이블 */
    private String selectTable;
    /** 조회할 코드명 */
    private String selectCd;
    /** 조회할 명칭명 */
    private String selectNm;
    /** 조건문 */
    private String selectWhere;
    /** 처리구분 */
    private String procFg;

    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the nmcodeGrpCd
     */
    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    /**
     * @param nmcodeGrpCd the nmcodeGrpCd to set
     */
    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    /**
     * @return the nmcodeItem1
     */
    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    /**
     * @param nmcodeItem1 the nmcodeItem1 to set
     */
    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    /**
     * @return the selectTable
     */
    public String getSelectTable() {
        return selectTable;
    }

    /**
     * @param selectTable the selectTable to set
     */
    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }

    /**
     * @return the selectCd
     */
    public String getSelectCd() {
        return selectCd;
    }

    /**
     * @param selectCd the selectCd to set
     */
    public void setSelectCd(String selectCd) {
        this.selectCd = selectCd;
    }

    /**
     * @return the selectNm
     */
    public String getSelectNm() {
        return selectNm;
    }

    /**
     * @param selectNm the selectNm to set
     */
    public void setSelectNm(String selectNm) {
        this.selectNm = selectNm;
    }

    /**
     * @return the selectWhere
     */
    public String getSelectWhere() {
        return selectWhere;
    }

    /**
     * @param selectWhere the selectWhere to set
     */
    public void setSelectWhere(String selectWhere) {
        this.selectWhere = selectWhere;
    }

    /**
     * @return the procFg
     */
    public String getProcFg() {
        return procFg;
    }

    /**
     * @param procFg the procFg to set
     */
    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }
}
