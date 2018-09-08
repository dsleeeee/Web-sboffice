package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.common.enums.InFg;

import java.util.List;

/**
 * @Class Name : TouchClassVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 * @ 2018.09.07  노현수      스타일코드, 이미지명 추가
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TouchClassVO extends CmmVO {

    private static final long serialVersionUID = 1163205565875003414L;
    /** 매장코드 */
    private String storeCd;
    /** 터치키분류코드 */
    private String tukeyGrpCd;
    /** 터치키분류코드 */
    private String tukeyClassCd;
    /** 터치키분류명 */
    private String tukeyClassNm;
    /** 스타일코드 */
    private String styleCd;
    /** 페이지번호 */
    private Long pageNo;
    /** X */
    private Long x = 0L;
    /** Y */
    private Long y = 0L;
    /** 폭 */
    private Long width = 0L;
    /** 높이 */
    private Long height = 0L;
    /** 이미지명 */
    private String imgNm;
    /** 입력구분 H:본사, S:매장 */
    private InFg inFg;
    /** 폰트크기 */
    private Long fontSize = 10L;
    /** 폰트색 */
    private String fontColor = "#000000";
    /** 채움색 */
    private String fillColor = "#000000";
    /** 테이블들 */
    private List<TouchVO> touchs;


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
     * @return the tukeyGrpCd
     */
    public String getTukeyGrpCd() {
        return tukeyGrpCd;
    }

    /**
     * @param tukeyGrpCd the tukeyGrpCd to set
     */
    public void setTukeyGrpCd(String tukeyGrpCd) {
        this.tukeyGrpCd = tukeyGrpCd;
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
     * @return the tukeyClassNm
     */
    public String getTukeyClassNm() {
        return tukeyClassNm;
    }

    /**
     * @param tukeyClassNm the tukeyClassNm to set
     */
    public void setTukeyClassNm(String tukeyClassNm) {
        this.tukeyClassNm = tukeyClassNm;
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

    /**
     * @return the touchs
     */
    public List<TouchVO> getTouchs() {
        return touchs;
    }

    /**
     * @param touchs the touchs to set
     */
    public void setTouchs(List<TouchVO> touchs) {
        this.touchs = touchs;
    }

}
