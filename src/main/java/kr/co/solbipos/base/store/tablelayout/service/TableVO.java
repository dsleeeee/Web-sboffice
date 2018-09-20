package kr.co.solbipos.base.store.tablelayout.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.base.store.tableattr.enums.TblTypeFg;

/**
 * @Class Name : TableVO.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
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
public class TableVO extends CmmVO {

    private static final long serialVersionUID = -5141418534001504479L;
    /** 매장코드 */
    private String storeCd;
    /** 테이블코드 */
    private String tblCd;
    /** 테이블명 */
    private String tblNm;
    /** 테이블그룹코드 */
    private String tblGrpCd;
    /** 테이블좌석수 */
    private Long tblSeatCnt = 0L;;
    /** X */
    private Long x = 0L;
    /** Y */
    private Long y = 0L;
    /** 폭 */
    private Long width = 0L;
    /** 높이 */
    private Long height = 0L;
    /** 테이블유형구분 */
    private TblTypeFg tblTypeFg;
    /** 사용여부 */
    private String useYn;
    
    
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
     * @return the tblCd
     */
    public String getTblCd() {
        return tblCd;
    }
    /**
     * @param tblCd the tblCd to set
     */
    public void setTblCd(String tblCd) {
        this.tblCd = tblCd;
    }
    /**
     * @return the tblNm
     */
    public String getTblNm() {
        return tblNm;
    }
    /**
     * @param tblNm the tblNm to set
     */
    public void setTblNm(String tblNm) {
        this.tblNm = tblNm;
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
     * @return the tblSeatCnt
     */
    public Long getTblSeatCnt() {
        return tblSeatCnt;
    }
    /**
     * @param tblSeatCnt the tblSeatCnt to set
     */
    public void setTblSeatCnt(Long tblSeatCnt) {
        this.tblSeatCnt = tblSeatCnt;
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
     * @return the tblTypeFg
     */
    public TblTypeFg getTblTypeFg() {
        return tblTypeFg;
    }
    /**
     * @param tblTypeFg the tblTypeFg to set
     */
    public void setTblTypeFg(TblTypeFg tblTypeFg) {
        this.tblTypeFg = tblTypeFg;
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

}
