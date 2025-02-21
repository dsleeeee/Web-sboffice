package kr.co.solbipos.base.store.empTalk.service;

import kr.co.solbipos.application.common.service.PageVO;

public class EmpTalkVO extends PageVO {

    private static final long serialVersionUID = 3962591897640186247L;

    /** 구분 */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 직원대화 코드 */
    private String empTextNo;

    /** 직원대화 상용구 */
    private String empTextInfo;

    /** 사용여부 */
    private String useYn;

    /** 등록구분 */
    private String regFg;

    /** 표시순서 */
    private  String dispSeq;

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

    public String getEmpTextNo() {
        return empTextNo;
    }

    public void setEmpTextNo(String empTextNo) {
        this.empTextNo = empTextNo;
    }

    public String getEmpTextInfo() {
        return empTextInfo;
    }

    public void setEmpTextInfo(String empTextInfo) {
        this.empTextInfo = empTextInfo;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }
}
