package kr.co.solbipos.store.manage.envConfgBatchChange.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : EnvConfgBatchChangeVO.java
 * @Description : 기초관리 > 매장정보관리 > 환경변수일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.02.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class EnvConfgBatchChangeVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 매장명 */
    private String storeNm;

    /** 등록여부 */
    private String useYn;

    /** 환경설정코드 */
    private String envstCd;

    /** 환경설정값 */
    private String envstVal;

    /** 시스템패스워드 */
    private String systemPw;

    /** 직접입력여부 */
    private String dirctInYn;

    /** 대상구분 */
    private String targtFg;

    /** 구분 */
    private String gubun;

    /** 업체코드 */
    private String agencyCd;

    /** POS번호 */
    private String posNo;

    /** 기능키번호 */
    private String fnkeyNo;

    /** 기능키명 */
    private String fnkeyNm;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() { return hqOfficeNm; }

    public void setHqOfficeNm(String hqOfficeNm) { this.hqOfficeNm = hqOfficeNm; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getEnvstCd() { return envstCd; }

    public void setEnvstCd(String envstCd) { this.envstCd = envstCd; }

    public String getEnvstVal() { return envstVal; }

    public void setEnvstVal(String envstVal) { this.envstVal = envstVal; }

    public String getSystemPw() { return systemPw; }

    public void setSystemPw(String systemPw) { this.systemPw = systemPw; }

    public String getDirctInYn() { return dirctInYn; }

    public void setDirctInYn(String dirctInYn) { this.dirctInYn = dirctInYn; }

    public String getTargtFg() { return targtFg; }

    public void setTargtFg(String targtFg) { this.targtFg = targtFg; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getAgencyCd() { return agencyCd; }

    public void setAgencyCd(String agencyCd) { this.agencyCd = agencyCd; }

    public String getPosNo() { return posNo; }

    public void setPosNo(String posNo) { this.posNo = posNo; }

    public String getFnkeyNo() {
        return fnkeyNo;
    }

    public void setFnkeyNo(String fnkeyNo) {
        this.fnkeyNo = fnkeyNo;
    }

    public String getFnkeyNm() {
        return fnkeyNm;
    }

    public void setFnkeyNm(String fnkeyNm) {
        this.fnkeyNm = fnkeyNm;
    }
}