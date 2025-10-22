package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service;

/**
 * @Class Name : DlvrAgencyLinkReqVO.java
 * @Description : 배달관리 - 배달정보 - 배달대행사 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.14  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.14
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */

public class DlvrAgencyLinkReqVO {

    private static final long serialVersionUID = 2555999796764348528L;

    private String shopName;
    private String texNo;
    private String ceoName;
    private String postNo;
    private String addrBase;
    private String addrDetail;
    private String posType;
    private String posShopId;
    private String linkType;
    private String searchType;
    private String agencyCode;
    private String subAgencyCode;
    private String storeCode;
    private String isB2BContract;
    private String mappingSequence;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTexNo() {
        return texNo;
    }

    public void setTexNo(String texNo) {
        this.texNo = texNo;
    }

    public String getCeoName() {
        return ceoName;
    }

    public void setCeoName(String ceoName) {
        this.ceoName = ceoName;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getAddrBase() {
        return addrBase;
    }

    public void setAddrBase(String addrBase) {
        this.addrBase = addrBase;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getPosShopId() {
        return posShopId;
    }

    public void setPosShopId(String posShopId) {
        this.posShopId = posShopId;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getSubAgencyCode() {
        return subAgencyCode;
    }

    public void setSubAgencyCode(String subAgencyCode) {
        this.subAgencyCode = subAgencyCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getIsB2BContract() {
        return isB2BContract;
    }

    public void setIsB2BContract(String isB2BContract) {
        this.isB2BContract = isB2BContract;
    }

    public String getMappingSequence() {
        return mappingSequence;
    }

    public void setMappingSequence(String mappingSequence) {
        this.mappingSequence = mappingSequence;
    }
}
