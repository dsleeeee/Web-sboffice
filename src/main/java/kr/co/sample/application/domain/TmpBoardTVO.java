package kr.co.sample.application.domain;

public class TmpBoardTVO {
    
    private String boardSeqNo;
    private String boardTitle;
    private String boardContents;
    
    
    /**
     * @return the boardSeqNo
     */
    public String getBoardSeqNo() {
        return boardSeqNo;
    }
    /**
     * @param boardSeqNo the boardSeqNo to set
     */
    public void setBoardSeqNo(String boardSeqNo) {
        this.boardSeqNo = boardSeqNo;
    }
    /**
     * @return the boardTitle
     */
    public String getBoardTitle() {
        return boardTitle;
    }
    /**
     * @param boardTitle the boardTitle to set
     */
    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }
    /**
     * @return the boardContents
     */
    public String getBoardContents() {
        return boardContents;
    }
    /**
     * @param boardContents the boardContents to set
     */
    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }
    
}
