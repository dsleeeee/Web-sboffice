package kr.co.solbipos.base.store.timeSlot.service;

import kr.co.solbipos.application.common.service.PageVO;

public class TimeSlotVO extends PageVO {

    private static final long serialVersionUID = 8050354827837735132L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 시작시각 */
    private String startTime;
    /** 종료시각 */
    private String endTime;
    /** 코드값 */
    private String nmcodeCd;
    /** 코드명 */
    private String nmcodeNm;
    /** 명칭코드항목1 */
    private String nmcodeItem1;

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

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNmcodeCd() {
        return nmcodeCd;
    }

    public void setNmcodeCd(String nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
    }

    public String getNmcodeNm() {
        return nmcodeNm;
    }

    public void setNmcodeNm(String nmcodeNm) {
        this.nmcodeNm = nmcodeNm;
    }

    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }
}
