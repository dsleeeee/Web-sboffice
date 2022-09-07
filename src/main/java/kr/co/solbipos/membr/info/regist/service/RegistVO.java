package kr.co.solbipos.membr.info.regist.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;

import java.util.Arrays;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @Class Name : RegistVO.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018.05.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RegistVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 등록 구분 */
    private OrgnFg orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 회원소속코드 */
    @NotBlank(groups = {RegistDelete.class}, message = "{regist.membr.org.cd}{cmm.not.find}")
    private String membrOrgnCd;
    /** 회원번호 */
    private String membrNo;
    /** 회원명 */
//    @NotBlank(groups = {Regist.class}, message = "{regist.membr.nm}{cmm.require.text}")
    private String membrNm;
    /** 회원영문명 */
    private String membrEngNm;
    /** 회원닉네임 */
    private String membrNicknm;
    /** 회원분류코드 */
    private String membrClassCd;
    /** 회원카드번호 */
    private String membrCardNo;
    /** 우리매장 등록회원 */
    private String storeMembr;
    /** 우리매장 방문회원 */
    private String visitStoreMembr;
    /** 등록매장코드 */
    @NotBlank(groups = {Regist.class}, message = "{regist.reg.store.cd}{cmm.require.text}")
    private String regStoreCd;
    /** 등록매장코드(여러건) */
    private String regStoreCds[];
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
//    @NotBlank(groups = {Regist.class}, message = "{regist.wedding}{cmm.require.text}")
    private WeddingYn weddingYn;
    /** 결혼기념일 */
    private String weddingday;
    /** 이메일수신여부 */
//    @NotBlank(groups = {Regist.class}, message = "{regist.email.recv}{cmm.require.text}")
    private UseYn emailRecvYn;
    /** SMS수신여부 */
//    @NotBlank(groups = {Regist.class}, message = "{regist.sms.recv}{cmm.require.text}")
    private UseYn smsRecvYn;
    /** 사용여부 */
    @NotBlank(groups = {Regist.class}, message = "{cmm.useYn}{cmm.require.text}")
    private String useYn;
    /** 비고 */
    private String remark;
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
    /** 가입일*/
    private String regDt;

    /** 고객카드상태구분*/
    private String cstCardUseFg;

    /** 고객카드상태구분*/
    private String cstCardStatFg;
    /** 이전고객카드번호*/
    private String oldCstCardNo;
    /** 고객카드발급구분*/
    private String cstCardIssFg;
    /** 발급비고*/
    private String issRemark;
    /** 발급소속코드*/
    private String issOrgnCd;
    /** 발급일자*/
    private String issDate;
    /** 발급일자*/
    private String dlvrLzoneCd;

    private String dlvrMzoneCd;
    private String lastDlvrDate;
    private Integer totDlvrCnt;
    private Integer dlvrAddrSeq;
    private Integer dlvrTelSeq;

    private String memberSaleFg;
    private String memberPointFg;
    private Integer chgPoint;
    private String chgDate;
    /** 프로시져 결과 */
    private String result;

    private String pointChgFg;
    private Integer movePoint;

    private String regUseStoreCd;
    private String regUseStoreCds[];
    private String storeNm;

    private boolean newMemberYn;

    /** 보내는 회원번호 */
    private String memberNoSend;

    /** 보내는 회원명 */
    private String memberNmSend;

    /** 보내는 가용포인트 */
    private int pointSend;

    /** 받는 회원번호 */
    private String memberNoReceive;

    /** 받는 회원명 */
    private String memberNmReceive;

    /** 받는 포인트 */
    private int pointReceive;

    /** 구분 */
    private String gubun;

    /** 구분 */
    private String gubunMemberNo;

    /** 포인트적립구분 */
    private String pointSaveFg;

    /** 변경순번 */
    private String chgSeq;

    /** 현금 적립률 */
    private String memberCash;

    /** 신용카드 적립률 */
    private String memberCard;

    /** 광고성 SMS전송 */
    private String marketingSmsGubun;

    /** 엑셀 다운로드 구분 */
    private String excelGubun;

    /** 강제삭제여부 */
    private String forcedDeleteYn;

    /** 회원번호 array */
    private String arrMembrNo[];

    /**
     * [회원분류 관리구분]
     */
    private String membrClassManageFg;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 회원구분 */
    private String memberFg;

    /** 회원-구분 */
    private String privateFg;

    /** 외상가능 */
    private String postpaidFg;

    /** 인원수 */
    private String peopleCnt;

    /** 단체구분 */
    private String groupFg;

    /** 단체 종류 구분 */
    private String groupTypeFg;

    /** 납부상태 구분 */
    private String paymentFg;

    /** 강사명 */
    private String teacherCd;

    /** 강습구분 */
    private String classFg;

    /** 스케이트종류 */
    private String skateFg;

    /** 가입일자 */
    private String registerDate;

    /** 사용기간 구분 */
    private String useDateFg;

    /** 시작일자 */
    private String useStartDate;

    /** 만료일자 */
    private String useEndDate;

    /** 사용요일 */
    private String useWeek;

    /** 상품명 */
    private String useProdNm;

    /** 매출금액 */
    private String useAmt;

    /** 영업비 */
    private String businessAmt;

    /** 강사비 */
    private String teacherAmt;

    /** 강사인원 */
    private String teacherCnt;

    /** 잔액 */
    private String useRemainAmt;

    /** 소송비 */
    private String transportationAmt;

    /** 소송수량 */
    private String transportationCnt;



    /**
     * @return the orgnFg
     */

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
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
     * @return the membrOrgnCd
     */

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    /**
     * @param membrOrgnCd the membrOrgnCd to set
     */
    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    /**
     * @return the membrNo
     */

    public String getMembrNo() {
        return membrNo;
    }

    /**
     * @param membrNo the membrNo to set
     */
    public void setMembrNo(String membrNo) {
        this.membrNo = membrNo;
    }

    /**
     * @return the membrNm
     */

    public String getMembrNm() {
        return membrNm;
    }

    /**
     * @param membrNm the membrNm to set
     */
    public void setMembrNm(String membrNm) {
        this.membrNm = membrNm;
    }

    /**
     * @return the membrEngNm
     */

    public String getMembrEngNm() {
        return membrEngNm;
    }

    /**
     * @param membrEngNm the membrEngNm to set
     */
    public void setMembrEngNm(String membrEngNm) {
        this.membrEngNm = membrEngNm;
    }

    /**
     * @return the membrNicknm
     */

    public String getMembrNicknm() {
        return membrNicknm;
    }

    /**
     * @param membrNicknm the membrNicknm to set
     */
    public void setMembrNicknm(String membrNicknm) {
        this.membrNicknm = membrNicknm;
    }

    /**
     * @return the membrClassCd
     */

    public String getMembrClassCd() {
        return membrClassCd;
    }

    /**
     * @param membrClassCd the membrClassCd to set
     */
    public void setMembrClassCd(String membrClassCd) {
        this.membrClassCd = membrClassCd;
    }

    /**
     * @return the membrCardNo
     */

    public String getMembrCardNo() {
        return membrCardNo;
    }

    /**
     * @param membrCardNo the membrCardNo to set
     */
    public void setMembrCardNo(String membrCardNo) {
        this.membrCardNo = membrCardNo;
    }

    public String getStoreMembr() {
        return storeMembr;
    }

    public void setStoreMembr(String storeMembr) {
        this.storeMembr = storeMembr;
    }

    public String getVisitStoreMembr() {
        return visitStoreMembr;
    }

    public void setVisitStoreMembr(String visitStoreMembr) {
        this.visitStoreMembr = visitStoreMembr;
    }

    /**
     * @return the regStoreCd
     */

    public String getRegStoreCd() {
        return regStoreCd;
    }

    /**
     * @param regStoreCd the regStoreCd to set
     */
    public void setRegStoreCd(String regStoreCd) {
        this.regStoreCd = regStoreCd;
    }

    /**
     * @return the regStoreCds
     */

    public String[] getRegStoreCds() {
        return regStoreCds;
    }

    /**
     * @param regStoreCds the regStoreCds to set
     */
    public void setRegStoreCds(String[] regStoreCds) {
        this.regStoreCds = regStoreCds;
    }

    /**
     * @return the postNo
     */

    public String getPostNo() {
        return postNo;
    }

    /**
     * @param postNo the postNo to set
     */
    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    /**
     * @return the addr
     */

    public String getAddr() {
        return addr;
    }

    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * @return the addrDtl
     */

    public String getAddrDtl() {
        return addrDtl;
    }

    /**
     * @param addrDtl the addrDtl to set
     */
    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }

    /**
     * @return the birthday
     */

    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the lunarYn
     */

    public String getLunarYn() {
        return lunarYn;
    }

    /**
     * @param lunarYn the lunarYn to set
     */
    public void setLunarYn(String lunarYn) {
        this.lunarYn = lunarYn;
    }

    /**
     * @return the gendrFg
     */

    public String getGendrFg() {
        return gendrFg;
    }

    /**
     * @param gendrFg the gendrFg to set
     */
    public void setGendrFg(String gendrFg) {
        this.gendrFg = gendrFg;
    }

    /**
     * @return the emailAddr
     */

    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * @param emailAddr the emailAddr to set
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    /**
     * @return the shortNo
     */

    public String getShortNo() {
        return shortNo;
    }

    /**
     * @param shortNo the shortNo to set
     */
    public void setShortNo(String shortNo) {
        this.shortNo = shortNo;
    }

    /**
     * @return the telNo
     */

    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }


    /**
     * @return the weddingYn
     */

    public WeddingYn getWeddingYn() {
        return weddingYn;
    }

    /**
     * @param weddingYn the weddingYn to set
     */
    public void setWeddingYn(WeddingYn weddingYn) {
        this.weddingYn = weddingYn;
    }

    public String getWeddingday() {
        return weddingday;
    }

    /**
     * @param weddingday the weddingday to set
     */
    public void setWeddingday(String weddingday) {
        this.weddingday = weddingday;
    }



    /**
     * @return the emailRecvYn
     */

    public UseYn getEmailRecvYn() {
        return emailRecvYn;
    }

    /**
     * @param emailRecvYn the emailRecvYn to set
     */
    public void setEmailRecvYn(UseYn emailRecvYn) {
        this.emailRecvYn = emailRecvYn;
    }

    /**
     * @return the smsRecvYn
     */

    public UseYn getSmsRecvYn() {
        return smsRecvYn;
    }

    /**
     * @param smsRecvYn the smsRecvYn to set
     */
    public void setSmsRecvYn(UseYn smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    /**
     * @return the useYn
     */

    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the remark
     */

    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the anvType
     */

    public AnvType getAnvType() {
        return anvType;
    }

    /**
     * @param anvType the anvType to set
     */
    public void setAnvType(AnvType anvType) {
        this.anvType = anvType;
    }

    /**
     * @return the anvStartDate
     */

    public String getAnvStartDate() {
        return anvStartDate;
    }

    /**
     * @param anvStartDate the anvStartDate to set
     */
    public void setAnvStartDate(String anvStartDate) {
        this.anvStartDate = anvStartDate;
    }

    /**
     * @return the anvEndDate
     */

    public String getAnvEndDate() {
        return anvEndDate;
    }

    /**
     * @param anvEndDate the anvEndDate to set
     */
    public void setAnvEndDate(String anvEndDate) {
        this.anvEndDate = anvEndDate;
    }

    /**
     * @return the periodType
     */

    public PeriodType getPeriodType() {
        return periodType;
    }

    /**
     * @param periodType the periodType to set
     */
    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    /**
     * @return the periodStartDate
     */

    public String getPeriodStartDate() {
        return periodStartDate;
    }

    /**
     * @param periodStartDate the periodStartDate to set
     */
    public void setPeriodStartDate(String periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    /**
     * @return the periodEndDate
     */

    public String getPeriodEndDate() {
        return periodEndDate;
    }

    /**
     * @param periodEndDate the periodEndDate to set
     */
    public void setPeriodEndDate(String periodEndDate) {
        this.periodEndDate = periodEndDate;
    }


    /**
     * @return the postpaidStoreCds
     */

    public String getPostpaidStoreCds() {
        return postpaidStoreCds;
    }

    /**
     * @param postpaidStoreCds the postpaidStoreCds to set
     */
    public void setPostpaidStoreCds(String postpaidStoreCds) {
        this.postpaidStoreCds = postpaidStoreCds;
    }

    /**
     * @return the cdCompany
     */

    public String getCdCompany() {
        return cdCompany;
    }

    /**
     * @param cdCompany the cdCompany to set
     */
    public void setCdCompany(String cdCompany) {
        this.cdCompany = cdCompany;
    }

    /**
     * @return the cdPartner
     */

    public String getCdPartner() {
        return cdPartner;
    }

    /**
     * @param cdPartner the cdPartner to set
     */
    public void setCdPartner(String cdPartner) {
        this.cdPartner = cdPartner;
    }

    /**
     * @return the startSaveSale
     */
    public String getStartSaveSale() {
        return startSaveSale;
    }

    /**
     * @param startSaveSale the startSaveSale to set
     */
    public void setStartSaveSale(String startSaveSale) {
        this.startSaveSale = startSaveSale;
    }

    /**
     * @return the endSaveSale
     */
    public String getEndSaveSale() {
        return endSaveSale;
    }

    /**
     * @param endSaveSale the endSaveSale to set
     */
    public void setEndSaveSale(String endSaveSale) {
        this.endSaveSale = endSaveSale;
    }

    /**
     * @return the startAvablPoint
     */
    public String getStartAvablPoint() {
        return startAvablPoint;
    }

    /**
     * @param startAvablPoint the startAvablPoint to set
     */
    public void setStartAvablPoint(String startAvablPoint) {
        this.startAvablPoint = startAvablPoint;
    }

    /**
     * @return the endAvablPoint
     */
    public String getEndAvablPoint() {
        return endAvablPoint;
    }

    /**
     * @param endAvablPoint the endAvablPoint to set
     */
    public void setEndAvablPoint(String endAvablPoint) {
        this.endAvablPoint = endAvablPoint;
    }

    /**
     * @return the getPhoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the totSavePoint
     */
    public String getTotSavePoint() {
        return totSavePoint;
    }
    /**
     * @param totSavePoint the totSavePoint to set
     */
    public void setTotSavePoint(String totSavePoint) {
        this.totSavePoint = totSavePoint;
    }

    /**
     * @return the totUsePoint
     */
    public String getTotUsePoint() {
        return totUsePoint;
    }

    /**
     * @param totUsePoint the totUsePoint to set
     */
    public void setTotUsePoint(String totUsePoint) {
        this.totUsePoint = totUsePoint;
    }

    /**
     * @return the avablPoint
     */
    public String getAvablPoint() {
        return avablPoint;
    }

    /**
     * @param avablPoint the avablPoint to set
     */
    public void setAvablPoint(String avablPoint) {
        this.avablPoint = avablPoint;
    }

    /**
     * @return the cdtotAdjPointPartner
     */
    public String getTotAdjPoint() {
        return totAdjPoint;
    }

    /**
     * @param totAdjPoint the totAdjPoint to set
     */
    public void setTotAdjPoint(String totAdjPoint) {
        this.totAdjPoint = totAdjPoint;
    }

    /**
     * @return the firstSaleDate
     */
    public String getFirstSaleDate() {
        return firstSaleDate;
    }

    /**
     * @param firstSaleDate the firstSaleDate to set
     */
    public void setFirstSaleDate(String firstSaleDate) {
        this.firstSaleDate = firstSaleDate;
    }

    /**
     * @return the lastSaleDate
     */
    public String getLastSaleDate() {
        return lastSaleDate;
    }

    /**
     * @param lastSaleDate the lastSaleDate to set
     */
    public void setLastSaleDate(String lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }

    /**
     * @return the regDt
     */
    public String getRegDt() {
        return regDt;
    }

    /**
     * @param regDt the regDt to set
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getCstCardUseFg() {
        return cstCardUseFg;
    }

    public void setCstCardUseFg(String cstCardUseFg) {
        this.cstCardUseFg = cstCardUseFg;
    }

    /**
     * @return the cstCardStatFg
     */
    public String getCstCardStatFg() {
        return cstCardStatFg;
    }

    /**
     * @param cstCardStatFg the cstCardStatFg to set
     */
    public void setCstCardStatFg(String cstCardStatFg) {
        this.cstCardStatFg = cstCardStatFg;
    }

    /**
     * @return the oldCstCardNo
     */
    public String getOldCstCardNo() {
        return oldCstCardNo;
    }

    /**
     * @param oldCstCardNo the oldCstCardNo to set
     */
    public void setOldCstCardNo(String oldCstCardNo) {
        this.oldCstCardNo = oldCstCardNo;
    }

    /**
     * @return the cstCardIssFg
     */
    public String getCstCardIssFg() {
        return cstCardIssFg;
    }

    /**
     * @param cstCardIssFg the cstCardIssFg to set
     */
    public void setCstCardIssFg(String cstCardIssFg) {
        this.cstCardIssFg = cstCardIssFg;
    }

    /**
     * @return the issRemark
     */
    public String getIssRemark() {
        return issRemark;
    }

    /**
     * @param issRemark the issRemark to set
     */
    public void setIssRemark(String issRemark) {
        this.issRemark = issRemark;
    }

    /**
     * @return the issOrgnCd
     */
    public String getIssOrgnCd() {
        return issOrgnCd;
    }

    /**
     * @param issOrgnCd the issOrgnCd to set
     */
    public void setIssOrgnCd(String issOrgnCd) {
        this.issOrgnCd = issOrgnCd;
    }

    /**
     * @return the issDate
     */
    public String getIssDate() {
        return issDate;
    }

    /**
     * @param issDate the issDate to set
     */
    public void setIssDate(String issDate) {
        this.issDate = issDate;
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

    public Integer getDlvrAddrSeq() {
        return dlvrAddrSeq;
    }

    public void setDlvrAddrSeq(Integer dlvrAddrSeq) {
        this.dlvrAddrSeq = dlvrAddrSeq;
    }

    public Integer getDlvrTelSeq() {
        return dlvrTelSeq;
    }

    public void setDlvrTelSeq(Integer dlvrTelSeq) {
        this.dlvrTelSeq = dlvrTelSeq;
    }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

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

    public Integer getChgPoint() {
        return chgPoint;
    }

    public void setChgPoint(Integer chgPoint) {
        this.chgPoint = chgPoint;
    }

    public String getChgDate() {
        return chgDate;
    }

    public void setChgDate(String chgDate) {
        this.chgDate = chgDate;
    }

    public String getPointChgFg() { return pointChgFg; }

    public void setPointChgFg(String pointChgFg) { this.pointChgFg = pointChgFg; }

    public Integer getMovePoint() { return movePoint; }

    public void setMovePoint(Integer movePoint) { this.movePoint = movePoint; }

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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public boolean isNewMemberYn() {
        return newMemberYn;
    }

    public void setNewMemberYn(boolean newMemberYn) {
        this.newMemberYn = newMemberYn;
    }

    public String getMemberNoSend() {
        return memberNoSend;
    }

    public void setMemberNoSend(String memberNoSend) {
        this.memberNoSend = memberNoSend;
    }

    public String getMemberNmSend() {
        return memberNmSend;
    }

    public void setMemberNmSend(String memberNmSend) {
        this.memberNmSend = memberNmSend;
    }

    public int getPointSend() {
        return pointSend;
    }

    public void setPointSend(int pointSend) {
        this.pointSend = pointSend;
    }

    public String getMemberNoReceive() {
        return memberNoReceive;
    }

    public void setMemberNoReceive(String memberNoReceive) {
        this.memberNoReceive = memberNoReceive;
    }

    public String getMemberNmReceive() {
        return memberNmReceive;
    }

    public void setMemberNmReceive(String memberNmReceive) {
        this.memberNmReceive = memberNmReceive;
    }

    public int getPointReceive() {
        return pointReceive;
    }

    public void setPointReceive(int pointReceive) {
        this.pointReceive = pointReceive;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getGubunMemberNo() {
        return gubunMemberNo;
    }

    public void setGubunMemberNo(String gubunMemberNo) {
        this.gubunMemberNo = gubunMemberNo;
    }

    public String getPointSaveFg() {
        return pointSaveFg;
    }

    public void settPointSaveFg(String pointSaveFg) {
        this.pointSaveFg = pointSaveFg;
    }

    public String getChgSeq() {
        return chgSeq;
    }

    public void setChgSeq(String chgSeq) {
        this.chgSeq = chgSeq;
    }

    public String getMemberCash() {
        return memberCash;
    }

    public void setMemberCash(String memberCash) {
        this.memberCash = memberCash;
    }

    public String getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(String memberCard) {
        this.memberCard = memberCard;
    }

    public String getMarketingSmsGubun() {
        return marketingSmsGubun;
    }

    public void setMarketingSmsGubun(String marketingSmsGubun) {
        this.marketingSmsGubun = marketingSmsGubun;
    }

    public String getExcelGubun() {
        return excelGubun;
    }

    public void setExcelGubun(String excelGubun) {
        this.excelGubun = excelGubun;
    }

    public String getForcedDeleteYn() {
        return forcedDeleteYn;
    }

    public void setForcedDeleteYn(String forcedDeleteYn) {
        this.forcedDeleteYn = forcedDeleteYn;
    }

    public String[] getArrMembrNo() {
        return arrMembrNo;
    }

    public void setArrMembrNo(String[] arrMembrNo) {
        this.arrMembrNo = arrMembrNo;
    }

    public String getMembrClassManageFg() {
        return membrClassManageFg;
    }

    public void setMembrClassManageFg(String membrClassManageFg) {
        this.membrClassManageFg = membrClassManageFg;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getMemberFg() {
        return memberFg;
    }

    public void setMemberFg(String memberFg) {
        this.memberFg = memberFg;
    }

    public String getPrivateFg() {
        return privateFg;
    }

    public void setPrivateFg(String privateFg) {
        this.privateFg = privateFg;
    }

    public String getPostpaidFg() {
        return postpaidFg;
    }

    public void setPostpaidFg(String postpaidFg) {
        this.postpaidFg = postpaidFg;
    }

    public String getPeopleCnt() { return peopleCnt; }

    public void setPeopleCnt(String peopleCnt) {
        this.peopleCnt = peopleCnt;
    }

    public String getGroupFg() { return groupTypeFg; }

    public void setGroupFg(String groupTypeFg) {
        this.groupTypeFg = groupTypeFg;
    }

    public String getGroupTypeFg() { return groupTypeFg; }

    public void setGroupTypeFg(String groupTypeFg) {
        this.groupTypeFg = groupTypeFg;
    }

    public String getPaymentFg() { return paymentFg; }

    public void setPaymentFg(String paymentFg) {
        this.paymentFg = paymentFg;
    }

    public String getTeacherCd() { return teacherCd; }

    public void setTeacherCd(String teacherCd) {
        this.teacherCd = teacherCd;
    }

    public String getClassFg() { return classFg; }

    public void setClassFg(String classFg) { this.classFg = classFg; }

    public String getSkateFg() { return skateFg; }

    public void setSkateFg(String skateFg) { this.skateFg = skateFg; }

    public String getRegisterDate() { return registerDate; }

    public void setRegisterDate(String registerDate) { this.registerDate = registerDate; }

    public String getUseDateFg() { return useDateFg; }

    public void setUseDateFg(String useDateFg) { this.useDateFg = useDateFg; }

    public String getUseStartDate() { return useStartDate; }

    public void setUseStartDate(String useStartDate) { this.useStartDate = useStartDate; }

    public String getUseEndDate() { return useEndDate; }

    public void setUseEndDate(String useEndDate) { this.useEndDate = useEndDate; }

    public String getUseWeek() { return useWeek; }

    public void setUseWeek(String useWeek) { this.useWeek = useWeek; }

    public String getUseProdNm() { return useProdNm; }

    public void setUseProdNm(String useProdNm) { this.useProdNm = useProdNm; }

    public String getUseAmt() { return useAmt; }

    public void setUseAmt(String useAmt) { this.useAmt = useAmt; }

    public String getBusinessAmt() { return businessAmt; }

    public void setBusinessAmt(String businessAmt) { this.businessAmt = businessAmt; }

    public String getTeacherAmt() { return teacherAmt; }

    public void setTeacherAmt(String teacherAmt) { this.teacherAmt = teacherAmt; }

    public String getTeacherCnt() { return teacherCnt; }

    public void setTeacherCnt(String teacherCnt) { this.teacherCnt = teacherCnt; }

    public String getUseRemainAmt() { return useRemainAmt; }

    public void setUseRemainAmt(String useRemainAmt) { this.useRemainAmt = useRemainAmt; }

    public String getTransportationAmt() { return transportationAmt; }

    public void setTransportationAmt(String transportationAmt) { this.transportationAmt = transportationAmt; }

    public String getTransportationCnt() { return transportationCnt; }

    public void setTransportationCnt(String transportationCnt) { this.transportationCnt = transportationCnt; }


    @Override
	public String toString() {
		return "RegistVO [orgnFg=" + orgnFg + ", hqOfficeCd=" + hqOfficeCd + ", storeCd=" + storeCd + ", membrOrgnCd="
				+ membrOrgnCd + ", membrNo=" + membrNo + ", membrNm=" + membrNm + ", membrEngNm=" + membrEngNm
				+ ", membrNicknm=" + membrNicknm + ", membrClassCd=" + membrClassCd + ", membrCardNo=" + membrCardNo
				+ ", storeMembr=" + storeMembr + ", visitStoreMembr=" + visitStoreMembr + ", regStoreCd=" + regStoreCd
				+ ", regStoreCds=" + Arrays.toString(regStoreCds) + ", postNo=" + postNo + ", addr=" + addr
				+ ", addrDtl=" + addrDtl + ", birthday=" + birthday + ", lunarYn=" + lunarYn + ", gendrFg=" + gendrFg
				+ ", emailAddr=" + emailAddr + ", shortNo=" + shortNo + ", telNo=" + telNo + ", weddingYn=" + weddingYn
				+ ", weddingday=" + weddingday + ", emailRecvYn=" + emailRecvYn + ", smsRecvYn=" + smsRecvYn
				+ ", useYn=" + useYn + ", remark=" + remark + ", anvType=" + anvType + ", anvStartDate=" + anvStartDate
				+ ", anvEndDate=" + anvEndDate + ", periodType=" + periodType + ", periodStartDate=" + periodStartDate
				+ ", periodEndDate=" + periodEndDate + ", postpaidStoreCds=" + postpaidStoreCds + ", cdCompany="
				+ cdCompany + ", cdPartner=" + cdPartner + ", startSaveSale=" + startSaveSale + ", endSaveSale="
				+ endSaveSale + ", startAvablPoint=" + startAvablPoint + ", endAvablPoint=" + endAvablPoint
				+ ", phoneNo=" + phoneNo + ", totSavePoint=" + totSavePoint + ", totUsePoint=" + totUsePoint
				+ ", avablPoint=" + avablPoint + ", totAdjPoint=" + totAdjPoint + ", firstSaleDate=" + firstSaleDate
				+ ", lastSaleDate=" + lastSaleDate + ", regDt=" + regDt + ", cstCardUseFg=" + cstCardUseFg
				+ ", cstCardStatFg=" + cstCardStatFg + ", oldCstCardNo=" + oldCstCardNo + ", cstCardIssFg="
				+ cstCardIssFg + ", issRemark=" + issRemark + ", issOrgnCd=" + issOrgnCd + ", issDate=" + issDate
				+ ", dlvrLzoneCd=" + dlvrLzoneCd + ", dlvrMzoneCd=" + dlvrMzoneCd + ", lastDlvrDate=" + lastDlvrDate
				+ ", totDlvrCnt=" + totDlvrCnt + ", dlvrAddrSeq=" + dlvrAddrSeq + ", dlvrTelSeq=" + dlvrTelSeq
				+ ", memberSaleFg=" + memberSaleFg + ", memberPointFg=" + memberPointFg + ", chgPoint=" + chgPoint
				+ ", chgDate=" + chgDate + ", result=" + result + ", pointChgFg=" + pointChgFg + ", movePoint="
				+ movePoint + "]";
	}
}
