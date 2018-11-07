package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.common.enums.InFg;

/**
 * @Class Name : TouchKeyVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TouchKeyVO extends CmmVO {

    private static final long serialVersionUID = -3103530249114726049L;

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
    /** 터치키분류코드 */
    private String tukeyClassCd;
    /** 터치키코드 */
    private String tukeyCd;
    /** 터치키구분 : 01:버튼, 02:상품명태그, 03:금액태그 */
    private String tukeyFg;
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
    /** 스타일코드 */
    private String styleCd;
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

    /** 복사할 매장 코드 */
    private String copyStoreCd;


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
     * @return the tukeyFg
     */
    public String getTukeyFg() {
        return tukeyFg;
    }

    /**
     * @param tukeyFg the tukeyFg to set
     */
    public void setTukeyFg(String tukeyFg) {
        this.tukeyFg = tukeyFg;
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
}
