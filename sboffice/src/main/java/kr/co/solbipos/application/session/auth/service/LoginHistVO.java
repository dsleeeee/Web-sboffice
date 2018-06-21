package kr.co.solbipos.application.session.auth.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.session.auth.enums.LoginOrigin;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : LoginHistVO.java
 * @Description : 어플리케이션 > 세션 > 인증
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
public class LoginHistVO extends CmmVO {

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
