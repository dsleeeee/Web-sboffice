package kr.co.solbipos.application.domain.user;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.solbipos.application.domain.BaseDomain;
import kr.co.solbipos.application.validate.user.Find;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseDomain {

    private static final long serialVersionUID = 1L;
    
    /** 소속코드 */
    private String orgnCd;

    /** 사원번호 */
    private String empNo;

    /** 사원명 */
    @NotBlank( groups={ Find.class }, message = "{msg.cmm.mpno}{msg.cmm.require.text}" )
    @Size( groups={ Find.class }, max = 10, message = "{msg.cmm.size.max}" )
    private String empNm;

    /** 사원비밀번호 */
    private String empPwd;

    /** 소속구분_A:시스템,C:대리점,H:본사,S:매장 */
    private String orgnFg;

    /** 웹사용여부 */
    private String webUseYn;

    /** 사용자아이디 */
    private String userId;

    /** 휴대폰번호 */
    @NotBlank( groups={ Find.class }, message = "{label.login.userId}{msg.cmm.require.text}" )
    @Size( groups={ Find.class }, max = 15, message = "{msg.cmm.size.max}" )
    private String mpNo;

    /** 이메일주소 */
    private String emailAddr;

    /** 우편번호 */
    private String postNo;

    /** 주소 */
    private String addr;

    /** 주소상세 */
    private String addrDtl;

    /** 고용구분_1:재직,2:휴직,9:퇴직 */
    private String hireFg;

    /** SMS수신여부 */
    private String smsRecvYn;

    /** 사용여부 */
    private String useYn;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

}
