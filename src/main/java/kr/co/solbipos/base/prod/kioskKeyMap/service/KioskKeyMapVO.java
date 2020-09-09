package kr.co.solbipos.base.prod.kioskKeyMap.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KioskKeyMapVO extends PageVO {

    private static final long serialVersionUID = -448836635464575392L;

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
    /** POS 번호 */
    private String posNo;
    /** 카테고리코드 */
    private String tuClsCd;
    /** 카테고리명 */
    private String tuClsNm;
    /** 페이지수 */
    private String tuPage;
    /** x */
    private String x;
    /** y */
    private String y;
    /** 폭 */
    private String width;
    /** 높이 */
    private String height;
    /** Display 순서 */
    private String indexNo;
    /** 용도구분  */
    private String clsFg;
    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;
    /** 전체기간 여부 */
    private boolean chkDt;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 바코드 */
    private String barCd;
    /** 사용여부 */
    private String useYn;
    /** 상품유형구분 */
    private String prodTypeFg;
    /** 등록여부 */
    private String regYn;
    /** 키맵코드 */
    private String tuKeyCd;

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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getTuClsNm() {
        return tuClsNm;
    }

    public void setTuClsNm(String tuClsNm) {
        this.tuClsNm = tuClsNm;
    }

    public String getTuPage() {
        return tuPage;
    }

    public void setTuPage(String tuPage) {
        this.tuPage = tuPage;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getSalePrcFg() {
        return salePrcFg;
    }

    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public String getTuKeyCd() {
        return tuKeyCd;
    }

    public void setTuKeyCd(String tuKeyCd) {
        this.tuKeyCd = tuKeyCd;
    }
}
