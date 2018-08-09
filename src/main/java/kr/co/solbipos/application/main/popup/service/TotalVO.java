package kr.co.solbipos.application.main.popup.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
* @Class Name : TotalVO.java
* @Description : 게시판 조회용 (TB_WB_BOARD_INFO)
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.06.01  김지은      최초생성
*
* @author 솔비포스 차세대개발실 김지은
* @since 2018. 05.01
* @version 1.0
* @see
*
* @Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class TotalVO extends CmmVO {

    private static final long serialVersionUID = 6709089925312941257L;
    /** 게시판 코드 */
    private String boardCd;
    /** 게시판 구분 */
    private String boardFg;
    /** 시작 일자 */
    private String startDate;
    /** 종료 일자 */
    private String endDate;
    /** 제목 */
    private String title;
    /** 공지 여부 */
    private String noticeYn;
    /** 승인 구분 */
    private String apprFg;
    /** 대상 구분 */
    private String targetFg;
    /** 대상 일부 구분 */
    private String targetPartFg;
    /** 대상 부문 */
    private String targetSect;
    /** 대상 팀 */
    private String targetTeam;
    /** 조회 건수 */
    private String viewCnt;
    /** 댓글 건수 */
    private String answerCnt;
    /** 사용 여부 */
    private String useYn;
    /** 내용 */
    private String content;
    
    
    /**
     * @return the boardCd
     */
    public String getBoardCd() {
        return boardCd;
    }
    /**
     * @param boardCd the boardCd to set
     */
    public void setBoardCd(String boardCd) {
        this.boardCd = boardCd;
    }
    /**
     * @return the boardFg
     */
    public String getBoardFg() {
        return boardFg;
    }
    /**
     * @param boardFg the boardFg to set
     */
    public void setBoardFg(String boardFg) {
        this.boardFg = boardFg;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the noticeYn
     */
    public String getNoticeYn() {
        return noticeYn;
    }
    /**
     * @param noticeYn the noticeYn to set
     */
    public void setNoticeYn(String noticeYn) {
        this.noticeYn = noticeYn;
    }
    /**
     * @return the apprFg
     */
    public String getApprFg() {
        return apprFg;
    }
    /**
     * @param apprFg the apprFg to set
     */
    public void setApprFg(String apprFg) {
        this.apprFg = apprFg;
    }
    /**
     * @return the targetFg
     */
    public String getTargetFg() {
        return targetFg;
    }
    /**
     * @param targetFg the targetFg to set
     */
    public void setTargetFg(String targetFg) {
        this.targetFg = targetFg;
    }
    /**
     * @return the targetPartFg
     */
    public String getTargetPartFg() {
        return targetPartFg;
    }
    /**
     * @param targetPartFg the targetPartFg to set
     */
    public void setTargetPartFg(String targetPartFg) {
        this.targetPartFg = targetPartFg;
    }
    /**
     * @return the targetSect
     */
    public String getTargetSect() {
        return targetSect;
    }
    /**
     * @param targetSect the targetSect to set
     */
    public void setTargetSect(String targetSect) {
        this.targetSect = targetSect;
    }
    /**
     * @return the targetTeam
     */
    public String getTargetTeam() {
        return targetTeam;
    }
    /**
     * @param targetTeam the targetTeam to set
     */
    public void setTargetTeam(String targetTeam) {
        this.targetTeam = targetTeam;
    }
    /**
     * @return the viewCnt
     */
    public String getViewCnt() {
        return viewCnt;
    }
    /**
     * @param viewCnt the viewCnt to set
     */
    public void setViewCnt(String viewCnt) {
        this.viewCnt = viewCnt;
    }
    /**
     * @return the answerCnt
     */
    public String getAnswerCnt() {
        return answerCnt;
    }
    /**
     * @param answerCnt the answerCnt to set
     */
    public void setAnswerCnt(String answerCnt) {
        this.answerCnt = answerCnt;
    }
    /**
     * @return the useYn
     */
    public String getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
}
