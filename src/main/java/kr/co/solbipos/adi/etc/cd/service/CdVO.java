package kr.co.solbipos.adi.etc.cd.service;

import kr.co.solbipos.application.common.service.CmmVO;

import java.util.Date;

/**
 * @Class Name : CdVO.java
 * @Description : 부가서비스 > 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.13  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 09.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CdVO extends CmmVO {

    private static final long serialVersionUID = -1046090570078823455L;
    /** 소속구분 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 가맹점코드 */
    private String storeCd;
    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
    /** 명칭코드코드 */
    private String nmcodeCd;
    /** 명칭코드명 */
    private String nmcodeNm;
    /** 명칭코드항목1 */
    private String nmcodeItem1;
    /** 명칭코드항목2 */
    private String nmcodeItem2;
    /** 사용여부 */
    private String useYn;
    /** 세부명칭갯수 */
    private String cnt;


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
     * @return the nmcodeGrpCd
     */
    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    /**
     * @param nmcodeGrpCd the nmcodeGrpCd to set
     */
    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    /**
     * @return the nmcodeCd
     */
    public String getNmcodeCd() {
        return nmcodeCd;
    }

    /**
     * @param nmcodeCd the nmcodeCd to set
     */
    public void setNmcodeCd(String nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
    }

    /**
     * @return the nmcodeNm
     */
    public String getNmcodeNm() {
        return nmcodeNm;
    }

    /**
     * @param nmcodeNm the nmcodeNm to set
     */
    public void setNmcodeNm(String nmcodeNm) {
        this.nmcodeNm = nmcodeNm;
    }

    /**
     * @return the nmcodeItem1
     */
    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    /**
     * @param nmcodeItem1 the nmcodeItem1 to set
     */
    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    /**
     * @return the nmcodeItem2
     */
    public String getNmcodeItem2() {
        return nmcodeItem2;
    }

    /**
     * @param nmcodeItem2 the nmcodeItem2 to set
     */
    public void setNmcodeItem2(String nmcodeItem2) {
        this.nmcodeItem2 = nmcodeItem2;
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

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
