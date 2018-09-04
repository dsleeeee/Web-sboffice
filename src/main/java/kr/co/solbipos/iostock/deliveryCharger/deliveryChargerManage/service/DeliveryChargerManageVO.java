package kr.co.solbipos.iostock.deliveryCharger.deliveryChargerManage.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DeliveryChargerManageVO extends PageVO {

    private static final long serialVersionUID = 3050436407558452127L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 배송기사코드 */
    private String dlvrCd;
    /** 배송기사명 */
    private String dlvrNm;
    /** 차량번호 */
    private String carNo;
    /** 전화번호 */
    private String telNo;
    /** 휴대폰번호 */
    private String hpNo;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String regDt;
    /** 등록자 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정자 */
    private String modId;
    /** 창고코드 */
    private String storageCd;
    /** 창고코드 */
    private String storageNm;
    /** 그리드 공통 체크박스 */
    private Boolean gChk = false;

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
     * @return the dlvrCd
     */
    public String getDlvrCd() {
        return dlvrCd;
    }

    /**
     * @param dlvrCd the dlvrCd to set
     */
    public void setDlvrCd(String dlvrCd) {
        this.dlvrCd = dlvrCd;
    }

    /**
     * @return the dlvrNm
     */
    public String getDlvrNm() {
        return dlvrNm;
    }

    /**
     * @param dlvrNm the dlvrNm to set
     */
    public void setDlvrNm(String dlvrNm) {
        this.dlvrNm = dlvrNm;
    }

    /**
     * @return the carNo
     */
    public String getCarNo() {
        return carNo;
    }

    /**
     * @param carNo the carNo to set
     */
    public void setCarNo(String carNo) {
        this.carNo = carNo;
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
     * @return the hpNo
     */
    public String getHpNo() {
        return hpNo;
    }

    /**
     * @param hpNo the hpNo to set
     */
    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
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
     * @return the regDt
     */
    @Override public String getRegDt() {
        return regDt;
    }

    /**
     * @param regDt the regDt to set
     */
    @Override public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    /**
     * @return the regId
     */
    @Override public String getRegId() {
        return regId;
    }

    /**
     * @param regId the regId to set
     */
    @Override public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return the modDt
     */
    @Override public String getModDt() {
        return modDt;
    }

    /**
     * @param modDt the modDt to set
     */
    @Override public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    /**
     * @return the modId
     */
    @Override public String getModId() {
        return modId;
    }

    /**
     * @param modId the modId to set
     */
    @Override public void setModId(String modId) {
        this.modId = modId;
    }

    /**
     * @return the storageCd
     */
    public String getStorageCd() {
        return storageCd;
    }

    /**
     * @param storageCd the storageCd to set
     */
    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    /**
     * @return the storageNm
     */
    public String getStorageNm() {
        return storageNm;
    }

    /**
     * @param storageNm the storageNm to set
     */
    public void setStorageNm(String storageNm) {
        this.storageNm = storageNm;
    }

    /**
     * @return the gChk
     */
    @Override public Boolean getgChk() {
        return gChk;
    }

    /**
     * @param gChk the gChk to set
     */
    @Override public void setgChk(Boolean gChk) {
        this.gChk = gChk;
    }
}
