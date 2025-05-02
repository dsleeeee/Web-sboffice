package kr.co.solbipos.base.prod.pizzaTopping.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PizzaToppingVO.java
 * @Description : 미스터피자 > 상품관리 > 피자-토핑관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PizzaToppingVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장 */
    private String storeCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 사용여부 */
    private String useYn;

    /** 상품코드 */
    private String srchProdCd;

    /** 상품명 */
    private String srchProdNm;

    /** 토핑 상품코드 */
    private String toppingProdCd;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) { this.prodClassCd = prodClassCd; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getSrchProdCd() { return srchProdCd; }

    public void setSrchProdCd(String srchProdCd) { this.srchProdCd = srchProdCd; }

    public String getSrchProdNm() { return srchProdNm; }

    public void setSrchProdNm(String srchProdNm) { this.srchProdNm = srchProdNm; }

    public String getToppingProdCd() { return toppingProdCd; }

    public void setToppingProdCd(String toppingProdCd) { this.toppingProdCd = toppingProdCd; }
}