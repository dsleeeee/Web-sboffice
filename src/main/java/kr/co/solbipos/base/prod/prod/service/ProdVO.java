package kr.co.solbipos.base.prod.prod.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ProdVO.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @ 수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수       최초생성
 * @ 2018.10.19  노현수       소속구분 타입 변경
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdVO extends PageVO {

    private static final long serialVersionUID = 6100122429987446624L;
    /**
     * 본사코드
     */
    private String hqOfficeCd;
    /**
     * 매장코드
     */
    private String storeCd;
    /**
     * 상품코드
     */
    private String prodCd;
    /**
     * 상품명
     */
    private String prodNm;
    /**
     * 상품분류코드
     */
    private String prodClassCd;
    /**
     * 원산지코드
     */
    private String orgplceCd;
    /**
     * 바코드
     */
    private String barCd;
    /**
     * 사용여부
     */
    private String useYn;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

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

    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }

    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    /**
     * @return the orgplceCd
     */
    public String getOrgplceCd() {
        return orgplceCd;
    }

    /**
     * @param orgplceCd the orgplceCd to set
     */
    public void setOrgplceCd(String orgplceCd) { this.orgplceCd = orgplceCd; }

    /**
     * @return the barCd
     */
    public String getBarCd() { return barCd; }

    /**
     * @param barCd the barCd to set
     */
    public void setBarCd(String barCd) { this.barCd = barCd; }

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
}
