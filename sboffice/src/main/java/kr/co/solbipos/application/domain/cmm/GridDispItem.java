package kr.co.solbipos.application.domain.cmm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GridDispItem extends Cmm {
    
    private static final long serialVersionUID = 1L;

    /** 사용자 아이디 */
    private String userId;

    /** 리소스 코드 */
    private String resrceCd;

    /** 그리드 인덱스 */
    private Long gridIdx;

    /** 컬럼 항목 */
    private String columnItem;

}
