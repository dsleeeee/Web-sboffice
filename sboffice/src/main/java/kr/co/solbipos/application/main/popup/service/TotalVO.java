package kr.co.solbipos.application.main.popup.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 게시판 조회용 (TB_WB_BOARD_INFO)
 * 
 * @author 김지은
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TotalVO extends CmmVO {

    private static final long serialVersionUID = 1L;
    
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

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아아디 */
    private String modId;
}
