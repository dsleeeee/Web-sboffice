package kr.co.solbipos.sys.board.boardMaster.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BoardMasterVO.java
 * @Description : 시스템관리 > 게시판관리 > 게시판관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.02.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BoardMasterVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 게시판 코드 */
    private String boardCd;

    /** 게시판 명 */
    private String boardNm;

    /** 게시판 구분 */
    private String boardFg;

    /** 답변허용여부 */
    private String answerFg;

    /** 자동승인여부 */
    private String autoApprFg;

    public String getBoardCd() {
        return boardCd;
    }

    public void setBoardCd(String boardCd) {
        this.boardCd = boardCd;
    }

    public String getBoardNm() { return boardNm; }

    public void setBoardNm(String boardNm) { this.boardNm = boardNm; }

    public String getBoardFg() {
        return boardFg;
    }

    public void setBoardFg(String boardFg) { this.boardFg = boardFg; }

    public String getAnswerFg() { return answerFg; }

    public void setAnswerFg(String answerFg) { this.answerFg = answerFg; }

    public String getAutoApprFg() { return autoApprFg; }

    public void setAutoApprFg(String autoApprFg) { this.autoApprFg = autoApprFg; }
}