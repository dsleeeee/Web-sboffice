package kr.co.solbipos.membr.info.regist.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.regist.enums.WeddingYn;
import kr.co.solbipos.membr.info.regist.service.enums.AnvType;
import kr.co.solbipos.membr.info.regist.service.enums.PeriodType;
import kr.co.solbipos.membr.info.regist.validate.Regist;
import kr.co.solbipos.membr.info.regist.validate.RegistDelete;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Class Name : MemberMappingVO.java
 * @Description : 회원관리 > 회원정보 > 회원정보관리 > 회원 매핑 거래처
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.10  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018.12.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MemberMappingVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 회사코드 */
    private String cdCompany;
    /** 거래처코드 */
    private String cdPartner;
    /** 거래처명 */
    private String lnPartner;
    /** 사업자등록번호 */
    private String noCompany;
    /** 대표자명 */
    private String nmCeo;
    /** 주민번호 */
    private String noRes;
    /** 은행코드 */
    private String cdBank;
    /** 은행명 */
    private String nmBank;
    /** 계좌번호 */
    private String noDeposit;
    /** 예금주 */
    private String nmDeposit;
    /** 우편번호 */
    private String noPost1;
    /** 주소 */
    private String dcAds1H;
    /** 상세주소 */
    private String dcAds1D;
    /** 전화번호 */
    private String noTel;
    /** 팩스번호 */
    private String noFax;
    /** 업태 */
    private String tpJob;
    /** 업종 */
    private String clsJob;
    /** 사용여부 */
    private UseYn useYn;
    /** 메일 */
    private String eMail;
    /** 사업자등록구분 */
    private String fgPartner;
    /** 등록일자 */
    private String dtsInsert;
    /** 등록자 */
    private String idInsert;
    /** 수정일자 */
    private String dtsUpdate;
    /** 수정자 */
    private String idUpdate;

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
     * @return the lnPartner
     */

    public String getLnPartner() {
        return lnPartner;
    }

    /**
     * @param lnPartner the lnPartner to set
     */
    public void setLnPartner(String lnPartner) {
        this.lnPartner = lnPartner;
    }

    /**
     * @return the noCompany
     */

    public String getNoCompany() {
        return noCompany;
    }

    /**
     * @param noCompany the noCompany to set
     */
    public void setNoCompany(String noCompany) {
        this.noCompany = noCompany;
    }

    /**
     * @return the nmCeo
     */

    public String getNmCeo() {
        return nmCeo;
    }

    /**
     * @param nmCeo the nmCeo to set
     */
    public void setNmCeo(String nmCeo) {
        this.nmCeo = nmCeo;
    }

    /**
     * @return the noRes
     */

    public String getNoRes() {
        return noRes;
    }

    /**
     * @param noRes the noRes to set
     */
    public void setNoRes(String noRes) {
        this.noRes = noRes;
    }

    /**
     * @return the cdBank
     */

    public String getCdBank() {
        return cdBank;
    }

    /**
     * @param cdBank the cdBank to set
     */
    public void setCdBank(String cdBank) {
        this.cdBank = cdBank;
    }

    /**
     * @return the nmBank
     */

    public String getNmBank() {
        return nmBank;
    }

    /**
     * @param nmBank the nmBank to set
     */
    public void setNmBank(String nmBank) {
        this.nmBank = nmBank;
    }

    /**
     * @return the noDeposit
     */

    public String getNoDeposit() {
        return noDeposit;
    }

    /**
     * @param noDeposit the noDeposit to set
     */
    public void setNoDeposit(String noDeposit) {
        this.noDeposit = noDeposit;
    }

    /**
     * @return the nmDeposit
     */

    public String getNmDeposit() {
        return nmDeposit;
    }

    /**
     * @param nmDeposit the nmDeposit to set
     */
    public void setNmDeposit(String nmDeposit) {
        this.nmDeposit = nmDeposit;
    }

    /**
     * @return the noPost1
     */

    public String getNoPost1() {
        return noPost1;
    }

    /**
     * @param noPost1 the noPost1 to set
     */
    public void setNoPost1(String noPost1) {
        this.noPost1 = noPost1;
    }

    /**
     * @return the dcAds1H
     */

    public String getDcAds1H() {
        return dcAds1H;
    }

    /**
     * @param dcAds1H the dcAds1H to set
     */
    public void setDcAds1H(String dcAds1H) {
        this.dcAds1H = dcAds1H;
    }

    /**
     * @return the dcAds1D
     */

    public String getDcAds1D() {
        return dcAds1D;
    }

    /**
     * @param dcAds1D the dcAds1D to set
     */
    public void setDcAds1D(String dcAds1D) {
        this.dcAds1D = dcAds1D;
    }

    /**
     * @return the noTel
     */

    public String getNoTel() {
        return noTel;
    }

    /**
     * @param noTel the noTel to set
     */
    public void setNoTel(String noTel) {
        this.noTel = noTel;
    }

    /**
     * @return the noFax
     */

    public String getNoFax() {
        return noFax;
    }

    /**
     * @param noFax the noFax to set
     */
    public void setNoFax(String noFax) {
        this.noFax = noFax;
    }

    /**
     * @return the tpJob
     */

    public String getTpJob() {
        return tpJob;
    }

    /**
     * @param tpJob the tpJob to set
     */
    public void setTpJob(String tpJob) {
        this.tpJob = tpJob;
    }

    /**
     * @return the clsJob
     */

    public String getClsJob() {
        return clsJob;
    }

    /**
     * @param clsJob the clsJob to set
     */
    public void setClsJob(String clsJob) {
        this.clsJob = clsJob;
    }

    /**
     * @return the useYn
     */

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the eMail
     */

    public String geteMail() {
        return eMail;
    }

    /**
     * @param eMail the eMail to set
     */
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * @return the fgPartner
     */

    public String getFgPartner() {
        return fgPartner;
    }

    /**
     * @param fgPartner the fgPartner to set
     */
    public void setFgPartner(String fgPartner) {
        this.fgPartner = fgPartner;
    }

    /**
     * @return the dtsInsert
     */

    public String getDtsInsert() {
        return dtsInsert;
    }

    /**
     * @param dtsInsert the dtsInsert to set
     */
    public void setDtsInsert(String dtsInsert) {
        this.dtsInsert = dtsInsert;
    }

    /**
     * @return the idInsert
     */

    public String getIdInsert() {
        return idInsert;
    }

    /**
     * @param idInsert the idInsert to set
     */
    public void setIdInsert(String idInsert) {
        this.idInsert = idInsert;
    }

    /**
     * @return the dtsUpdate
     */

    public String getDtsUpdate() {
        return dtsUpdate;
    }

    /**
     * @param dtsUpdate the dtsUpdate to set
     */
    public void setDtsUpdate(String dtsUpdate) {
        this.dtsUpdate = dtsUpdate;
    }

    /**
     * @return the idUpdate
     */

    public String getIdUpdate() {
        return idUpdate;
    }

    /**
     * @param idUpdate the idUpdate to set
     */
    public void setIdUpdate(String idUpdate) {
        this.idUpdate = idUpdate;
    }
}

