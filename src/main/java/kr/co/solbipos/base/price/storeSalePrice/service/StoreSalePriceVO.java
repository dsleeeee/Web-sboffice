package kr.co.solbipos.base.price.storeSalePrice.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : StoreSalePriceVO.java
 * @Description : 기초관리 - 가격관리 - 매장판매가현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.10  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreSalePriceVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 조회 매장 */
    private String arrStoreCd[];

    /** 상품코드 */
    private String prodCd;

    /** 바코드 */
    private String barcdCd;

    /** 상품명 */
    private String prodNm;
    
    /** 상품분류코드 */
    private String prodClassCd;
    
    /** 판매가 */
    private Boolean saleUprc;

    /** 내점가 */
    private Boolean stinSaleUprc;

    /** 배달가 */
    private Boolean dlvrSaleUprc;

    /** 포장가 */
    private Boolean packSaleUprc;

    /**
     * workMode<br>
     * 1 : 상품정보수정<br>
     * 2 : 신규상품등록<br>
     * 3 : 매장등록<br>
     */
    private WorkModeFg workMode;

    /** 프로시져 실행 결과 */
    private String result;

    /** 사용자 아이디 */
    private String userId;

    /**
     * @return the hqOfficeCd
     */

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */

    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    /**
     * @return the prodCd
     */

    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getBarcdCd() {
        return barcdCd;
    }

    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
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

    public Boolean getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(Boolean saleUprc) {
        this.saleUprc = saleUprc;
    }

    public Boolean getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(Boolean stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public Boolean getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(Boolean dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public Boolean getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(Boolean packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
