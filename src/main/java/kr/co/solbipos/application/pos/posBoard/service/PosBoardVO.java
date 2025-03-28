package kr.co.solbipos.application.pos.posBoard.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PosBoardVO.java
 * @Description : POS 화면에서 게시판(포스용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosBoardVO extends PageVO {

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

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

    /** 열람구분 */
    private String gubunReadCombo;

    /** 열람구분(포스에서 받는 수신여부) */
    private String readYn;

    /** 공지팝업 여부(미열람 공지사항 띄움) */
    private String noticePopupYn;

    /** 접속자 ID */
    private String posUserId;

    /** 메뉴코드 */
    private String menuCd;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

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

    public String getReadYn() { return readYn; }

    public void setReadYn(String readYn) {
        this.readYn = readYn;
    }

    public String getNoticePopupYn() { return noticePopupYn; }

    public void setNoticePopupYn(String noticePopupYn) {
        this.noticePopupYn = noticePopupYn;
    }

    public String getPosUserId() {
        return posUserId;
    }

    public void setPosUserId(String posUserId) {
        this.posUserId = posUserId;
    }

    public String getMenuCd() {
        return menuCd;
    }

    public void setMenuCd(String menuCd) {
        this.menuCd = menuCd;
    }
}