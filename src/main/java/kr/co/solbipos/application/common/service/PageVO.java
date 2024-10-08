package kr.co.solbipos.application.common.service;

/**
 * @Class Name : PageVO.java
 * @Description : 페이징 테이블 공통 도메인 클래스
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PageVO extends CmmVO {

    private static final long serialVersionUID = -7409076830553634318L;
    /** 기본페이징 사이즈 */
    private static final int DEFAULT_PAGE_SCALE = 10;
    /** 기본리스트 사이즈 */
    private static final int DEFAULT_LIST_SCALE = 10;
    /** 레코드의 총 갯수 */
    private Integer totCnt = 0;
    /** 총 페이지 갯수 */
    private Integer totalPage;
    /** 보여지는 페이지그룹의 페이지 갯수 */
    private Integer pageScale = DEFAULT_PAGE_SCALE;
    /** 한 페이지에 표시되는 레코드 갯수 */
    private Integer listScale = DEFAULT_LIST_SCALE;
    /** 현재 페이지 */
    private Integer curr = 1;
    /** 다음 보여질 페이지그룹의 첫 번째 페이지 */
    private Integer limit;
    /** 이전 보여질 페이지그룹의 마지막 페이지 */
    private Integer offset;
    /** 검색 시작 날짜 */
    private String startDate;
    /** 검색 종료 날짜 */
    private String endDate;
    /** 검색 날짜 전체 체크 여부 */
    private Boolean isChkDate;

    /** 페이지 초기화 여부 */
    private Boolean isPageChk;

	public Integer getTotCnt() {
        return totCnt;
    }

    public void setTotCnt(Integer totCnt) {
        this.totCnt = totCnt;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageScale() {
        return pageScale;
    }

    public void setPageScale(Integer pageScale) {
        this.pageScale = pageScale;
    }

    public Integer getListScale() {
        return listScale;
    }

    public void setListScale(Integer listScale) {
        this.listScale = listScale;
        prevNext();
    }

    public Integer getCurr() {
        return curr;
    }

    public void setCurr(Integer curr) {
        this.curr = curr;
        prevNext();
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsChkDate() {
        return isChkDate;
    }

    public void setIsChkDate(Boolean isChkDate) {
        this.isChkDate = isChkDate;
    }

    public void prevNext() {
        offset = ((curr - 1) * listScale) + 1;
        limit = curr * listScale;
    }

    public void setTotalCount(Integer totCnt) {
        this.totCnt = totCnt;
        calc();
    }

    public void calc() {
        totalPage = totCnt <= 0 ? 1 : (int) Math.ceil((double) totCnt / listScale);
        offset = Math.max(1, curr - 1 - ((curr - 1) % pageScale));
        limit = Math.min(totalPage,
                curr + 1 + (pageScale - (curr % pageScale > 0 ? curr % pageScale : pageScale)));
    }

    public Boolean getIsPageChk() {
		return isPageChk;
	}

	public void setIsPageChk(Boolean isPageChk) {
		this.isPageChk = isPageChk;
		if (!isPageChk) {
    		this.setCurr(1);
    	}
	}

}
