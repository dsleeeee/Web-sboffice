package kr.co.solbipos.sale.benson.timeSaleStoreBenson.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : TimeSaleStoreBensonVO.java
 * @Description : 벤슨 > 간소화화면 > 시간대매출(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TimeSaleStoreBensonVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269215L;

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

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자 아이디 */
    private String userId;

    /** 시간대 */
    private String timeCol;

    /** 시간대 Array */
    private String[] arrTimeCol;

    /** 배달포장구분 구분 코드*/
    private String dlvrOrderFg;

    /** 배달포장구분 구분 코드 리스트*/
    private String [] dlvrOrderFgList;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

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

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTimeCol() {
        return timeCol;
    }

    public void setTimeCol(String timeCol) {
        this.timeCol = timeCol;
    }

    public String[] getArrTimeCol() {
        return arrTimeCol;
    }

    public void setArrTimeCol(String[] arrTimeCol) {
        this.arrTimeCol = arrTimeCol;
    }

    public String getDlvrOrderFg() {
        return dlvrOrderFg;
    }

    public void setDlvrOrderFg(String dlvrOrderFg) {
        this.dlvrOrderFg = dlvrOrderFg;
    }

    public String[] getDlvrOrderFgList() {
        return dlvrOrderFgList;
    }

    public void setDlvrOrderFgList(String[] dlvrOrderFgList) {
        this.dlvrOrderFgList = dlvrOrderFgList;
    }
}
