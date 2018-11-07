package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreFnkeyVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리 > 기능키복사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.11.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosFnkeyVO extends CmmVO {

    private static final long serialVersionUID = -4479796927422847740L;

    /** 매장코드 */
    private String storeCd;
    /** 포스번호 */
    private int posNo;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 표기순번 */
    private String dispSeq;
    /** x */
    private String x;
    /** y */
    private String y;
    /** 폭 */
    private String width;
    /** 높이 */
    private String height;
    /** 스타일코드 */
    private String styleCd;
    /** 이미지파일명 */
    private String imgNm;
    /** 사용여부 */
    private String useYn;
    /** 복사할 매장코드 */
    private String copyStoreCd;

    /** 여러 포스값 */
    private String[] arrPosNo;

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
     * @return the posNo
     */

    public int getPosNo() {
        return posNo;
    }

    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(int posNo) {
        this.posNo = posNo;
    }

    /**
     * @return the fnkeyNo
     */

    public String getFnkeyNo() {
        return fnkeyNo;
    }

    /**
     * @param fnkeyNo the fnkeyNo to set
     */
    public void setFnkeyNo(String fnkeyNo) {
        this.fnkeyNo = fnkeyNo;
    }

    /**
     * @return the fnkeyFg
     */

    public String getFnkeyFg() {
        return fnkeyFg;
    }

    /**
     * @param fnkeyFg the fnkeyFg to set
     */
    public void setFnkeyFg(String fnkeyFg) {
        this.fnkeyFg = fnkeyFg;
    }

    /**
     * @return the dispSeq
     */

    public String getDispSeq() {
        return dispSeq;
    }

    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }

    /**
     * @return the x
     */

    public String getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(String x) {
        this.x = x;
    }

    /**
     * @return the y
     */

    public String getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(String y) {
        this.y = y;
    }

    /**
     * @return the width
     */

    public String getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */

    public String getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the styleCd
     */

    public String getStyleCd() {
        return styleCd;
    }

    /**
     * @param styleCd the styleCd to set
     */
    public void setStyleCd(String styleCd) {
        this.styleCd = styleCd;
    }

    /**
     * @return the imgNm
     */

    public String getImgNm() {
        return imgNm;
    }

    /**
     * @param imgNm the imgNm to set
     */
    public void setImgNm(String imgNm) {
        this.imgNm = imgNm;
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
     * @return the copyStoreCd
     */

    public String getCopyStoreCd() {
        return copyStoreCd;
    }

    /**
     * @param copyStoreCd the copyStoreCd to set
     */
    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
    }

    /**
     * @return the arrPosNo
     */

    public String[] getArrPosNo() {
        return arrPosNo;
    }

    /**
     * @param arrPosNo the arrPosNo to set
     */
    public void setArrPosNo(String[] arrPosNo) {
        this.arrPosNo = arrPosNo;
    }
}
