package kr.co.solbipos.dlvr.info.dlvrAgencyLink.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    // OMS_API 용
    /** 매장명 */
    private String shopName;
    /** 사업자번호 */
    private String texNo;
    /** 대표자명 */
    private String ceoName;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addrBase;
    /** 상세주소 */
    private String addrDetail;
    /** POS사 정보코드 */
    private String posType;
    /** POS사 매장ID*/
    private String posShopId;
    /** 연동타입 */
    private String linkType;
    /** 배달대행사 코드 */
    private String agencyCode;
    /** 하위배달대행사 코드 */
    private String subAgencyCode;
    /** 배달업체관리코드 */
    private String storeCode;
    /** 생각대로(B2B)사용여부 */
    private String isB2BContract;
    /** 매핑일련번호 */
    private String mappingSequence;

    // POS_OMS_API 용
    /** 매장코드 */
    private String pos_shop_id;
    /** 주문 연동 활성화 여부 */
    private String use_orderandrider;

    // 연동상태 저장여부
    @JsonIgnore
    private String apiStoreYn;

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

    public String getPos_shop_id() {
        return pos_shop_id;
    }

    public void setPos_shop_id(String pos_shop_id) {
        this.pos_shop_id = pos_shop_id;
    }

    public String getUse_orderandrider() {
        return use_orderandrider;
    }

    public void setUse_orderandrider(String use_orderandrider) {
        this.use_orderandrider = use_orderandrider;
    }

    public String getApiStoreYn() {
        return apiStoreYn;
    }

    public void setApiStoreYn(String apiStoreYn) {
        this.apiStoreYn = apiStoreYn;
    }
}
