package kr.co.solbipos.adi.board.library.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : LibraryVO.java
 * @Description : 부가서비스 > 자료실 > 자료실
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class LibraryVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 게시판코드 */
    private String boardCd;

    /** 조회구분 */
    private String gubunCombo;

    /** 조회구분 검색어 */
    private String gubunName;

    /** 게시일련번호 */
    private String boardSeqNo;

    /** 파일 경로 */
    private String filePath;

    /** 원본 파일 명 */
    private String orginlFileNm;

    /** 파일 명 */
    private String fileNm;

    /** 파일확장자 */
    private String fileExt;

    /** 인덱스 */
    private String idx;

    /** 게시등록자 이름 */
    private String userNm;

    /** 사용자ID */
    private String userId;

    /** 제목 */
    private String title;

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

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getOrginlFileNm() { return orginlFileNm; }

    public void setOrginlFileNm(String orginlFileNm) { this.orginlFileNm = orginlFileNm; }

    public String getFileNm() { return fileNm; }

    public void setFileNm(String fileNm) { this.fileNm = fileNm; }

    public String getFileExt() { return fileExt; }

    public void setFileExt(String fileExt) { this.fileExt = fileExt; }

    public String getIdx() { return idx; }

    public void setIdx(String idx) { this.idx = idx; }

    public String getUserNm() { return userNm; }

    public void setUserNm(String userNm) { this.userNm = userNm; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
}