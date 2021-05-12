package kr.co.solbipos.pos.confg.verAddr.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;

/**
* @Class Name : AddrApplcStoreVO.java
* @Description : 포스관리 > POS 설정관리 > 주소 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2021.05.10  권지현      최초생성
*
* @author 솔비포스 개발본부 WEB개발팀 권지현
* @since 2021.05.10
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class AddrApplcStoreVO extends PageVO {

    private static final long serialVersionUID = -8752600684230631461L;
    /** 버전일련번호 */
    private String verSerNo;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 매장명 */
    private String storeNm;
    /** 버전수신구분 */
    private VerRecvFg verRecvFg;
    /** 버전수신일시 */
    private String verRecvDt;
    /** 용도 */
    private String clsFg;
    /** 상태 */
    private String sysStatFg;
    /** van 코드 */
    private String vanCd;
    /** POS번호 */
    private String posNo;
    /** 포스명  */
    private String posNm;
    /** 포스IP  */
    private String posIp;
    /** 최종버전 */
    private String lastVer;
    /** 등록여부 */
    private String searchSatus;
    /** 프로시져 결과 */
    private String result;
    /** 복수검색여부 */
    private String chkMulti;


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

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
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
     * @return the verRecvFg
     */
    public VerRecvFg getVerRecvFg() {
        return verRecvFg;
    }
    /**
     * @param verRecvFg the verRecvFg to set
     */
    public void setVerRecvFg(VerRecvFg verRecvFg) {
        this.verRecvFg = verRecvFg;
    }
    /**
     * @return the verRecvDt
     */
    public String getVerRecvDt() {
        return verRecvDt;
    }
    /**
     * @param verRecvDt the verRecvDt to set
     */
    public void setVerRecvDt(String verRecvDt) {
        this.verRecvDt = verRecvDt;
    }
    /**
     * @return the clsFg
     */
    public String getClsFg() {
        return clsFg;
    }
    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }
    /**
     * @return the sysStatFg
     */
    public String getSysStatFg() {
        return sysStatFg;
    }
    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
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
     * @return the posIp
     */
    public String getPosIp() {
        return posIp;
    }
    /**
     * @param posIp the posIp to set
     */
    public void setPosIp(String posIp) {
        this.posIp = posIp;
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
     * @return the searchSatus
     */

    public String getSearchSatus() {
        return searchSatus;
    }

    /**
     * @param searchSatus the searchSatus to set
     */
    public void setSearchSatus(String searchSatus) {
        this.searchSatus = searchSatus;
    }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    public String getChkMulti() {
        return chkMulti;
    }

    public void setChkMulti(String chkMulti) {
        this.chkMulti = chkMulti;
    }
}
