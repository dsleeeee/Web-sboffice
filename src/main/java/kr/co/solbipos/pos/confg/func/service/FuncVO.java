package kr.co.solbipos.pos.confg.func.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : FuncVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class FuncVO extends CmmVO{

    private static final long serialVersionUID = -572198696435720893L;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 매장종류구분 */
    private String storeFg;
    /** 포스구분 */
    private String posFg;
    /** 일반 이미지파일명 */
    private String imgFileNm0;
    /** 외식 이미지파일명 */
    private String imgFileNm1;
    /** 일반 사용여부 */
    private Boolean fnkeyUseYn0;
    /** 외식 사용여부 */
    private Boolean fnkeyUseYn1;
    /** 표기순번 */
    private String dispSeq;
    /** 위치조정여부 */
    private Boolean posiAdjYn;
    /** 열위치 */
    private String colPosi;
    /** 줄위치 */
    private String rowPosi;
    /** 폭 */
    private String width;
    /** 높이 */
    private String height;
    /** 사용여부 */
    private String useYn;
    
    
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
     * @return the storeFg
     */
    public String getStoreFg() {
        return storeFg;
    }
    /**
     * @param storeFg the storeFg to set
     */
    public void setStoreFg(String storeFg) {
        this.storeFg = storeFg;
    }
    /**
     * @return the posFg
     */
    public String getPosFg() {
        return posFg;
    }
    /**
     * @param posFg the posFg to set
     */
    public void setPosFg(String posFg) {
        this.posFg = posFg;
    }
    /**
     * @return the imgFileNm0
     */
    public String getImgFileNm0() {
        return imgFileNm0;
    }
    /**
     * @param imgFileNm0 the imgFileNm0 to set
     */
    public void setImgFileNm0(String imgFileNm0) {
        this.imgFileNm0 = imgFileNm0;
    }
    /**
     * @return the imgFileNm1
     */
    public String getImgFileNm1() {
        return imgFileNm1;
    }
    /**
     * @param imgFileNm1 the imgFileNm1 to set
     */
    public void setImgFileNm1(String imgFileNm1) {
        this.imgFileNm1 = imgFileNm1;
    }
    /**
     * @return the fnkeyUseYn0
     */
    public Boolean getFnkeyUseYn0() {
        return fnkeyUseYn0;
    }
    /**
     * @param fnkeyUseYn0 the fnkeyUseYn0 to set
     */
    public void setFnkeyUseYn0(Boolean fnkeyUseYn0) {
        this.fnkeyUseYn0 = fnkeyUseYn0;
    }
    /**
     * @return the fnkeyUseYn1
     */
    public Boolean getFnkeyUseYn1() {
        return fnkeyUseYn1;
    }
    /**
     * @param fnkeyUseYn1 the fnkeyUseYn1 to set
     */
    public void setFnkeyUseYn1(Boolean fnkeyUseYn1) {
        this.fnkeyUseYn1 = fnkeyUseYn1;
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
     * @return the posiAdjYn
     */
    public Boolean getPosiAdjYn() {
        return posiAdjYn;
    }
    /**
     * @param posiAdjYn the posiAdjYn to set
     */
    public void setPosiAdjYn(Boolean posiAdjYn) {
        this.posiAdjYn = posiAdjYn;
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
    
}
