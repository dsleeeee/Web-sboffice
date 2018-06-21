package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : PwdChgHistVO.java
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
public class PwdChgHistVO extends CmmVO {

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
