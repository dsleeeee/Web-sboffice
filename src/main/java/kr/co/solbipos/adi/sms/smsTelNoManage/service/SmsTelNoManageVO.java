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

    /** 완료상태에서 빠지는 행의 관리요청번호(중복체크 제외 대상, 콤마구분) */
    private String modCertId;

    /** 완료상태에서 빠지는 행의 관리요청번호 목록 */
    private String[] modCertIdList;

    /** 소속코드 */
    private String srchOrgnCd;

    /** 소속명 */
    private String srchOrgnNm;

    /** 발신번호 유형 (0:휴대폰번호, 1:유선번호) */
    private String telFg;

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

    public String getModCertId() {
        return modCertId;
    }

    public void setModCertId(String modCertId) {
        this.modCertId = modCertId;
    }

    public String[] getModCertIdList() {
        return modCertIdList;
    }

    public void setModCertIdList(String[] modCertIdList) {
        this.modCertIdList = modCertIdList;
    }

    public String getSrchOrgnCd() { return srchOrgnCd; }

    public void setSrchOrgnCd(String srchOrgnCd) { this.srchOrgnCd = srchOrgnCd; }

    public String getSrchOrgnNm() { return srchOrgnNm; }

    public void setSrchOrgnNm(String srchOrgnNm) { this.srchOrgnNm = srchOrgnNm; }

    public String getTelFg() { return telFg; }

    public void setTelFg(String telFg) { this.telFg = telFg; }

    /* ===== 음성파일등록 (TB_WB_BOARD_ATCH 재사용) ===== */
    /** 신청건 식별번호(BOARD_SEQ_NO 역할) */
    private String boardSeqNo;
    /** 첨부 인덱스(신청건 내 파일순번) */
    private String idx;
    /** 파일 경로 */
    private String filePath;
    /** 저장 파일명 */
    private String fileNm;
    /** 원본 파일명 */
    private String orginlFileNm;
    /** 파일 확장자 */
    private String fileExt;
    /** 임시경로(키 저장용 : ORGN_CD|USER_ID|CERT_ID) */
    private String tempPath;
    /** 음성파일 건수 */
    private String voiceFileCnt;

    public String getBoardSeqNo() { return boardSeqNo; }
    public void setBoardSeqNo(String boardSeqNo) { this.boardSeqNo = boardSeqNo; }
    public String getIdx() { return idx; }
    public void setIdx(String idx) { this.idx = idx; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getFileNm() { return fileNm; }
    public void setFileNm(String fileNm) { this.fileNm = fileNm; }
    public String getOrginlFileNm() { return orginlFileNm; }
    public void setOrginlFileNm(String orginlFileNm) { this.orginlFileNm = orginlFileNm; }
    public String getFileExt() { return fileExt; }
    public void setFileExt(String fileExt) { this.fileExt = fileExt; }
    public String getTempPath() { return tempPath; }
    public void setTempPath(String tempPath) { this.tempPath = tempPath; }
    public String getVoiceFileCnt() { return voiceFileCnt; }
    public void setVoiceFileCnt(String voiceFileCnt) { this.voiceFileCnt = voiceFileCnt; }
}