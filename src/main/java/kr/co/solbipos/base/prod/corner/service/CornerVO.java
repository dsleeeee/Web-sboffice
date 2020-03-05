package kr.co.solbipos.base.prod.corner.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : CornerController.java
 * @Description : 기초관리 - 상품관리 - 코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.27  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 02.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CornerVO extends PageVO {

    private static final long serialVersionUID = 98861569690197055L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 코너코드 */
    private String cornrCd;
    /** 코너코드(검색용) */
    private String srchCornrCd;
    /** 상품코드목록*/
    private String arrProdCd[];


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

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
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

    public String getCornrCd() {
        return cornrCd;
    }

    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    public String getSrchCornrCd() {
        return srchCornrCd;
    }

    public void setSrchCornrCd(String srchCornrCd) {
        this.srchCornrCd = srchCornrCd;
    }

    public String[] getArrProdCd() {
        return arrProdCd;
    }

    public void setArrProdCd(String[] arrProdCd) {
        this.arrProdCd = arrProdCd;
    }
}

