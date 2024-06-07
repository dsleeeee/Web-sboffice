package kr.co.solbipos.pos.confg.vermanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;

/**
* @Class Name : ApplcStoreVO.java
* @Description : 포스관리 > POS 설정관리 > POS 버전 관리
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class ApplcStoreVO extends PageVO {

    private static final long serialVersionUID = -8752600684230631461L;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private OrgnFg orgnFg;
    /** 대리점코드 */
    private String agencyCd;
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
    /** 포스 프로그램 구분 */
    private String progFg;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    /** 프로그램 상세구분 */
    private String progDetailFg;
    /** 주소 */
    private String addr;
    /** 예약일시 */
    private String resveDate;

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

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

    public String getProgFg() {
        return progFg;
    }

    public void setProgFg(String progFg) {
        this.progFg = progFg;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getProgDetailFg() {
        return progDetailFg;
    }

    public void setProgDetailFg(String progDetailFg) {
        this.progDetailFg = progDetailFg;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getResveDate() {
        return resveDate;
    }

    public void setResveDate(String resveDate) {
        this.resveDate = resveDate;
    }
}
