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
 * @ 2018.10.01  노현수      기능키관련 수정 외
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.07.31
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosFuncVO extends CmmVO {

    private static final long serialVersionUID = 3967533176391929481L;
    /** 매장코드 */
    private String storeCd;
    /** 포스번호 */
    private String posNo;
    /** 포스구분 */
    private String posFg;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 위치조정여부 */
    private Boolean posiAdjYn = false;
    /** 표기순번 */
    private String dispSeq;
    /** X 좌표 */
    private Double x = 0d;
    /** Y 좌표*/
    private Double y = 0d;
    /** 폭 */
    private Double width = 0d;
    /** 넓이 */
    private Double height = 0d;
    /** 사용여부 */
    private Boolean useYn = false;
    /** 인증여부 */
    private Boolean authYn = false;
    /** 사원번호 */
    private String empNo;

    /** 복사 대상이 되는 포스 */
    private String copyPos;
    /** 복사 타겟이 되는 포스 */
    private String targetPos;

    /** 설정구분 */
    private String confgFg;
    /** 스타일코드 */
    private String styleCd;
    /** 이미지명 */
    private String imgNm;
    /** XML */
    private String xml;


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
    public Double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public Double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(Double y) {
        this.y = y;
    }

    public Double getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(Double width) {
        this.width = width;
    }
    /**
     * @return the height
     */
    public Double getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(Double height) {
        this.height = height;
    }
    /**
     * @return the authYn
     */
    public Boolean getAuthYn() { return authYn; }
    /**
     * @param authYn the authYn to set
     */
    public void setAuthYn(Boolean authYn) {
        this.authYn = authYn;
    }
    /**
     * @return the useYn
     */
    public Boolean getUseYn() { return useYn; }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(Boolean useYn) {
        this.useYn = useYn;
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

    /**
     * @return the confgFg
     */
    public String getConfgFg() {
        return confgFg;
    }

    /**
     * @param confgFg the confgFg to set
     */
    public void setConfgFg(String confgFg) {
        this.confgFg = confgFg;
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
     * @return the xml
     */
    public String getXml() {
        return xml;
    }

    /**
     * @param xml the xml to set
     */
    public void setXml(String xml) {
        this.xml = xml;
    }
}
