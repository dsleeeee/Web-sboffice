package kr.co.solbipos.application.session.user.service;

import kr.co.solbipos.application.common.service.CmmVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Class Name : OtpAuthVO.java
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
public class OtpAuthVO extends CmmVO {

    private static final long serialVersionUID = 1L;

    /** 순서 */
    private String seq;

    /** 사용자 아이디 */
    private String userId;

    /** 인증 구분(904) */
    private String authFg;

    /** 인증 번호 */
    private String authNo;

    /** 수신 휴대폰 번호 */
    private String recvMpNo;

    /** 요청 IP */
    private String reqIp;

    /** 요청 일자 */
    private String reqDate;

    /** 요청 일시 */
    private String reqDt;

    /** 요청 일시 + 분 */
    private int otpLimit;
}
