package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.common.enums.InFg;

import java.util.List;

/**
 * @Class Name : TouchKeyClassVO.java
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
public class TouchKeyClassVO extends CmmVO {

    private static final long serialVersionUID = -3699216234626892457L;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 터치키그룹코드 : 시즌,행사별 등 일종의 템플릿 */
    private String tukeyGrpCd;
    /** 터치키분류코드 */
    private String tukeyClassCd;
    /** 터치키분류명 */
    private String tukeyClassNm;
    /** 스타일코드 */
    private String styleCd;
    /** 페이지번호 */
    private Integer pageNo = 0;
    /** 페이지줄수 */
    private Integer pageRows = 0;
    /** X */
    private Double x = 0d;
    /** Y */
    private Double y = 0d;
    /** 폭 */
    private Double width = 0d;
    /** 높이 */
    private Double height = 0d;
    /** 이미지명 */
    private String imgNm;
    /** 입력구분 H:본사, S:매장 */
    private InFg inFg;
    /** 폰트크기 */
    private Integer fontSize;
    /** 폰트색 */
    private String fontColor;
    /** 채움색 */
    private String fillColor;

    /** 테이블들 */
    private List<TouchKeyVO> touchs;

    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

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
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageRows
     */
    public Integer getPageRows() {
        return pageRows;
    }

    /**
     * @param pageRows the pageRows to set
     */
    public void setPageRows(Integer pageRows) {
        this.pageRows = pageRows;
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

    /**
     * @return the width
     */
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
    public List<TouchKeyVO> getTouchs() {
        return touchs;
    }

    /**
     * @param touchs the touchs to set
     */
    public void setTouchs(List<TouchKeyVO> touchs) {
        this.touchs = touchs;
    }

}
