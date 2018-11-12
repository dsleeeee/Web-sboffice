package kr.co.solbipos.membr.info.regist.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
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
    @NotBlank(groups = {Regist.class}, message = "{regist.membr.nm}{cmm.require.text}")
    private String membrNm;
    /** 회원영문명 */
    private String memberEngNm;
    /** 회원닉네임 */
    private String membrNicknm;
    /** 회원분류코드 */
    private String membrClassCd;
    /** 회원카드번호 */
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
    private String creditStoreCds;


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
     * @return the memberEngNm
     */

    public String getMemberEngNm() {
        return memberEngNm;
    }

    /**
     * @param memberEngNm the memberEngNm to set
     */
    public void setMemberEngNm(String memberEngNm) {
        this.memberEngNm = memberEngNm;
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

    public String getWeddingYn() {
        return weddingYn;
    }

    /**
     * @param weddingYn the weddingYn to set
     */
    public void setWeddingYn(String weddingYn) {
        this.weddingYn = weddingYn;
    }

    /**
     * @return the weddingday
     */

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

    public String getEmailRecvYn() {
        return emailRecvYn;
    }

    /**
     * @param emailRecvYn the emailRecvYn to set
     */
    public void setEmailRecvYn(String emailRecvYn) {
        this.emailRecvYn = emailRecvYn;
    }

    /**
     * @return the smsRecvYn
     */

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    /**
     * @param smsRecvYn the smsRecvYn to set
     */
    public void setSmsRecvYn(String smsRecvYn) {
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
     * @return the creditStoreCds
     */

    public String getCreditStoreCds() {
        return creditStoreCds;
    }

    /**
     * @param creditStoreCds the creditStoreCds to set
     */
    public void setCreditStoreCds(String creditStoreCds) {
        this.creditStoreCds = creditStoreCds;
    }
}
