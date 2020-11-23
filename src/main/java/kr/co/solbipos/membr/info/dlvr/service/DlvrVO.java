package kr.co.solbipos.membr.info.dlvr.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

public class DlvrVO extends PageVO {
    private OrgnFg orgnFg;
    private String membrOrgnCd;
    private String membrNo;
    private String membrNm;
    private String membrClassCd;
    private Integer dlvrAddrSeq;
    private String dlvrStoreCd;
    private String dlvrLzoneCd;
    private String dlvrMzoneCd;
    private String addr;
    private String addrDtl;
    private String lastDlvrDate;
    private Integer totDlvrCnt;
    private String useYn;
    private Integer dlvrTelSeq;
    private String telNo;
    private String shortNo;
    private String regStoreCd;
    private String storeCd;

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getMembrNo() {
        return membrNo;
    }

    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    public String getMembrNm() {
        return membrNm;
    }

    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    public String getMembrClassCd() {
        return membrClassCd;
    }

    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    public Integer getDlvrAddrSeq() {
        return dlvrAddrSeq;
    }

    public void setDlvrAddrSeq(Integer dlvrAddrSeq) {
        this.dlvrAddrSeq = dlvrAddrSeq;
    }

    public String getDlvrStoreCd() {
        return dlvrStoreCd;
    }

    public void setDlvrStoreCd(String dlvrStoreCd) {
        this.dlvrStoreCd = dlvrStoreCd;
    }

    public String getDlvrLzoneCd() {
        return dlvrLzoneCd;
    }

    public void setDlvrLzoneCd(String dlvrLzoneCd) {
        this.dlvrLzoneCd = dlvrLzoneCd;
    }

    public String getDlvrMzoneCd() {
        return dlvrMzoneCd;
    }

    public void setDlvrMzoneCd(String dlvrMzoneCd) {
        this.dlvrMzoneCd = dlvrMzoneCd;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDtl() {
        return addrDtl;
    }

    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }

    public String getLastDlvrDate() {
        return lastDlvrDate;
    }

    public void setLastDlvrDate(String lastDlvrDate) {
        this.lastDlvrDate = lastDlvrDate;
    }

    public Integer getTotDlvrCnt() {
        return totDlvrCnt;
    }

    public void setTotDlvrCnt(Integer totDlvrCnt) {
        this.totDlvrCnt = totDlvrCnt;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public Integer getDlvrTelSeq() {
        return dlvrTelSeq;
    }

    public void setDlvrTelSeq(Integer dlvrTelSeq) {
        this.dlvrTelSeq = dlvrTelSeq;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getShortNo() {
        return shortNo;
    }

    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }

    public String getRegStoreCd() {
        return regStoreCd;
    }

    public void setRegStoreCd(String regStoreCd) {
        this.regStoreCd = regStoreCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
}
