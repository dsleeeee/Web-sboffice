package kr.co.solbipos.base.store.posfunc.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : PosFuncVO.java
 * @Description : 기초관리 > 매장관리 > 포스기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.07.31  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.07.31
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosFuncVO extends CmmVO {

    /** 매장코드 */
    private String storeCd;
    /** 포스번호 */
    private String posNo;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 위치조정여부 */
    private String posiAdjYn;
    /** 표기순번 */
    private String dispSeq;
    /** 열위치 */
    private String colPosi;
    /** 줄위치 */
    private String rowPosi;
    /** 폭 */
    private String width;
    /** 넓이 */
    private String height;
    
    
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
    public String getPosNo() {
        return posNo;
    }
    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(String posNo) {
        this.posNo = posNo;
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
     * @return the fnkeyNm
     */
    public String getFnkeyNm() {
        return fnkeyNm;
    }
    /**
     * @param fnkeyNm the fnkeyNm to set
     */
    public void setFnkeyNm(String fnkeyNm) {
        this.fnkeyNm = fnkeyNm;
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
     * @return the posiAdjYn
     */
    public String getPosiAdjYn() {
        return posiAdjYn;
    }
    /**
     * @param posiAdjYn the posiAdjYn to set
     */
    public void setPosiAdjYn(String posiAdjYn) {
        this.posiAdjYn = posiAdjYn;
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
     * @return the colPosi
     */
    public String getColPosi() {
        return colPosi;
    }
    /**
     * @param colPosi the colPosi to set
     */
    public void setColPosi(String colPosi) {
        this.colPosi = colPosi;
    }
    /**
     * @return the rowPosi
     */
    public String getRowPosi() {
        return rowPosi;
    }
    /**
     * @param rowPosi the rowPosi to set
     */
    public void setRowPosi(String rowPosi) {
        this.rowPosi = rowPosi;
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
    
}
