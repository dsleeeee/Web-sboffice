package kr.co.solbipos.pos.loginstatus.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : LoginStatusVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 로그인현황
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
public class LoginStatusVO extends PageVO {

    private static final long serialVersionUID = 8968621419105878331L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** POS 번호 */
    private String posNo;
    /** 영업일자 */
    private String loginDate;
    /** 디바이스 번호 */
    private String hwAuthKey;
    /** 로그인 IP */
    private String loginIp;
    /** 로그인 일시 */
    private String loginDt;
    /** POS 현재 버전 */
    private String posVerNo;
    /** 매장 상태 */
    private SysStatFg sysStatFg;
    /** 매장 상태 이름 */
    private String sysStatFgNm;
    /** 로그인 순서 */
    private String loginSeq;
    /** 전체 기간 체크 */
    private boolean chkDt;

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
     * @return the loginDate
     */
    public String getLoginDate() {
        return loginDate;
    }
    /**
     * @param loginDate the loginDate to set
     */
    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
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
     * @return the loginIp
     */
    public String getLoginIp() {
        return loginIp;
    }
    /**
     * @param loginIp the loginIp to set
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    /**
     * @return the loginDt
     */
    public String getLoginDt() {
        return loginDt;
    }
    /**
     * @param loginDt the loginDt to set
     */
    public void setLoginDt(String loginDt) {
        this.loginDt = loginDt;
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
     * @return the sysStatFg
     */
    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }
    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }
    /**
     * @return the sysStatFgNm
     */
    public String getSysStatFgNm() {
        return sysStatFgNm;
    }
    /**
     * @param sysStatFgNm the sysStatFgNm to set
     */
    public void setSysStatFgNm(String sysStatFgNm) {
        this.sysStatFgNm = sysStatFgNm;
    }
    /**
     * @return the loginSeq
     */
    public String getLoginSeq() {
        return loginSeq;
    }
    /**
     * @param loginSeq the loginSeq to set
     */
    public void setLoginSeq(String loginSeq) {
        this.loginSeq = loginSeq;
    }

    /**
     * @return the chkDt
     */

    public boolean isChkDt() {
        return chkDt;
    }

    /**
     * @param chkDt the chkDt to set
     */
    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }
}
