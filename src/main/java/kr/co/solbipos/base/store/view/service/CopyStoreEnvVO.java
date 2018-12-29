package kr.co.solbipos.base.store.view.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : VanConfigVO.java
* @Description : 기초관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.13  김영근      최초생성
*
* @author nhn kcp 김영근
* @since 2018. 08.13
* @version 1.0
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class VanConfigVO extends PageVO {

    private static final long serialVersionUID = -1721149026984410203L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 코너코드 */
    private String cornrCd;
    /** 코너명 */
    private String cornrNm;
    /** 대표자명 */
    private String ownerNm;
    /** 사업자번호 */
    private String bizNo;
    /** 전화번호 */
    private String telNo;
    /** 밴사코드 */
    private String vanCd;
    /** 밴사명 */
    private String vannNm;
    /** 밴사터미널번호 */
    private String vanTermnlNo;
    /** 밴사일련번호 */
    private String vanSerNo;
    /** 사용여부 */
    private String useYn;
    /** 코너 사용여부*/
    private String cornerUseYn;

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
     * @return the cornrCd
     */

    public String getCornrCd() {
        return cornrCd;
    }

    /**
     * @param cornrCd the cornrCd to set
     */
    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    /**
     * @return the cornrNm
     */

    public String getCornrNm() {
        return cornrNm;
    }

    /**
     * @param cornrNm the cornrNm to set
     */
    public void setCornrNm(String cornrNm) {
        this.cornrNm = cornrNm;
    }

    /**
     * @return the ownerNm
     */

    public String getOwnerNm() {
        return ownerNm;
    }

    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    /**
     * @return the bizNo
     */

    public String getBizNo() {
        return bizNo;
    }

    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * @return the telNo
     */

    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @return the vanCd
     */

    public String getVanCd() {
        return vanCd;
    }

    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }

    /**
     * @return the vannNm
     */

    public String getVannNm() {
        return vannNm;
    }

    /**
     * @param vannNm the vannNm to set
     */
    public void setVannNm(String vannNm) {
        this.vannNm = vannNm;
    }

    /**
     * @return the vanTermnlNo
     */

    public String getVanTermnlNo() {
        return vanTermnlNo;
    }

    /**
     * @param vanTermnlNo the vanTermnlNo to set
     */
    public void setVanTermnlNo(String vanTermnlNo) {
        this.vanTermnlNo = vanTermnlNo;
    }

    /**
     * @return the vanSerNo
     */

    public String getVanSerNo() {
        return vanSerNo;
    }

    /**
     * @param vanSerNo the vanSerNo to set
     */
    public void setVanSerNo(String vanSerNo) {
        this.vanSerNo = vanSerNo;
    }

    /**
     * @return the useYn
     */

    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the cornerUseYn
     */

    public String getCornerUseYn() {
        return cornerUseYn;
    }

    /**
     * @param cornerUseYn the cornerUseYn to set
     */
    public void setCornerUseYn(String cornerUseYn) {
        this.cornerUseYn = cornerUseYn;
    }
}
