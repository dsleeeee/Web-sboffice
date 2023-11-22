package kr.co.solbipos.base.multilingual.kiosk.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KioskVO extends PageVO {

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
}
