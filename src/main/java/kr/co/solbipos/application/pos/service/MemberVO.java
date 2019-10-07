package kr.co.solbipos.application.pos.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MemberVO.java
 * @Description : 회원관리 > 회원정보 > 회원등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MemberVO extends PageVO {

    // TODO
    // 안동관. 2018-08-14
    // 회원등록시 MemberVO 는 같이 사용해도 될듯. 현재는 회원관련된 내용이 없이 POS 화면에서만 간편 회원등록을 하므로
    // 해당위치에 두고 나중에 정리 필요.

    private static final long serialVersionUID = 5096147755991148966L;

    /** 회원소속코드 */
    private String membrOrgnCd;
    /** 회원번호 */
    private String membrNo;
    /** 회원명 */
    private String membrNm;
    /** 회원닉네임 */
    private String membrNicknm;
    /** 회원분류코드 */
    private String membrClassCd;
    /** 회원카드번호 */
    private String membrCardNo;
    /** 등록매장코드 */
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
    private String gendrFg;
    /** 이메일주소 */
    private String emailAddr;
    /** 단축번호 */
    private String shortNo;
    /** 전화번호 */
    private String telNo;
    /** 결혼여부 */
    private String weddingYn;
    /** 결혼기념일 */
    private String weddingday;
    /** 이메일수신여부 */
    private String emailRecvYn;
    /** SMS수신여부 */
    private String smsRecvYn;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;
    /** 회원영문명 */
    private String memberEngNm;

    /** 후불회원 적용매장코드 */
    private String postpaidStoreCds;
    /** 회원 거래처 매핑코드 (회사코드)*/
    private String cdCompany;
    /** 회원 거래처 매핑코드 (거래처코드)*/
    private String cdPartner;

    /** 회원구분 */
    private String  membrFg;


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
     * @return the regDt
     */
    @Override
    public String getRegDt() {
        return regDt;
    }

    /**
     * @param regDt the regDt to set
     */
    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    /**
     * @return the regId
     */
    @Override
    public String getRegId() {
        return regId;
    }

    /**
     * @param regId the regId to set
     */
    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return the modDt
     */
    @Override
    public String getModDt() {
        return modDt;
    }

    /**
     * @param modDt the modDt to set
     */
    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    /**
     * @return the modId
     */
    @Override
    public String getModId() {
        return modId;
    }

    /**
     * @param modId the modId to set
     */
    @Override
    public void setModId(String modId) {
        this.modId = modId;
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

    public String getMembrFg() {
        return membrFg;
    }

    public void setMembrFg(String membrFg) {
        this.membrFg = membrFg;
    }
}
