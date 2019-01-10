package kr.co.solbipos.pos.confg.verrecv.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
* @Class Name : VerRecvVO.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 수신현황
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class VerRecvVO extends PageVO {

    private static final long serialVersionUID = -6089897008439296961L;
    /** 버전일련번호 */
    private String verSerNo;
    /** 버전명 */
    private String verSerNm;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 최종버전 */
    private String lastVer;
    /** 사용여부 */
    private String useYn;
    /** 수신여부 */
    private String verRecvYn;

    /**
     * @return the verSerNo
     */
    public String getVerSerNo() {
        return verSerNo;
    }
    /**
     * @param verSerNo the verSerNo to set
     */
    public void setVerSerNo(String verSerNo) {
        this.verSerNo = verSerNo;
    }
    /**
     * @return the verSerNm
     */
    public String getVerSerNm() {
        return verSerNm;
    }
    /**
     * @param verSerNm the verSerNm to set
     */
    public void setVerSerNm(String verSerNm) {
        this.verSerNm = verSerNm;
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
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }
    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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
     * @return the lastVer
     */
    public String getLastVer() {
        return lastVer;
    }
    /**
     * @param lastVer the lastVer to set
     */
    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
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
     * @return the verRecvYn
     */

    public String getVerRecvYn() {
        return verRecvYn;
    }

    /**
     * @param verRecvYn the verRecvYn to set
     */
    public void setVerRecvYn(String verRecvYn) {
        this.verRecvYn = verRecvYn;
    }
}
