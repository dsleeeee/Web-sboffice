package kr.co.solbipos.application.domain.login;

import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.application.enums.login.LoginOrigin;
import kr.co.solbipos.application.enums.login.LoginResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 로그인 사용 기록 저장<br>
 * table : TB_WB_LOGIN_HIST
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginHist extends Cmm {

    private static final long serialVersionUID = 1L;

    /** 순서 */
    private String seq;

    /** 사용자 아이디 */
    private String userId;

    /** 상태 코드 */
    private LoginResult statCd;

    /** 로그인 소속 */
    private LoginOrigin loginOrgn;

    /** 로그인 IP */
    private String loginIp;

    /** 브라우저 정보 */
    private String brwsrInfo;

    /** 로그인 일자 */
    private String loginDate;

    /** 로그인 일시 */
    private String loginDt;
}
