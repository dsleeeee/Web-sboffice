package kr.co.solbipos.excclc.excclc.dailyTable.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DailyTableVO extends PageVO {

    private static final long serialVersionUID = -6606377306049900821L;

    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 Array */
    private String arrStoreCd[];
    /** 매장코드 */
    private String storeCd;

    /** 회원소속코드 */
    private String membrOrgnCd;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }
}
