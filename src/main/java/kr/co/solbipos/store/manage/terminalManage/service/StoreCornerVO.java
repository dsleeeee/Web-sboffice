package kr.co.solbipos.store.manage.terminalManage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreCornerVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.30  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.30
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreCornerVO extends CmmVO {

    /** [매장코드] */
    private String storeCd;
    /** [포스번호] */
    private String cornrCd;
    /** [포스명] */
    private String cornrNm;
    /** [포스명] */
    private String ownerNm;
    /** [포스명] */
    private String bizNo;
    /** [포스명] */
    private String telNo;
    /** [밴사코드] */
    private String vanCd;
    /** [터미널번호] */
    private String vanTermnlNo;
    /**  [밴사t시리얼번호] */
    private String vanSerNo;
    /** [사용여부] 사용:Y 사용안함:N */
    private String useYn;

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
}
