package kr.co.solbipos.application.domain.cmm;

import lombok.Data;

@Data
public class Page {

    private static final int DEFAULT_PAGE_SCALE = 10;

    /** 레코드의 총 갯수 */
    Integer totalCount = 0;
    /** 총 페이지 갯수 */
    Integer totalPage;
    /** 보여지는 페이지그룹의 페이지 갯수 */
    Integer pageScale;
    /** 한 페이지에 표시되는 레코드 갯수 */
    Integer listScale;
    /** 현재 페이지 */
    Integer curr;
    /** 다음 보여질 페이지그룹의 첫 번째 페이지 */
    Integer next;
    /** 이전 보여질 페이지그룹의 마지막 페이지 */
    Integer prev;
    /**  */
    private String startDt;
    /**  */
    private String endDt;
    /**   */
    private Boolean chkDt;
    
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public void setCurr(Integer curr) {
        this.curr = curr;
        prevNext();
    }

    public void setListScale(Integer listScale) {
        this.listScale = listScale;
        prevNext();
    }

    public void prevNext() {
        if (listScale != null && curr != null) {
            prev = ((curr - 1) * listScale) + 1;
            next = curr * listScale;
        }
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        calc();
    }

    public void calc() {
        if (pageScale == null) {
            pageScale = DEFAULT_PAGE_SCALE;
        }
        if (curr < 0) {
            curr = 1;
        }
        totalPage = totalCount < 0 ? 1 : (int) Math.ceil((double) totalCount / listScale);
        prev = Math.max(1, curr - 1 - ((curr - 1) % pageScale));
        next = Math.min(totalPage,
                curr + 1 + (pageScale - (curr % pageScale > 0 ? curr % pageScale : pageScale)));
    }
}
