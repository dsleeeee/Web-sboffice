package kr.co.solbipos.sale.prod.monthProdMrpizza.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MonthProdMrpizzaVO.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MonthProdMrpizzaVO extends PageVO {

    private static final long serialVersionUID = -7510017610133290349L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 조회매장 */
    private String storeCd;

    /** 조회월 */
    private String startMonth;
    private String endMonth;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류 */
    private String prodClassCd;

    /** 사용자 아이디 */
    private String userId;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품코드 */
    private String[] prodCdList;

    /** 상품코드 */
    private String prodCds;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 사용자별 본사 공통코드 */
    private String userHqNmcodeCd;

    /** 상품표시옵션 */
    private String prodOption;

    /** 일자표시옵션 */
    private String dayOption;

    /** 금액보정 */
    private String saleAmtFg;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 사이드상품분류코드(배열형) */
    private String arrProdClassCd [];

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
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

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String[] getProdCdList() {
        return prodCdList;
    }

    public void setProdCdList(String[] prodCdList) {
        this.prodCdList = prodCdList;
    }

    public String getProdCds() {
        return prodCds;
    }

    public void setProdCds(String prodCds) {
        this.prodCds = prodCds;
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

    public String getProdOption() {
        return prodOption;
    }

    public void setProdOption(String prodOption) {
        this.prodOption = prodOption;
    }

    public String getDayOption() {
        return dayOption;
    }

    public void setDayOption(String dayOption) {
        this.dayOption = dayOption;
    }

    public String getSaleAmtFg() {
        return saleAmtFg;
    }

    public void setSaleAmtFg(String saleAmtFg) {
        this.saleAmtFg = saleAmtFg;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String[] getArrProdClassCd() {
        return arrProdClassCd;
    }

    public void setArrProdClassCd(String[] arrProdClassCd) {
        this.arrProdClassCd = arrProdClassCd;
    }
}
