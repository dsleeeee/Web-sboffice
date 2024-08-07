package kr.co.solbipos.base.store.tableattr.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.store.tableattr.enums.AttrCd;
import kr.co.solbipos.base.store.tableattr.enums.TblTypeFg;
import kr.co.solbipos.base.store.tableattr.enums.TextalignFg;
import kr.co.solbipos.base.store.tableattr.enums.TextvalignFg;

/**
 * @Class Name : TableAttrVO.java
 * @Description : 기초관리 > 매장관리 > 테이블속성
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
public class TableAttrVO extends CmmVO {

    private static final long serialVersionUID = 5150631729111226450L;
    /** 매장코드 */
    private String storeCd;
    /** 테이블유형구분 */
    private String tblTypeFg;
    /** 테이블번호 */
    private String tblCd;
    /** 테이블번호 array */
    private String arrTblCd[];
	/** 테이블추가유형구분 */
    private String confgSubFg;
	/** 속성코드 */
    private AttrCd attrCd;
    /** 속성명 */
    private String attrNm;
    /** X */
    private Long x = 0L;
    /** Y */
    private Long y = 0L;
    /** 폭 */
    private Long width = 0L;
    /** 높이 */
    private Long height = 0L;
    /** 텍스트수평정렬구분 */
    private TextalignFg textalignFg = TextalignFg.CENTER;
    /** 텍스트수직정렬구분 */
    private TextvalignFg textvalignFg = TextvalignFg.MIDDLE;
    /** 이미지명 */
    private String imgNm;
    /** 폰트명 */
    private String fontNm = "NotoR";
    /** 폰트크기 */
    private Long fontSize = 10L;
    /** 폰트스타일구분 */
    private String fontStyleFg = "0";
    /** 폰트색 */
    private String fontColor = "#000000";
    /** 사용여부 */
    private String useYn;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
	/** 명칭코드코드 */
    private String nmcodeCd;
    /** 명칭코드명 */
    private String nmcodeNm;

    private String xmlGraph;
	private String xmlPreview;
	private String sid;
	private String gubun;

    public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
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
     * @return the tblTypeFg
     */
    public String getTblTypeFg() {
        return tblTypeFg;
    }
    /**
     * @param tblTypeFg the tblTypeFg to set
     */
    public void setTblTypeFg(String tblTypeFg) {
        this.tblTypeFg = tblTypeFg;
    }
    /**
     * @return the attrCd
     */
    public AttrCd getAttrCd() {
        return attrCd;
    }
    /**
     * @param attrCd the attrCd to set
     */
    public void setAttrCd(AttrCd attrCd) {
        this.attrCd = attrCd;
    }
    /**
     * @return the attrNm
     */
    public String getAttrNm() {
        return attrNm;
    }
    /**
     * @param attrNm the attrNm to set
     */
    public void setAttrNm(String attrNm) {
        this.attrNm = attrNm;
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
     * @return the textalignFg
     */
    public TextalignFg getTextalignFg() {
        return textalignFg;
    }
    /**
     * @param textalignFg the textalignFg to set
     */
    public void setTextalignFg(TextalignFg textalignFg) {
        this.textalignFg = textalignFg;
    }
    /**
     * @return the textvalignFg
     */
    public TextvalignFg getTextvalignFg() {
        return textvalignFg;
    }
    /**
     * @param textvalignFg the textvalignFg to set
     */
    public void setTextvalignFg(TextvalignFg textvalignFg) {
        this.textvalignFg = textvalignFg;
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
     * @return the fontStyleFg
     */
    public String getFontStyleFg() {
        return fontStyleFg;
    }
    /**
     * @param fontStyleFg the fontStyleFg to set
     */
    public void setFontStyleFg(String fontStyleFg) {
        this.fontStyleFg = fontStyleFg;
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

    public String getXmlGraph() {
		return xmlGraph;
	}
	public void setXmlGraph(String xmlGraph) {
		this.xmlGraph = xmlGraph;
	}
	public String getXmlPreview() {
		return xmlPreview;
	}
	public void setXmlPreview(String xmlPreview) {
		this.xmlPreview = xmlPreview;
	}

    public String getNmcodeGrpCd() {
		return nmcodeGrpCd;
	}
	public void setNmcodeGrpCd(String nmcodeGrpCd) {
		this.nmcodeGrpCd = nmcodeGrpCd;
	}
	public String getNmcodeCd() {
		return nmcodeCd;
	}
	public void setNmcodeCd(String nmcodeCd) {
		this.nmcodeCd = nmcodeCd;
	}
	public String getNmcodeNm() {
		return nmcodeNm;
	}
	public void setNmcodeNm(String nmcodeNm) {
		this.nmcodeNm = nmcodeNm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getConfgSubFg() {
		return confgSubFg;
	}
	public void setConfgSubFg(String confgSubFg) {
		this.confgSubFg = confgSubFg;
	}
	public String getTblCd() {
		return tblCd;
	}
	public void setTblCd(String tblCd) {
		this.tblCd = tblCd;
	}
	public String[] getArrTblCd() {
		return arrTblCd;
	}
	public void setArrTblCd(String[] arrTblCd) {
		this.arrTblCd = arrTblCd;
	}

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }
}
