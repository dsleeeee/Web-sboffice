package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreFnkeyVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리 > 기능키복사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.02  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.11.02
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreFnkeyVO extends CmmVO {

    private static final long serialVersionUID = -4479796927422847740L;

    /** 매장코드 */
    private String storeCd;
    /** 기능키번호 */
    private String fnkeyNo;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 포스구분 */
    private String posFg;
    /** 인증여부 */
    private String authYn;
    /** 이미지파일명 */
    private String imgFileNm;
    /** 표기순번 */
    private String dispSeq;
    /** 위치조정여부 */
    private String posiAdjYn;
    /** 사용여부 */
    private String useYn;
    /** 복사할 매장코드 */
    private String copyStoreCd;


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
     * @return the fnkeyNo
     */

    public String getFnkeyNo() {
        return fnkeyNo;
    }

    /**
     * @param fnkeyNo the fnkeyNo to set
     */
    public void setFnkeyNo(String fnkeyNo) {
        this.fnkeyNo = fnkeyNo;
    }

    /**
     * @return the fnkeyNm
     */

    public String getFnkeyNm() {
        return fnkeyNm;
    }

    /**
     * @param fnkeyNm the fnkeyNm to set
     */
    public void setFnkeyNm(String fnkeyNm) {
        this.fnkeyNm = fnkeyNm;
    }

    /**
     * @return the fnkeyFg
     */

    public String getFnkeyFg() {
        return fnkeyFg;
    }

    /**
     * @param fnkeyFg the fnkeyFg to set
     */
    public void setFnkeyFg(String fnkeyFg) {
        this.fnkeyFg = fnkeyFg;
    }

    /**
     * @return the posFg
     */

    public String getPosFg() {
        return posFg;
    }

    /**
     * @param posFg the posFg to set
     */
    public void setPosFg(String posFg) {
        this.posFg = posFg;
    }

    /**
     * @return the authYn
     */

    public String getAuthYn() {
        return authYn;
    }

    /**
     * @param authYn the authYn to set
     */
    public void setAuthYn(String authYn) {
        this.authYn = authYn;
    }

    /**
     * @return the imgFileNm
     */

    public String getImgFileNm() {
        return imgFileNm;
    }

    /**
     * @param imgFileNm the imgFileNm to set
     */
    public void setImgFileNm(String imgFileNm) {
        this.imgFileNm = imgFileNm;
    }

    /**
     * @return the dispSeq
     */

    public String getDispSeq() {
        return dispSeq;
    }

    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }

    /**
     * @return the posiAdjYn
     */

    public String getPosiAdjYn() {
        return posiAdjYn;
    }

    /**
     * @param posiAdjYn the posiAdjYn to set
     */
    public void setPosiAdjYn(String posiAdjYn) {
        this.posiAdjYn = posiAdjYn;
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
