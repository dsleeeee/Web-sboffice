package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SideMenuSelProdVO.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SideMenuSelProdVO extends PageVO {

    private static final long serialVersionUID = -1314708357337155299L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사이드선택분류코드 */
    private String sdselGrpCd;
    /** 사이드선택그룹코드 */
    private String sdselClassCd;
    /** 상품코드 */
    private String prodCd;
    /** 추가상품단가 */
    private Double addProdUprc;
    /** 추가상품수량 */
    private Double addProdQty;
    /** 표기순번 */
    private Integer dispSeq;
    /** 사용여부 */
    private String useYn;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 상품명 */
    private String prodNm;

    /** 사이드메뉴생성구분 */
    private String sideEnvstVal;

    /** 프로시져 결과 */
    private String result;

    /** 고정상품구분 */
    private String fixProdFg;

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

    /**
     * @return the sdselGrpCd
     */

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    /**
     * @param sdselGrpCd the sdselGrpCd to set
     */
    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    /**
     * @return the sdselClassCd
     */
    public String getSdselClassCd() {
        return sdselClassCd;
    }

    /**
     * @param sdselClassCd the sdselClassCd to set
     */
    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
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

    /**
     * @return the addProdUprc
     */
    public Double getAddProdUprc() {
        return addProdUprc;
    }

    /**
     * @param addProdUprc the addProdUprc to set
     */
    public void setAddProdUprc(Double addProdUprc) {
        this.addProdUprc = addProdUprc;
    }

    /**
     * @return the addProdQty
     */
    public Double getAddProdQty() {
        return addProdQty;
    }

    /**
     * @param addProdQty the addProdQty to set
     */
    public void setAddProdQty(Double addProdQty) {
        this.addProdQty = addProdQty;
    }

    /**
     * @return the dispSeq
     */
    public Integer getDispSeq() {
        return dispSeq;
    }

    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
    }

    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getSideEnvstVal() {
        return sideEnvstVal;
    }

    public void setSideEnvstVal(String sideEnvstVal) {
        this.sideEnvstVal = sideEnvstVal;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    public String getFixProdFg() {
        return fixProdFg;
    }

    public void setFixProdFg(String fixProdFg) {
        this.fixProdFg = fixProdFg;
    }
}
