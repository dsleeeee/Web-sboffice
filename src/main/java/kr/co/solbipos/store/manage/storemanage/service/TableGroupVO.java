package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : TableGroupVO.java
 * @Description :
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.30  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.08.30
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TableGroupVO extends CmmVO {

    /**  [회원소속코드] */
    private String storeCd;
    /**  [테이블그룹코드] */
    private String tblGrpCd;
    /**  [테이블그룹명] */
    private String tblGrpNm;
    /**  [테이블그룹코드] */
    private String tblGrpFg;
    /**  [배경이미지명] */
    private String bgImgNm;
    /**  [표기순번] */
    private int dispSeq;
    /**  [사용여부] */
    private UseYn useYn;


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
     * @return the tblGrpFg
     */

    public String getTblGrpFg() {
        return tblGrpFg;
    }

    /**
     * @param tblGrpFg the tblGrpFg to set
     */
    public void setTblGrpFg(String tblGrpFg) {
        this.tblGrpFg = tblGrpFg;
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
     * @return the dispSeq
     */

    public int getDispSeq() {
        return dispSeq;
    }

    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(int dispSeq) {
        this.dispSeq = dispSeq;
    }

    /**
     * @return the useYn
     */

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }
}
