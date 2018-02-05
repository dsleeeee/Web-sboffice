package kr.co.solbipos.application.domain.cmm;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuUseHist extends Cmm {

    private static final long serialVersionUID = 1L;
    
    /** 순서 */
    private String seq;

    /** 사용자 아이디 */
    private String userId;

    /** 리소스 코드 */
    private String resrceCd;

    /** 사용 일자 */
    private String useDate;

    /** 사용 일시 */
    private String useDt;
}
