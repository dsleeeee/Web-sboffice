package kr.co.solbipos.sys.cd.systemcd.service;

import kr.co.solbipos.application.common.service.CmmVO;

import java.util.Date;

/**
 * @Class Name : SystemCdVO.java
 * @Description : 시스템관리 > 코드관리 > 시스템 명칭관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SystemCdVO extends CmmVO {

    private static final long serialVersionUID = -479310152661794262L;
    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;
    /** 명칭코드코드 */
    private String nmcodeCd;
    /** 명칭코드명 */
    private String nmcodeNm;
    /** 명칭코드항목1 */
    private String nmcodeItem1;
    /** 명칭코드항목2 */
    private Date nmcodeItem2;
    /** 사용컬럼명 */
    private String useColNm;
    
    
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
    public Date getNmcodeItem2() {
        return nmcodeItem2;
    }
    /**
     * @param nmcodeItem2 the nmcodeItem2 to set
     */
    public void setNmcodeItem2(Date nmcodeItem2) {
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
    
}
