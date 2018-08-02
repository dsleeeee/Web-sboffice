package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.common.enums.InFg;

/**
 * @Class Name : TouchVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TouchVO extends CmmVO {

    private static final long serialVersionUID = -5156116686836619650L;
    /** 매장코드 */
    private String storeCd;
    /** 터치키분류코드 */
    private String tukeyClassCd;
    /** 터치키코드 */
    private String tukeyCd;
    /** 상품코드 */
    private String prodCd;
    /** 페이지번호 */
    private Long pageNo = 0L;;
    /** X */
    private Long x = 0L;
    /** Y */
    private Long y = 0L;
    /** 폭 */
    private Long width = 0L;
    /** 높이 */
    private Long height = 0L;
    /** 입력구분 H:본사, S:매장 */
    private InFg inFg;
    /** 폰트크기 */
    private Long fontSize = 10L;
    /** 폰트색 */
    private String fontColor = "#000000";
    /** 채움색 */
    private String fillColor = "#000000";
    
    
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
     * @return the tukeyClassCd
     */
    public String getTukeyClassCd() {
        return tukeyClassCd;
    }
    /**
     * @param tukeyClassCd the tukeyClassCd to set
     */
    public void setTukeyClassCd(String tukeyClassCd) {
        this.tukeyClassCd = tukeyClassCd;
    }
    /**
     * @return the tukeyCd
     */
    public String getTukeyCd() {
        return tukeyCd;
    }
    /**
     * @param tukeyCd the tukeyCd to set
     */
    public void setTukeyCd(String tukeyCd) {
        this.tukeyCd = tukeyCd;
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
     * @return the pageNo
     */
    public Long getPageNo() {
        return pageNo;
    }
    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }
    /**
     * @return the x
     */
    public Long getX() {
        return x;
    }
    /**
     * @param x the x to set
     */
    public void setX(Long x) {
        this.x = x;
    }
    /**
     * @return the y
     */
    public Long getY() {
        return y;
    }
    /**
     * @param y the y to set
     */
    public void setY(Long y) {
        this.y = y;
    }
    /**
     * @return the width
     */
    public Long getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(Long width) {
        this.width = width;
    }
    /**
     * @return the height
     */
    public Long getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(Long height) {
        this.height = height;
    }
    /**
     * @return the inFg
     */
    public InFg getInFg() {
        return inFg;
    }
    /**
     * @param inFg the inFg to set
     */
    public void setInFg(InFg inFg) {
        this.inFg = inFg;
    }
    /**
     * @return the fontSize
     */
    public Long getFontSize() {
        return fontSize;
    }
    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(Long fontSize) {
        this.fontSize = fontSize;
    }
    /**
     * @return the fontColor
     */
    public String getFontColor() {
        return fontColor;
    }
    /**
     * @param fontColor the fontColor to set
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    /**
     * @return the fillColor
     */
    public String getFillColor() {
        return fillColor;
    }
    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

}
