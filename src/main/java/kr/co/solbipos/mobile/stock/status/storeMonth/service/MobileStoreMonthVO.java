package kr.co.solbipos.mobile.stock.status.storeMonth.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : MobileStoreMonthVO.java
 * @Description : (모바일)재고현황 > 매장월수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileStoreMonthVO extends PageVO {

    private static final long serialVersionUID = 4794218082380254256L;

    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 매장사업장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 로그인 권한 */
    private String orgnFg;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드코드 */
    private String barcdCd;
    /** 거래처코드 */
    private String vendrCd;
    private String arrVendrCd[];
    /** 상품분류코드 */
    private String prodClassCd;
    /** 단위구분 */
    private String unitFg;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
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

    public String getBarcdCd() {
        return barcdCd;
    }

    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String[] getArrVendrCd() {
        return arrVendrCd;
    }

    public void setArrVendrCd(String arrVendrCd[]) {
        this.arrVendrCd = arrVendrCd;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getUnitFg() {
        return unitFg;
    }

    public void setUnitFg(String unitFg) {
        this.unitFg = unitFg;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String arrStoreCd[]) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
