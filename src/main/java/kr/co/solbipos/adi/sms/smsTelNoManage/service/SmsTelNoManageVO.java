package kr.co.solbipos.adi.sms.smsTelNoManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SmsTelNoManageVO.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SmsTelNoManageVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 소속코드 */
    private String orgnCd;

    /** 소속명 */
    private String orgnNm;

    /** 전화번호 */
    private String telNo;

    /** 노출우선순위 */
    private String useSeq;

    /** 관리요청번호 */
    private String certId;

    /** 결과코드 */
    private String resCd;

    /** 사용여부 */
    private String useYn;

    /** 처리구분 */
    private String addProcFg;

    /** 파일명 */
    private String fileName;

    /** 비고 */
    private String remark;

    /** 사용자ID */
    private String userId;

    /** 반려사유 */
    private String returnRemark;

    /** 수정전 전화번호 */
    private String backTelNo;

    /** 수정전 처리구분 */
    private String backAddProcFg;

    /** 통화일시 */
    private String telDt;

    /** 신청자 이름 */
    private String addSmsUserNm;

    /** 신청자 연락처 */
    private String addSmsTelNo;

    /** 다운로드 파일명 */
    private String downloadFileName;

    /** 전화번호 */
    private String chkTelNo;

    /** 전화번호 */
    private String[] chkTelNoList;

    /** 전화번호 */
    private String modTelNo;

    /** 전화번호 */
    private String[] modTelNoList;

    public String getOrgnCd() { return orgnCd; }

    public void setOrgnCd(String orgnCd) { this.orgnCd = orgnCd; }

    public String getOrgnNm() { return orgnNm; }

    public void setOrgnNm(String orgnNm) { this.orgnNm = orgnNm; }

    public String getTelNo() { return telNo; }

    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getUseSeq() { return useSeq; }

    public void setUseSeq(String useSeq) { this.useSeq = useSeq; }

    public String getCertId() { return certId; }

    public void setCertId(String certId) { this.certId = certId; }

    public String getResCd() { return resCd; }

    public void setResCd(String resCd) { this.resCd = resCd; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getAddProcFg() { return addProcFg; }

    public void setAddProcFg(String addProcFg) { this.addProcFg = addProcFg; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getReturnRemark() { return returnRemark; }

    public void setReturnRemark(String returnRemark) { this.returnRemark = returnRemark; }

    public String getBackTelNo() { return backTelNo; }

    public void setBackTelNo(String backTelNo) { this.backTelNo = backTelNo; }

    public String getBackAddProcFg() { return backAddProcFg; }

    public void setBackAddProcFg(String backAddProcFg) { this.backAddProcFg = backAddProcFg; }

    public String getTelDt() { return telDt; }

    public void setTelDt(String telDt) { this.telDt = telDt; }

    public String getAddSmsUserNm() { return addSmsUserNm; }

    public void setAddSmsUserNm(String addSmsUserNm) { this.addSmsUserNm = addSmsUserNm; }

    public String getAddSmsTelNo() { return addSmsTelNo; }

    public void setAddSmsTelNo(String addSmsTelNo) { this.addSmsTelNo = addSmsTelNo; }

    public String getDownloadFileName() { return downloadFileName; }

    public void setDownloadFileName(String downloadFileName) { this.downloadFileName = downloadFileName; }

    public String getChkTelNo() { return chkTelNo; }

    public void setChkTelNo(String chkTelNo) {
        this.chkTelNo = chkTelNo;
    }

    public String[] getChkTelNoList() {
        return chkTelNoList;
    }

    public void setChkTelNoList(String[] chkTelNoList) {
        this.chkTelNoList = chkTelNoList;
    }

    public String getModTelNo() {
        return modTelNo;
    }

    public void setModTelNo(String modTelNo) {
        this.modTelNo = modTelNo;
    }

    public String[] getModTelNoList() {
        return modTelNoList;
    }

    public void setModTelNoList(String[] modTelNoList) {
        this.modTelNoList = modTelNoList;
    }
}