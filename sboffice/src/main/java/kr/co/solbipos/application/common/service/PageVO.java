package kr.co.solbipos.application.common.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Class Name : PageVO.java
 * @Description : 페이징 테이블 공통 도메인 클래스
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PageVO extends CmmVO {

    private static final long serialVersionUID = -2149669246783003251L;
    /** 기본페이징 사이즈 */
    private static final int DEFAULT_PAGE_SCALE = 10;
    /** 기본리스트 사이즈 */
    private static final int DEFAULT_LIST_SCALE = 15;
    /** 레코드의 총 갯수 */
    Integer totCnt = 0;
    /** 총 페이지 갯수 */
    Integer totalPage;
    /** 보여지는 페이지그룹의 페이지 갯수 */
    Integer pageScale;
    /** 한 페이지에 표시되는 레코드 갯수 */
    Integer listScale;
    /** 현재 페이지 */
    Integer curr;
    /** 다음 보여질 페이지그룹의 첫 번째 페이지 */
    Integer limit;
    /** 이전 보여질 페이지그룹의 마지막 페이지 */
    Integer offset;
    /** 검색 시작 날짜 */
    private String startDt;
    /** 검색 종료 날짜 */
    private String endDt;
    /** 검색 날짜 전체 체크 여부 */
    private Boolean chkDt;
    
    /** VO내의 모든 값 출력 */
    public String getProperties() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    
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
    public String getStartDt() {
        return startDt;
    }
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    public String getEndDt() {
        return endDt;
    }
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
    public Boolean getChkDt() {
        return chkDt;
    }
    public void setChkDt(Boolean chkDt) {
        this.chkDt = chkDt;
    }
    
    public void prevNext() {
        if ( listScale != null && curr != null ) {
            offset = ((curr - 1) * listScale) + 1;
            limit = curr * listScale;
        }
    }

    public void setTotalCount(Integer totCnt) {
        this.totCnt = totCnt;
        calc();
    }

    public void calc() {
        if ( pageScale == null ) {
            pageScale = DEFAULT_PAGE_SCALE;
        }
        if ( curr < 0 || curr == null ) {
            curr = 1;
        }
        if ( listScale == null ) {
            listScale = DEFAULT_LIST_SCALE;
        }
        
        totalPage = totCnt < 0 ? 1 : (int) Math.ceil((double) totCnt / listScale);
        offset = Math.max(1, curr - 1 - ((curr - 1) % pageScale));
        limit = Math.min(totalPage,
                curr + 1 + (pageScale - (curr % pageScale > 0 ? curr % pageScale : pageScale)));
    }
    
}
