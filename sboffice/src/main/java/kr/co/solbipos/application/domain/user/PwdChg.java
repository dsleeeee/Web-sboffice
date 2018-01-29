package kr.co.solbipos.application.domain.user;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.BaseDomain;
import kr.co.solbipos.application.validate.user.PwChange;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PwdChg extends BaseDomain {

    private static final long serialVersionUID = 1L;
    
    @NotBlank( groups={ PwChange.class }, message = "{label.login.userPasswd}{msg.cmm.require.text}" )
    @Size( groups={ PwChange.class }, min = 4, max = 25, message = "{msg.cmm.size.max}" )
    private String newPw;
    
    @NotBlank( groups={ PwChange.class }, message = "{label.login.userPasswd}{msg.cmm.require.text}" )
    @Size( groups={ PwChange.class }, min = 4, max = 25, message = "{msg.cmm.size.max}" )
    private String newPwConf;
    
    private String uuid;
    
    private String userId;
    private String orginPwd;
    private String halfId;
}
