package kr.co.solbipos.sys.stats.userWebHist.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : UserWebHistVO.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 * @ 2026.09.22  김유승      조회정보(조회건수) 팝업 조회조건 추가
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class UserWebHistVO extends PageVO {

    private static final long serialVersionUID = -2914510780423954700L;

    /** 사용자ID */
    private String userId;

    /** 사용자명 */
    private String userNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 로그인 IP */
    private String loginIp;

    /** 버추얼로그인 사용자 아이디 */
    private String vUserId;

    /** 세션 삭제 비밀번호 */
    private String password;

    /** 사용자 세션 아이디 */
    private String sessionId;

    /** [조회정보 팝업] 조회기간 시작 (YYYYMMDD) */
    private String chkDay1;

    /** [조회정보 팝업] 조회기간 종료 (YYYYMMDD) */
    private String chkDay2;

    /** [조회정보 팝업] USER_ID 포함검색 (입력 시 임계치 미적용) */
    private String chkUserId;

    /** [조회정보 팝업] IP별 상세조회 대상 USER_ID */
    private String ipUserId;

    /** [조회정보 팝업] ① 기간 조회 실건수 임계치 (0 이하 = 미사용) */
    private int thChkToday;

    /** [조회정보 팝업] ② 최근5분 조회 실건수 임계치 (0 이하 = 미사용) */
    private int thChk5m;

    /** [조회정보 팝업] ③ 기간 국외IP 접근 임계치 (0 이하 = 미사용) */
    private int thBlkToday;

    /** [조회정보 팝업] ④ 최근10분 국외IP 접근 임계치 (0 이하 = 미사용) */
    private int thBlk10m;

    /** [조회정보 팝업] ⑥ 기간 로그인 시도 임계치 (0 이하 = 미사용) */
    private int thLgnToday;

    /** [조회정보 팝업] ⑦ 최근5분 로그인 시도 임계치 (0 이하 = 미사용) */
    private int thLgn5m;

    /** [일시정지 이력] 처리구분 P:일시정지 R:해제 */
    private String procFg;

    /** [일시정지 이력] 처리일시 */
    private String procDt;

    /** [일시정지 이력] 처리자 */
    private String procId;

    /** [일시정지 이력] 처리 전 사용자상태 */
    private String befStatFg;

    /** [일시정지 이력] 처리 후 사용자상태 */
    private String aftStatFg;

    /** [일시정지 이력] 반영여부 Y/N */
    private String resultFg;

    /** [일시정지 이력] 비고 */
    private String remark;

    /** [일시정지 이력] 화면 요약 텍스트 */
    private String summaryTxt;

    /** [일시정지 이력] 화면 스냅샷 — 조회정보 ① 그리드 선택 행 */
    private String risk;
    private int    todayReal;
    private int    todayTot;
    private int    min5Real;
    private int    meReal;
    private int    lgnTodayTot;
    private int    lgnTodayErr;
    private int    lgnMin5Err;
    private int    lgnIpCnt;
    private int    blkTodayCnt;
    private int    blkMin10Cnt;
    private String chkLastIp;
    private int    chkIpCnt;
    private String ipHit;
    private String countryCode;
    private String blockReason;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getvUserId() {
        return vUserId;
    }

    public void setvUserId(String vUserId) {
        this.vUserId = vUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getChkDay1() {
        return chkDay1;
    }

    public void setChkDay1(String chkDay1) {
        this.chkDay1 = chkDay1;
    }

    public String getChkDay2() {
        return chkDay2;
    }

    public void setChkDay2(String chkDay2) {
        this.chkDay2 = chkDay2;
    }

    public String getChkUserId() {
        return chkUserId;
    }

    public void setChkUserId(String chkUserId) {
        this.chkUserId = chkUserId;
    }

    public String getIpUserId() {
        return ipUserId;
    }

    public void setIpUserId(String ipUserId) {
        this.ipUserId = ipUserId;
    }

    public int getThChkToday() {
        return thChkToday;
    }

    public void setThChkToday(int thChkToday) {
        this.thChkToday = thChkToday;
    }

    public int getThChk5m() {
        return thChk5m;
    }

    public void setThChk5m(int thChk5m) {
        this.thChk5m = thChk5m;
    }

    public int getThBlkToday() {
        return thBlkToday;
    }

    public void setThBlkToday(int thBlkToday) {
        this.thBlkToday = thBlkToday;
    }

    public int getThBlk10m() {
        return thBlk10m;
    }

    public void setThBlk10m(int thBlk10m) {
        this.thBlk10m = thBlk10m;
    }

    public int getThLgnToday() {
        return thLgnToday;
    }

    public void setThLgnToday(int thLgnToday) {
        this.thLgnToday = thLgnToday;
    }

    public int getThLgn5m() {
        return thLgn5m;
    }

    public void setThLgn5m(int thLgn5m) {
        this.thLgn5m = thLgn5m;
    }

    public String getProcFg() { return procFg; }
    public void setProcFg(String procFg) { this.procFg = procFg; }
    public String getProcDt() { return procDt; }
    public void setProcDt(String procDt) { this.procDt = procDt; }
    public String getProcId() { return procId; }
    public void setProcId(String procId) { this.procId = procId; }
    public String getBefStatFg() { return befStatFg; }
    public void setBefStatFg(String befStatFg) { this.befStatFg = befStatFg; }
    public String getAftStatFg() { return aftStatFg; }
    public void setAftStatFg(String aftStatFg) { this.aftStatFg = aftStatFg; }
    public String getResultFg() { return resultFg; }
    public void setResultFg(String resultFg) { this.resultFg = resultFg; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getSummaryTxt() { return summaryTxt; }
    public void setSummaryTxt(String summaryTxt) { this.summaryTxt = summaryTxt; }
    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }
    public int getTodayReal() { return todayReal; }
    public void setTodayReal(int todayReal) { this.todayReal = todayReal; }
    public int getTodayTot() { return todayTot; }
    public void setTodayTot(int todayTot) { this.todayTot = todayTot; }
    public int getMin5Real() { return min5Real; }
    public void setMin5Real(int min5Real) { this.min5Real = min5Real; }
    public int getMeReal() { return meReal; }
    public void setMeReal(int meReal) { this.meReal = meReal; }
    public int getLgnTodayTot() { return lgnTodayTot; }
    public void setLgnTodayTot(int lgnTodayTot) { this.lgnTodayTot = lgnTodayTot; }
    public int getLgnTodayErr() { return lgnTodayErr; }
    public void setLgnTodayErr(int lgnTodayErr) { this.lgnTodayErr = lgnTodayErr; }
    public int getLgnMin5Err() { return lgnMin5Err; }
    public void setLgnMin5Err(int lgnMin5Err) { this.lgnMin5Err = lgnMin5Err; }
    public int getLgnIpCnt() { return lgnIpCnt; }
    public void setLgnIpCnt(int lgnIpCnt) { this.lgnIpCnt = lgnIpCnt; }
    public int getBlkTodayCnt() { return blkTodayCnt; }
    public void setBlkTodayCnt(int blkTodayCnt) { this.blkTodayCnt = blkTodayCnt; }
    public int getBlkMin10Cnt() { return blkMin10Cnt; }
    public void setBlkMin10Cnt(int blkMin10Cnt) { this.blkMin10Cnt = blkMin10Cnt; }
    public String getChkLastIp() { return chkLastIp; }
    public void setChkLastIp(String chkLastIp) { this.chkLastIp = chkLastIp; }
    public int getChkIpCnt() { return chkIpCnt; }
    public void setChkIpCnt(int chkIpCnt) { this.chkIpCnt = chkIpCnt; }
    public String getIpHit() { return ipHit; }
    public void setIpHit(String ipHit) { this.ipHit = ipHit; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getBlockReason() { return blockReason; }
    public void setBlockReason(String blockReason) { this.blockReason = blockReason; }
}
