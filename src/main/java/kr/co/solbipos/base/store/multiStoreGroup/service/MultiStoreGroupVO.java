package kr.co.solbipos.base.store.multiStoreGroup.service;

import kr.co.solbipos.application.common.service.PageVO;

public class MultiStoreGroupVO extends PageVO {

    private static final long serialVersionUID = 6066190488614060344L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 그룹코드 */
    private String multistoreCd;
    /** 그룹명 */
    private String multistoreNm;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 매장상태 */
    private String sysStatFg;


    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getMultistoreCd() {
        return multistoreCd;
    }

    public void setMultistoreCd(String multistoreCd) {
        this.multistoreCd = multistoreCd;
    }

    public String getMultistoreNm() {
        return multistoreNm;
    }

    public void setMultistoreNm(String multistoreNm) {
        this.multistoreNm = multistoreNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }
}
