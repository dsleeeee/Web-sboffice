package kr.co.solbipos.adi.board.board.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BoardVO.java
 * @Description : 부가서비스 > 게시판 > 일반게시판
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.02.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BoardVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

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

    /** 조회매장 */
    private String storeCd;

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

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }
}