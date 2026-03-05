package kr.co.solbipos.sys.admin.posLogCollectMgmt.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : PosLogCollectMgmtVO.java
 * @Description : 시스템관리 > 관리자기능 > POS로그수집관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.04  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.03.04
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class PosLogCollectMgmtVO extends PageVO {

    private static final long serialVersionUID = -5344255068153121893L;

    /** 명령타입 */
    private String commandType;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 영업일자(등록일자) */
    private String saleDate;

    /** 포스번호 */
    private String posNo;

    /** 로그타입 */
    private String logType;

    /** 시작일자 */
    private String dateFrom;

    /** 종료일자 */
    private String dateTo;

    /** SQL문 */
    private String sql;

    /** DB백업 포함여부 */
    private String includeDbBackup;

    /** 스마트오더 수집여부 */
    private String smartOrderYn;

    /** VCAT 경로 */
    private String vcatPath;

    /** 사유 */
    private String remark;

    /** 순번 */
    private String seq;

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getIncludeDbBackup() {
        return includeDbBackup;
    }

    public void setIncludeDbBackup(String includeDbBackup) {
        this.includeDbBackup = includeDbBackup;
    }

    public String getSmartOrderYn() {
        return smartOrderYn;
    }

    public void setSmartOrderYn(String smartOrderYn) {
        this.smartOrderYn = smartOrderYn;
    }

    public String getVcatPath() {
        return vcatPath;
    }

    public void setVcatPath(String vcatPath) {
        this.vcatPath = vcatPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
