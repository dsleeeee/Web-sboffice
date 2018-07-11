package kr.co.solbipos.application.session.user.service;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.common.validate.Login;
import kr.co.common.validate.PwChange;
import kr.co.common.validate.UserPwChange;
import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : PwdChgVO.java
 * @Description : 어플리케이션 > 세션 > 사용자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PwdChgVO extends CmmVO {
    
    private static final long serialVersionUID = -4842995979467618739L;
    
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
