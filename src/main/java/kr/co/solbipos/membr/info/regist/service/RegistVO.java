package kr.co.solbipos.membr.info.regist.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.hibernate.validator.constraints.NotBlank;

public class RegistVO extends PageVO {

    private static final long serialVersionUID = 1L;
    /** 회원소속코드 */
    @NotBlank(groups = {RegistDelete.class}, message = "{regist.membr.org.cd}{cmm.not.find}")
    private String membrOrgnCd;
    /** 회원번호 */
    @NotBlank(groups = {Regist.class, RegistDelete.class}, message = "{regist.membr.no}{cmm.require.text}")
    private String membrNo;
    /** 회원명 */
    @NotBlank(groups = {Regist.class}, message = "{regist.membr.nm}{cmm.require.text}")
    private String membrNm;
    /** 회원닉네임 */
    private String membrNicknm;
    /** 회원분류코드 */
    private String membrClassCd;
    /** 회원카드번호 */
    @NotBlank(groups = {Regist.class}, message = "{regist.membr.card.no}{cmm.require.text}")
    private String membrCardNo;
    /** 등록매장코드 */
    @NotBlank(groups = {Regist.class}, message = "{regist.reg.store.cd}{cmm.require.text}")
    private String regStoreCd;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 주소상세 */
    private String addrDtl;
    /** 생일 */
    private String birthday;
    /** 음력여부 */
    private String lunarYn;
    /** 성별구분 */
    @NotBlank(groups = {Regist.class}, message = "{regist.gender}{cmm.require.text}")
    private String gendrFg;
    /** 이메일주소 */
    private String emailAddr;
    /** 단축번호 */
    private String shortNo;
    /** 전화번호 */
    @NotBlank(groups = {Regist.class}, message = "{regist.tel}{cmm.require.text}")
    private String telNo;
    /** 결혼여부 기혼:Y 미혼:N */
    @NotBlank(groups = {Regist.class}, message = "{regist.wedding}{cmm.require.text}")
    private String weddingYn;
    /** 결혼기념일 */
    private String weddingday;
    /** 이메일수신여부 */
    @NotBlank(groups = {Regist.class}, message = "{regist.email.recv}{cmm.require.text}")
    private String emailRecvYn;
    /** SMS수신여부 */
    @NotBlank(groups = {Regist.class}, message = "{regist.sms.recv}{cmm.require.text}")
    private String smsRecvYn;
    /** 사용여부 */
    @NotBlank(groups = {Regist.class}, message = "{cmm.useYn}{cmm.require.text}")
    private String useYn;
    /** 비고 */
    private String remark;
    /** 기념일 타입 */
    private AnvType anvType;
    /** 기념일 date start */
    private String anvStartDt;
    /** 기념일 date end */
    private String anvEndDt;
    /** 조회기간 타입 */
    private PeriodType periodType;
    /** 조회기간 타입 date start */
    private String periodStartDt;
    /** 조회기간 타입 date end */
    private String periodEndDt;

    public String getAnvStartDt() {
        return anvStartDt;
    }

    public void setAnvStartDt(String anvStartDt) {
        this.anvStartDt = anvStartDt;
    }

    public String getAnvEndDt() {
        return anvEndDt;
    }

    public void setAnvEndDt(String anvEndDt) {
        this.anvEndDt = anvEndDt;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStartDt() {
        return periodStartDt;
    }

    public void setPeriodStartDt(String periodStartDt) {
        this.periodStartDt = periodStartDt;
    }

    public String getPeriodEndDt() {
        return periodEndDt;
    }

    public void setPeriodEndDt(String periodEndDt) {
        this.periodEndDt = periodEndDt;
    }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    /**
     * @return
     */
    public String getMembrNo() {
        return membrNo;
    }

    /**
     * @param membrNo
     */
    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    /**
     * @return
     */
    public String getMembrNm() {
        return membrNm;
    }

    /**
     * @param membrNm
     */
    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    /**
     * @return
     */
    public String getMembrNicknm() {
        return membrNicknm;
    }

    /**
     * @param membrNicknm
     */
    public void setMembrNicknm(String membrNicknm) {
        this.membrNicknm = membrNicknm;
    }

    /**
     * @return
     */
    public String getMembrClassCd() {
        return membrClassCd;
    }

    /**
     * @param membrClassCd
     */
    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    /**
     * @return
     */
    public String getMembrCardNo() {
        return membrCardNo;
    }

    /**
     * @param membrCardNo
     */
    public void setMembrCardNo(String membrCardNo) {
        this.membrCardNo = membrCardNo;
    }

    /**
     * @return
     */
    public String getRegStoreCd() {
        return regStoreCd;
    }

    /**
     * @param regStoreCd
     */
    public void setRegStoreCd(String regStoreCd) {
        this.regStoreCd = regStoreCd;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLunarYn() {
        return lunarYn;
    }

    public void setLunarYn(String lunarYn) {
        this.lunarYn = lunarYn;
    }

    public String getGendrFg() {
        return gendrFg;
    }

    public void setGendrFg(String gendrFg) {
        this.gendrFg = gendrFg;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getShortNo() {
        return shortNo;
    }

    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getWeddingYn() {
        return weddingYn;
    }

    public void setWeddingYn(String weddingYn) {
        this.weddingYn = weddingYn;
    }

    public String getWeddingday() {
        return weddingday;
    }

    public void setWeddingday(String weddingday) {
        this.weddingday = weddingday;
    }

    public String getEmailRecvYn() {
        return emailRecvYn;
    }

    public void setEmailRecvYn(String emailRecvYn) {
        this.emailRecvYn = emailRecvYn;
    }

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AnvType getAnvType() {
        return anvType;
    }

    public void setAnvType(AnvType anvType) {
        this.anvType = anvType;
    }
}
