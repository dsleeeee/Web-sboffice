package kr.co.solbipos.application.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public @Data class Page {

    private static final int DEFAULT_PAGE_SCALE = 10;
    private static final int DEFAULT_LIST_SCALE = 20;
    private static final int DEFAULT_CURR = 1;

    Integer totalCount; // 레코드의 총 갯수
    Integer totalPage; // 총 페이지 갯수
    Integer pageScale; // 보여지는 페이지그룹의 페이지 갯수
    Integer listScale; // 한 페이지에 표시되는 레코드 갯수
    Integer curr; // 현재 페이지
    Integer next; // 다음 보여질 페이지그룹의 첫 번째 페이지
    Integer prev; // 이전 보여질 페이지그룹의 마지막 페이지

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
            prev = (curr - 1) * listScale;
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

        if (curr < 0)
            curr = 1;

        totalPage = totalCount < 0 ? 1 : (int) Math.ceil((double) totalCount / listScale);
        prev = Math.max(1, curr - 1 - ((curr - 1) % pageScale));
        next = Math.min(totalPage,
                curr + 1 + (pageScale - (curr % pageScale > 0 ? curr % pageScale : pageScale)));

        log.debug(toString());
    }

    @Override
    public String toString() {
        return String.format(
                "totPage: %s, listScale: %s, pageScale: %s, curr: %s, prev: %s, next: %s",
                totalPage, listScale, pageScale, curr, prev, next);
    }
}
