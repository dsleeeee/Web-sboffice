package kr.co.solbipos.membr.info.chgBatch.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.hibernate.validator.constraints.NotBlank;

public class ChgBatchVO extends PageVO {

    /**
     * 등록 구분
     */
    private OrgnFg orgnFg;
    /**
     * 본사코드
     */
    private String hqOfficeCd;
    /**
     * 매장코드
     */
    private String storeCd;
    /**
     * 회원소속코드
     */
    @NotBlank(groups = {RegistDelete.class}, message = "{regist.membr.org.cd}{cmm.not.find}")
    private String membrOrgnCd;
    /**
     * 회원번호
     */
    private String membrNo;
    /**
     * 회원명
     */
    @NotBlank(groups = {Regist.class}, message = "{regist.membr.nm}{cmm.require.text}")
    private String membrNm;
    /**
     * 회원닉네임
     */
    private String membrNicknm;
    /**
     * 회원분류코드
     */
    private String membrClassCd;
    /**
     * 회원카드번호
     */
    private String membrCardNo;
    /**
     * 등록매장코드
     */
    @NotBlank(groups = {Regist.class}, message = "{regist.reg.store.cd}{cmm.require.text}")
    private String regStoreCd;
    /** 등록매장코드 */
    /**
     * 등록매장코드(여러건)
     */
    private String regStoreCds[];
    /**
     * 우편번호
     */
    private String postNo;
    /**
     * 주소
     */
    private String addr;
    /**
     * 주소상세
     */
    private String addrDtl;
    /**
     * 생일
     */
    private String birthday;
    /**
     * 음력여부
     */
    private String lunarYn;
    /**
     * 성별구분
     */
    private String gendrFg;
    /**
     * 이메일주소
     */
    private String emailAddr;
    /**
     * 단축번호
     */
    private String shortNo;
    /**
     * 전화번호
     */
    private String telNo;
    /**
     * 결혼여부
     */
    private String weddingYn;
    /**
     * 결혼기념일
     */
    private String weddingday;
    /**
     * 이메일수신여부
     */
    private String emailRecvYn;
    /**
     * SMS수신여부
     */
    private String smsRecvYn;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * 비고
     */
    private String remark;
    /**
     * 회원영문명
     */
    private String memberEngNm;
    /**
     * 고객카드사용구분
     */
    private String cstCardUseFg;
    /**
     * 고객카드발급횟수
     */
    private Integer cstCardIssCnt;
    /**
     * 최초고객카드번호
     */
    private String orgCstCardNo;
    /** 기념일 타입 */
    private AnvType anvType;
    /** 기념일 date start */
    private String anvStartDate;
    /** 기념일 date end */
    private String anvEndDate;
    /** 조회기간 타입 */
    private PeriodType periodType;
    /** 조회기간 타입 date start */
    private String periodStartDate;
    /** 조회기간 타입 date end */
    private String periodEndDate;
    /** 후불회원 적용매장코드 */
    private String postpaidStoreCds;
    /** 회원 거래처 매핑코드 (회사코드)*/
    private String cdCompany;
    /** 회원 거래처 매핑코드 (거래처코드)*/
    private String cdPartner;
    /** 적립매출 start */
    private String startSaveSale;
    /** 적립매출 end */
    private String endSaveSale;
    /** 가용포인트 start */
    private String startAvablPoint;
    /** 가용포인트 end */
    private String endAvablPoint;
    /** 핸드폰번호*/
    private String phoneNo;
    /** 적립포인트*/
    private String totSavePoint;
    /** 사용포인트*/
    private String totUsePoint;
    /** 조정포인트*/
    private String avablPoint;
    /** 가용포인트*/
    private String totAdjPoint;
    /** 최초방문일*/
    private String firstSaleDate;
    /** 최종방문일*/
    private String lastSaleDate;

    /** 우리매장 방문회원 */
    private String visitStoreMembr;

    /** 우리매장 등록회원 */
    private String storeMembr;

    /** 사용매장(여러건) */
    private String regUseStoreCd;
    private String regUseStoreCds[];

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(OrgnFg orgnFg) {
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

    public String getMembrNicknm() {
        return membrNicknm;
    }

    public void setMembrNicknm(String membrNicknm) {
        this.membrNicknm = membrNicknm;
    }

    public String getMembrClassCd() {
        return membrClassCd;
    }

    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    public String getMembrCardNo() {
        return membrCardNo;
    }

    public void setMembrCardNo(String membrCardNo) {
        this.membrCardNo = membrCardNo;
    }

    public String getRegStoreCd() {
        return regStoreCd;
    }

    public void setRegStoreCd(String regStoreCd) {
        this.regStoreCd = regStoreCd;
    }

    public String[] getRegStoreCds() {
        return regStoreCds;
    }

    public void setRegStoreCds(String[] regStoreCds) {
        this.regStoreCds = regStoreCds;
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

    public String getMemberEngNm() {
        return memberEngNm;
    }

    public void setMemberEngNm(String memberEngNm) {
        this.memberEngNm = memberEngNm;
    }

    public String getCstCardUseFg() {
        return cstCardUseFg;
    }

    public void setCstCardUseFg(String cstCardUseFg) {
        this.cstCardUseFg = cstCardUseFg;
    }

    public Integer getCstCardIssCnt() {
        return cstCardIssCnt;
    }

    public void setCstCardIssCnt(Integer cstCardIssCnt) {
        this.cstCardIssCnt = cstCardIssCnt;
    }

    public String getOrgCstCardNo() {
        return orgCstCardNo;
    }

    public void setOrgCstCardNo(String orgCstCardNo) {
        this.orgCstCardNo = orgCstCardNo;
    }

    public AnvType getAnvType() {
        return anvType;
    }

    public void setAnvType(AnvType anvType) {
        this.anvType = anvType;
    }

    public String getAnvStartDate() {
        return anvStartDate;
    }

    public void setAnvStartDate(String anvStartDate) {
        this.anvStartDate = anvStartDate;
    }

    public String getAnvEndDate() {
        return anvEndDate;
    }

    public void setAnvEndDate(String anvEndDate) {
        this.anvEndDate = anvEndDate;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(String periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public String getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(String periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public String getPostpaidStoreCds() {
        return postpaidStoreCds;
    }

    public void setPostpaidStoreCds(String postpaidStoreCds) {
        this.postpaidStoreCds = postpaidStoreCds;
    }

    public String getCdCompany() {
        return cdCompany;
    }

    public void setCdCompany(String cdCompany) {
        this.cdCompany = cdCompany;
    }

    public String getCdPartner() {
        return cdPartner;
    }

    public void setCdPartner(String cdPartner) {
        this.cdPartner = cdPartner;
    }

    public String getStartSaveSale() {
        return startSaveSale;
    }

    public void setStartSaveSale(String startSaveSale) {
        this.startSaveSale = startSaveSale;
    }

    public String getEndSaveSale() {
        return endSaveSale;
    }

    public void setEndSaveSale(String endSaveSale) {
        this.endSaveSale = endSaveSale;
    }

    public String getStartAvablPoint() {
        return startAvablPoint;
    }

    public void setStartAvablPoint(String startAvablPoint) {
        this.startAvablPoint = startAvablPoint;
    }

    public String getEndAvablPoint() {
        return endAvablPoint;
    }

    public void setEndAvablPoint(String endAvablPoint) {
        this.endAvablPoint = endAvablPoint;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getTotSavePoint() {
        return totSavePoint;
    }

    public void setTotSavePoint(String totSavePoint) {
        this.totSavePoint = totSavePoint;
    }

    public String getTotUsePoint() {
        return totUsePoint;
    }

    public void setTotUsePoint(String totUsePoint) {
        this.totUsePoint = totUsePoint;
    }

    public String getAvablPoint() {
        return avablPoint;
    }

    public void setAvablPoint(String avablPoint) {
        this.avablPoint = avablPoint;
    }

    public String getTotAdjPoint() {
        return totAdjPoint;
    }

    public void setTotAdjPoint(String totAdjPoint) {
        this.totAdjPoint = totAdjPoint;
    }

    public String getFirstSaleDate() {
        return firstSaleDate;
    }

    public void setFirstSaleDate(String firstSaleDate) {
        this.firstSaleDate = firstSaleDate;
    }

    public String getLastSaleDate() {
        return lastSaleDate;
    }

    public void setLastSaleDate(String lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }

    public String getVisitStoreMembr() {
        return visitStoreMembr;
    }

    public void setVisitStoreMembr(String visitStoreMembr) {
        this.visitStoreMembr = visitStoreMembr;
    }

    public String getStoreMembr() { return storeMembr; }

    public void setStoreMembr(String storeMembr) {
        this.storeMembr = storeMembr;
    }

    public String getRegUseStoreCd() {
        return regUseStoreCd;
    }

    public void setRegUseStoreCd(String regUseStoreCd) {
        this.regUseStoreCd = regUseStoreCd;
    }

    public String[] getRegUseStoreCds() {
        return regUseStoreCds;
    }

    public void setRegUseStoreCds(String[] regUseStoreCds) {
        this.regUseStoreCds = regUseStoreCds;
    }
}
