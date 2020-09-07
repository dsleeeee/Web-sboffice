package kr.co.solbipos.base.prod.kioskKeyMap.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KioskKeyMapVO extends PageVO {

    private static final long serialVersionUID = -448836635464575392L;

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
}
