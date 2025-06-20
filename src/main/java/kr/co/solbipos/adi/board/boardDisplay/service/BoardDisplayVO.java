package kr.co.solbipos.adi.board.boardDisplay.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BoardDisplayVO.java
 * @Description : 부가서비스 > 게시판 > 일반게시판노출순서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BoardDisplayVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 소속코드 */
    private String orgnCd;

    /** 게시판코드 */
    private String boardCd;

    /** 조회구분 */
    private String gubunCombo;

    /** 조회구분 검색어 */
    private String gubunName;

    /** 게시일련번호 */
    private String boardSeqNo;

    /** 조회 건수 */
    private String viewCnt;

    /** 인덱스 */
    private String idx;

    /** 제목 */
    private String title;

    /** 공지 여부 */
    private String noticeYn;

    /** 승인 구분 */
    private String apprFg;

    /** 게시 대상 구분 */
    private String targetFg;

    /** 게시등록자 이름 */
    private String userNm;

    /** 내용 */
    private String content;

    /** 댓글 건수 */
    private String answerCnt;

    /** 사용자ID */
    private String userId;

    /** 권한 그룹 명 */
    private String authGrpNm;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 조회매장 */
    private String storeNm;

    /** 파일 경로 */
    private String filePath;

    /** 원본 파일 명 */
    private String orginlFileNm;

    /** 파일 명 */
    private String fileNm;

    /** 조회매장 */
    private String storeCds;

    /** 본사 또는 매장 코드 */
    private String partOrgnCd;

    /** 본사 또는 매장 명칭 */
    private String partOrgnNm;

    /** 파일확장자 */
    private String fileExt;

    /** 날짜 */
    private String date;

    /** 비고 */
    private String remark;

    /** 긴급공지여부 */
    private String emergencyYn;

    /** 열람구분 */
    private String gubunReadCombo;

    /** 임시파일경로 */
    private String tempPath;

    /** 웹 접속 경로 */
    private String rootUrl;

    /** FULL SIZE 표시 */
    private String fullSizeYn;

    /** 팝업여부(WEB) */
    private String popupYn;

    /**
     * 소속 그룹 코드
     * 프렌차이즈인 경우 본사코드, 단독매장인 경우 자기자신의 매장코드, 시스템 or 대리점인 경우 AGENCY_CD
     */
    private String orgnGrpCd;

    /** 노출여부 */
    private String topYn;

    /** 노출순서 */
    private String dispSeq;

    /** 강조여부 */
    private Boolean backColorYn;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOrgnCd() {
        return orgnCd;
    }

    public void setOrgnCd(String orgnCd) {
        this.orgnCd = orgnCd;
    }

    public String getBoardCd() { return boardCd; }

    public void setBoardCd(String boardCd) { this.boardCd = boardCd; }

    public String getGubunCombo() {
        return gubunCombo;
    }

    public void setGubunCombo(String gubunCombo) {
        this.gubunCombo = gubunCombo;
    }

    public String getGubunName() { return gubunName; }

    public void setGubunName(String gubunName) { this.gubunName = gubunName; }

    public String getBoardSeqNo() { return boardSeqNo; }

    public void setBoardSeqNo(String boardSeqNo) { this.boardSeqNo = boardSeqNo; }

    public String getViewCnt() { return viewCnt; }

    public void setViewCnt(String viewCnt) { this.viewCnt = viewCnt; }

    public String getIdx() { return idx; }

    public void setIdx(String idx) { this.idx = idx; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getNoticeYn() { return noticeYn; }

    public void setNoticeYn(String noticeYn) { this.noticeYn = noticeYn; }

    public String getApprFg() { return apprFg; }

    public void setApprFg(String apprFg) { this.apprFg = apprFg; }

    public String getTargetFg() { return targetFg; }

    public void setTargetFg(String targetFg) { this.targetFg = targetFg; }

    public String getUserNm() { return userNm; }

    public void setUserNm(String userNm) { this.userNm = userNm; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getAnswerCnt() { return answerCnt; }

    public void setAnswerCnt(String answerCnt) { this.answerCnt = answerCnt; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getAuthGrpNm() { return authGrpNm; }

    public void setAuthGrpNm(String authGrpNm) { this.authGrpNm = authGrpNm; }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getOrginlFileNm() { return orginlFileNm; }

    public void setOrginlFileNm(String orginlFileNm) { this.orginlFileNm = orginlFileNm; }

    public String getFileNm() { return fileNm; }

    public void setFileNm(String fileNm) { this.fileNm = fileNm; }

    public String getStoreCds() { return storeCds; }

    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String getPartOrgnCd() { return partOrgnCd; }

    public void setPartOrgnCd(String partOrgnCd) { this.partOrgnCd = partOrgnCd; }

    public String getPartOrgnNm() { return partOrgnNm; }

    public void setPartOrgnNm(String partOrgnNm) { this.partOrgnNm = partOrgnNm; }

    public String getFileExt() { return fileExt; }

    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getRemark() { return remark; }

    public void seRemark(String remark) { this.remark = remark; }

    public String getGubunReadCombo() {
        return gubunReadCombo;
    }

    public void setGubunReadCombo(String gubunReadCombo) {
        this.gubunReadCombo = gubunReadCombo;
    }

    public String getEmergencyYn() {
        return emergencyYn;
    }

    public void setEmergencyYn(String emergencyYn) {
        this.emergencyYn = emergencyYn;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getFullSizeYn() {
        return fullSizeYn;
    }

    public void setFullSizeYn(String fullSizeYn) {
        this.fullSizeYn = fullSizeYn;
    }

    public String getPopupYn() {
        return popupYn;
    }

    public void setPopupYn(String popupYn) {
        this.popupYn = popupYn;
    }

    public String getOrgnGrpCd() {
        return orgnGrpCd;
    }

    public void setOrgnGrpCd(String orgnGrpCd) {
        this.orgnGrpCd = orgnGrpCd;
    }

    public String getTopYn() {
        return topYn;
    }

    public void setTopYn(String topYn) {
        this.topYn = topYn;
    }

    public String getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(String dispSeq) {
        this.dispSeq = dispSeq;
    }

    public Boolean getBackColorYn() {
        return backColorYn;
    }

    public void setBackColorYn(Boolean backColorYn) {
        this.backColorYn = backColorYn;
    }
}