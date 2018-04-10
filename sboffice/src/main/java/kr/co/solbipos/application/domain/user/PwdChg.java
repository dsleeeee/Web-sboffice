package kr.co.solbipos.application.domain.user;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.cmm.Cmm;
import kr.co.solbipos.application.validate.login.Login;
import kr.co.solbipos.application.validate.user.PwChange;
import kr.co.solbipos.application.validate.user.UserPwChange;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 패스워드 변경
 * 
 * @author 정용길
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PwdChg extends Cmm {

    private static final long serialVersionUID = 1L;

    /** 기존 비밀번호 */
    @NotBlank(groups = {UserPwChange.class, Login.class},
            message = "{login.userPasswd}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    private String currentPw;

    @NotBlank(groups = {PwChange.class, UserPwChange.class},
            message = "{login.pw.chg.new}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    /** 새 비밀번호 */
    private String newPw;

    @NotBlank(groups = {PwChange.class, UserPwChange.class},
            message = "{login.pw.chg.new}{cmm.require.text}")
    @Size(groups = {PwChange.class}, min = 8, max = 25, message = "{cmm.size.max}")
    /** 새 비밀번호 확인 */
    private String newPwConf;

    private String uuid;

    /** 사용자 아이디 */
    @NotBlank( groups={ Login.class }, message = "{login.userId}{cmm.require.text}" )
    @Size( groups={ Login.class }, max = 20, message = "{cmm.size.max}" )
    private String userId;
    
    private String orginPwd;
    private String halfId;
}
