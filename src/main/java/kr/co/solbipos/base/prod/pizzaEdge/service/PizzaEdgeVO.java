package kr.co.solbipos.base.prod.pizzaEdge.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PizzaEdgeVO.java
 * @Description : 미스터피자 > 상품관리 > 피자-엣지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PizzaEdgeVO extends PageVO {

    private static final long serialVersionUID = -8807164610731883029L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 상품코드, 명 */
    private String prodCd;
    private String prodNm;
    /** 사이드그룹코드, 명 */
    private String sdselGrpCd;
    private String sdselGrpNm;
    /** 사이드분류코드, 명 */
    private String sdselClassCd;
    private String sdselClassNm;
    /** 사이드상품코드, 명 */
    private String sdselProdCd;
    private String sdselProdNm;
    /** 매핑상품코드 */
    private String mappingProdCd;
    /** 매핑사이드상품코드 */
    private String mappingSdselClassCd;

    /** 팝업 여부 */
    private String popUpClassYn;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
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

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    public String getSdselProdCd() {
        return sdselProdCd;
    }

    public void setSdselProdCd(String sdselProdCd) {
        this.sdselProdCd = sdselProdCd;
    }

    public String getSdselProdNm() {
        return sdselProdNm;
    }

    public void setSdselProdNm(String sdselProdNm) {
        this.sdselProdNm = sdselProdNm;
    }

    public String getMappingProdCd() {
        return mappingProdCd;
    }

    public void setMappingProdCd(String mappingProdCd) {
        this.mappingProdCd = mappingProdCd;
    }

    public String getMappingSdselClassCd() {
        return mappingSdselClassCd;
    }

    public void setMappingSdselClassCd(String mappingSdselClassCd) {
        this.mappingSdselClassCd = mappingSdselClassCd;
    }

    public String getPopUpClassYn() {
        return popUpClassYn;
    }

    public void setPopUpClassYn(String popUpClassYn) {
        this.popUpClassYn = popUpClassYn;
    }
}
