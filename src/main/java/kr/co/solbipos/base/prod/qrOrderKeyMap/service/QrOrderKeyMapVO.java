package kr.co.solbipos.base.prod.qrOrderKeyMap.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : QrOrderKeyMapVO.java
 * @Description : 기초관리 > 상품관리2 > QR주문키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.28  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.28
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class QrOrderKeyMapVO extends PageVO {

    private static final long serialVersionUID = 2160268323741874558L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 소속구분 */
    private String orgnFg;

    /** 용도구분 */
    private String clsFg;

    /**  */
    private String indexNo;

    /** 페이지수 */
    private String tuPage;

    /** 카테고리 코드 */
    private String tuClsCd;

    /** 키맵그룹(카테고리그룹) */
    private String tuClsType;

    /** 포스번호 */
    private String posNo;

    /** 카테고리명 */
    private String tuClsNm;

    /** X값 */
    private String x;

    /** Y값 */
    private String y;

    /** 너비 */
    private String width;

    /** 높이 */
    private String height;

    /** 키오스크터치분류키설명 */
    private String clsMemo;

    /** KIOSK 중분류 사용 */
    private String tuMClsFg;

    /** 카테고리명(영문) */
    private String tuClsEnNm;

    /** 카테고리명(중문) */
    private String tuClsCnNm;

    /** 카테고리명(일문) */
    private String tuClsJpNm;

    /** 바코드번호 */
    private String barCd;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 상품유형구분 */
    private String prodTypeFg;

    /** 키맵코드 */
    private String tuKeyCd;

    /** 전체기간 여부 */
    private boolean chkDt;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 사용여부 */
    private String useYn;

    /** 등록여부 */
    private String regYn;

    /** 내점, 배달, 포장 체크박스 */
    private boolean saleTypeYnSin;
    private boolean saleTypeYnDlv;
    private boolean saleTypeYnPkg;

    /** 내점, 배달, 포장 값 */
    private String saleTypeYnSinVal;
    private String saleTypeYnDlvVal;
    private String saleTypeYnPkgVal;

    /** 연동구분 */
    private String agencyFg;

    /** 연동결과 */
    private String agencyUseYn;

    /** 응답값 */
    private String lastResponse;

    /** 응답일시 */
    private String lastResponseDt;

    /** 응답코드 */
    private String lastStatusCode;

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

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getTuPage() {
        return tuPage;
    }

    public void setTuPage(String tuPage) {
        this.tuPage = tuPage;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getTuClsType() {
        return tuClsType;
    }

    public void setTuClsType(String tuClsType) {
        this.tuClsType = tuClsType;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getTuClsNm() {
        return tuClsNm;
    }

    public void setTuClsNm(String tuClsNm) {
        this.tuClsNm = tuClsNm;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getClsMemo() {
        return clsMemo;
    }

    public void setClsMemo(String clsMemo) {
        this.clsMemo = clsMemo;
    }

    public String getTuMClsFg() {
        return tuMClsFg;
    }

    public void setTuMClsFg(String tuMClsFg) {
        this.tuMClsFg = tuMClsFg;
    }

    public String getTuClsEnNm() {
        return tuClsEnNm;
    }

    public void setTuClsEnNm(String tuClsEnNm) {
        this.tuClsEnNm = tuClsEnNm;
    }

    public String getTuClsCnNm() {
        return tuClsCnNm;
    }

    public void setTuClsCnNm(String tuClsCnNm) {
        this.tuClsCnNm = tuClsCnNm;
    }

    public String getTuClsJpNm() {
        return tuClsJpNm;
    }

    public void setTuClsJpNm(String tuClsJpNm) {
        this.tuClsJpNm = tuClsJpNm;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getTuKeyCd() {
        return tuKeyCd;
    }

    public void setTuKeyCd(String tuKeyCd) {
        this.tuKeyCd = tuKeyCd;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public boolean isSaleTypeYnSin() {
        return saleTypeYnSin;
    }

    public void setSaleTypeYnSin(boolean saleTypeYnSin) {
        this.saleTypeYnSin = saleTypeYnSin;
    }

    public boolean isSaleTypeYnDlv() {
        return saleTypeYnDlv;
    }

    public void setSaleTypeYnDlv(boolean saleTypeYnDlv) {
        this.saleTypeYnDlv = saleTypeYnDlv;
    }

    public boolean isSaleTypeYnPkg() {
        return saleTypeYnPkg;
    }

    public void setSaleTypeYnPkg(boolean saleTypeYnPkg) {
        this.saleTypeYnPkg = saleTypeYnPkg;
    }

    public String getSaleTypeYnSinVal() {
        return saleTypeYnSinVal;
    }

    public void setSaleTypeYnSinVal(String saleTypeYnSinVal) {
        this.saleTypeYnSinVal = saleTypeYnSinVal;
    }

    public String getSaleTypeYnDlvVal() {
        return saleTypeYnDlvVal;
    }

    public void setSaleTypeYnDlvVal(String saleTypeYnDlvVal) {
        this.saleTypeYnDlvVal = saleTypeYnDlvVal;
    }

    public String getSaleTypeYnPkgVal() {
        return saleTypeYnPkgVal;
    }

    public void setSaleTypeYnPkgVal(String saleTypeYnPkgVal) {
        this.saleTypeYnPkgVal = saleTypeYnPkgVal;
    }

    public String getAgencyUseYn() {
        return agencyUseYn;
    }

    public void setAgencyUseYn(String agencyUseYn) {
        this.agencyUseYn = agencyUseYn;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getLastResponseDt() {
        return lastResponseDt;
    }

    public void setLastResponseDt(String lastResponseDt) {
        this.lastResponseDt = lastResponseDt;
    }

    public String getLastStatusCode() {
        return lastStatusCode;
    }

    public void setLastStatusCode(String lastStatusCode) {
        this.lastStatusCode = lastStatusCode;
    }

    public String getAgencyFg() {
        return agencyFg;
    }

    public void setAgencyFg(String agencyFg) {
        this.agencyFg = agencyFg;
    }
}
