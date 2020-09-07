package kr.co.solbipos.sys.admin.logSend.service;

import kr.co.solbipos.application.common.service.PageVO;

public class LogSendVO extends PageVO {

    private static final long serialVersionUID = 3457905152351659714L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 시스템상태구분 */
    private String sysStatFg;
    /** [POS-DB/로그 서버송신구분]:Y/N */
    private String dbSendYn;
    /** 포스 번호*/
    private String PosNo;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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

    public String getDbSendYn() {
        return dbSendYn;
    }

    public void setDbSendYn(String dbSendYn) {
        this.dbSendYn = dbSendYn;
    }

    public String getPosNo() {
        return PosNo;
    }

    public void setPosNo(String posNo) {
        PosNo = posNo;
    }
}
