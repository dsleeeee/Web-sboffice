package kr.co.solbipos.store.manage.terminalManage.service;

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
public class StoreTerminalVO extends CmmVO {

    /** [매장코드] */
    private String storeCd;
    /** [포스번호] */
    private String posNo;
    /** [터미널번호] */
    private String cornrCd;
    /** [벤더구분] */
    private String vendorFg;
    /** [벤더코드] */
    private String vendorCd;
    /** [벤더명] */
    private String vendorNm;
    /** [벤더 터미널번호] */
    private String vendorTermnlNo;
    /** [벤더시리얼번호] */
    private String vendorSerNo;


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
     * @return the vendorFg
     */

    public String getVendorFg() {
        return vendorFg;
    }

    /**
     * @param vendorFg the vendorFg to set
     */
    public void setVendorFg(String vendorFg) {
        this.vendorFg = vendorFg;
    }

    /**
     * @return the vendorCd
     */

    public String getVendorCd() {
        return vendorCd;
    }

    /**
     * @param vendorCd the vendorCd to set
     */
    public void setVendorCd(String vendorCd) {
        this.vendorCd = vendorCd;
    }

    /**
     * @return the vendorNm
     */

    public String getVendorNm() {
        return vendorNm;
    }

    /**
     * @param vendorNm the vendorNm to set
     */
    public void setVendorNm(String vendorNm) {
        this.vendorNm = vendorNm;
    }

    /**
     * @return the vendorTermnlNo
     */

    public String getVendorTermnlNo() {
        return vendorTermnlNo;
    }

    /**
     * @param vendorTermnlNo the vendorTermnlNo to set
     */
    public void setVendorTermnlNo(String vendorTermnlNo) {
        this.vendorTermnlNo = vendorTermnlNo;
    }

    /**
     * @return the vendorSerNo
     */

    public String getVendorSerNo() {
        return vendorSerNo;
    }

    /**
     * @param vendorSerNo the vendorSerNo to set
     */
    public void setVendorSerNo(String vendorSerNo) {
        this.vendorSerNo = vendorSerNo;
    }
}
