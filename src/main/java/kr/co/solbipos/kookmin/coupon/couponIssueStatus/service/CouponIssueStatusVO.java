package kr.co.solbipos.kookmin.coupon.couponIssueStatus.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : CouponIssueStatusVO.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰상태관리(관리자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class CouponIssueStatusVO extends PageVO {

    private static final long serialVersionUID = 4644333117525268641L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 일자 */
    private String saleDate;

    /** 쿠폰발행 상태구분 */
    private String coupnStatusFg;

    /** 최종상태 */
    private String useStatusFg;

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

    /** 권종분류코드 */
    private String payClassCd;

    /** 쿠폰일렬번호 */
    private String coupnSerNo;

    /** 쿠폰상태변환 구분 */
    private String issueStatusFg;

    /** 쿠폰상태변환 비고 */
    private String issueRemark;

    /** 사용일 */
    private String coupnUseDate;


    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getCoupnStatusFg() {
        return coupnStatusFg;
    }

    public void setCoupnStatusFg(String coupnStatusFg) {
        this.coupnStatusFg = coupnStatusFg;
    }

    public String getUseStatusFg() {
        return useStatusFg;
    }

    public void setUseStatusFg(String useStatusFg) {
        this.useStatusFg = useStatusFg;
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

    public String getPayClassCd() {
        return payClassCd;
    }

    public void setPayClassCd(String payClassCd) {
        this.payClassCd = payClassCd;
    }

    public String getCoupnSerNo() {
        return coupnSerNo;
    }

    public void setCoupnSerNo(String coupnSerNo) {
        this.coupnSerNo = coupnSerNo;
    }

    public String getIssueStatusFg() {
        return issueStatusFg;
    }

    public void setIssueStatusFg(String issueStatusFg) {
        this.issueStatusFg = issueStatusFg;
    }

    public String getIssueRemark() {
        return issueRemark;
    }

    public void setIssueRemark(String issueRemark) {
        this.issueRemark = issueRemark;
    }

    public String getCoupnUseDate() {
        return coupnUseDate;
    }

    public void setCoupnUseDate(String coupnUseDate) {
        this.coupnUseDate = coupnUseDate;
    }
}
