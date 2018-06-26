package kr.co.solbipos.application.common.service;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = -2149669246783003251L;
    private static final int DEFAULT_PAGE_SCALE = 10;

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
            offset = ((curr - 1) * listScale) + 1;
            limit = curr * listScale;
        }
    }

    public void setTotalCount(Integer totCnt) {
        this.totCnt = totCnt;
        calc();
    }

    public void calc() {
        if (pageScale == null) {
            pageScale = DEFAULT_PAGE_SCALE;
        }
        if (curr < 0) {
            curr = 1;
        }
        
        totalPage = totCnt < 0 ? 1 : (int) Math.ceil((double) totCnt / listScale);
        offset = Math.max(1, curr - 1 - ((curr - 1) % pageScale));
        limit = Math.min(totalPage,
                curr + 1 + (pageScale - (curr % pageScale > 0 ? curr % pageScale : pageScale)));
    }
    
    /** VO내의 모든 값 출력 */
    public String getProperties() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
