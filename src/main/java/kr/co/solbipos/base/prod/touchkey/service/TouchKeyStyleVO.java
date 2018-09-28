package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : TouchKeyStyleVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.25  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TouchKeyStyleVO extends CmmVO {

    private static final long serialVersionUID = 4669068485254747743L;

    /** 스타일코드 */
    private String styleCd;
    /** 스타일명 */
    private String styleNm;
    /** 버튼구분 */
    private String buttonFg;
    /** 버튼상태 */
    private String buttonStat;
    /** 버튼색상 */
    private String buttonColor;
    /** 텍스트정렬 */
    private String textAlign;
    /** 텍스트수직정렬 */
    private String textVerticalAlign;
    /** 폰트명 */
    private String fontNm;
    /** 폰트사이즈 */
    private String fontSize;
    /** 폰트색상 */
    private String fontColor;
    /** 폰트스타일:Bold 여부 */
    private String fontStyleBold;
    /** 폰트스타일:Italic 여부 */
    private String fontStyleItalic;
    /** 폰트스타일:Underline 여부 */
    private String fontStyleUnderline;


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
     * @return the styleNm
     */
    public String getStyleNm() {
        return styleNm;
    }

    /**
     * @param styleNm the styleNm to set
     */
    public void setStyleNm(String styleNm) {
        this.styleNm = styleNm;
    }

    /**
     * @return the buttonFg
     */
    public String getButtonFg() {
        return buttonFg;
    }

    /**
     * @param buttonFg the buttonFg to set
     */
    public void setButtonFg(String buttonFg) {
        this.buttonFg = buttonFg;
    }

    /**
     * @return the buttonStat
     */
    public String getButtonStat() {
        return buttonStat;
    }

    /**
     * @param buttonStat the buttonStat to set
     */
    public void setButtonStat(String buttonStat) {
        this.buttonStat = buttonStat;
    }

    /**
     * @return the buttonColor
     */
    public String getButtonColor() {
        return buttonColor;
    }

    /**
     * @param buttonColor the buttonColor to set
     */
    public void setButtonColor(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    /**
     * @return the textAlign
     */
    public String getTextAlign() {
        return textAlign;
    }

    /**
     * @param textAlign the textAlign to set
     */
    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    /**
     * @return the textVerticalAlign
     */
    public String getTextVerticalAlign() {
        return textVerticalAlign;
    }

    /**
     * @param textVerticalAlign the textVerticalAlign to set
     */
    public void setTextVerticalAlign(String textVerticalAlign) {
        this.textVerticalAlign = textVerticalAlign;
    }

    /**
     * @return the fontNm
     */
    public String getFontNm() {
        return fontNm;
    }

    /**
     * @param fontNm the fontNm to set
     */
    public void setFontNm(String fontNm) {
        this.fontNm = fontNm;
    }

    /**
     * @return the fontSize
     */
    public String getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(String fontSize) {
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
     * @return the fontStyleBold
     */
    public String getFontStyleBold() {
        return fontStyleBold;
    }

    /**
     * @param fontStyleBold the fontStyleBold to set
     */
    public void setFontStyleBold(String fontStyleBold) {
        this.fontStyleBold = fontStyleBold;
    }

    /**
     * @return the fontStyleItalic
     */
    public String getFontStyleItalic() {
        return fontStyleItalic;
    }

    /**
     * @param fontStyleItalic the fontStyleItalic to set
     */
    public void setFontStyleItalic(String fontStyleItalic) {
        this.fontStyleItalic = fontStyleItalic;
    }

    /**
     * @return the fontStyleUnderline
     */
    public String getFontStyleUnderline() {
        return fontStyleUnderline;
    }

    /**
     * @param fontStyleUnderline the fontStyleUnderline to set
     */
    public void setFontStyleUnderline(String fontStyleUnderline) {
        this.fontStyleUnderline = fontStyleUnderline;
    }
}
