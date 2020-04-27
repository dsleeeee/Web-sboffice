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
    /** 배송구분 */
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
}
