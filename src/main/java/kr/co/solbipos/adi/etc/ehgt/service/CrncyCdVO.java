package kr.co.solbipos.adi.etc.ehgt.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : HqCdVO.java
 * @Description : 부가서비스 > 환율 관리 > 통화구분 코드
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.09  조병준      최초생성
 *
 * @author NHN한국사이버결제 조병준
 * @since 2018. 08.09
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class CrncyCdVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    /** 조직코드 */
    private String orgnCd;
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
    /** 사용컬럼명 */
    private String useColNm;
    /** 사용여부 */
    private UseYn useYn;
    
    
    /**
     * @return the orgnCd
     */
    public String getOrgnCd() {
        return orgnCd;
    }
    /**
     * @param orgnCd the orgnCd to set
     */
    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
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
     * @return the useColNm
     */
    public String getUseColNm() {
        return useColNm;
    }
    /**
     * @param useColNm the useColNm to set
     */
    public void setUseColNm(String useColNm) {
        this.useColNm = useColNm;
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
