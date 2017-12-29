package kr.co.solbipos.application.domain.cmm;

import kr.co.solbipos.application.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Cmm extends BaseDomain {

    private static final long serialVersionUID = 1L;
    
    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;

    /** 수정 일시 */
    private String modDt;

    /** 수정 아이디 */
    private String modId;
}
