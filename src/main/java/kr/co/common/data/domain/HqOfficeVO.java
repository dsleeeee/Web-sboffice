package kr.co.common.data.domain;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : HqOfficeVO.java
 * @Description : 본사 조회 공통 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.01  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 11.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqOfficeVO extends PageVO {

    private static final long serialVersionUID = -2340647114412075524L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사코드 */
    private String hqOfficeNm;
    /** 본사코드 */
    private String ownerNm;
    /** 본사코드 */
    private String bizNo;
    /** 본사코드 */
    private String sysStatFg;
    /** 본사코드 */
    private String clsFg;
    /** 업체코드 */
    private String agencyCd;
    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;


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
     * @return the hqOfficeNm
     */

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    /**
     * @return the ownerNm
     */

    public String getOwnerNm() {
        return ownerNm;
    }

    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    /**
     * @return the bizNo
     */

    public String getBizNo() {
        return bizNo;
    }

    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    /**
     * @return the sysStatFg
     */

    public String getSysStatFg() {
        return sysStatFg;
    }

    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    /**
     * @return the clsFg
     */

    public String getClsFg() {
        return clsFg;
    }

    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
}
