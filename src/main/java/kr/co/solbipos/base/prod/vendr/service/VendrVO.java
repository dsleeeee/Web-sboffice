package kr.co.solbipos.base.prod.vendr.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.vendr.enums.VendorFg;

/**
 * @Class Name : TouchKeyVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.08  노해민      최초생성
 *
 * @author NHN한국사이버결제 KCP 노해민
 * @since 2018.08.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VendrVO extends PageVO {

    private static final long serialVersionUID = -5156116686836619650L;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 본사브랜드코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처명 */
    private String vendrNm;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 대표자명 */
    private String ownerNm;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자유형코드 */
    private String bizTypeCd;
    /** 사업자항목코드 */
    private String bizItemCd;
    /** 전화번호 */
    private String telNo;
    /** 팩스번호 */
    private String faxNo;
    /** 이메일주소 */
    private String emailAddr;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 주소상세 */
    private String addrDtl;
    /** 배송구분 */
    private String shipPg;
    /** 부가세포함여부 */
    private String vatIncldYn;
    /** 직배송 */
    private String shipFg;
    /** 사용여부 */
    private UseYn useYn;
    /** 비고 */
    private String remark;
    /** 공급단가 */
    private String splyUprc;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;
    /** 거래처구분 */
    private VendorFg vendorFg;
    /** 취급여부 */
    private String trtmntYn;
    /** 상품분류코드 */
    private String prodClassCd;

    /** 업체구분 */
    private String companyFg;
    /** 사업자구분 */
    private String businessFg;
    /** 구매ID */
    private String purchaseId;
    /** 법인번호 */
    private String corporationNumber;
    /** 업태 */
    private String businessStatus;
    /** 업종 */
    private String industry;
    /** 홈페이지 */
    private String homepage;
    /** 대표자전화 */
    private String ownerTelNo;
    /** 대표자Email */
    private String ownerEmail;
    /** 담당자명 */
    private String managerNm;
    /** 담당자전화번호 */
    private String managerTelNo;
    /** 담당자Email */
    private String managerEmail;
    /** 담당자직위 */
    private String managerSpot;
    /** 담당자휴대전화 */
    private String managerPhoneNo;
    /** 은행코드 */
    private String bankCd;
    /** 계좌번호 */
    private String accountNo;
    /** 예금주 */
    private String depositor;
    /** 수금 일자/주기 */
    private String collectFg;
    /** 더존ERP */
    private String douzoneErp;
    /** 외상한도액 */
    private String creditLimit;
    /** 담보종류 */
    private String collateralType;
    /** 담보금액 */
    private String collateralAmt;
    /** 외상한대조일도액 */
    private String contrastDate;
    /** 대조자(점포) */
    private String collatorStore;
    /** 대조자(업체) */
    private String collatorCompany;
    /** 거래시작일 */
    private String dealStartDate;
    /** 거래종료일 */
    private String dealEndDate;
    /** 최종매출일 */
    private String lastSaleDate;
    /** 최종입금일 */
    private String lastDepositDate;

    /** 회원코드 */
    private String membrNo;

    /** 회원명 */
    private String membrNm;

    /** 전자계산서 */
    private String electronicBill;

    /** 매입상품 */
    private String acquireProd;

    /** 매입처구분 */
    private String acquireCd;

    /** 매입구분 */
    private String acquireFg;

    /** 관리지점 */
    private String manageSpotCd;

    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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
     * @return the vendrCd
     */
    public String getVendrCd() {
        return vendrCd;
    }
    /**
     * @param vendrCd the vendrCd to set
     */
    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }
    /**
     * @return the vendrNm
     */
    public String getVendrNm() {
        return vendrNm;
    }
    /**
     * @param vendrNm the vendrNm to set
     */
    public void setVendrNm(String vendrNm) {
        this.vendrNm = vendrNm;
    }
    /**
     * @return the prodCd
     */
    public String getProdCd() {
        return prodCd;
    }
    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }
    /**
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }
    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    /**
     * @return the ownerNm
     */
    public String getOwnerNm() {
        return ownerNm;
    }
    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }
    /**
     * @return the bizNo
     */
    public String getBizNo() {
        return bizNo;
    }
    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
    /**
     * @return the bizTypeCd
     */
    public String getBizTypeCd() {
        return bizTypeCd;
    }
    /**
     * @param bizTypeCd the bizTypeCd to set
     */
    public void setBizTypeCd(String bizTypeCd) {
        this.bizTypeCd = bizTypeCd;
    }
    /**
     * @return the bizItemCd
     */
    public String getBizItemCd() {
        return bizItemCd;
    }
    /**
     * @param bizItemCd the bizItemCd to set
     */
    public void setBizItemCd(String bizItemCd) {
        this.bizItemCd = bizItemCd;
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
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
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
     * @return the shipPg
     */
    public String getShipPg() {
        return shipPg;
    }
    /**
     * @param shipPg the shipPg to set
     */
    public void setShipPg(String shipPg) {
        this.shipPg = shipPg;
    }
    /**
     * @return the vatIncldYn
     */
    public String getVatIncldYn() {
        return vatIncldYn;
    }
    /**
     * @param vatIncldYn the vatIncldYn to set
     */
    public void setVatIncldYn(String vatIncldYn) {
        this.vatIncldYn = vatIncldYn;
    }
    /**
     * @return the shipFg
     */
    public String getShipFg() {
        return shipFg;
    }
    /**
     * @param shipFg the shipFg to set
     */
    public void setShipFg(String shipFg) {
        this.shipFg = shipFg;
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
     * @return the splyUprc
     */
    public String getSplyUprc() {
        return splyUprc;
    }
    /**
     * @param splyUprc the splyUprc to set
     */
    public void setSplyUprc(String splyUprc) {
        this.splyUprc = splyUprc;
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
    /**
     * @return the regId
     */
    public String getRegId() {
        return regId;
    }
    /**
     * @param regId the regId to set
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }
    /**
     * @return the modDt
     */
    public String getModDt() {
        return modDt;
    }
    /**
     * @param modDt the modDt to set
     */
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }
    /**
     * @return the modId
     */
    public String getModId() {
        return modId;
    }
    /**
     * @param modId the modId to set
     */
    public void setModId(String modId) {
        this.modId = modId;
    }
    /**
     * @return the vendorFg
     */
    public VendorFg getVendorFg() {
        return vendorFg;
    }
    /**
     * @param vendorFg the vendorFg to set
     */
    public void setVendorFg(VendorFg vendorFg) {
        this.vendorFg = vendorFg;
    }

    public String getTrtmntYn() {
        return trtmntYn;
    }

    public void setTrtmntYn(String trtmntYn) {
        this.trtmntYn = trtmntYn;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getCompanyFg() {
        return companyFg;
    }

    public void setCompanyFg(String companyFg) {
        this.companyFg = companyFg;
    }

    public String getBusinessFg() {
        return businessFg;
    }

    public void setBusinessFg(String businessFg) {
        this.businessFg = businessFg;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getCorporationNumber() { return corporationNumber; }

    public void setCorporationNumber(String corporationNumber) {
        this.corporationNumber = corporationNumber;
    }

    public String getBusinessStatus() { return businessStatus; }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getIndustry() { return industry; }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getHomepage() { return homepage; }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOwnerTelNo() { return ownerTelNo; }

    public void setOwnerTelNo(String ownerTelNo) { this.ownerTelNo = ownerTelNo; }

    public String getOwnerEmail() { return ownerEmail; }

    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public String getManagerNm() { return managerNm; }

    public void setManagerNm(String managerNm) { this.managerNm = managerNm; }

    public String getManagerTelNo() { return managerTelNo; }

    public void setManagerTelNo(String managerTelNo) { this.managerTelNo = managerTelNo; }

    public String getManagerEmail() { return managerEmail; }

    public void setManagerEmail(String managerEmail) { this.managerEmail = managerEmail; }

    public String getManagerSpot() { return managerSpot; }

    public void setManagerSpot(String managerSpot) { this.managerSpot = managerSpot; }

    public String getManagerPhoneNo() { return managerPhoneNo; }

    public void setManagerPhoneNo(String managerPhoneNo) { this.managerPhoneNo = managerPhoneNo; }

    public String getBankCd() { return bankCd; }

    public void setBankCd(String bankCd) { this.bankCd = bankCd; }

    public String getAccountNo() { return accountNo; }

    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }

    public String getDepositor() { return depositor; }

    public void setDepositor(String depositor) { this.depositor = depositor; }

    public String getCollectFg() { return collectFg; }

    public void setCollectFg(String collectFg) { this.collectFg = collectFg; }

    public String getDouzoneErp() { return douzoneErp; }

    public void setDouzoneErp(String douzoneErp) { this.douzoneErp = douzoneErp; }

    public String getCreditLimit() { return creditLimit; }

    public void setCreditLimit(String creditLimit) { this.creditLimit = creditLimit; }

    public String getCollateralType() { return collateralType; }

    public void setCollateralType(String collateralType) { this.collateralType = collateralType; }

    public String getCollateralAmt() { return collateralAmt; }

    public void setCollateralAmt(String collateralAmt) { this.collateralAmt = collateralAmt; }

    public String getContrastDate() { return contrastDate; }

    public void setContrastDate(String contrastDate) { this.contrastDate = contrastDate; }

    public String getCollatorStore() { return collatorStore; }

    public void setCollatorStore(String collatorStore) { this.collatorStore = collatorStore; }

    public String getCollatorCompany() { return collatorCompany; }

    public void setCollatorCompany(String collatorCompany) { this.collatorCompany = collatorCompany; }

    public String getDealStartDate() { return dealStartDate; }

    public void setDealStartDate(String dealStartDate) { this.dealStartDate = dealStartDate; }

    public String getDealEndDate() { return dealEndDate; }

    public void setDealEndDate(String dealEndDate) { this.dealEndDate = dealEndDate; }

    public String getLastSaleDate() { return lastSaleDate; }

    public void setLastSaleDate(String lastSaleDate) { this.lastSaleDate = lastSaleDate; }

    public String getLastDepositDate() { return lastDepositDate; }

    public void setLastDepositDate(String lastDepositDate) { this.lastDepositDate = lastDepositDate; }

    public String getMembrNo() { return membrNo; }

    public void setMembrNo(String membrNo) { this.membrNo = membrNo; }

    public String getMembrNm() { return membrNm; }

    public void setMembrNm(String membrNm) { this.membrNm = membrNm; }

    public String getElectronicBill() { return electronicBill; }

    public void setElectronicBill(String electronicBill) { this.electronicBill = electronicBill; }

    public String getAcquireProd() { return acquireProd; }

    public void setAcquireProd(String acquireProd) { this.acquireProd = acquireProd; }

    public String getAcquireCd() { return acquireCd; }

    public void setAcquireCd(String acquireCd) { this.acquireCd = acquireCd; }

    public String getAcquireFg() { return acquireFg; }

    public void setAcquireFg(String acquireFg) { this.acquireFg = acquireFg; }

    public String getManageSpotCd() { return manageSpotCd; }

    public void setManageSpotCd(String manageSpotCd) { this.manageSpotCd = manageSpotCd; }
}