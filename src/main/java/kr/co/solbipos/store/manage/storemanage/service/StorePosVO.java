package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StorePosVO.java
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
public class StorePosVO extends CmmVO {

    /** [매장코드] */
    private String storeCd;
    /** [포스번호] */
    private String posNo;
    /** [포스명] */
    private String posNm;
    /** [하드웨어인증키] */
    private String hwAuthKey;
    /** [포스버전번호] */
    private String posVerNo;
    /** [사용여부] 사용:Y 사용안함:N */
    private String useYn;
    /** [비고] */
    private String remark;
    /** [터미널번호] */
    private String vanTermnlNo;
    /** [인증여부] */
    private String vanCertYn;
    /** [밴사코드] */
    private String vanCd;
    /**  [밴사코드] */
    private String vanSerNo;


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
     * @return the posNo
     */
    public String getPosNo() {
        return posNo;
    }

    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }


    /**
     * @return the posNm
     */
    public String getPosNm() {
        return posNm;
    }

    /**
     * @param posNm the posNm to set
     */
    public void setPosNm(String posNm) {
        this.posNm = posNm;
    }


    /**
     * @return the hwAuthKey
     */
    public String getHwAuthKey() {
        return hwAuthKey;
    }

    /**
     * @param hwAuthKey the hwAuthKey to set
     */
    public void setHwAuthKey(String hwAuthKey) {
        this.hwAuthKey = hwAuthKey;
    }


    /**
     * @return the posVerNo
     */
    public String getPosVerNo() {
        return posVerNo;
    }

    /**
     * @param posVerNo the posVerNo to set
     */
    public void setPosVerNo(String posVerNo) {
        this.posVerNo = posVerNo;
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
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * @return the vanCertYn
     */
    public String getVanCertYn() {
        return vanCertYn;
    }

    /**
     * @param vanCertYn the vanCertYn to set
     */
    public void setVanCertYn(String vanCertYn) {
        this.vanCertYn = vanCertYn;
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
}
