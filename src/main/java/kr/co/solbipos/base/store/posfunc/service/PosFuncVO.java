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
    /** 사용여부 */
    private String useYn = "0";
    /** 인증여부 */
    private String authYn = "0";
    /** 사원번호 */
    private String empNo;

    /** 복사 대상이 되는 포스 */
    private String copyPos;
    /** 복사 타겟이 되는 포스 */
    private String targetPos;


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
    /**
     * @return the authYn
     */
    public String getAuthYn() { return authYn; }
    /**
     * @param authYn the authYn to set
     */
    public void setAuthYn(String authYn) {
        if(authYn == "true") {
            this.authYn = "Y";
        } else {
            this.authYn = "N";
        }
    }
    /**
     * @return the useYn
     */
    public String getUseYn() { return useYn; }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        if(useYn == "true") {
            this.useYn = "Y";
        } else {
            this.useYn = "N";
        }
    }
    /**
     * @return the empNo
     */
    public String getEmpNo() { return empNo; }
    /**
     * @param empNo the useYn to empNo
     */
    public void setEmpNo(String empNo) { this.empNo = empNo; }

    /**
     * @return the copyPos
     */
    public String getCopyPos() { return copyPos; }
    /**
     * @param copyPos the useYn to copyPos
     */
    public void setCopyPos(String copyPos) { this.copyPos = copyPos; }
    /**
     * @return the targetPos
     */
    public String getTargetPos() { return targetPos; }
    /**
     * @param targetPos the useYn to targetPos
     */
    public void setTargetPos(String targetPos) { this.targetPos = targetPos; }
}
