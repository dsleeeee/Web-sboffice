package kr.co.solbipos.application.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchType extends BaseDomain {
    /**  */
    private static final long serialVersionUID = 1L;

    /** 검색 구분 */
    String searchType;

    /** 검색어 */
    String searchText;
}
