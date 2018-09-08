package kr.co.solbipos.pos.confg.func.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;

/**
 * @Class Name : FuncStoreVO.java
 * @Description : 포스관리 > POS 설정관리 > POS 기능정의 > 적용 매장 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.29  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 08.29
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class FuncStoreVO extends CmmVO {

    /** 기능키번호 */
    private String fnkeyNo;
    /** 기능키명 */
    private String fnkeyNm;
    /** 기능키구분 */
    private String fnkeyFg;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사사업장명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 용도구분  */
    private String clsFg;
    /** 시스템상태구분 */
    private SysStatFg sysStatFg;
    /** 기능키 적용매장 등록여부 */
    private String regYn;
    /** 기능키 관련 프로시져 실행 결과 */
    private String result;

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
     * @return the storeNm
     */

    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
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

    /**
     * @return the sysStatFg
     */

    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }

    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    /**
     * @return the regYn
     */

    public String getRegYn() {
        return regYn;
    }

    /**
     * @param regYn the regYn to set
     */
    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    /**
     * @return the result
     * @param storeResult
     */

    public String getResult(String storeResult) {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
}
