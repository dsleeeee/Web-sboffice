package kr.co.solbipos.base.store.tablelayout.service;

import java.util.List;
import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.store.tableattr.enums.TblGrpFg;

/**
 * @Class Name : TableGroupVO.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
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
public class TableGroupVO extends CmmVO {

    private static final long serialVersionUID = 5423133339606391890L;
    /** 매장코드 */
    private String storeCd;
    /** 테이블그룹코드 */
    private String tblGrpCd;
    /** 테이블그룹구분-일반,배달 */
    private TblGrpFg tblGrpFg;
    /** 테이블그룹명 */
    private String tblGrpNm;
    /** 배경이미지명 */
    private String bgImgNm;
    /** 배경색 */
    private String bgColor;
    /** 표기순서 */
    private Long dispSeq;
    /** 사용여부 */
    private String useYn;
    /** 테이블들 */
    private List<TableVO> tables;
    
    
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
     * @return the tblGrpCd
     */
    public String getTblGrpCd() {
        return tblGrpCd;
    }
    /**
     * @param tblGrpCd the tblGrpCd to set
     */
    public void setTblGrpCd(String tblGrpCd) {
        this.tblGrpCd = tblGrpCd;
    }
    /**
     * @return the tblGrpFg
     */
    public TblGrpFg getTblGrpFg() {
        return tblGrpFg;
    }
    /**
     * @param tblGrpFg the tblGrpFg to set
     */
    public void setTblGrpFg(TblGrpFg tblGrpFg) {
        this.tblGrpFg = tblGrpFg;
    }
    /**
     * @return the tblGrpNm
     */
    public String getTblGrpNm() {
        return tblGrpNm;
    }
    /**
     * @param tblGrpNm the tblGrpNm to set
     */
    public void setTblGrpNm(String tblGrpNm) {
        this.tblGrpNm = tblGrpNm;
    }
    /**
     * @return the bgImgNm
     */
    public String getBgImgNm() {
        return bgImgNm;
    }
    /**
     * @param bgImgNm the bgImgNm to set
     */
    public void setBgImgNm(String bgImgNm) {
        this.bgImgNm = bgImgNm;
    }
    /**
     * @return the bgColor
     */
    public String getBgColor() {
        return bgColor;
    }
    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    /**
     * @return the dispSeq
     */
    public Long getDispSeq() {
        return dispSeq;
    }
    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(Long dispSeq) {
        this.dispSeq = dispSeq;
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
     * @return the tables
     */
    public List<TableVO> getTables() {
        return tables;
    }
    /**
     * @param tables the tables to set
     */
    public void setTables(List<TableVO> tables) {
        this.tables = tables;
    }

}
