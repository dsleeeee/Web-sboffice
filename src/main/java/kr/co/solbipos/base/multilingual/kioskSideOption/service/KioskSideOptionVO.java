package kr.co.solbipos.base.multilingual.kioskSideOption.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KioskSideOptionVO extends PageVO {

    private static final long serialVersionUID = 7945879495044857803L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 키맵그룹(카테고리그룹) */
    private String tuClsType;
    /** 카테고리코드 */
    private String tuClsCd;
    /** 카테고리명(영문)*/
    private String tuClsEnNm;
    /** 카테고리명(중문) */
    private String tuClsCnNm;
    /** 카테고리명(일문) */
    private String tuClsJpNm;
    /** 사이드세트구분 */
    private String sdselTypeFg;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 사이드선택그룹명 */
    private String sdselGrpNm;
    /** 사이드선택그룹명[영문] */
    private String sdselGrpEnNm;
    /** 사이드선택그룹명[중문] */
    private String sdselGrpCnNm;
    /** 사이드선택그룹명[일문] */
    private String sdselGrpJpNm;
    /** 사이드선택분류코드 */
    private String sdselClassCd;
    /** 사이드선택분류명 */
    private String sdselClassNm;
    /** 사이드선택분류명[영문] */
    private String sdselClassEnNm;
    /** 사이드선택분류명[중문] */
    private String sdselClassCnNm;
    /** 사이드선택분류명[일문] */
    private String sdselClassJpNm;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getTuClsType() {
        return tuClsType;
    }

    public void setTuClsType(String tuClsType) {
        this.tuClsType = tuClsType;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getTuClsEnNm() {
        return tuClsEnNm;
    }

    public void setTuClsEnNm(String tuClsEnNm) {
        this.tuClsEnNm = tuClsEnNm;
    }

    public String getTuClsCnNm() {
        return tuClsCnNm;
    }

    public void setTuClsCnNm(String tuClsCnNm) {
        this.tuClsCnNm = tuClsCnNm;
    }

    public String getTuClsJpNm() {
        return tuClsJpNm;
    }

    public void setTuClsJpNm(String tuClsJpNm) {
        this.tuClsJpNm = tuClsJpNm;
    }

    public String getSdselTypeFg() {
        return sdselTypeFg;
    }

    public void setSdselTypeFg(String sdselTypeFg) {
        this.sdselTypeFg = sdselTypeFg;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    public String getSdselGrpEnNm() {
        return sdselGrpEnNm;
    }

    public void setSdselGrpEnNm(String sdselGrpEnNm) {
        this.sdselGrpEnNm = sdselGrpEnNm;
    }

    public String getSdselGrpCnNm() {
        return sdselGrpCnNm;
    }

    public void setSdselGrpCnNm(String sdselGrpCnNm) {
        this.sdselGrpCnNm = sdselGrpCnNm;
    }

    public String getSdselGrpJpNm() {
        return sdselGrpJpNm;
    }

    public void setSdselGrpJpNm(String sdselGrpJpNm) {
        this.sdselGrpJpNm = sdselGrpJpNm;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    public String getSdselClassEnNm() {
        return sdselClassEnNm;
    }

    public void setSdselClassEnNm(String sdselClassEnNm) {
        this.sdselClassEnNm = sdselClassEnNm;
    }

    public String getSdselClassCnNm() {
        return sdselClassCnNm;
    }

    public void setSdselClassCnNm(String sdselClassCnNm) {
        this.sdselClassCnNm = sdselClassCnNm;
    }

    public String getSdselClassJpNm() {
        return sdselClassJpNm;
    }

    public void setSdselClassJpNm(String sdselClassJpNm) {
        this.sdselClassJpNm = sdselClassJpNm;
    }
}
