package kr.co.solbipos.application.common.service;

/**
 * @Class Name : StoreVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreVO extends CmmVO {

    private static final long serialVersionUID = 7641222002611869745L;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 대표자명 */
    private String ownerNm;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 디폴트 매장 */
    private String defaultStoreCd;

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
     * @return the defaultStoreCd
     */

    public String getDefaultStoreCd() {
        return defaultStoreCd;
    }

    /**
     * @param defaultStoreCd the defaultStoreCd to set
     */
    public void setDefaultStoreCd(String defaultStoreCd) {
        this.defaultStoreCd = defaultStoreCd;
    }
}
