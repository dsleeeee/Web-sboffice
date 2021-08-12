package kr.co.solbipos.adi.sms.marketingSmsSend.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;

/**
 * @Class Name : MarketingSmsSendVO.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MarketingSmsSendVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 소속코드 */
    private String orgnCd;

    /**
     * 소속 그룹 코드
     * 프렌차이즈인 경우 본사코드, 단독매장인 경우 자기자신의 매장코드, 시스템 or 대리점인 경우 AGENCY_CD
     */
    private String orgnGrpCd;

    /** 매장코드 */
    private String storeCd;

    /** 회원번호 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    /** 회원영문명 */
    private String membrEngNm;

    /** 광고성 SMS전송 */
    private String marketingSmsGubun;

    /** 우리매장 방문회원 */
    private String visitStoreMembr;

    /** 우리매장 등록회원 */
    private String storeMembr;

    /** 고객카드상태구분*/
    private String cstCardUseFg;

    /** 회원카드번호 */
    private String membrCardNo;

    /** 전화번호 */
    private String telNo;

    /** 이메일주소 */
    private String emailAddr;

    /** 사용매장(여러건) */
    private String regUseStoreCd;
    private String regUseStoreCds[];

    /** 등록매장코드(여러건) */
    private String regStoreCd;
    private String regStoreCds[];

    /** 회원분류코드 */
    private String membrClassCd;

    /** 성별 */
    private String gendrFg;

    /** 이메일수신여부 */
    private UseYn emailRecvYn;

    /** SMS수신여부 */
    private UseYn smsRecvYn;

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

    /** 적립매출 start */
    private String startSaveSale;
    /** 적립매출 end */
    private String endSaveSale;

    /** 가용포인트 start */
    private String startAvablPoint;
    /** 가용포인트 end */
    private String endAvablPoint;

    /** 적립구매출횟수, 적립매출금액 콤보박스 */
    private String memberSaleFg;

    /** 가용포인트 */
    private String memberPointFg;

    /** 단축번호 */
    private String shortNo;

    /** 결혼여부 기혼:Y 미혼:N */
    private WeddingYn weddingYn;

    /** 신규회원 */
    private boolean newMemberYn;

    /** 주소 */
    private String addr;

    /** 사용여부 */
    private String useYn;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getOrgnGrpCd() { return orgnGrpCd; }

    public void setOrgnGrpCd(String orgnGrpCd) { this.orgnGrpCd = orgnGrpCd; }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
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

    public String getMembrEngNm() {
        return membrEngNm;
    }

    public void setMembrEngNm(String membrEngNm) {
        this.membrEngNm = membrEngNm;
    }

    public String getMarketingSmsGubun() {
        return marketingSmsGubun;
    }

    public void setMarketingSmsGubun(String marketingSmsGubun) {
        this.marketingSmsGubun = marketingSmsGubun;
    }

    public String getVisitStoreMembr() {
        return visitStoreMembr;
    }

    public void setVisitStoreMembr(String visitStoreMembr) {
        this.visitStoreMembr = visitStoreMembr;
    }

    public String getStoreMembr() {
        return storeMembr;
    }

    public void setStoreMembr(String storeMembr) {
        this.storeMembr = storeMembr;
    }

    public String getCstCardUseFg() { return cstCardUseFg; }

    public void setCstCardUseFg(String cstCardUseFg) {
        this.cstCardUseFg = cstCardUseFg;
    }

    public String getMembrCardNo() {
        return membrCardNo;
    }

    public void setMembrCardNo(String membrCardNo) {
        this.membrCardNo = membrCardNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
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

    public String getMembrClassCd() {
        return membrClassCd;
    }

    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    public String getGendrFg() {
        return gendrFg;
    }

    public void setGendrFg(String gendrFg) {
        this.gendrFg = gendrFg;
    }

    public UseYn getEmailRecvYn() {
        return emailRecvYn;
    }

    public void setEmailRecvYn(UseYn emailRecvYn) {
        this.emailRecvYn = emailRecvYn;
    }

    public UseYn getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(UseYn smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
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

    public void setAnvStartDate(String anvStartDate) { this.anvStartDate = anvStartDate; }

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

    public String getMemberSaleFg() {
        return memberSaleFg;
    }

    public void setMemberSaleFg(String memberSaleFg) {
        this.memberSaleFg = memberSaleFg;
    }

    public String getMemberPointFg() {
        return memberPointFg;
    }

    public void setMemberPointFg(String memberPointFg) {
        this.memberPointFg = memberPointFg;
    }

    public String getShortNo() {
        return shortNo;
    }

    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }

    public WeddingYn getWeddingYn() {
        return weddingYn;
    }

    public void setWeddingYn(WeddingYn weddingYn) {
        this.weddingYn = weddingYn;
    }

    public boolean isNewMemberYn() {
        return newMemberYn;
    }

    public void setNewMemberYn(boolean newMemberYn) {
        this.newMemberYn = newMemberYn;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }
}