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

    private static final long serialVersionUID = -6010694897967742075L;

    /** 스타일코드 */
    private String styleCd;
    /** 버튼구분 */
    private String buttonFg;
    /** 사용여부 */
    private String useYn;
    /** 비고 */
    private String remark;
    /** 버튼태그구분 */
    private String buttonTagFg;
    /** 배경색 : 마우스오버시 */
    private String buttonOnColor;
    /** 배경색 */
    private String buttonOffColor;
    /** 텍스트정렬 */
    private String textAlign;
    /** 텍스트수직정렬 */
    private String textVerticalAlign;
    /** 폰트명 */
    private String fontNm;
    /** 폰트사이즈 */
    private Integer fontSize;
    /** 폰트색상 : 마우스오버시 */
    private String fontOnColor;
    /** 폰트색상 */
    private String fontOffColor;
    /** 폰트스타일:Bold 여부 */
    private String fontStyleBold;
    /** 폰트스타일:Italic 여부 */
    private String fontStyleItalic;
    /** 폰트스타일:Underline 여부 */
    private String fontStyleUnderline;

    /** 스타일명 */
    private String styleNm;


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
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the buttonTagFg
     */
    public String getButtonTagFg() {
        return buttonTagFg;
    }

    /**
     * @param buttonTagFg the buttonTagFg to set
     */
    public void setButtonTagFg(String buttonTagFg) {
        this.buttonTagFg = buttonTagFg;
    }

    /**
     * @return the buttonOnColor
     */
    public String getButtonOnColor() {
        return buttonOnColor;
    }

    /**
     * @param buttonOnColor the buttonOnColor to set
     */
    public void setButtonOnColor(String buttonOnColor) {
        this.buttonOnColor = buttonOnColor;
    }

    /**
     * @return the buttonOffColor
     */
    public String getButtonOffColor() {
        return buttonOffColor;
    }

    /**
     * @param buttonOffColor the buttonOffColor to set
     */
    public void setButtonOffColor(String buttonOffColor) {
        this.buttonOffColor = buttonOffColor;
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
    public Integer getFontSize() {
        return fontSize;
    }

    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * @return the fontOnColor
     */
    public String getFontOnColor() {
        return fontOnColor;
    }

    /**
     * @param fontOnColor the fontOnColor to set
     */
    public void setFontOnColor(String fontOnColor) {
        this.fontOnColor = fontOnColor;
    }

    /**
     * @return the fontOffColor
     */
    public String getFontOffColor() {
        return fontOffColor;
    }

    /**
     * @param fontOffColor the fontOffColor to set
     */
    public void setFontOffColor(String fontOffColor) {
        this.fontOffColor = fontOffColor;
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
}
