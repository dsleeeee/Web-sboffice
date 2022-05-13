package kr.co.solbipos.base.prod.prodKitchenprintLink.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : ProdKitchenprintLinkVO.java
 * @Description : 기초관리 > 상품관리 > 상품-매장주방프린터연결
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.09  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.02.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdKitchenprintLinkVO extends PageVO {

    /** 전체기간 여부 */
    private boolean chkDt;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 브랜드명 */
    private String hqBrandNm;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드 */
    private String barCd;

    /** 판매단가 */
    private String saleUprc;

    /** 사용여부 */
    private String useYn;

    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];

    /** 매장명 */
    private String storeNm;

    /** 프린터번호 */
    private String prterNo;

    /** 프린터명 */
    private String pprterNm;

    /** 매장상태구분 */
    private SysStatFg sysStatFg;

    /** 시스템폐점일자 */
    private String sysClosureDate;

    /** 사용자 아이디 */
    private String userId;

    /** 그룹코드 */
    private String printerGroupCd;

    /** 그룹명 */
    private String printerGroupNm;

    /** 비고 */
    private String remark;

    /** 상품유형구분 */
    private String prodTypeFg;

    /** 메뉴그룹 */
    private String storeGroup;

    public String getSysClosureDate() {
        return sysClosureDate;
    }

    public void setSysClosureDate(String sysClosureDate) {
        this.sysClosureDate = sysClosureDate;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getHqBrandNm() {
        return hqBrandNm;
    }

    public void setHqBrandNm(String hqBrandNm) {
        this.hqBrandNm = hqBrandNm;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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

    public String getPrterNo() {
        return prterNo;
    }

    public void setPrterNo(String prterNo) {
        this.prterNo = prterNo;
    }

    public String getPprterNm() {
        return pprterNm;
    }

    public void setPprterNm(String pprterNm) {
        this.pprterNm = pprterNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrinterGroupCd() {
        return printerGroupCd;
    }

    public void setPrinterGroupCd(String printerGroupCd) {
        this.printerGroupCd = printerGroupCd;
    }

    public String getPrinterGroupNm() {
        return printerGroupNm;
    }

    public void setPrinterGroupNm(String printerGroupNm) {
        this.printerGroupNm = printerGroupNm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getStoreGroup() {
        return storeGroup;
    }

    public void setStoreGroup(String storeGroup) {
        this.storeGroup = storeGroup;
    }
}