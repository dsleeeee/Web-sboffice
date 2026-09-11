package kr.co.solbipos.sale.benson.payFgBenson.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PayFgBensonVO.java
 * @Description : 벤슨 > 결제수단별 매출 > 결제수단별 일 매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PayFgBensonVO extends PageVO {

    private static final long serialVersionUID = 4567094913301369313L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 결제수단컬럼 */
    private String payCol;
    /** 결제수단 array */
    private String arrPayCol[];
    /** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
    private String pivotPayCol;

    /** 검색옵션 */
    private String option;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 사용자별 본사 공통코드 */
    private String userHqNmcodeCd;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getPayCol() {
        return payCol;
    }

    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }

    public String[] getArrPayCol() {
        return arrPayCol;
    }

    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
    }

    public String getPivotPayCol() {
        return pivotPayCol;
    }

    public void setPivotPayCol(String pivotPayCol) {
        this.pivotPayCol = pivotPayCol;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getUserHqNmcodeCd() {
        return userHqNmcodeCd;
    }

    public void setUserHqNmcodeCd(String userHqNmcodeCd) {
        this.userHqNmcodeCd = userHqNmcodeCd;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
