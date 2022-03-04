package kr.co.solbipos.mobile.prod.sideMenuSoldOut.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileSideMenuSoldOutVO.java
 * @Description : 상품관리 > 품절관리(선택상품)
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.03  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.03.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileSideMenuSoldOutVO extends PageVO {

    private static final long serialVersionUID = 2507060678407090632L;

    /** 매장코드 */
    private String storeCd;
    /** 상품코드 */
    private String prodCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 사이드선택그룹명 */
    private String sdselGrpNm;
    /** 사이드선택분류코드 */
    private String sdselClassCd;
    /** 사용자아이디 */
    private String userId;
    /** 품절여부 */
    private String SoldOutYn;

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSoldOutYn() {
        return SoldOutYn;
    }

    public void setSoldOutYn(String soldOutYn) {
        SoldOutYn = soldOutYn;
    }
}