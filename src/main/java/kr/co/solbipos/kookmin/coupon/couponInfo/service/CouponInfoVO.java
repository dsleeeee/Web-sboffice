package kr.co.solbipos.kookmin.coupon.couponInfo.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : CouponInfoVO.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class CouponInfoVO extends PageVO {
    private static final long serialVersionUID = 2670923172891914494L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;

    /** 쿠폰코드 */
    private String coupnCd;
    /** 쿠폰명 */
    private String coupnNm;

    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    
    /** 부서코드 */
    private String partCd;
    /** 부서명 */
    private String partNm;

    /** 발행수량 */
    private String coupnCount;

    /** 제목 */
    private String coupnPrintTitle;

    /** 발행처 */
    private String coupnPrintPart;

    /** 사용매장 */
    private String coupnPrintStore;

    /** 인쇄문구 */
    private String coupnPrintRemark;

    /** 권종분류코드 */
    private String payClassCd;

    /** 세션 ID */
    private String sessionId;

    /** 회원번호 */
    private String coupnMembrNo;

    /** 수신자명 */
    private String coupnIssueInfo1;

    /** 수신자연락처 */
    private String coupnIssueInfo2;

    /** 쿠폰일렬번호 */
    private String coupnSerNo;

    /** 발행일 */
    private String issueDate;

    /** 판매일  */
    private String saleDate;

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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getCoupnCd() {
        return coupnCd;
    }

    public void setCoupnCd(String coupnCd) {
        this.coupnCd = coupnCd;
    }

    public String getCoupnNm() {
        return coupnNm;
    }

    public void setCoupnNm(String coupnNm) {
        this.coupnNm = coupnNm;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getPartCd() {
        return partCd;
    }

    public void setPartCd(String partCd) {
        this.partCd = partCd;
    }

    public String getPartNm() {
        return partNm;
    }

    public void setPartNm(String partNm) {
        this.partNm = partNm;
    }

    public String getCoupnCount() {
        return coupnCount;
    }

    public void setCoupnCount(String coupnCount) {
        this.coupnCount = coupnCount;
    }

    public String getCoupnPrintTitle() {
        return coupnPrintTitle;
    }

    public void setCoupnPrintTitle(String coupnPrintTitle) {
        this.coupnPrintTitle = coupnPrintTitle;
    }

    public String getCoupnPrintPart() {
        return coupnPrintPart;
    }

    public void setCoupnPrintPart(String coupnPrintPart) {
        this.coupnPrintPart = coupnPrintPart;
    }

    public String getCoupnPrintStore() {
        return coupnPrintStore;
    }

    public void setCoupnPrintStore(String coupnPrintStore) {
        this.coupnPrintStore = coupnPrintStore;
    }

    public String getCoupnPrintRemark() {
        return coupnPrintRemark;
    }

    public void setCoupnPrintRemark(String coupnPrintRemark) {
        this.coupnPrintRemark = coupnPrintRemark;
    }

    public String getPayClassCd() {
        return payClassCd;
    }

    public void setPayClassCd(String payClassCd) {
        this.payClassCd = payClassCd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCoupnMembrNo() {
        return coupnMembrNo;
    }

    public void setCoupnMembrNo(String coupnMembrNo) {
        this.coupnMembrNo = coupnMembrNo;
    }

    public String getCoupnIssueInfo1() {
        return coupnIssueInfo1;
    }

    public void setCoupnIssueInfo1(String coupnIssueInfo1) {
        this.coupnIssueInfo1 = coupnIssueInfo1;
    }

    public String getCoupnIssueInfo2() {
        return coupnIssueInfo2;
    }

    public void setCoupnIssueInfo2(String coupnIssueInfo2) {
        this.coupnIssueInfo2 = coupnIssueInfo2;
    }

    public String getCoupnSerNo() {
        return coupnSerNo;
    }

    public void setCoupnSerNo(String coupnSerNo) {
        this.coupnSerNo = coupnSerNo;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
