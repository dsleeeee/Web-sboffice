package kr.co.solbipos.accounting.accountingMain.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : AccountingMainVO.java
 * @Description : 벤슨 > 회계관리 > 회계관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class AccountingMainVO extends PageVO {
    private static final long serialVersionUID = 1L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 시작월 */
    private String startMonth;

    /** 종료월 */
    private String endMonth;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 옵션01 */
    private String option01;

    /** 옵션02 */
    private String option02;

    /** 옵션03 */
    private Boolean option03;

    /** 옵션04 */
    private String option04;

    /** 공통코드 : 코드 */
    private String acNmcodeCd;

    /** 공통코드 : 코드명 */
    private String acNmcodeNm;

    /** 공통코드 : 비고1 */
    private String acNmcodeEtc01;

    /** 공통코드 : 비고2 */
    private String acNmcodeEtc02;

    /** 공통코드 : 비고3 */
    private String acNmcodeEtc03;

    /** 공통코드 상세 : 코드 */
    private String acNmcodeDtlCd;

    /** 공통코드 상세 : 코드명 */
    private String acNmcodeDtlNm;

    /** 공통코드 상세 : 비고1 */
    private String acNmcodeDtlEtc01;

    /** 공통코드 상세 : 비고2 */
    private String acNmcodeDtlEtc02;

    /** 공통코드 상세 : 비고3 */
    private String acNmcodeDtlEtc03;

    /** 사용여부 */
    private String useYn;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getOption01() {
        return option01;
    }

    public void setOption01(String option01) {
        this.option01 = option01;
    }

    public String getOption02() {
        return option02;
    }

    public void setOption02(String option02) {
        this.option02 = option02;
    }

    public Boolean getOption03() {
        return option03;
    }

    public void setOption03(Boolean option03) {
        this.option03 = option03;
    }

    public String getOption04() {
        return option04;
    }

    public void setOption04(String option04) {
        this.option04 = option04;
    }

    public String getAcNmcodeCd() {
        return acNmcodeCd;
    }

    public void setAcNmcodeCd(String acNmcodeCd) {
        this.acNmcodeCd = acNmcodeCd;
    }

    public String getAcNmcodeNm() {
        return acNmcodeNm;
    }

    public void setAcNmcodeNm(String acNmcodeNm) {
        this.acNmcodeNm = acNmcodeNm;
    }

    public String getAcNmcodeEtc01() {
        return acNmcodeEtc01;
    }

    public void setAcNmcodeEtc01(String acNmcodeEtc01) {
        this.acNmcodeEtc01 = acNmcodeEtc01;
    }

    public String getAcNmcodeEtc02() {
        return acNmcodeEtc02;
    }

    public void setAcNmcodeEtc02(String acNmcodeEtc02) {
        this.acNmcodeEtc02 = acNmcodeEtc02;
    }

    public String getAcNmcodeEtc03() {
        return acNmcodeEtc03;
    }

    public void setAcNmcodeEtc03(String acNmcodeEtc03) {
        this.acNmcodeEtc03 = acNmcodeEtc03;
    }

    public String getAcNmcodeDtlCd() {
        return acNmcodeDtlCd;
    }

    public void setAcNmcodeDtlCd(String acNmcodeDtlCd) {
        this.acNmcodeDtlCd = acNmcodeDtlCd;
    }

    public String getAcNmcodeDtlNm() {
        return acNmcodeDtlNm;
    }

    public void setAcNmcodeDtlNm(String acNmcodeDtlNm) {
        this.acNmcodeDtlNm = acNmcodeDtlNm;
    }

    public String getAcNmcodeDtlEtc01() {
        return acNmcodeDtlEtc01;
    }

    public void setAcNmcodeDtlEtc01(String acNmcodeDtlEtc01) {
        this.acNmcodeDtlEtc01 = acNmcodeDtlEtc01;
    }

    public String getAcNmcodeDtlEtc02() {
        return acNmcodeDtlEtc02;
    }

    public void setAcNmcodeDtlEtc02(String acNmcodeDtlEtc02) {
        this.acNmcodeDtlEtc02 = acNmcodeDtlEtc02;
    }

    public String getAcNmcodeDtlEtc03() {
        return acNmcodeDtlEtc03;
    }

    public void setAcNmcodeDtlEtc03(String acNmcodeDtlEtc03) {
        this.acNmcodeDtlEtc03 = acNmcodeDtlEtc03;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
