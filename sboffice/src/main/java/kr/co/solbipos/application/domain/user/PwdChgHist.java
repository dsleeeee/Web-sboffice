package kr.co.solbipos.application.domain.user;

import kr.co.solbipos.application.domain.cmm.Cmm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PwdChgHist extends Cmm {

    private static final long serialVersionUID = 1L;
    
    /** 사용자 아이디 */
    private String userId;

    /** 인덱스 */
    private Long idx;

    /** 이전 비밀번호 */
    private String priorPwd;

    /** 등록 IP */
    private String regIp;

    /** 등록 일시 */
    private String regDt;

    /** 등록 아이디 */
    private String regId;
}
